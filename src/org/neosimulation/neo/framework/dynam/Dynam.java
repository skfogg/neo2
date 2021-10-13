package org.neosimulation.neo.framework.dynam;

import java.io.Serializable;

import org.neosimulation.neo.framework.behcurr.Behavior;
import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.stateval.StateVal;

/**
 * Dynam - An object that attaches to a StateVal and provides the user defined
 * logic to correctly initialize, update, or both initialize and update that
 * StateVal's value during model execution. <br>
 * <br>
 * Extension of the initialization dynam class by a simulation model will create
 * a static stateval in the associated holon. The initial value of the static
 * stateval is determined by code in the extending class and its value does not
 * change through a simulation run. <br>
 * <br>
 * The extending class must define the method to calculate the initial value of
 * the stateval, which will subsequently remain at that value through the
 * simulation run. The extending class must also define any stateval
 * dependencies for the calculation of the initial value. Initialization
 * dependencies are used by the NEO framework to determine the order in which
 * the "initialize" method of all dynams are executed.
 * 
 * @author Isaac Griffith
 */
public abstract class Dynam implements Serializable {

    /**
     * StateVal to which this Dynam is attached
     */
    protected StateVal stateVal;
    /**
     * Holon in which this Dynam's StateVal resides
     */
    protected Holon holon;
    /**
     * This Dynams Behavior
     */
    protected Behavior behavior;
    /**
     * The model to which this dynam belongs
     */
    protected SimulationModel model;

    /**
     * Sets the initialization dependencies (To be user defined)
     */
    abstract public void setInitDeps();

    /**
     * Creates a dependency in a DependencyGraph from this Dynam's associated
     * IUpdatable to the IUpdatable associated with the StateVal with the given
     * name in the provided Holon, if such a StateVal exists. If the dependency
     * to be created is between an Updatable with a Dynam, and one without, then
     * no dependency is created and the named stateval is returned (if one
     * exists) or a null is returned (if one does not).
     * 
     * @param holon
     *            Holon in which to look for the StateVal with the given name
     * @param stateValName
     *            StateVal name to look up
     * @return The found StateVal, or null if no such StateVal exists in the
     *         holon provided
     */
    public final StateVal createDependency(Holon holon, String stateValName)
    {
        return this.holon.getModel().getDependencyManager().createDependency(this, holon, stateValName);
        
    }

    /**
     * Creates a dependency in a DependencyGraph between this Dynam and the
     * StateVal with the given name in this Dynams's Holon.
     * 
     * @param stateValName
     *            Name of the StateVal to lookup
     * @return StateVal found, or null if no such StateVal with the given name
     *         exists.
     */
    public final StateVal createDependency(String stateValName)
    {
        return holon.getModel().getDependencyManager().createDependency(this, stateValName);
    }

    /**
     * Creates a bidirectional association between this dynam and the provided
     * StateVal
     * 
     * @param state
     *            StateVal for which this dynam is responsible for
     *            initialization/updating the value.
     */
    public final void attach(StateVal state)
    {
        if (state == null)
            return;

        this.stateVal = state;
        state.attach(this);
        holon = state.getHolon();
        if (holon != null)
            setModel(holon.getSimulationModel());
    }

    /**
     * Sets the SimulationModel to which Dynam belongs. If you are receiving
     * sporadic NullPointerExceptions on Dynams not associated with StateVals,
     * it is probably because you haven't set the Dynam.
     * 
     * @param simulationModel
     *            associated SimulationModel of this Dynam
     */
    public void setModel(SimulationModel simulationModel)
    {
        this.model = simulationModel;
    }

    /**
     * Returns the name of the Behavior to which this Dynam is associated
     * 
     * @return Name of the Behavior to which this Dynam is associated
     */
    public String getBehaviorName()
    {
        String retVal = "";

        if (behavior != null)
        {
            retVal = behavior.getName();
        }

        return retVal;
    }

    /**
     * Retrieves the name of the currency to which this dynam is associated.
     * 
     * @return The name of this Dynam's currency
     */
    public String getCurrencyName()
    {
        String retVal = "";
        if (behavior != null)
        {
            if (behavior.getCurrency() != null)
                retVal = behavior.getCurrency().getName();
        }

        return retVal;
    }

    /**
     * Sets the behavior to which this Dynam is to be associated.
     * 
     * @param behavior
     *            Behavior to associated this Dynam with.
     */
    public void setBehavior(Behavior behavior)
    {
        this.behavior = behavior;
        this.behavior.registerDynam(this);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((behavior == null) ? 0 : behavior.hashCode());
        result = prime * result + ((holon == null) ? 0 : holon.hashCode());
        result = prime * result + ((stateVal == null) ? 0 : stateVal.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Dynam))
        {
            return false;
        }
        Dynam other = (Dynam) obj;
        if (behavior == null)
        {
            if (other.behavior != null)
            {
                return false;
            }
        }
        else if (!behavior.equals(other.behavior))
        {
            return false;
        }
        if (holon == null)
        {
            if (other.holon != null)
            {
                return false;
            }
        }
        else if (!holon.equals(other.holon))
        {
            return false;
        }
        if (stateVal == null)
        {
            if (other.stateVal != null)
            {
                return false;
            }
        }
        else if (!stateVal.equals(other.stateVal))
        {
            return false;
        }
        return true;
    }

    /**
     * Retrieves the StateVal to which this Dynam is attached
     * 
     * @return The attached StateVal
     */
    public StateVal getStateVal()
    {
        return stateVal;
    }
}
