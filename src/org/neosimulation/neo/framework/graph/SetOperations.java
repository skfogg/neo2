package org.neosimulation.neo.framework.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * SetOperations - This class defines basic operations from set theory. It
 * includes the union, difference, symmetric difference, and intersection
 * operations. It also provides a means to test whether one set is the subset or
 * superset of another set.
 * 
 * @author Isaac Griffith
 */
public class SetOperations {

    /**
     * Union Operation. Given set A and set B, this method returns a new set
     * composed of all of the items in both set A and set B.
     * 
     * @param <T>
     *            Type parameter of both the input sets and the returned set.
     * @param setA
     *            Set A
     * @param setB
     *            Set B
     * @return The union of set A and set B.
     * @throws SetOperationsException
     *             thrown if either of the sets is null.
     */
    public static <T> Set<T> union(Set<T> setA, Set<T> setB) throws SetOperationsException
    {
        checkForNullSets(setA, setB);

        Set<T> tmp = new HashSet<T>(setA);
        tmp.addAll(setB);
        return tmp;
    }

    /**
     * Intersection operation. Given set A and set B, this method returns a new
     * set composed of the items common to both.
     * 
     * @param <T>
     *            Type parameter of both the input sets and the returned set.
     * @param setA
     *            Set A
     * @param setB
     *            Set B
     * @return The intersection of set A and set B
     * @throws SetOperationsException
     *             thrown if either set is null.
     */
    public static <T> Set<T> intersection(Set<T> setA, Set<T> setB) throws SetOperationsException
    {
        checkForNullSets(setA, setB);

        Set<T> tmp = new HashSet<T>();
        for (T x : setA)
            if (setB.contains(x))
                tmp.add(x);
        return tmp;
    }

    /**
     * Difference operation. This method returns all members of set A which are
     * not members of set B.
     * 
     * @param <T>
     *            The type parameter of both input sets and the returned set
     * @param setA
     *            Set A
     * @param setB
     *            Set B
     * @return A new set composed of items from set A not found in set B.
     * @throws SetOperationsException
     *             thrown if either of the sets is null.
     */
    public static <T> Set<T> difference(Set<T> setA, Set<T> setB) throws SetOperationsException
    {
        checkForNullSets(setA, setB);

        Set<T> tmp = new HashSet<T>(setA);
        tmp.removeAll(setB);
        return tmp;
    }

    /**
     * Symmetric Difference operation. Returns all members of A and B that are
     * not in both A and B
     * 
     * @param <T>
     *            The type parameter of both the input sets and the returned
     *            set.
     * @param setA
     *            Set A
     * @param setB
     *            Set B
     * @return A new set containing items that are members of either set A or B
     *         that are not found in the intersection of A and B.
     * @throws SetOperationsException
     *             thrown if either of the sets is null.
     */
    public static <T> Set<T> symDifference(Set<T> setA, Set<T> setB) throws SetOperationsException
    {
        checkForNullSets(setA, setB);

        Set<T> tmpA;
        Set<T> tmpB;

        tmpA = union(setA, setB);
        tmpB = intersection(setA, setB);
        return difference(tmpA, tmpB);
    }

    /**
     * Tests whether set A is a subset of set B, or whether set B is a superset
     * of set A. That is, whether set B contains at least the same items as set
     * A.
     * 
     * @param <T>
     *            The type parameter for both input sets.
     * @param setA
     *            Set A
     * @param setB
     *            Set B
     * @return true if set A is a subset of set B.
     * @throws SetOperationsException
     *             thrown if either set is null.
     */
    public static <T> boolean isSubset(Set<T> setA, Set<T> setB) throws SetOperationsException
    {
        checkForNullSets(setA, setB);

        return setB.containsAll(setA);
    }

    /**
     * Tests whether set A is the superset of set B. That is, whether set A
     * contains at least the same items as set B.
     * 
     * @param <T>
     *            The type parameter of both input sets.
     * @param setA
     *            Set A
     * @param setB
     *            Set B
     * @return true if set A is a super set of set B.
     * @throws SetOperationsException
     *             thrown if either of the sets is null.
     */
    public static <T> boolean isSuperset(Set<T> setA, Set<T> setB) throws SetOperationsException
    {
        checkForNullSets(setA, setB);

        return setA.containsAll(setB);
    }

    /**
     * Checks whether either of the provided sets is null. If either is then a
     * SetOperationsException is thrown.
     * 
     * @param setA
     *            Set A
     * @param setB
     *            Set B
     * @throws SetOperationsException
     *             thrown if either Set A or B is null
     */
    private static <T> void checkForNullSets(Set<T> setA, Set<T> setB) throws SetOperationsException
    {
        if (setA == null || setB == null)
        {
            throw new SetOperationsException("Cannot union with null sets.");
        }
    }
}