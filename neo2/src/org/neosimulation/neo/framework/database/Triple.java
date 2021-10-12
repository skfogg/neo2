/**
 * 
 */
package org.neosimulation.neo.framework.database;

import java.io.Serializable;

/**
 * Triple - Represents a set of three generic values. In the NEO Framework this
 * is used to represent the three pieces of information necessary to generate a
 * specific StateVal for a behavior. This then allows the system to only
 * maintain a single Triple which can generate all of the needed versions of
 * that StateVal contained in the Triple.
 * 
 * @author Isaac Griffith
 */
@SuppressWarnings("serial")
public class Triple<NAME, TYPE, VALUE> implements Serializable {

    /**
     * Name of the StateVal whose info is stored in this Triple
     */
    private NAME name;
    /**
     * Type of StateVal (i.e., double, integer, generic)
     */
    private TYPE type;
    /**
     * The initial value information of the stateval
     */
    private VALUE value;

    /**
     * Constructs a new Triple with the provided name, type, and value;
     * 
     * @param name
     *            Name of this triple
     * @param type
     *            Type of this triple
     * @param value
     *            Value of this triple
     */
    public Triple(NAME name, TYPE type, VALUE value)
    {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    /**
     * Returns the the Name of this triple.
     * 
     * @return Name of this Triple
     */
    public NAME getName()
    {
        return name;
    }

    /**
     * Returns the Type of this Triple
     * 
     * @return Type of this Triple
     */
    public TYPE getType()
    {
        return type;
    }

    /**
     * Returns the Value of this triple
     * 
     * @return Value of this Triple
     */
    public VALUE getValue()
    {
        return value;
    }
}
