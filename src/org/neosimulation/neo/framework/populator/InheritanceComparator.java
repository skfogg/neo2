/**
 * 
 */
package org.neosimulation.neo.framework.populator;

import java.util.Comparator;

/**
 * InheritanceComparator - A Comparator that is used to compare two
 * java.lang.Class objects. If these objects are in the same inheritance
 * hierarchy it will provide a comparison between the depths of each class in
 * the inheritance heirarchy. This comparator should be used to determine the
 * ordering of classes which may be in a direct line in the inheritance
 * hierarchy. That is, when one of the classes could be a direct descendant of
 * the other.
 * 
 * @author Isaac Griffith
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class InheritanceComparator implements Comparator<Class> {

    /**
     * This method compares two Class objects to determine which class is higher
     * in the inheritance hierarchy than the other. It will return a negative,
     * positive, or zero integer if the first class is lower in the hierarchy,
     * higher in the hierarchy, or the classes are the same (equal) or are not
     * directly connected through inheritance.
     * 
     * @param class1
     *            The Class instance to be compared to by the second parameter
     * @param class2
     *            The Class instance to be compared to by the first parameter
     * @return -1 if the first class is in the direct inheritance path of the
     *         second and is below the second class (either a direct subclass of
     *         the second or a derivative of a direct subclass), 0 if the
     *         classes are equal (the same) or are not in the same inheritance
     *         path, or 1 if the first class is a super class or at all above
     *         the second class in a direct inheritance path.
     */
    public int compare(Class class1, Class class2)
    {
        int retVal = Integer.MIN_VALUE;

        // Check if class1 and class2 are the same class or class1 is a
        // superclass of class2
        if (class1.isAssignableFrom(class2))
        {
            retVal = mainComparison(class1, class2, 0, 1);
        }
        // Check if class2 is below class1 or if they are equal
        else if (class2.isAssignableFrom(class1))
        {
            retVal = mainComparison(class1, class2, 0, -1);
        }
        // Otherwise they are not in the same inheritance hierarchy and
        // comparison for the purposes
        // of this comparator they are equal.
        else
        {
            retVal = 0;
        }

        return retVal;
    }

    /**
     * Actually performs the comparison between the two classes
     * 
     * @param class1
     *            Class to be compared
     * @param class2
     *            Class to be compared
     * @param retVal1
     *            value to return if the comparison is successful
     * @param retVal2
     *            value to return if the comparison is unsuccessful
     * @return the value of retVal1 if the two classes are equivalent, retVal2
     *         otherwise
     */
    private int mainComparison(Class class1, Class class2, int retVal1, int retVal2)
    {
        int retVal = retVal2;

        if (class1.getName().equals(class2.getName()))
        {
            retVal = retVal1;
        }

        return retVal;
    }
}
