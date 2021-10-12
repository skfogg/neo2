/**
 * 
 */
package org.neosimulation.neo.framework.config;

/**
 * A class used to ensure that the intervals used in an output configuration
 * are valid within the range 1 - Integer.MAX_VALUE.
 * 
 * @author Isaac Griffith
 */
public class OutputConfigInterval implements Comparable<OutputConfigInterval>{
    /**
     * The interval value of this object
     */
    private final int interval;
    /**
     * The owning instance of OutputConfig
     */
    private OutputConfig parent;

    /**
     * The minimum value of the range
     */
    private static final int MIN = 1;
    /**
     * The maximum value of the range
     */
    private static final int MAX = Integer.MAX_VALUE;

    /**
     * @param parent
     *            The OutputConfig which owns this OutputConfigInterval
     *            object
     * @param interval
     *            The value to be stored within this OutputConfigInterval
     *            object
     * @throws OutputConfigException
     *             thrown if the provided interval is not within range
     */
    public OutputConfigInterval(OutputConfig parent, int interval) throws OutputConfigException
    {
        if (parent == null)
        {
            throw new OutputConfigException("This OutputConfigInterval is not associated to any OutputConfig.");
        }

        if (interval >= MIN && interval <= MAX)
        {
            this.interval = interval;
        }
        else
        {
            throw new OutputConfigException("The interval specified is outside the range: " + MIN + " - " + MAX);
        }
    }

    /**
     * Retrieves the interval stored within this class
     * 
     * @return interval.
     */
    public int getInterval()
    {
        return interval;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(OutputConfigInterval o)
    {
        return Integer.compare(o.getInterval(), this.interval);
    }
}
