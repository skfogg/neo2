/**
 * 
 */
package org.neosimulation.neo.framework.model;

import org.neosimulation.neo.framework.dynam.Dynam;
import org.neosimulation.neo.framework.stateval.StateVal;
import org.neosimulation.neo.user.AutoDynam;
import org.neosimulation.neo.user.InitDynam;
import org.neosimulation.neo.user.ManualDynam;

/**
 * @author Isaac
 */
public final class Dependency implements Comparable<Dependency> {

    /**
     * Type code indicating a dependency of a ManualDynam on an AutoDynam
     */
    public static final int AUTO_MANUAL = 1;
    /**
     * Type code indicating a dependency of a ManualDynam on an InitDynam
     */
    public static final int INIT_MANUAL = 2;
    /**
     * Type code indicating a dependency of a ManualDynam on a ManualDynam
     */
    public static final int MANUAL_MANUAL = 3;
    /**
     * Type code indicating a dependency of an AutoDynam on a ManualDynam
     */
    public static final int MANUAL_AUTO = 4;
    /**
     * Type code indicating a dependency of an InitDynam on a ManualDynam
     */
    public static final int MANUAL_INIT = 5;
    /**
     * Type code indicating a dependency of an InitDynam on an AutoDynam
     */
    public static final int AUTO_INIT = 6;
    /**
     * Type code indicating a dependency of an AutoDynam on an InitDynam
     */
    public static final int INIT_AUTO = 7;
    /**
     * Type code indicating a dependency of an InitDynam on an InitDynam
     */
    public static final int INIT_INIT = 8;
    /**
     * Type code indicating a dependency of an AutoDynam on an AutoDynam
     */
    public static final int AUTO_AUTO = 9;

    /**
     * Value of the type code
     */
    private int value;
    /**
     * Destination (dependent) IUpdatable
     */
    private IUpdatable dest;
    /**
     * Source IUpdatable
     */
    private IUpdatable src;

    /**
     * Factory Method used to create a dependency between two IUpdatables
     * 
     * @param sourceUpdater
     *            Source IUpdatable
     * @param destUpdater
     *            Destination IUpdatable (depends on source)
     * @return The Dependency object
     */
    public static Dependency createDependency(IUpdatable sourceUpdater, IUpdatable destUpdater)
    {
        int value = 0;

        Dynam src = ((StateValUpdater) sourceUpdater).getStateVal().getDynam();
        Dynam dest = ((StateValUpdater) destUpdater).getStateVal().getDynam();

        if (src instanceof ManualDynam)
        {
            if (dest instanceof ManualDynam)
            {
                value = MANUAL_MANUAL;
            }
            else if (dest instanceof AutoDynam)
            {
                value = MANUAL_AUTO;
            }
            else
            {
                value = MANUAL_INIT;
            }
        }
        else if (src instanceof AutoDynam)
        {
            if (dest instanceof ManualDynam)
            {
                value = AUTO_MANUAL;
            }
            else if (dest instanceof AutoDynam)
            {
                value = AUTO_AUTO;
            }
            else
            {
                value = AUTO_INIT;
            }
        }
        else if (src instanceof InitDynam)
        {
            if (dest instanceof ManualDynam)
            {
                value = INIT_MANUAL;
            }
            else if (dest instanceof AutoDynam)
            {
                value = INIT_AUTO;
            }
            else
            {
                value = INIT_INIT;
            }
        }

        return new Dependency(value, sourceUpdater, destUpdater);
    }

    /**
     * Private Constructor which creates a new Dependency with the associated
     * value (type), between the source and destination IUpdatables.
     * 
     * @param value
     *            Type code defining the type of dependency
     * @param src
     *            Source IUpdatable
     * @param dest
     *            Destination IUpdatable (Dependent on Source)
     */
    private Dependency(int value, IUpdatable src, IUpdatable dest)
    {
        this.value = value;
        this.src = src;
        this.dest = dest;
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
        result = prime * result + ((dest == null) ? 0 : dest.hashCode());
        result = prime * result + ((src == null) ? 0 : src.hashCode());
        result = prime * result + value;
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
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Dependency other = (Dependency) obj;
        if (dest == null)
        {
            if (other.dest != null)
                return false;
        }
        else if (!dest.equals(other.dest))
            return false;
        if (src == null)
        {
            if (other.src != null)
                return false;
        }
        else if (!src.equals(other.src))
            return false;
        if (value != other.value)
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Dependency o)
    {
        return Integer.compare(value, o.getValue());
    }

    /**
     * @return the value of the dependency type code
     */
    public int getValue()
    {
        return value;
    }

    /**
     * @return the destination (dependent) IUpdatable
     */
    public IUpdatable getDest()
    {
        return dest;
    }

    /**
     * @return the source IUpdatable
     */
    public IUpdatable getSrc()
    {
        return src;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StateVal source = ((StateValUpdater) src).getStateVal();
        StateVal desti = ((StateValUpdater) dest).getStateVal();
        return "Dependency [value=" + value + ", src=" + source.getHolon().getName() + "." + source.getName()
                + ", dest=" + desti.getHolon().getName() + "." + desti.getName() + "]";
    }
}
