/*
 *
 */

package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.ICalculatorInt;
import org.neosimulation.neo.framework.dynam.IInitializerInt;
import org.neosimulation.neo.framework.model.StateValContainerException;
import org.neosimulation.neo.framework.stateval.CellPoolInt;
import org.neosimulation.neo.framework.stateval.FaceFluxInt;

/**
 * CellPoolDynamInt - An Integer Based AutoDynam attached to a CellPoolVal and which is responsible for updating that CellPoolVal's value
 * 
 * @author Isaac Griffith
 */
public abstract class CellPoolDynamInt extends CellPoolDynam implements ICalculatorInt, IInitializerInt {

    /**
     * The transient flux value updated by each attached face coming into the associated cell.
     */
    private int transientValue = 0;
    /**
     * A CellPoolInt reference to this CellPoolDynam's StateVal
     */
    private CellPoolInt cellPool;

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamInt#calculate()
     */
    public synchronized int calculate()
    {
        // Default Implementation is the existing StateVal value plus the change
        // from all of the fluxes
        int retVal = cellPool.value + transientValue;
        transientValue = 0;

        return retVal;
    }

    /**
     * Updates the transient flux value of this CellPoolDynamInt's associated CellPoolVal to minimize redundant method calls to the corresponding FaceFluxVal to
     * obtain the values.
     * 
     * @param flux
     *            The FaceFluxInt whose value is to update the transient flux value
     * @throws StateValContainerException
     * @throws CellPoolDynamException
     */
    public synchronized void updateTransientValue(FaceFluxInt flux) throws StateValContainerException,
            CellPoolDynamException
    {
        if (flux != null)
        {
            if (!flux.isNull())
            {
                double multiplier = flux.getUpdater().getMultiplier();
                transientValue += multiplier * flux.value;
            }
            else
            {
                throw new StateValContainerException("Grabbing null value for updater");
            }
        }
        else
        {
            throw new CellPoolDynamException("Specified FaceFluxDouble was null");
        }
    }

    /**
     * Retrieves the current value of the transient value (change due to flux) for this CellPool dynam
     * 
     * @return The current value of the transient value
     */
    public int getTransientValue()
    {
        return transientValue;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.IInitializerInt#initialize()
     */
    @Override
    public int initialize()
    {
        return calculate();
    }

    /**
     * Calculates the mass remaining based on current fired faces
     * 
     * @return mass remaining
     */
    public int getRemainderAsMass()
    {
        return Math.max(0, cellPool.value + transientValue);
    }

    /**
     * Calculates the mass remaining based on current fired faces
     * 
     * @return mass remaining
     */
    public int getRemainderAsRate()
    {
        int multiplier = (int) this.getStateVal().getUpdater().getMultiplier();
        return Math.max(0, cellPool.value + (transientValue / multiplier));
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CellPoolDynam#setCalcDeps()
     */
    @Override
    public synchronized void setCalcDeps()
    {
        // TODO Auto-generated method stub
        super.setCalcDeps();

        cellPool = (CellPoolInt) stateVal;
    }
}
