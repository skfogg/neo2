/**
 * 
 */
package org.neosimulation.neo.framework.model;

import org.neosimulation.neo.framework.stateval.StateVal;

/**
 * StateValUpdater - StateValUpdaters are the objects which are controlled by the Solver's associated with the SimulationModel. These Solver's use the
 * StateValUpdaters to pass commands to the StateVal, which is associated with a User defined Dynam. The StateVal then passes these commands onto the associated
 * User-defined Dynam which, in turn, modifies the value of the StateVal according to the contained User-defined logic.
 * 
 * @author Isaac Griffith
 */
public class StateValUpdater implements IUpdatable {

    /**
     * The index of this StateValUpdater used to define its position in the grand loop
     */
    private int index;
    /**
     * The StateVal associated with this StateValUpdater
     */
    protected StateVal stateVal;
    /**
     * The value to multiply the calculated flux value by
     */
    private double multiplier;

    /**
     * Constructs a new StateValUpdater which controls the update/initialize process for the provided StateVal
     * 
     * @param stateVal
     *            StateVal which this StateValUpdater controls
     */
    public StateValUpdater(StateVal stateVal)
    {
        this.stateVal = stateVal;
        index = Integer.MIN_VALUE;
        multiplier = 1;
    }

    /**
     * Passes the initialize command for a Dynam down the chain from the SimulationModel to the Updater, from the Updater to the StateVal, and from the StateVal
     * to the Dynam. The Dynam then initializes the flux value associated with its StateVal.
     */
    public void initialize()
    {
        stateVal.initialize();
    }

    /**
     * Passes the update command for a Dynam down the chain from the SimulationModel to the Updater, from the Updater to the StateVal, and from the StateVal to
     * the Dynam. The Dynam then updates the flux value associated with its StateVal.
     */
    public void update()
    {
        stateVal.update();
    }

    /**
     * Returns the StateVal instance for which this Updater is responsible to as an interface between the SimulationModel and the StateVal.
     * 
     * @return The StateVal this Updater is responsible to and for.
     */
    public StateVal getStateVal()
    {
        return stateVal;
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
        if (!(obj instanceof StateValUpdater))
        {
            return false;
        }
        StateValUpdater other = (StateValUpdater) obj;
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

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.model.IUpdatable#compareTo(org.neosimulation .neo.framework.model.IUpdatable)
     */
    public int compareTo(IUpdatable updatable)
    {
        if (getIndex() < updatable.getIndex())
            return -1;

        if (getIndex() > updatable.getIndex())
            return 1;

        return 0;

        // return Integer.compare(getIndex(), updatable.getIndex());
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.model.IUpdatable#getIndex()
     */
    public int getIndex()
    {
        return index;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.model.IUpdatable#setIndex(int)
     */
    public void setIndex(int index)
    {
        this.index = index;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.model.IUpdatable#setMultiplier(int)
     */
    @Override
    public void setMultiplier(double multiplier)
    {
        // TODO Auto-generated method stub
        this.multiplier = multiplier;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.model.IUpdatable#getMultiplier()
     */
    @Override
    public double getMultiplier()
    {
        return multiplier;
    }
}
