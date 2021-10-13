/**
 * 
 */
package org.neosimulation.neo.framework.populator;

import java.io.Serializable;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.List;

/**
 * NEOClassLoader - ClassLoader for the NEO Framework which provides the means
 * to dynamically load the classes of a model's packages at runtime.
 * 
 * @author Isaac Griffith
 */
public class NEOClassLoader extends URLClassLoader implements Serializable {

    /**
     * Constructs a new NEOClassLoader for the provided URLs and with the
     * provided parent ClassLoader. This ClassLoader uses the provided
     * URLStreamHandlerFactor to read the URL streams.
     * 
     * @param urls
     *            URLs representing the classes to be loaded
     * @param parent
     *            Parent ClassLoader to be used to find classes that have
     *            already been loaded
     * @param factory
     *            URLStreamHandlerFactory to be used to read from the URLs
     */
    public NEOClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory)
    {
        super(urls, parent, factory);
    }

    /**
     * Constructs a new NEOClassLoader for the provided URLs with the provided
     * parent ClassLoader
     * 
     * @param urls
     *            URLs representing the classes to be loaded
     * @param parent
     *            Parent ClassLoader to be used to find classes that have
     *            already been loaded
     */
    public NEOClassLoader(URL[] urls, ClassLoader parent)
    {
        super(urls, parent);
    }

    /**
     * Constructs a new NEOClassLoader for the provided URLs
     * 
     * @param urls
     *            URLs representing the classes to be loaded
     */
    public NEOClassLoader(URL[] urls)
    {
        super(urls);
    }

    /**
     * Adds a JAR file to be loaded by this ClassLoader
     * 
     * @param file
     *            File representing an entire JAR compressed package to be
     *            loaded
     */
    public void addJar(URL file)
    {
        this.addURL(file);
    }

    /**
     * Adds a list of URLs to be loaded by this ClassLoader
     * 
     * @param urls
     *            URLs representing the classes to be loaded
     */
    public void addURLs(List<URL> urls)
    {
        for (URL file : urls)
        {
            this.addURL(file);
        }
    }
}
