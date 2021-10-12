/**
 * 
 */
package org.neosimulation.neo.user.interpolator;

import java.util.Arrays;
import java.util.Map;

/**
 * VectorInputInterpolator - A vector based time-series input interpolator.
 * 
 * @author Isaac Griffith
 */
public class VectorInputInterpolator extends InputInterpolator {

    /**
     * Constructs a new VectorInputInterpolator with the provided key/value map,
     * and which uses the provided strategy for interpolation.
     * 
     * @param valuesMap
     *            The known key/value pairs
     * @param strategy
     *            The interpolation method.
     */
    protected VectorInputInterpolator(Map<Double, Double> valuesMap, InterpolationStrategy strategy)
    {
        super(strategy, true);
        this.valuesMap = valuesMap;
        initialize();
    }
    
    /* (non-Javadoc)
     * @see org.neosimulation.neo.user.interpolator.InputInterpolator#initialize()
     */
    @Override
    protected void initialize()
    {
        super.initialize();
        maxWindowSize = valuesMap.keySet().size();

        sortedKeys = new double[maxWindowSize];
        
        int i = 0;
        for (double key : valuesMap.keySet()) {
            if (i == sortedKeys.length)
                break;
            
            sortedKeys[i] = key;
            i++;
        }
        
        Arrays.sort(sortedKeys);
        maxKey = sortedKeys[sortedKeys.length - 1];
        minKey = sortedKeys[0];
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.user.interpolator.InputInterpolator#canInterpolate
     * ()
     */
    @Override
    public boolean canInterpolate()
    {
        boolean retVal = false;

        if (strategy.numValuesReqd() <= valuesMap.keySet().size())
        {
            retVal = true;
        }

        return retVal;
    }
}
