/**
 * 
 */
package org.neosimulation.neo.user.interpolator;

import java.util.Map;

/**
 * InterpolationStrategy - The abstract base class for a given
 * InterpolationStrategy. The implementors of this abstract class define the
 * underlying behavior associated
 * 
 * @author Isaac Griffith
 */
public interface InterpolationStrategy {

    /**
     * Given a set of known keys which define the range within which the new key
     * lies, we can then use those values to interpolate the new key's estimated
     * value.
     * 
     * @param knownKeys
     *            Pair of bound keys between which the provided key falls, where
     *            the first index of this array is the lower value
     * @param newKey
     *            The new key whose interpolated value is being requested
     * @param values
     *            The key/values pair mappings
     * @return The newly interpolated value
     */
    double interpolate(double[] knownKeys, double newKey, Map<Double, Double> values);

    /**
     * Finds a range of values from the provided sorted keys array which defines
     * a range within which the provided key exists. The returned array of
     * double values can then be used to interpolate the estimated value for the
     * provided key.
     * 
     * @param sortedKeys
     *            a sorted array of the keys from the valuesMap
     * @param key
     *            the key requesting an interpolated value
     * @return an array of two values representing the keys from the datasource
     *         between which the requested key would fall.
     */
    double[] findKnownKeys(double[] sortedKeys, double key);

    /**
     * Returns the minimum number of values required by this strategy to achieve
     * proper interpolation.
     * 
     * @return Minimum number of values required by interpolation
     */
    int numValuesReqd();
}
