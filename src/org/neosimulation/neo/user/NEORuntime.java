/**
 * 
 */
package org.neosimulation.neo.user;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.neosimulation.neo.framework.NEOException;
import org.neosimulation.neo.framework.config.NEOContext;
import org.neosimulation.neo.framework.manager.ModelManager;

/**
 * NEORuntime - This class represents the access point for user models to
 * extend. It connects the Model Management system, Simulation Model,
 * NEOContext, and Outputter.
 * 
 * @author Isaac Griffith
 */
public class NEORuntime {

    /**
     * NEOContext housing the necessary information used to generate simulation
     * models based on the user's underlying model code
     */
    private NEOContext context;

    /**
     * Constructs a new NEORuntime instance
     * 
     * @param args
     *            Command line arguments
     * @throws NEOException
     */
    public NEORuntime(String args[]) throws NEOException
    {
        // this(NEOModelManager.getInstance(), args);
        this.context = new NEOContext();

        ModelManager manager = context.getManager();
        beginProcessing(args, manager);
    }

    /**
     * Constructs a new NEORuntime instance using the provided type of
     * ModelManager
     * 
     * @param manager
     *            The specific ModelManager instance to be used
     * @param args
     *            Command line arguments
     * @throws NEOException
     */
    public NEORuntime(ModelManager manager, String args[]) throws NEOException
    {
        this.context = new NEOContext(manager, false);

        beginProcessing(args, manager);
    }

    /**
     * Begins the processing, of the context associated with this NEORuntime, by
     * the ModelManager.
     * 
     * @param args
     *            Command line arguments
     * @param manager
     *            The specific ModelManger instance to be used
     * @throws NEOException
     */
    private void beginProcessing(String args[], ModelManager manager) throws NEOException
    {
        File modelBase = getModelBase();

        newModel(args, modelBase, manager);
        manager.addContext(context);
        manager.process();
    }

    /**
     * This method is used to direct the model manager to load model specific
     * resources, and to extract the basic model id to which the associated
     * information in the args is to be stored in this context
     * 
     * @param args
     *            command line arguments
     * @param modelBase
     *            File object representing the root directory of the model
     * @param manager
     *            The specific instance of ModelManager to use
     */
    public void newModel(String[] args, File modelBase, ModelManager manager)
    {
        manager.loadModelResources(modelBase, context);

        if (args != null)
        {
            Map<String, String> temp = new HashMap<>();

            fillInHashMap(args, temp);
            context.setContextInfo(temp);
        }
    }

    /**
     * Method to fill in a provided hashmap using an array of strings. This
     * assumes that the provided array of strings contains n/2 key value pairs
     * and that the keys are in the same format as keys in the control table,
     * except that they begin with an '-'
     * 
     * @param args
     *            the arguments passed in from the command line
     * @param map
     *            Output HashMap containing argument name/value pairs
     */
    private void fillInHashMap(String[] args, Map<String, String> map)
    {
        if (args.length <= 1)
        {
            return;
        }
        else
        {
            for (int i = 0; i < args.length; i += 2)
            {
                String key = args[i].substring(1);
                String value = args[i + 1];

                map.put(key, value);
            }
        }
    }

    /**
     * Assumes that the directory in which java was invoked will be the
     * directory in which the model configuration info is stored.
     * 
     * @return File representation of the directory in which java was invoke,
     *         assuming that this is the model root directory
     */
    private File getModelBase() throws NEORuntimeException
    {
        String currDir = System.getProperty("user.dir");

        File retVal = new File(currDir);
        if (retVal.exists() && retVal.isDirectory())
        {
            return retVal;
        }
        else
        {
            throw new NEORuntimeException("The Model Root: " + currDir + " is not a valid directory or does not exist.");
        }
    }
}
