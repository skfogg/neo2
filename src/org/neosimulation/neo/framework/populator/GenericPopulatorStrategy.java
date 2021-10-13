/**
 * 
 */
package org.neosimulation.neo.framework.populator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.TypeVariable;
import java.util.Set;

import org.neosimulation.neo.framework.behcurr.Behavior;
import org.neosimulation.neo.framework.database.Triple;
import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.model.IUpdatable;
import org.neosimulation.neo.framework.stateval.CellPoolGeneric;
import org.neosimulation.neo.framework.stateval.FaceFluxGeneric;
import org.neosimulation.neo.framework.stateval.StateGeneric;
import org.neosimulation.neo.framework.stateval.StateVal;
import org.neosimulation.neo.user.NEOObject;

/**
 * GenericPopulatorStrategy - A PopulatorStrategy designed to populate the
 * Network with generic typed statevals.
 * 
 * @author Isaac Griffith
 */
public class GenericPopulatorStrategy extends PopulatorStrategy {

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.PopulatorStrategy#installStateVals(java
     * .lang.Class, org.neosimulation.neo.framework.Behavior,
     * org.neosimulation.neo.framework.Holon)
     */
    @Override
    public StateVal installStateVals(Class dynamClass, Behavior behavior, Holon holon)
    {
        // FIXME: Requires Refactoring
        StateVal stateVal = null;
        TypeVariable[] types = dynamClass.getTypeParameters();

        if (types.length == 0)
        {
            if (checkIsCurrencyHolon(Face.class, dynamClass, behavior, holon))
            {
                stateVal = new FaceFluxGeneric(dynamClass.getSimpleName(), holon);
            }
            else if (checkIsCurrencyHolon(Cell.class, dynamClass, behavior, holon))
            {
                stateVal = new CellPoolGeneric(dynamClass.getSimpleName(), holon);
            }
            else
            {
                stateVal = new StateGeneric(dynamClass.getSimpleName(), holon);
            }
        }
        else
        {
            Class neoObj = (Class) types[0].getClass();
            if (NEOObject.class.isAssignableFrom(neoObj))
            {
                try
                {
                    Constructor con = neoObj.getDeclaredConstructor();
                    NEOObject value = (NEOObject) con.newInstance();

                    if (checkIsCurrencyHolon(Face.class, dynamClass, behavior, holon))
                    {
                        stateVal = new FaceFluxGeneric<NEOObject>(dynamClass.getSimpleName(), holon);
                    }
                    else if (checkIsCurrencyHolon(Cell.class, dynamClass, behavior, holon))
                    {
                        stateVal = new CellPoolGeneric<NEOObject>(dynamClass.getSimpleName(), holon);
                    }
                    else
                    {
                        stateVal = new StateGeneric<NEOObject>(dynamClass.getSimpleName(), holon);
                    }

                    // TODO The next statement needs to be looked at a little
                    // deeper
                    StateGeneric<NEOObject> sgn = (StateGeneric<NEOObject>) stateVal;
                    sgn.value = value;
                }
                catch (SecurityException e)
                {
                    NEOLogger.logException(
                            holon.getSimulationModel(),
                            "Security exception while try to reflectively instantiate a NEOObject: "
                                    + neoObj.getSimpleName(), e);
                }
                catch (NoSuchMethodException e)
                {
                    NEOLogger.logException(holon.getSimulationModel(),
                            "The NEOObject to be instantiated does not have the proper constructor.", e);
                }
                catch (IllegalArgumentException e)
                {
                    NEOLogger.logException(holon.getSimulationModel(),
                            "Improper arguments to the constructor of the NEOObject being instantiated.", e);
                }
                catch (InstantiationException e)
                {
                    NEOLogger.logException(holon.getSimulationModel(),
                            "Could not reflectively instantiate the NEOObject: " + neoObj.getSimpleName(), e);
                }
                catch (IllegalAccessException e)
                {
                    NEOLogger.logException(
                            holon.getSimulationModel(),
                            "Security issue in attempting to reflectively construct the following NEOObject: "
                                    + neoObj.getSimpleName(), e);
                }
                catch (InvocationTargetException e)
                {
                    NEOLogger.logException(holon.getSimulationModel(),
                            "Could not reflectively call the constructor for the NEOObject: " + neoObj.getSimpleName(),
                            e);
                }
            }
            else if (String.class.isAssignableFrom(neoObj))
            {
                if (checkIsCurrencyHolon(Face.class, dynamClass, behavior, holon))
                {
                    stateVal = new FaceFluxGeneric<String>(dynamClass.getSimpleName(), holon);
                }
                else if (checkIsCurrencyHolon(Cell.class, dynamClass, behavior, holon))
                {
                    stateVal = new CellPoolGeneric<String>(dynamClass.getSimpleName(), holon);
                }
                else
                {
                    stateVal = new StateGeneric<String>(dynamClass.getSimpleName(), holon);
                }
            }
        }

        return stateVal;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.PopulatorStrategy#installDefaultStateVals
     * (java.util.Set, java.lang.String,
     * org.neosimulation.neo.framework.database.Triple,
     * org.neosimulation.neo.framework.Holon)
     */
    @Override
    public void installDefaultStateVals(Set<IUpdatable> updatables, String currencyName,
            Triple<String, String, Object> triple, Holon holon)
    {
        StateGeneric<Object> genVal = null;
        Object value = triple.getValue();

        if (checkIsCurrencyHolon(Cell.class, currencyName, triple, holon))
        {
            genVal = new CellPoolGeneric<Object>(triple.getName(), holon, value);
        }
        else if (checkIsCurrencyHolon(Face.class, currencyName, triple, holon))
        {
            genVal = new FaceFluxGeneric<Object>(triple.getName(), holon, value);
        }
        else
        {
            genVal = new StateGeneric<Object>(triple.getName(), holon, value);
        }

        holon.addStateVal(genVal);
        updatables.add(genVal.getUpdater());
    }

}
