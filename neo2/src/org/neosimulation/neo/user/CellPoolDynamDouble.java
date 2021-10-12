/*
 *
 */
package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.ICalculatorDouble;
import org.neosimulation.neo.framework.dynam.IInitializerDouble;
import org.neosimulation.neo.framework.model.StateValContainerException;
import org.neosimulation.neo.framework.stateval.CellPoolDouble;
import org.neosimulation.neo.framework.stateval.FaceFluxDouble;

/**
 * CellPoolDynamDouble - An Double Based AutoDynam attached to a CellPoolVal and
 * which is responsible for updating that CellPoolVal's value
 * 
 * @author Isaac Griffith
 */
public abstract class CellPoolDynamDouble extends CellPoolDynam implements ICalculatorDouble, IInitializerDouble {

    /**
     * The Transient value of flux coming in from all of the incoming faces.
     */
    private double transientValue = 0;
    /**
     * A CellPoolDouble reference to this CellPoolDynam's StateVal
     */
    private CellPoolDouble cellPool;

    /**
     * Default Implementation is the existing StateVal value plus the change
     * from all of the incoming fluxes
     * 
     * @return current value plus that of the transient value
     */
    public double calculate()
    {
        double retVal = cellPool.value + transientValue;
        transientValue = 0;

        return retVal;
    }

    /**
     * Updates the transient flux value of this CellPoolDynamDouble's associated
     * CellPoolVal to minimize redundant method calls to the corresponding
     * FaceFluxVal to obtain the values.
     * 
     * @param flux
     *            The FaceFluxDouble whose value is to update the transient flux
     *            value
     * @throws StateValContainerException
     * @throws CellPoolDynamException 
     */
    public synchronized void updateTransientValue(FaceFluxDouble flux) throws StateValContainerException, CellPoolDynamException
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
     * Retrieves the current value of the transient value (change due to flux)
     * for this CellPool dynam
     * 
     * @return The current value of the transient value.
     */
    public double getTransientValue()
    {
        return transientValue;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.IInitializerDouble#initialize()
     */
    @Override
    public double initialize()
    {
        return calculate();
    }

    /**
     * Calculates the mass remaining based on current fired faces
     * 
     * @return mass remaining
     */
    public double getRemainderAsMass()
    {
        return Math.max(0, cellPool.value + transientValue);
    }

    /**
     * Calculates the mass remaining based on current fired faces
     * 
     * @return mass remaining
     */
    public double getRemainderAsRate()
    {
        double multiplier = this.getStateVal().getUpdater().getMultiplier();
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
        
        cellPool = (CellPoolDouble) stateVal;
    }
}
