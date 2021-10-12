/**
 * 
 */
package org.neosimulation.neo.framework.holon;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.neosimulation.neo.framework.behcurr.Behavior;
import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.model.StateValContainer;
import org.neosimulation.neo.framework.model.StateValContainerException;
import org.neosimulation.neo.framework.stateval.StateVal;

/**
 * Holon - The most basic element of structure in a Flux Network. The Holon makes up an atomic piece of the structure of a SimulationModel. Held within the
 * Holon is a StateValContainer, in which, StateVals are contained, thus providing the means of transferring and storing flux within the network of Holons, that
 * is the SimulationModel.
 * 
 * @author Isaac Griffith
 */
public class Holon implements Serializable {

    /**
     * The Name of this Holon (Not necessarily unique)
     */
    protected String name;
    /**
     * StateValContainer which maintains the collection of StateVals associated with this Holon
     */
    protected StateValContainer container = new StateValContainer();
    /**
     * The SimulationModel for which this Holon is a part of
     */
    protected SimulationModel model;
    /**
     * The Unique ID for this Holon
     */
    protected long uniqueID = -1;
    /**
     * Set of installed behaviors for this holon
     */
    protected Set<Behavior> behaviors;

    /**
     * Constructs a new Holon with the given Name and associated with the provided SimulationModel. This Holon will then retrieve the next unique HolonID for a
     * Holon in the provided SimulationModel.
     * 
     * @param name
     *            Name of the newly constructed Holon
     * @param model
     *            SimulationModel for which this Holon is associated with
     */
    public Holon(String name, SimulationModel model)
    {
        this.name = name;
        this.model = model;

        uniqueID = model.getNextHolonID();
        behaviors = new HashSet<>();
    }

    /**
     * Returns the StateVal associated with this Holon having the given name. If no holon exists with the given name, then a null value is returned.
     * 
     * @param name
     *            Name of the Holon to be retrieved from this Holon's StateValContainer
     * @return StateVal with the provided name and associated with this Holon, or null if no such StateVal exists
     */
    public synchronized final StateVal getStateVal(String name) throws StateValContainerException
    {
        return container.getStateVal(name);
    }

    /**
     * Returns the name of this Holon
     * 
     * @return name of this Holon
     */
    public synchronized final String getName()
    {
        return name;
    }

    /**
     * Sets the name of this Holon to the provided String
     * 
     * @param name
     *            New Name of this Holon
     */
    public synchronized final void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the unique ID of this Holon
     * 
     * @return the unique ID number of this Holon
     */
    public synchronized final long getID()
    {
        return uniqueID;
    }

    /**
     * Adds the specified StateVal to this Holon's StateValContainer, effectively making the provided StateVal apart of this Holon.
     * 
     * @param state
     *            The new StateVal instance to be added to this Holon's StateValContainer, if a StateVal with the same unique ID as the StateVal attempting to
     *            be added already exists, then nothing happens.
     */
    public synchronized final void addStateVal(StateVal state)
    {
        state.setHolon(this);
        container.addStateVal(state);
    }

    /**
     * Removes the provided StateVal from this Holon's StateValContainer
     * 
     * @param state
     *            StateVal to be removed from this Holon's StateValContainer
     */
    public synchronized final void removeStateVal(StateVal state)
    {
        state.setHolon(null);
        container.removeStateVal(state);
    }

    /**
     * @return the model
     */
    public SimulationModel getModel()
    {
        return model;
    }

    /**
     * Returns the SimulationModel for which this Holon is associated.
     * 
     * @return this Holon's Containing SimulationModel
     */
    public synchronized final SimulationModel getSimulationModel()
    {
        return model;
    }

    /**
     * Returns a List of all the StateVals associated with this Holon and contained within the Holon's StateValContainer
     * 
     * @return List of StateVals associated with this Holon
     */
    public synchronized final List<StateVal> getStateVals()
    {
        return container.getStateVals();
    }

    public synchronized boolean isBehaviorInstalled(Behavior beh)
    {
        return behaviors.contains(beh);
    }

    public synchronized void addInstalledBehavior(Behavior beh)
    {
        if (beh != null)
            behaviors.add(beh);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object arg0)
    {
        if (arg0 instanceof Holon)
        {
            if (((Holon) arg0).getID() == this.getID())
            {
                return true;
            }
        }

        return false;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return (int) this.uniqueID;
    }
}
