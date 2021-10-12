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
import org.neosimulation.neo.framework.stateval.CellPoolInt;
import org.neosimulation.neo.framework.stateval.FaceFluxInt;
import org.neosimulation.neo.framework.stateval.StateInt;
import org.neosimulation.neo.framework.stateval.StateVal;

/**
 * IntegerPopulatorStrateg - A Strategy for populating the Network with Integer
 * based StateVals.
 * 
 * @author Isaac Griffith
 */
public class IntegerPopulatorStrategy extends PopulatorStrategy {

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.PopulatorStrategy#installStateVals(java
     * .lang.Class, org.neosimulation.neo.framework.StateVal,
     * org.neosimulation.neo.framework.Behavior,
     * org.neosimulation.neo.framework.Holon)
     */
    @Override
    public StateVal installStateVals(Class dynamClass, Behavior behavior, Holon holon)
    {
        StateVal stateVal = null;
        if (checkIsCurrencyHolon(Face.class, dynamClass, behavior, holon))
        {
            stateVal = new FaceFluxInt(dynamClass.getSimpleName(), holon);
        }
        else if (checkIsCurrencyHolon(Cell.class, dynamClass, behavior, holon))
        {
            stateVal = new CellPoolInt(dynamClass.getSimpleName(), holon);
        }
        else
        {
            stateVal = new StateInt(dynamClass.getSimpleName(), holon);
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
        StateInt intVal = null;
        int value = (Integer) triple.getValue();
        
        if (checkIsCurrencyHolon(Cell.class, currencyName, triple, holon))
        {
            intVal = new CellPoolInt(triple.getName(), holon, value);
        }
        else if (checkIsCurrencyHolon(Face.class, currencyName, triple, holon))
        {
            intVal = new FaceFluxInt(triple.getName(), holon, value);
        }
        else
        {
            intVal = new StateInt(triple.getName(), holon, value);
        }
        
        holon.addStateVal(intVal);
        updatables.add(intVal.getUpdater());
    }

}
