/**
 * 
 */
package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.ICalculatorLong;
import org.neosimulation.neo.framework.dynam.IInitializerLong;
import org.neosimulation.neo.framework.model.StateValContainerException;
import org.neosimulation.neo.framework.stateval.CellPoolLong;
import org.neosimulation.neo.framework.stateval.FaceFluxLong;

/**
 * CellPoolDynamLong - A long Based AutoDynam attached to a CellPoolVal and which is responsible for updating that CellPoolVal's value
 * 
 * @author Isaac Griffith
 */
public abstract class CellPoolDynamLong extends CellPoolDynam implements ICalculatorLong, IInitializerLong {

    /**
     * The transient flux value updated by each attached face coming long the associated cell.
     */
    private long transientValue = 0;
    /**
     * A CellPoollong reference to this CellPoolDynam's StateVal
     */
    private CellPoolLong cellPool;

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamlong#calculate()
     */
    public synchronized long calculate()
    {
        // Default Implementation is the existing StateVal value plus the change
        // from all of the fluxes
        long retVal = cellPool.value + transientValue;
        transientValue = 0;

        return retVal;
    }

    /**
     * Updates the transient flux value of this CellPoolDynamlong's associated CellPoolVal to minimize redundant method calls to the corresponding FaceFluxVal
     * to obtain the values.
     * 
     * @param flux
     *            The FaceFluxlong whose value is to update the transient flux value
     * @throws StateValContainerException
     * @throws CellPoolDynamException
     */
    public synchronized void updateTransientValue(FaceFluxLong flux) throws StateValContainerException,
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
    public long getTransientValue()
    {
        return transientValue;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.IInitializerlong#initialize()
     */
    @Override
    public long initialize()
    {
        return calculate();
    }

    /**
     * Calculates the mass remaining based on current fired faces
     * 
     * @return mass remaining
     */
    public long getRemainderAsMass()
    {
        return Math.max(0, cellPool.value + transientValue);
    }

    /**
     * Calculates the mass remaining based on current fired faces
     * 
     * @return mass remaining
     */
    public long getRemainderAsRate()
    {
        long multiplier = (long) this.getStateVal().getUpdater().getMultiplier();
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

        cellPool = (CellPoolLong) stateVal;
    }

}
