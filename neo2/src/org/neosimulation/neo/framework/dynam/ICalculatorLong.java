/**
 * 
 */
package org.neosimulation.neo.framework.dynam;

/**
 * ICalculatorLong - Long based Calculator which allows an IUpdatable to
 * allows NEO to know that a Dynam is attached to the contained StateVal.
 * 
 * @author Isaac Griffith
 *
 */
public interface ICalculatorLong {

    /**
     * Algorithm which calculates the update to a specific stateval.
     * 
     * @return integer value updating the flux value for a contained stateval
     */
    public long calculate();
}
