/**
 * 
 */
package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.ICalculatorGeneric;
import org.neosimulation.neo.framework.dynam.IInitializerGeneric;
import org.neosimulation.neo.framework.stateval.CellPoolDouble;
import org.neosimulation.neo.framework.stateval.CellPoolGeneric;
import org.neosimulation.neo.framework.stateval.FaceFluxGeneric;
import org.neosimulation.neo.framework.time.TimeKeeper;

/**
 * CellPoolDynamGeneric - An Generic Based AutoDynam attached to a CellPoolVal
 * and which is responsible for updating that CellPoolVal's value
 * 
 * @author Isaac Griffith
 * @param <CLASS>
 *            The type of value to be stored in the cell
 */
public abstract class CellPoolDynamGeneric<CLASS> extends CellPoolDynam implements ICalculatorGeneric<CLASS>,
        IInitializerGeneric<CLASS> {

    /**
     * The transient value which is used to update the stored value of the
     * associated CellPoolValGeneric
     */
    private CLASS transientValue;
    /**
     * A CellPoolGeneric reference to this CellPoolDynam's StateVal
     */
    private CellPoolGeneric<CLASS> cellPool;
    /**
     * The Model's timekeeper
     */
    private TimeKeeper timeKeeper;

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamGeneric#calculate()
     */
    @Override
    public CLASS calculate()
    {
        return null;
    }

    /**
     * Updates the transient flux value of this CellPoolDynamGeneric's
     * associated CellPoolVal to minimize redundant method calls to the
     * corresponding FaceFluxVal to obtain the values.
     * 
     * @param fluxVal
     *            The FaceFluxGeneric whose value is to update the transient
     *            flux value
     */
    public synchronized void updateTransientValue(FaceFluxGeneric<CLASS> fluxVal)
    {
        // TODO: need to define fully define the interface in NEOObject to
        // include a way to handle this and other possiblities
    }

    /**
     * Retrieves the current value of the transient value (change due to flux)
     * for this CellPool dynam
     * 
     * @return The current value of the transient value.
     */
    public CLASS getTransientValue()
    {
        return transientValue;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.IInitializerGeneric#initialize()
     */
    @Override
    public CLASS initialize()
    {
        return calculate();
    }

    /**
     * Calculates the mass remaining based on current fired faces
     * 
     * @return mass remaining
     */
    public CLASS getRemainderAsMass()
    {
        //return Math.max(0, cellPool.value + transientValue);
        return null;
    }

    /**
     * Calculates the mass remaining based on current fired faces
     * 
     * @return mass remaining
     */
    public CLASS getRemainderAsRate()
    {
        //return Math.max(0, cellPool.value + (transientValue / timeKeeper.getTimeStep()));
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynam#setInitDeps()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setCalcDeps()
    {
        super.setInitDeps();

        cellPool = (CellPoolGeneric<CLASS>) stateVal;
        timeKeeper = cellPool.getHolon().getSimulationModel().getTimeKeeper();
    }
}
