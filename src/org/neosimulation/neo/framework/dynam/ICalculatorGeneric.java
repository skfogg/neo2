package org.neosimulation.neo.framework.dynam;

/**
 * ICalculatorGeneric - Integer based Calculator which allows an IUpdatable to
 * allows NEO to know that a Dynam is attached to the contained StateVal.
 * 
 * @author Clemente Izurieta
 * @author Isaac Griffith
 * @param <CLASS>
 *            The generic type of this ICalculatorGeneric
 */
public interface ICalculatorGeneric<CLASS> {

    /**
     * Algorithm which calculates the update to a specific stateval.
     * 
     * @return integer value updating the flux value for a contained stateval
     */
    public CLASS calculate();
}
