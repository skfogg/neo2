/**
 * 
 */
package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.ICalculatorGeneric;
import org.neosimulation.neo.framework.dynam.IInitializerGeneric;

/**
 * AutoDynamGeneric - An Double based Dynam whose calculate method is invoked
 * (automatically) by the main loop manager of the SimulationModel
 * 
 * @author Isaac Griffith
 * @param <CLASS>
 *            The type of the AutoDynamGeneric
 */
public abstract class AutoDynamGeneric<CLASS> extends AutoDynam implements ICalculatorGeneric<CLASS>,
        IInitializerGeneric<CLASS> {

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.IInitializerGeneric#initialize()
     */
    @Override
    public CLASS initialize()
    {
        return calculate();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.ICalculatorGeneric#calculate()
     */
    @Override
    public abstract CLASS calculate();

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynam#setCalcDeps()
     */
    @Override
    public abstract void setCalcDeps();

}
