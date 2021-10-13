/**
 * 
 */
package org.neosimulation.neo.user.interpolator;

import java.util.HashMap;
import java.util.Map;

import org.neosimulation.neo.framework.logging.Loggable;
import org.neosimulation.neo.framework.logging.NEOLogger;

/**
 * InputInerpolator - Abstract Class providing the basic functionalities for an
 * interpolator which covers a range of known Time-Series data. Due to the
 * nature of the data, functionality such as scrolling records is provided to
 * minimize the memory required to hold the currently useful data.
 * 
 * @author Isaac Griffith
 */
public abstract class InputInterpolator implements Loggable {

    /**
     * The next unique id for an InputInterpolator.
     */
    private static long nextID = 0;
    /**
     * The logger used by this input interpolator
     */
    private NEOLogger logger;
    /**
     * Strategy representing the method of interpolation to be used.
     */
    protected InterpolationStrategy strategy;
    /**
     * The maximum size of the data to be stored in memory at one time prior to
     * reading more information from disk/database
     */
    protected int maxWindowSize = 20;
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
     * This input interpolator's unique id
     */
    private long id;

    /**
     * Constructs a new InputInterpolator using the provided
     * InterpolationStrategy
     * 
     * @param strategy
     *            Strategy representing the method of interpolation to be used
     */
    public InputInterpolator(InterpolationStrategy strategy, boolean shouldLog)
    {
        this.id = nextID++;
        this.strategy = strategy;
        valuesMap = new HashMap<>();
        sortedKeys = new double[maxWindowSize];
        maxKey = 0;
        // maxWindowSize = 0;
        minKey = 0;
        if (shouldLog)
            enableLogging();
    }

    /**
     * Tests whether this interpolator has enough information to perform an
     * interpolation. Note that this does not mean that any interpolation can be
     * performed just that some interpolation can.
     * 
     * @return True if interpolation using the associated data source is
     *         possible, this can be called prior to initialization
     */
    public abstract boolean canInterpolate();

    /**
     * Used to initialize this interpolator
     */
    protected void initialize()
    {
    }

    /**
     * Scrolls the window of available data based on attempting to place the key
     * within the boundaries of the window.
     * 
     * @param key
     *            the key which must be fit into the data window
     */
    protected void scrollRecord(double key)
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
        if (key > maxKey)
        {
            scrollRecord(key);
        }

        else if (key < minKey)
        {
            scrollRecord(key);
        }

        if (valuesMap.containsKey(key))
        {
            return valuesMap.get(key);
        }

        double[] keyPair = strategy.findKnownKeys(sortedKeys, key);

        return strategy.interpolate(keyPair, key, valuesMap);
    }

    /**
     * Allows the user to dynamically change the method interpolation to the new
     * strategy provided.
     * 
     * @param strategy
     *            The new method of interpolation to be used.
     */
    public void setInterpolationStrategy(InterpolationStrategy strategy)
    {
        this.strategy = strategy;
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
        logger = new NEOLogger("InputInterpolator-" + id, true);
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
