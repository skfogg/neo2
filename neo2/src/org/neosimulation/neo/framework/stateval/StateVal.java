/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

import java.io.Serializable;

import org.neosimulation.neo.framework.dynam.Dynam;
import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.model.IUpdatable;
import org.neosimulation.neo.framework.model.StateValUpdater;

/**
 * StateVal - The most basic storage of values for a model. A Stateval not only
 * contains a value but also a reference to the dynam which controls the
 * initialization of that value and the calculation of the value. It also
 * maintains a reference to the holon in which it is contained. Each stateval
 * maintains a unique id from which it can be identified.
 * 
 * @author Isaac Griffith
 */
public abstract class StateVal implements Serializable {

    /**
     * The algorithm, developed outside of the framework, which provides user
     * defined logic to initialize and calculate this StateVal's value.
     */
    protected Dynam dynam;
    /**
     * Name of this StateVal
     */
    protected String name;
    /**
     * The Holon to which this StateVal is contained.
     */
    protected Holon holon;
    /**
     * The StateValUpdater in charge of maintaining this StateVal's value and
     * acts as an interface between the stateval and the Solver of the
     * SimulationModel.
     */
    protected IUpdatable updater;
    /**
     * The unique identifying number for this StateVal
     */
    protected long uniqueID;
    /**
     * Current state of this StateVal
     */
    protected StateValState currentState;
    /**
     * State used to represent that this StateVal has been initialized
     */
    protected StateValState initializedState;
    /**
     * State used to represent that this StateVal has not been initialized
     */
    protected StateValState nullState;

    /**
     * Constructs a new StateVal with all fields set to null, excluding the ID
     * which is set to -1
     */
    public StateVal()
    {
        this.name = null;
        this.dynam = null;
        this.holon = null;
        this.uniqueID = -1;
        this.updater = new StateValUpdater(this);

        initializeState();
    }

    /**
     * Initializes the StateVal's state fields and places the stateval into the
     * correct initial state
     */
    protected abstract void initializeState();

    /**
     * Constructs a new StateVal with the specified name.
     * 
     * @param name
     *            Name of this new StateVal.
     */
    public StateVal(String name)
    {
        this();
        this.name = name.toUpperCase();
    }

    /**
     * Attaches the provided Dynam to this StateVal, and this StateVal to the
     * attached Dynam, thus creating a bidirectional association between the
     * two.
     * 
     * @param dynam
     *            Dynam to be attached to this StateVal
     */
    public abstract void attach(Dynam dynam);

    /**
     * Final method which returns the dynam attached to this StateVal.
     * 
     * @return This StateVal's Dynam
     */
    public final Dynam getDynam()
    {
        return dynam;
    }

    /**
     * Returns the name of this StateVal
     * 
     * @return Name of this StateVal or null, if one has not yet been assigned.
     */
    public final String getName()
    {
        return name;
    }

    /**
     * Sets the name of this StateVal to the Specified String
     * 
     * @param name
     *            New name of this StateVal
     */
    public final void setName(String name)
    {
        this.name = name.toUpperCase();
    }

    /**
     * Returns the Holon this StateVal is to be contained within
     * 
     * @return Returns the containing Holon, or null if this StateVal is not
     *         contained within a Holon (contained within another
     *         StateValContainer)
     */
    public final Holon getHolon()
    {
        return holon;
    }

    /**
     * Returns the StateValUpdater responsible for updating this specific
     * StateVal
     * 
     * @return StateValUpdater used to maintain this StateVal and provides a
     *         connection between this StateVal and The SimulationModel's Solver
     */
    public final IUpdatable getUpdater()
    {
        return updater;
    }

    /**
     * Returns the Unique Identifying number assigned to only this StateVal
     * 
     * @return the Unique ID of this StateVal
     */
    public final long getID()
    {
        return uniqueID;
    }

    /**
     * Sets this StateVal's internal unique ID. Note that this ID is unique only
     * to the model in which it is contained.
     * 
     * @param id
     *            the new value for this StateVal's unique (to the containing
     *            SimulationModel) id.
     */
    public void setID(long id)
    {
        this.uniqueID = id;
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
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (int) (uniqueID ^ (uniqueID >>> 32));
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
        if (!(obj instanceof StateVal))
        {
            return false;
        }
        StateVal other = (StateVal) obj;
        if (name == null)
        {
            if (other.name != null)
            {
                return false;
            }
        }
        else if (!name.equals(other.name))
        {
            return false;
        }
        if (uniqueID != other.uniqueID)
        {
            return false;
        }
        return true;
    }

    /**
     * Returns the value of this StateVal as a String;
     * 
     * @return the string value of this StateVal's value;
     */
    public abstract String getStringValue();

    /**
     * Calculates and updates this StateVal's value using it's Calculator
     */
    public abstract void update();

    /**
     * Initializes this StateVal's value to the value set by it's Initializer
     */
    public abstract void initialize();

    /**
     * Tests whether this StateVal has not been initialized.
     * 
     * @return false if this stateval has been initialized
     */
    public boolean isNull()
    {
        return currentState.isNull();
    }

    /**
     * Sets the holon in which this StateVal is contained
     * 
     * @param holon
     *            the new Holon containing this StateVal
     */
    public void setHolon(Holon holon)
    {
        this.holon = holon;
    }
}
