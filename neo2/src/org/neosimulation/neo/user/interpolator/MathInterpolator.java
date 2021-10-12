/**
 * 
 */
package org.neosimulation.neo.user.interpolator;

import java.util.HashMap;
import java.util.Map;

import org.neosimulation.neo.framework.logging.Loggable;
import org.neosimulation.neo.framework.logging.NEOLogger;

/**
 * MathInterpolator - Provides a means by which an unknown mathematical
 * function, which is describe by a map of key/value pairs can be interpolated
 * to provide use values for unknown keys.
 * 
 * @author Isaac Griffith
 */
public abstract class MathInterpolator implements Loggable {

    /**
     * The next unique ID for a MathInterpolator.
     */
    private static long nextID = 0;
    /**
     * The logger associated with this MathInterpolator.
     */
    private NEOLogger logger;
    /**
     * Strategy used to define the interpolation method to be used
     */
    protected InterpolationStrategy strategy;
    /**
     * The maximum size of the data to be stored in memory at one time prior to
     * reading more information from disk/database
     */
    protected int maxWindowSize;
    /**
     * the largest key in the current window
     */
    protected double maxKey;
    /**
     * the smallest key in the current window
     */
    protected double minKey;
    /**
     * stores the values from the information source, indexed by their keys
     */
    protected Map<Double, Double> valuesMap;
    /**
     * stores the set of keys, sorted to facilitate the binary sort algorithm
     */
    protected double[] sortedKeys;
    /**
     * The unique ID of this MathInterpolator.
     */
    private long id;

    /**
     * Creates a new MathInterpolator using the provided interpolation strategy
     * to provided the interpolation algorithm.
     * 
     * @param strategy
     *            The strategy that implements the interpolation algorithm.
     */
    protected MathInterpolator(InterpolationStrategy strategy, boolean shouldLog)
    {
        this.id = nextID++;
        this.strategy = strategy;
        valuesMap = new HashMap<Double, Double>();
        sortedKeys = new double[maxWindowSize];
        maxKey = 0;
        maxWindowSize = 0;
        minKey = 0;

        if (shouldLog)
            enableLogging();
    }

    /**
     * @return True if interpolation using the associated data source is
     *         possible, this can be called prior to initialization
     */
    public abstract boolean canInterpolate();

    /**
     * Initializes this interpolator
     */
    protected void initialize()
    {
    }

    /**
     * Given a supplied key this method looks either finds the associated known
     * value or provides an interpolated value derived from the currently known
     * data.
     * 
     * @param key
     *            The key for which an interpolated value is required
     * @return The interpolated value
     * @throws InterpolationError
     *             Thrown if there is an insufficient amount of data
     */
    public double getValue(double key)
    {
        if (valuesMap.containsKey(key))
        {
            return valuesMap.get(key);
        }

        double[] keyPair = strategy.findKnownKeys(sortedKeys, key);

        return strategy.interpolate(keyPair, key, valuesMap);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Loggable#getLogger()
     */
    @Override
    public NEOLogger getLogger()
    {
        return logger;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Loggable#enableLogging()
     */
    @Override
    public void enableLogging()
    {
        logger = new NEOLogger("MathInterpolator-" + id, true);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Loggable#disableLogging()
     */
    @Override
    public void disableLogging()
    {
        logger = null;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Loggable#isLoggingEnabled()
     */
    @Override
    public boolean isLoggingEnabled()
    {
        return (logger != null);
    }
}