/**
 * 
 */
package org.neosimulation.neo.framework.mtis;

/**
 * UpdatableConfigInteval - Object encapsulating a positive integer which can only be within the range of 1 - Integer.MAX_VALUE. This value is then used as the
 * value representing the intervals when the associated UpdatableConfig is utilized.
 * 
 * @author Isaac Griffith
 */
public class UpdatableConfigInterval implements Comparable<UpdatableConfigInterval> {

    /**
     * The actual value of this interval
     */
    private final long interval;
    /**
     * The associated UpdatableConfig of this interval
     */
    private UpdatableConfig parent;
    /**
     * The minimum value of any UpdatableConfigInterval
     */
    private static final int MIN = 1;
    /**
     * The maximum value of any UpdatableConfigInterval
     */
    private static final int MAX = Integer.MAX_VALUE;

    /**
     * Constructs a new UpdatableConfigInterval associated with the provided UpdatableConfig and with the provided value
     * 
     * @param parent
     *            associated UpdatableConfig
     * @param interval
     *            actual value for this interval
     * @throws UpdatableConfigIntervalException
     *             thrown if an invalid (null) UpdatableConfig is supplied or a value not within the range of MIN and MAX is supplied
     */
    public UpdatableConfigInterval(UpdatableConfig parent, int interval) throws UpdatableConfigIntervalException
    {
        if (parent == null)
        {
            throw new UpdatableConfigIntervalException(
                    "This UpdatableConfigInterval is not associated to any OutputConfig.");
        }

        if (interval >= MIN && interval <= MAX)
        {
            this.interval = interval;
        }
        else
        {
            throw new UpdatableConfigIntervalException("The interval specified is outside the range: " + MIN + " - "
                    + MAX);
        }
    }

    /**
     * Retrieves the interval stored within this class
     * 
     * @return interval contained
     */
    public long getInterval()
    {
        return interval;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(UpdatableConfigInterval o)
    {
        return Long.compare(o.getInterval(), this.interval);
    }

}
