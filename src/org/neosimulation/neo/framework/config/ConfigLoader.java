/**
 * 
 */
package org.neosimulation.neo.framework.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.neosimulation.neo.framework.database.ConfigKeys;
import org.neosimulation.neo.framework.database.Key;
import org.neosimulation.neo.framework.database.KeyException;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.manager.ModelManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * ConfigLoader - Used by the ModelManager to load both NEO Framework specific
 * configuration files and locations of apps. It also is used to load model
 * configurations in order to provide the information to create model runs.
 * 
 * @author Isaac Griffith
 */
public class ConfigLoader {

    /**
     * ModelManager to which this ConfigLoader is associated with and which this
     * loader provides information to.
     */
    private ModelManager manager;

    /**
     * Constructs a new ConfigLoader associated with the provided ModelManager
     * 
     * @param manager
     *            ModelManager instance associated with this loader.
     */
    public ConfigLoader(ModelManager manager)
    {
        this.manager = manager;
    }

    /**
     * Method to load the database queries used to extract information to build
     * a model
     */
    private void loadDatabaseQueries()
    {
        Properties prop = new Properties();
        try
        {
            prop.load(ConfigLoader.class.getResourceAsStream("queries.info"));
            manager.setQueries(prop);
        }
        catch (IOException e)
        {
            NEOLogger
                    .logException(
                            manager,
                            "Cannot find/read the properties file which describes the SQL queries used to extract information from the NEO input databases.",
                            e);
        }
    }

    /**
     * Method used to load the config keys, which are used to validate the
     * control table in a model's input database
     */
    private void loadConfigKeys()
    {
        // FIXME Requires Refactoring
        DefaultHandler handler = new DefaultHandler() {
            Stack<Key> parents = new Stack<>();
            ConfigKeys keys = new ConfigKeys();
            Key currentParent = null;

            /*
             * (non-Javadoc)
             * @see
             * org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
             * java.lang.String, java.lang.String, org.xml.sax.Attributes)
             */
            @Override
            public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes)
                    throws SAXException
            {
                if (localName.equals("parent"))
                {
                    String name = attributes.getValue("name");

                    if (name != null)
                    {
                        try
                        {
                            Key parent = new Key(name, currentParent, true);
                            keys.addParent(parent);
                            parents.push(parent);
                            currentParent = parent;
                        }
                        catch (KeyException keyex)
                        {
                            NEOLogger.logException(manager,
                                    "There was a problem loading keys in the config keys XML file. ", keyex);
                        }
                    }
                }
                else if (localName.equals("key"))
                {
                    String name = attributes.getValue("name");

                    if (name != null)
                    {
                        Key key;
                        try
                        {
                            key = new Key(name, currentParent);
                            keys.addKey(key);
                        }
                        catch (KeyException e)
                        {
                            NEOLogger.logException(manager,
                                    "There was a problem loading keys in the config keys XML file. ", e);
                        }
                    }
                }
            }

            /*
             * (non-Javadoc)
             * @see
             * org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
             * java.lang.String, java.lang.String)
             */
            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException
            {
                if (localName.equals("parent"))
                {
                    parents.pop();
                    if (parents.isEmpty())
                        currentParent = null;
                    else
                        currentParent = parents.peek();
                }
            }

            /*
             * (non-Javadoc)
             * @see org.xml.sax.helpers.DefaultHandler#endDocument()
             */
            @Override
            public void endDocument() throws SAXException
            {
                manager.setConfigKeys(keys);
            }
        };

