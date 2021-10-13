/**
 * 
 */
package org.neosimulation.neo.framework.behcurr;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.neosimulation.neo.framework.dynam.Dynam;

/**
 * Behavior - Class used to encapsulate the information about a specific behavior, such as
 * its name, the currency it is associated with, and the dynams which are
 * associated to it.
 * 
 * @author Isaac Griffith
 */
public class Behavior implements Serializable {

    /**
     * The Name of this Behavior
     */
    private String name;
    /**
     * The Currency to which this behavior belongs
     */
    private Currency currency;
    /**
     * The Dynams which are members of this Behavior
     */
    private List<Dynam> dynams;

    /**
     * Creates a new Behavior with the provided name and associated to the
     * provided Currency.
     * 
     * @param name
     *            Name of this Behavior
     * @param currency
     *            Currency this Behavior is associated with
     */
    public Behavior(String name, Currency currency)
    {
        this.name = name;
        this.currency = currency;

        dynams = new CopyOnWriteArrayList<>();
    }

    /**
     * Returns the Currency object to which the Behavior is associated.
     * 
     * @return Associated Currency
     */
    public Currency getCurrency()
    {
        return currency;
    }

    /**
     * Returns the list of Dynams installed in the model and which are a part of
     * this Behavior
     * 
     * @return List of all Dynams that associated to this Behavior
     */
    public List<Dynam> getDynams()
    {
        return dynams;
    }

    /**
     * Registers the provided Dynam as a part of this Behaviors known Dynams
     * 
     * @param dynam
     *            Dynam to register with this behavior
     */
    public void registerDynam(Dynam dynam)
    {
        if (!dynams.contains(dynam))
        {
            dynams.add(dynam);
            dynam.setBehavior(this);
        }
    }

    /**
     * Unregisters the provided Dynam from this Behavior. Effectively claiming
     * that this Dynam is not needed to support this Behavior in the model
     * 
     * @param dynam
     *            Dynam to be unregistered
     */
    public void unregisterDynam(Dynam dynam)
    {
        if (dynams.contains(dynam))
        {
            dynams.remove(dynam);
        }
    }

    /**
     * Requests the name of this behavior
     * 
     * @return The name of this Behavior.
     */
    public String getName()
    {
        return name;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return getName();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((currency == null) ? 0 : currency.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /* (non-Javadoc)
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
        if (!(obj instanceof Behavior))
        {
            return false;
        }
        Behavior other = (Behavior) obj;
        if (currency == null)
        {
            if (other.currency != null)
            {
                return false;
            }
        }
        else if (!currency.equals(other.currency))
        {
            return false;
        }
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
        return true;
    }
}
