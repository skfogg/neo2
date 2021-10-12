/**
 * 
 */
package org.neosimulation.neo.user.interpolator;

import java.util.Arrays;
import java.util.Map;

/**
 * VecotorMathInterpolator - A Vector based math interpolator.
 * 
 * @author Isaac Griffith
 */
public class VectorMathInterpolator extends MathInterpolator {

    /**
     * Constructs a new VectorMathInterpolator which is supplied with the known
     * key/value map, and which uses the provided InterpolationStrategy as the
     * interpolation method.
     * 
     * @param valuesMap
     *            the known key/value pair mapping
     * @param strategy
     *            interpolation method.
     */
    public VectorMathInterpolator(Map<Double, Double> valuesMap, InterpolationStrategy strategy)
    {
        super(strategy, true);
        this.valuesMap = valuesMap;
        initialize();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.user.interpolator.MathInterpolator#canInterpolate()
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
}
