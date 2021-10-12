/**
 * 
 */
package org.neosimulation.neo.framework.populator;

import java.util.Set;

import org.neosimulation.neo.framework.behcurr.Behavior;
import org.neosimulation.neo.framework.database.Triple;
import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.model.IUpdatable;
import org.neosimulation.neo.framework.stateval.CellPoolDouble;
import org.neosimulation.neo.framework.stateval.FaceFluxDouble;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.framework.stateval.StateVal;

/**
 * DoublePopulatorStrategy - A PopulatorStrategy implementation designed to
 * populate the network with double based StateVals.
 * 
 * @author Isaac Griffith
 */
public class DoublePopulatorStrategy extends PopulatorStrategy {

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.PopulatorStrategy#installStateVals(java
     * .util.HashMap, org.neosimulation.neo.framework.Behavior,
     * org.neosimulation.neo.framework.Holon)
     */
    @Override
    public StateVal installStateVals(Class dynamClass, Behavior behavior, Holon holon)
    {
        StateVal stateVal = null;
        if (checkIsCurrencyHolon(Face.class, dynamClass, behavior, holon))
        {
            stateVal = new FaceFluxDouble(dynamClass.getSimpleName(), holon);
        }
        else if (checkIsCurrencyHolon(Cell.class, dynamClass, behavior, holon))
        {
            stateVal = new CellPoolDouble(dynamClass.getSimpleName(), holon);
        }
        else
        {
            stateVal = new StateDouble(dynamClass.getSimpleName(), holon);
        }

        return stateVal;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.PopulatorStrategy#installNegDynamStateVals
     * (java.util.HashMap, org.neosimulation.neo.framework.Behavior,
     * org.neosimulation.neo.framework.Holon)
     */
    @Override
    public void installDefaultStateVals(Set<IUpdatable> updatables, String currencyName,
            Triple<String, String, Object> triple, Holon holon)
    {
        StateDouble doubleVal = null;
        double value = (Double) triple.getValue();
        
        if (checkIsCurrencyHolon(Cell.class, currencyName, triple, holon))
        {
            doubleVal = new CellPoolDouble(triple.getName(), holon, value);
        }
        else if (checkIsCurrencyHolon(Face.class, currencyName, triple, holon))
        {
            doubleVal = new FaceFluxDouble(triple.getName(), holon, value);
        }
        else
        {
            doubleVal = new StateDouble(triple.getName(), holon, value);
        }
        
        holon.addStateVal(doubleVal);
        updatables.add(doubleVal.getUpdater());
    }
}
