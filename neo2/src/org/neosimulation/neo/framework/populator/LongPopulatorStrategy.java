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
import org.neosimulation.neo.framework.stateval.CellPoolLong;
import org.neosimulation.neo.framework.stateval.FaceFluxLong;
import org.neosimulation.neo.framework.stateval.StateLong;
import org.neosimulation.neo.framework.stateval.StateVal;

/**
 * LongPopulatorStrategy - A Strategy for populating the Network with Long based
 * StateVals.
 * 
 * @author Isaac Griffith
 */
public class LongPopulatorStrategy extends PopulatorStrategy {

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.populator.PopulatorStrategy#installStateVals
     * (java.lang.Class, org.neosimulation.neo.framework.behcurr.Behavior,
     * org.neosimulation.neo.framework.holon.Holon)
     */
    @Override
    public StateVal installStateVals(Class dynamClass, Behavior behavior, Holon holon)
    {
        StateVal stateVal = null;
        if (checkIsCurrencyHolon(Face.class, dynamClass, behavior, holon))
        {
            stateVal = new FaceFluxLong(dynamClass.getSimpleName(), holon);
        }
        else if (checkIsCurrencyHolon(Cell.class, dynamClass, behavior, holon))
        {
            stateVal = new CellPoolLong(dynamClass.getSimpleName(), holon);
        }
        else
        {
            stateVal = new StateLong(dynamClass.getSimpleName(), holon);
        }

        return stateVal;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.populator.PopulatorStrategy#
     * installDefaultStateVals(java.util.Set, java.lang.String,
     * org.neosimulation.neo.framework.database.Triple,
     * org.neosimulation.neo.framework.holon.Holon)
     */
    @Override
    public void installDefaultStateVals(Set<IUpdatable> updatables, String currencyName,
            Triple<String, String, Object> triple, Holon holon)
    {
        StateLong longVal = null;
        long value = (Long) triple.getValue();

        if (checkIsCurrencyHolon(Cell.class, currencyName, triple, holon))
        {
            longVal = new CellPoolLong(triple.getName(), holon, value);
        }
        else if (checkIsCurrencyHolon(Face.class, currencyName, triple, holon))
        {
            longVal = new FaceFluxLong(triple.getName(), holon, value);
        }
        else
        {
            longVal = new StateLong(triple.getName(), holon, value);
        }

        holon.addStateVal(longVal);
        updatables.add(longVal.getUpdater());

    }

}
