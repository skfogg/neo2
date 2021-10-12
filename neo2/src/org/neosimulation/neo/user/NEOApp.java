/**
 * 
 */
package org.neosimulation.neo.user;

import java.util.HashMap;

import org.neosimulation.neo.framework.config.NEOContext;
import org.neosimulation.neo.framework.manager.ModelManager;
import org.neosimulation.neo.framework.model.SimulationModel;

/**
 * NEOApp - Provides a base interface upon which all external applications using
 * the NEO framework should be built. This allows the Model Management Subsytem
 * to load, initialize, and execute these apps correctly.
 * 
 * @author Isaac Griffith
 */
public abstract class NEOApp {

    /**
     * The associated model manager from which this NEO App can extract
     * information about the NEO system as a whole.
     */
    protected ModelManager manager;
    /**
     * The associated context from which this NEO App can extract information
     * about its specific model.
     */
    protected NEOContext context;
    /**
     * The instance name of this NEOApp
     */
    protected String name;

    /**
     * Creates and constructs a new instance of a NEOApp connected to the
     * provided ModelManager and NEOContext. It also provides the new instance
     * with a name to which it is referenced within the associated NEOContext.
     * 
     * @param name
     *            Name of this specific instance.
     * @param manager
     *            Provides the app with runtime information about the NEO
     *            system.
     * @param context
     *            Provides the app with model specific information.
     */
    public NEOApp(String name, ModelManager manager, NEOContext context)
    {
        this.manager = manager;
        this.context = context;
        this.name = name;
    }

    /**
     * Retrieves the current name of this NEOApp
     * 
     * @return The name of this specific instance of this NEOApp
     */
    public String getName()
    {
        return name;
    }

    /**
     * Initializes this app with the provided arguments.
     * 
     * @param args
     *            Mapping of argument names and values.
     */
    public abstract void init(HashMap<String, String> args);

    /**
     * Connects this app to the provided instance of SimulationModel.
     * 
     * @param model
     *            SimulationModel to which this NEOApp is to be connected to.
     */
    public abstract void execute(SimulationModel model);
}
