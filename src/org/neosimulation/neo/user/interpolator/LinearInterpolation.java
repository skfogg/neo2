/**
 * 
 */
package org.neosimulation.neo.user.interpolator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * LinearInterpolation - Strategy used by Interpolators to provide Linear
 * Interpolation. This works by first finding the two keys that surround the
 * provided key. Using these found keys we extract their values from a hash map.
 * This creates two points from which we can then interpolate along that line to
 * find the unknown value of the provided key.
 * 
 * @author Isaac Griffith
 */
public class LinearInterpolation implements InterpolationStrategy {

    /**
     * Key/Value map relating known keys to known values
     */
    protected HashMap<Double, Double> valuesMap;

    /**
     * The Minimum Number of values required to provide an interpolation using
     * this strategy.
     */
    private static final int MIN_VALUE = 2;

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.user.interpolator.InterpolationStrategy#interpolate
     * (double[], double)
     */
    /**
     * Given a pair of bounding keys, requests their associated values and
     * interpolates the value of the new key.
     * 
     * @param knownKeys
     *            Pair of bound keys between which the provided key falls, where
     *            the first index of this array is the lower value
     * @param newKey
     *            The new key whose interpolated value is being requested
     * @param valuesMap
     *            Map of key/values pairs to be used to extract the values
     * @return The newly interpolated value
     */
    @Override
    public double interpolate(double[] knownKeys, double newKey, Map<Double, Double> valuesMap)
    {
        double lowerY = valuesMap.get(knownKeys[0]);
        double upperY = valuesMap.get(knownKeys[1]);
        double slope = (upperY - lowerY) / (knownKeys[1] - knownKeys[0]);

        // normalize the key
        double normKey = newKey - knownKeys[0];

        double retVal = (slope * normKey);

        return (retVal + lowerY);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.user.interpolator.InterpolationStrategy#findKnownKeys
     * (double[], double)
     */
    @Override
    public double[] findKnownKeys(double[] sortedKeys, double key)
    {
        double keyPair[] = new double[2];
        int index = Arrays.binarySearch(sortedKeys, key);
        if (index < 0)
        {
            // Compute the insert index
            index = -index - 1;
        }
        if (index == 0)
        {
            setKeyPair(keyPair, sortedKeys, index, index + 1);
        }
        else if (index == sortedKeys.length)
        {
            if (sortedKeys.length > 2)
            {
                setKeyPair(keyPair, sortedKeys, index - 2, index - 1);
            }
            else
            {
                throw new InterpolationError("Attempting to extrapolate beyond known values!");
            }
        }
        else
        {
            setKeyPair(keyPair, sortedKeys, index - 1, index);
        }

        return keyPair;
    }

    /**
     * Sets the keyPair array values to be those in the fromArray with the
     * provided indexes.
     * 
     * @param keyPair
     *            keyPair array to which the values are to be assigned
     * @param fromArray
     *            array from which values are to be extracted
     * @param firstIndex
     *            first index in the from array
     * @param secondIndex
     *            second index in the from array
     */
    private void setKeyPair(double[] keyPair, final double[] fromArray, int firstIndex, int secondIndex)
    {
        keyPair[0] = fromArray[firstIndex];
        keyPair[1] = fromArray[secondIndex];
    }

    /**
     * Recursive Algorithm based on binary search
     * 
     * @param keys
     *            a sorted array of the keys from the valuesMap
     * @param key
     *            the key requesting an interpolated value
     * @return an array of two values representing the keys from the datasource
     *         between which the requested key would fall.
     */
    private double[] findKnownKeys(double[] keys, double key, int lowerBound, int upperBound)
    {
        // FIXME Requires Refactoring
        double[] retVal = new double[2];
        int middle = 0;

        if ((upperBound - lowerBound) > 3)
        {
            middle = lowerBound + (lowerBound / upperBound);
        }
        else if (upperBound == lowerBound)
        {
            middle = upperBound;
        }
        else
        {
            retVal[0] = keys[lowerBound];
            retVal[1] = keys[upperBound];
            return retVal;
        }

        // look at position between keys[middle] and keys[middle - 1}
        if (keys[middle] > key)
        {
            // if between these two keys return these key values.
            if (keys[middle - 1] < key)
            {
                retVal[0] = keys[middle];
                retVal[1] = keys[middle - 1];
                return retVal;
            }
            else
            // else search between middle and the lowerbound
            {
                return findKnownKeys(keys, key, lowerBound, middle - 1);
            }
        }
        else
        { // look at position between keys[middle] and keys[middle + 1]
          // if between these keys then return the key values
            if (keys[middle + 1] > key)
            {
                retVal[0] = keys[middle];
                retVal[1] = keys[middle + 1];
                return retVal;
            }
            else
            // else search between middle and the upperbound
            {
                return findKnownKeys(keys, key, middle + 1, upperBound);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.user.interpolator.InterpolationStrategy#numValuesReqd
     * ()
     */
    @Override
    public int numValuesReqd()
    {
        return MIN_VALUE;
    }
}
