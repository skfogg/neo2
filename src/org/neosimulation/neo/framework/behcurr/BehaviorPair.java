/**
 * 
 */
package org.neosimulation.neo.framework.behcurr;


/**
 * BehaviorPair - Class representing the combination of behaviors for a given Edge.
 * 
 * @author Isaac Griffith
 */
public class BehaviorPair {

    /**
     * From Side behavior of this behavior pair.
     */
    private Behavior fromBehavior;
    /**
     * To Side behavior of this behavior pair.
     */
    private Behavior toBehavior;

    /**
     * Constructs a new Behavior Pair for an Edge consisting of the Behavior's for the from and to sides of the Edge.
     * 
     * @param to
     *            To side Behavior
     * @param from
     *            From side Behavior
     */
    public BehaviorPair(Behavior to, Behavior from)
    {
        this.fromBehavior = from;
        this.toBehavior = to;
    }

    /**
     * Retrieves the From Side Behavior
     * 
     * @return The From Behavior of this Behavior Pair.
     */
    public Behavior getFromBehavior()
    {
        return fromBehavior;
    }

    /**
     * Retrieves the To Side Behavior
     * 
     * @return The To Behavior of this Behavior Pair.
     */
    public Behavior getToBehavior()
    {
        return toBehavior;
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
        result = prime * result + ((fromBehavior == null) ? 0 : fromBehavior.hashCode());
        result = prime * result + ((toBehavior == null) ? 0 : toBehavior.hashCode());
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
        BehaviorPair other = (BehaviorPair) obj;
        if (fromBehavior == null)
        {
            if (other.fromBehavior != null)
                return false;
        }
        else if (!fromBehavior.equals(other.fromBehavior))
            return false;
        if (toBehavior == null)
        {
            if (other.toBehavior != null)
                return false;
        }
        else if (!toBehavior.equals(other.toBehavior))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "BehaviorPair [fromBehavior=" + fromBehavior + ", toBehavior=" + toBehavior + "]";
    }
}
