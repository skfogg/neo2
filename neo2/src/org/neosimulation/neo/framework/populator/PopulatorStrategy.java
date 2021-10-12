package org.neosimulation.neo.framework.populator;

import java.util.Set;

import org.neosimulation.neo.framework.behcurr.Behavior;
import org.neosimulation.neo.framework.database.Triple;
import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.model.IUpdatable;
import org.neosimulation.neo.framework.stateval.StateVal;

/**
 * PopulatorStrategy - Interface defining the strategy to be used to install
 * statevals into holons using type specific control logic.
 * 
 * @author Isaac Griffith
 */
public abstract class PopulatorStrategy {

    /**
     * Installs StateVals based on the class type of the provided dynam Class
     * and whether the dynam name matches the behavior currency name. Once the
     * type of StateVal has been determined it is installed into the provided
     * Holon.
     * 
     * @param dynam
     *            Dynam Class type.
     * @param behavior
     *            Behavior used to determine if the dynam is a currency dynam.
     * @param holon
     *            Holon into which the StateVal is installed into.
     * @return The StateVal that has been installed
     */
    public abstract StateVal installStateVals(Class dynam, Behavior behavior, Holon holon);

    /**
     * Installs the StateVals defined in Default Values tables in the NEO Input
     * database. These StateVals have some default value to which they are
     * automatically initialized to. This method uses the
     * 
     * @param updatables
     *            The set of IUpdatables to which the StateValUpdater for the
     *            newly installed stateVal is to be added to.
     * @param currencyName
     *            The name of the currency to which the Triple (defining the
     *            Dynam associated with the StateVal) name should be compared to
     *            to determine if the StateVal is a currency StateVal.
     * @param triple
     *            Defines a StateVal and is used here to determine the type of
     *            StateVal to install, and the initial value of the new StateVal
     * @param holon
     *            Used here as the location to install the StateVal to and to
     *            determine the type of StateVal to install.
     */
    public abstract void installDefaultStateVals(Set<IUpdatable> updatables, String currencyName,
            Triple<String, String, Object> triple, Holon holon);

    /**
     * Determines if the the provided holon is a holon for the named currency by
     * using information in the provided holon triple and through verification
     * that the holon is of the same class type as the one provided.
     * 
     * @param holonClass
     *            Holon class type used for verification
     * @param currencyName
     *            Name of the currency
     * @param triple
     *            Triple to extract information from
     * @param holon
     *            actual holon being verified
     * @return true if the provided holon is a currency holding holon for the
     *         named currency, false otherwise.
     */
    protected boolean checkIsCurrencyHolon(Class holonClass, String currencyName,
            Triple<String, String, Object> triple, Holon holon)
    {
        if (triple == null || holon == null || currencyName == null)
            return false;
        return (holonClass.isInstance(holon) && triple.getName().equalsIgnoreCase(currencyName));
    }

    /**
     * Checks whether the provided holon contains a currency dynam based on the
     * provided dynam class and behavior object.
     * 
     * @param holonClass
     *            Holon class to compare the holon object to
     * @param dynamClass
     *            Dynam class whose name is used to determine if it is a
     *            currency
     * @param behavior
     *            Behavior used to extract the currency name from
     * @param holon
     *            Holon object
     * @return true if the holon object contains a currency of from the
     *         behavior, false otherwise.
     */
    protected boolean checkIsCurrencyHolon(Class holonClass, Class dynamClass, Behavior behavior, Holon holon)
    {
        if (behavior == null || holon == null)
            return false;
        return (holonClass.isInstance(holon) && dynamClass.getSimpleName().equalsIgnoreCase(
                behavior.getCurrency().getName()));
    }
}