        try
        {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            SAXParser parser = factory.newSAXParser();

            InputStream in = this.getClass().getResourceAsStream("keys.xml");
            parser.parse(in, handler);
        }
        catch (IOException | SAXException iox)
        {
            NEOLogger.logException(manager, "There was a problem accessing or parsing the config keys XML file. ", iox);
        }
        catch (ParserConfigurationException e)
        {
            NEOLogger.logException(manager,
                    "There was a problem configuring the parser to be used to load config keys. ", e);
        }
    }

    /**
     * Method which loads the NEO Apps into the class path based on those found
     * in the in the apps directory under the installation directory of NEO
     */
    private void loadNEOApps(List<String> availableApps)
    {
        // TODO Implement this method
    }

    /**
     * Method which controls the loading of resources used by the framework.
     * These resources are the config keys, available apps, and queries used to
     * load a model.
     */
    public void loadFrameworkResources()
    {
        String frameworkLocation = manager.getFrameworkLocation();
        List<String> frameworkApps = findAvailableApps(frameworkLocation);

        loadNEOApps(frameworkApps);
        loadDatabaseQueries();
        loadConfigKeys();
    }

    /**
     * Method used to search the base directory of the NEO installation for NEO
     * App Jar files. The names of these files are loaded and the main class is
     * found. A list of the fully qualified class names of the class which
     * extends NEO App in each jar is returned.
     * 
     * @param baseLocation
     *            NEO framework installation directory name
     * @return List of fully qualified class names of classes that extend NEO
     *         App
     */
    private List<String> findAvailableApps(String baseLocation)
    {
        List<String> retVal = new ArrayList<>();
        String location = baseLocation + File.separator + "apps" + File.separator;

        try
        {
            retVal = findFiles(location, ".jar");
        }
        catch (ConfigLoaderException e)
        {
            // NEOLogger.logException(manager, e.getMessage(), e);
        }

        return retVal;
    }

    /**
     * Method that controls the loading of a Model's resource. Using the
     * specified model's location (directory on disk) and the associated
     * NEOContext for the model, this method will find the model's config file
     * and begin to load the information necessary to initiate and start the
     * model run(s).
     * 
     * @param modelLocation
     *            Directory where the model is located on disk
     * @param context
     *            NEOContext associated with the model
     */
    public void loadModelResources(String modelLocation, NEOContext context)
    {
        List<String> configs;
        try
        {
            configs = findFiles(modelLocation + File.separator + "configs" + File.separator, ".xml");
            loadConfigXML(context, configs);
        }
        catch (ConfigLoaderException e)
        {
            NEOLogger.logException(context, "Problem occurred while loading model configs.", e);
        }
    }

    /**
     * Constructs and returns a ModelConfig object based on information
     * extracted from the children of the provided modelRoot Element. This newly
     * constructed ModelConfig object is then stored within the provided
     * NEOContext object.
     * 
     * @param context
     *            NEOContext object in which to store the newly created
     *            ModelConfig object.
     * @param modelRoot
     *            Root element of the Model Configuration XML file whose child
     *            elements will be used to provide the necessary information to
     *            create the ModelConfig object.
     * @return The newly constructed ModelConfig object, or null if the XML file
     *         contains no "active" model configs.
     */
    private ModelConfig loadModelConfig(NEOContext context, Element modelRoot)
    {
        // FIXME: Requires Refactoring

        ModelConfig retVal = null;
        String outputterType = "";
        String modelPrefix = "";
        DbConfig dbConfig = null;
        boolean isActive = false;
        boolean useLocal = false;
        String outputConfigName;
        boolean uniqueTables = false;

        String active = modelRoot.getAttributeNode("active").getValue();
        if (active.equals("true"))
        {
            isActive = true;
        }

        NodeList nodes = modelRoot.getElementsByTagName("prefix");
        Element prefix = (Element) nodes.item(0);
        modelPrefix = prefix.getTextContent();

        nodes = modelRoot.getElementsByTagName("output");
        Element output = (Element) nodes.item(0);

        nodes = output.getElementsByTagName("outputter-type");
        Element type = (Element) nodes.item(0);
        outputterType = type.getTextContent();

        nodes = output.getElementsByTagName("use-local");
        Element local = (Element) nodes.item(0);
        useLocal = Boolean.valueOf(local.getTextContent());

        uniqueTables = Boolean.valueOf(getFirstElementContent("unique-tables", output, true));

        try
        {
            retVal = new ModelConfig(modelPrefix, outputterType, useLocal, isActive, uniqueTables);
        }
        catch (ModelConfigException e)
        {
            NEOLogger.logException(context, "Problem loading model config for model: " + modelPrefix, e);
        }

        nodes = output.getElementsByTagName("dbconfig");
        if (nodes.getLength() > 0)
        {
            Element config = (Element) nodes.item(0);
            dbConfig = loadDbConfig(modelPrefix, context, config);

            if (dbConfig != null && dbConfig.isActive())
                retVal.setOutputDbConfig(dbConfig);
        }
        else
        {
            nodes = output.getElementsByTagName("output-dbconfig");
            outputConfigName = getFirstElementContent("output-dbconfig", output, true);
            retVal.setOutputDbConfigName(outputConfigName);
        }

        return retVal;
    }

    /**
     * Method to load the NEOApps to be associated with the provided model
     * prefix.
     * 
     * @param availableApps
     *            List of fully qualified class names of all available NEO App
     *            extensions
     * @param modelPrefix
     *            Prefix of the model associated with the apps to be loaded
     */
    private void loadApps(List<String> availableApps, String modelPrefix)
    {

    }

    /**
     * Searches a given path for files with a provided extension. If no such
     * path exists, it is not a directory, or it cannot be read due to
     * permissions issues then a ConfigLoaderException will be thrown.
     * 
     * @param path
     *            Starting path in which to begin the search for files with the
     *            specified extension.
     * @param extension
     *            The extension of files to be found.
     * @return List of file names matching the extension and found within the
     *         provided path.
     * @throws ConfigLoaderException
     *             Thrown if the provided path is not a directory or cannot be
     *             read.
     */
    private List<String> findFiles(String path, final String extension) throws ConfigLoaderException
    {
        List<String> retVal = new ArrayList<>();
        File location = new File(path);
        if (location.isDirectory() && location.canRead())
        {
            FilenameFilter filter = new FilenameFilter() {

                @Override
                public boolean accept(File dir, String name)
                {
                    if (name.endsWith(extension))
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
            };

            for (File file : location.listFiles(filter))
            {
                retVal.add(file.getPath());
            }

            return retVal;
        }
        else
        {
            throw new ConfigLoaderException();
        }
    }

    /**
     * Loads and Creates a new RunConfig object using the information from a XML
     * model configuration. This method searches the children of the configRoot
     * Element to extract the necessary information to create the RunConfig
     * object. The generated object is then associated with the provided
     * modelPrefix and stored in the provided NEOContext object.
     * 
     * @param modelPrefix
     *            prefix of the model to which this RunConfig is associated.
     * @param context
     *            NEOContext which stores this runConfig.
     * @param configRoot
     *            Root element from which the information is extracted.
     * @return The newly created RunConfig object, or null if the runconfig tag
     *         is not marked as "active."
     */
    private RunConfig loadRunConfig(String modelPrefix, NEOContext context, Element configRoot)
    {
        // FIXME Requires Refactoring
        RunConfig retVal = null;
        List<AppInstance> apps = new ArrayList<>();

        boolean isActive = new Boolean(configRoot.getAttributeNode("active").getValue());
        String runName = getFirstElementContent("name", configRoot, true);
        String solverType = getFirstElementContent("solver-type", configRoot, true);
        String runType = getFirstElementContent("run-type", configRoot, true);
        String inputName = getFirstElementContent("input-dbconfig", configRoot, true);

        NodeList nodes = configRoot.getElementsByTagName("apps");
        if (nodes.getLength() > 0)
        {
            Element appsElement = (Element) nodes.item(0);

            nodes = appsElement.getElementsByTagName("instance");
            for (int i = 0; i < nodes.getLength(); i++)
            {
                Element instance = (Element) nodes.item(i);
                String instanceID = getFirstElementContent("instanceID", instance, true);
                String appName = getFirstElementContent("app-name", instance, true);
                AppInstance app = new AppInstance(instanceID, appName);

                NodeList args = instance.getElementsByTagName("argument");
                for (int j = 0; j < args.getLength(); j++)
                {
                    Element arg = (Element) args.item(j);
                    String key = getFirstElementContent("key", arg, true);
                    String value = getFirstElementContent("value", arg, true);

                    app.addArgument(key, value);
                }

                apps.add(app);
            }
        }

        try
        {
            retVal = new RunConfig(modelPrefix, runName);
            retVal.setAppInstances(apps);
            retVal.setSolverType(solverType);
            retVal.setRunType(runType);
            retVal.setInputDbConfigName(inputName);
            retVal.setDbConfigs(context.getModelDbConfigs(modelPrefix));
            retVal.setActive(isActive);
        }
        catch (RunConfigException e)
        {
            NEOLogger.logException(manager, "There was a problem loading run config: " + runName + " for model: "
                    + modelPrefix + ".", e);
        }

        return retVal;
    }

    /**
     * Creates a DbConfig object by reading the XML of a model config file. It
     * uses the provided config root element to search for the necessary tags of
     * a DbConfig, and once all the necessary information has been collected it
     * generates a DbConfig and installs it into the provided NEOContext and
     * associates it with the provided modelPrefix.
     * 
     * @param modelPrefix
     *            modelPrefix to which the generated DbConfig is to be
     *            associated with in the provided NEOContext object.
     * @param context
     *            NEOContext in which the generated DbConfig object is to be
     *            stored.
     * @param configRoot
     *            Root element in the model config in which to search for the
     *            DbConfig information.
     * @return The newly generated DbConfig object, or null if the dbconfig is
     *         not marked as "active."
     */
    private DbConfig loadDbConfig(String modelPrefix, NEOContext context, Element configRoot)
    {
        boolean isActive = new Boolean(configRoot.getAttributeNode("active").getValue());
        String name = getFirstElementContent("name", configRoot, true);
        String user = getFirstElementContent("username", configRoot, false);
        String pass = getFirstElementContent("password", configRoot, false);
        String url = getFirstElementContent("url", configRoot, true);
        String driver = getFirstElementContent("driver", configRoot, false);

        return new DbConfig(name, url, user, pass, driver, isActive);
    }

    /**
     * Method used to retrieve the text content of a specified XML tag found in
     * the children of the provided root Element. This method will only return
     * the text content of the first child of the root element with the
     * specified tag name. If the boolean required flag is true and no child of
     * the root with the specified tag name can be found a ConfigLoaderError is
     * thrown.
     * 
     * @param tag
     *            Name of the tag to search for
     * @param root
     *            The root element to search under
     * @param required
     *            required flag that determines whether or not to throw a
     *            ConfigLoaderError if the tag cannot be found.
     * @return String representing the text content of the first child node
     *         under the root element with the specified tag name.
     */
    private String getFirstElementContent(String tag, Element root, boolean required)
    {
        NodeList nodes = root.getElementsByTagName(tag);
        String retVal = null;
        if (nodes.getLength() > 0)
        {
            Element element = (Element) nodes.item(0);
            retVal = element.getTextContent();
        }
        else if (required)
        {
            throw new ConfigLoaderError("Model Configuration is missing a required tag: " + tag);
        }

        return retVal;
    }

    /**
     * Method which parses the XML based model configuration files from the
     * provided list of file names and provides that information to the provided
     * NEOContext object.
     * 
     * @param context
     *            NEOContext associated with a model within which the
     *            information found in the files (whose names are found in the
     *            provided list of strings) is to be stored.
     * @param files
     *            List of strings representing the names of XML based model
     *            configuration files associated with a given model
     */
    private void loadConfigXML(NEOContext context, List<String> files)
    {
        // FIXME Requires Refactoring
        for (String config : files)
        {
            String modelPrefix = "";

            File file = new File(config);
            if (file.canRead() && !file.isDirectory())
            {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder;
                ModelConfig modelConfig = null;
                try
                {
                    builder = factory.newDocumentBuilder();
                    Document doc = builder.parse(file);

                    // Load the model config
                    NodeList nodes = doc.getElementsByTagName("model");
                    if (nodes.getLength() > 0)
                    {
                        Element model = (Element) nodes.item(0);
                        modelConfig = loadModelConfig(context, model);
                        if (modelConfig != null && modelConfig.isActive())
                        {
                            context.addModelConfig(modelConfig);
                            modelPrefix = modelConfig.getPrefix();
                        }
                    }

                    // Load DbConfigs and if necessary set the Model Config to
                    // the config with the appropriate name
                    nodes = doc.getElementsByTagName("dbconfig");
                    if (nodes.getLength() > 0)
                    {
                        for (int i = 0; i < nodes.getLength(); i++)
                        {
                            Element element = (Element) nodes.item(i);
                            if (((Element) element.getParentNode()).getTagName().equals("config"))
                            {
                                DbConfig dbConfig = loadDbConfig(modelPrefix, context, element);
                                if (dbConfig != null && dbConfig.isActive())
                                {
                                    try
                                    {
                                        context.addModelDbConfig(modelPrefix, dbConfig);
                                    }
                                    catch (NEOContextException e)
                                    {
                                        NEOLogger.logException(context, e.getMessage(), e);
                                    }

                                    if (modelConfig != null)
                                    {
                                        if (modelConfig.getOutputDbConfig() == null
                                                && modelConfig.getOutputDbConfigName().equals(dbConfig.getName()))
                                        {
                                            modelConfig.setOutputDbConfig(dbConfig);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Load all the run configs
                    nodes = doc.getElementsByTagName("runconfig");
                    if (nodes.getLength() > 0)
                    {
                        for (int i = 0; i < nodes.getLength(); i++)
                        {
                            Element element = (Element) nodes.item(i);
                            RunConfig runConfig = loadRunConfig(modelPrefix, context, element);
                            if (runConfig != null && runConfig.isActive())
                            {
                                try
                                {
                                    context.addModelRunConfig(modelPrefix, runConfig);
                                }
                                catch (NEOContextException e)
                                {
                                    NEOLogger.logException(context, e.getMessage(), e);
                                }
                            }
                        }
                    }
                }
                catch (ParserConfigurationException e)
                {
                    NEOLogger.logException(context,
                            "There was a problem configuring the parser for the model config files. ", e);
                }
                catch (IOException | SAXException e)
                {
                    NEOLogger.logException(context, "There was a problem parsing or accessing the model config file: "
                            + config + " ", e);
                }
            }
        }

        if (context.getAllModelConfigs().size() < 1)
        {
            NEOLogger.logWarning(context,
                    "There were no Available or Active Model Configs, so no model instances could be created.");
        }
    }
}
