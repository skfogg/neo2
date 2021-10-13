package org.neosimulation.neo.framework.dynam;

/**
 * ICalculatorInt - Integer based Calculator which allows an IUpdatable to
 * allows NEO to know that a Dynam is attached to the contained StateVal.
 * 
 * @author Clemente Izurieta
 * @author Isaac Griffith
 */
public interface ICalculatorInt {

    /**
     * Algorithm which calculates the update to a specific stateval.
     * 
     * @return integer value updating the flux value for a contained stateval
     */
    public int calculate();
}
