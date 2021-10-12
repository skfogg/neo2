/**
 * 
 */
package org.neosimulation.neo.framework.config;

import java.util.HashMap;
import java.util.Map;

/**
 * AppInstance - A class used to encapsulate the runtime information necessary
 * to reflectively instantiate an instance of a Class that extends NEOApp. This
 * class encapsulates the apps full class name, an id for the instance, and the
 * arguments (if any) which are passed into the constructor of the app.
 * 
 * @author Isaac Griffith
 */
public class AppInstance {

    /**
     * Unique ID for this instance (from the perspective of the associated
     * SimulationModel)
     */
    private String instanceID;
    /**
     * Full class name of the app.
     */
    private String appClassName;
    /**
     * Argument Map, where the key is the name of the argument, and the value is
     * the value for that argument. This is to be passed to the constructor of
     * the app when instantiated.
     */
    private Map<String, String> arguments;

    /**
     * Creates a new AppInstance to encapsulate information for instantiating a
     * NEOApp
     * 
     * @param id
     *            name of this instance
     * @param name
     *            class name of the app
     */
    public AppInstance(String id, String name)
    {
        instanceID = id;
        appClassName = name;

        arguments = new HashMap<>();
    }

    /**
     * Creates a new empty app instance
     */
    public AppInstance()
    {
        this("", "");
    }

    /**
     * Adds an argument to the argument map
     * 
     * @param key
     *            Name of the argument
     * @param value
     *            Value of the argument
     */
    public void addArgument(String key, String value)
    {
        arguments.put(key, value);
    }

    /**
     * Sets the name of the main class to be loaded
     * 
     * @param name
     *            New name of the class to be instantiated
     */
    public void setClassName(String name)
    {
        appClassName = name;
    }

    /**
     * Sets the instance id for the app
     * 
     * @param id
     *            new id for this instance of the app
     */
    public void setID(String id)
    {
        instanceID = id;
    }

    /**
     * Retrieves the class name of the main class of the app to be loaded
     * 
     * @return The class name of the app to be instantiated
     */
    public String getClassName()
    {
        return appClassName;
    }

    /**
     * Retrieves the instance ID of this app instance object.
     * 
     * @return The ID of the instance herein encapsulated
     */
    public String getID()
    {
        return instanceID;
    }
}
