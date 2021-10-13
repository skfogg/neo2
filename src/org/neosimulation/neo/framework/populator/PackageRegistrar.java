package org.neosimulation.neo.framework.populator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.neosimulation.neo.framework.behcurr.Behavior;
import org.neosimulation.neo.framework.behcurr.Currency;

/**
 * ClassPathLoader - Utility class which provides static methods used to
 * register a Jar or Directory structure containing classes that need to be
 * loaded dynamically at runtime.
 * 
 * @author Isaac Griffith
 */
public class PackageRegistrar {

    /**
     * Registers the Jar file specified by the File parameter as a location to
     * search for classes by the JVM System classloader
     * 
     * @param jarFile
     *            File representing the Jar file to be searched for classes to
     *            be loaded by the System Classloader
     * @throws IOException
     *             thrown if the Jar file does not exist or is inaccessible
     */
    public static void registerJar(Currency currency, File jarFile) throws IOException
    {
        URL jarURL = jarFile.toURI().toURL();
        NEOClassLoader loader = currency.getClassLoader();
        if (loader == null)
        {
            URL[] temp = { jarURL };
            loader = new NEOClassLoader(temp, ClassLoader.getSystemClassLoader());
            currency.setClassLoader(loader);
        }
        else
        {
            loader.addJar(jarURL);
        }
    }

    /**
     * Method used to load all classes in a provided directory structure into
     * the class path
     * 
     * @param directory
     *            root directory of the package for which classes are to be
     *            loaded
     * @throws IOException
     *             if there is a problem loading the classes from the directory
     */
    public static void registerDirectory(Currency currency, File directory) throws IOException
    {
        NEOClassLoader loader = currency.getClassLoader();

        if (!directory.isDirectory())
        {
            Logger.getLogger("PackageRegistrar").log(Level.SEVERE,
                    "Currency Package Location is invalid, must be a directory or Jar file");
            return;
        }
        else
        {
            List<URL> urls = new ArrayList<URL>();
            // generateDirectoryListing(directory, urls);
            urls.add(directory.toURI().toURL());

            if (loader == null)
            {
                URL[] temp = new URL[urls.size()];
                urls.toArray(temp);

                loader = new NEOClassLoader(temp, ClassLoader.getSystemClassLoader());
                currency.setClassLoader(loader);
            }
            else
            {
                loader.addURLs(urls);
            }
        }
    }

    /**
     * Recursive method used to extract all the class names in the provided
     * directory and add them to the provided list in URL format
     * 
     * @param directory
     *            File specifying the directory location to be listed
     * @param urls
     *            List of URLs for class files within the directory specified by
     *            the provided File
     * @throws IOException
     *             Thrown if the provided file does not exist or is inaccessible
     */
    private static void generateDirectoryListing(File directory, List<URL> urls) throws IOException
    {
        // check whether the file is a directory or not. If it is recursively
        // call this
        // method on each file, else if the file is a class file add the url of
        // that
        // file to the list of urls. Otherwise, do nothing.
        if (directory.isDirectory())
        {
            File[] files = directory.listFiles();
            for (File file : files)
            {
                generateDirectoryListing(file, urls);
            }
        }
        else if (directory.getPath().endsWith(".class"))
        {
            urls.add(directory.toURI().toURL());
        }
    }
}
