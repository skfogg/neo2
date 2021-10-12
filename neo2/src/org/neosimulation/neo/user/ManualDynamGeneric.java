/**
 * 
 */
package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.ICalculatorGeneric;
import org.neosimulation.neo.framework.dynam.IInitializerGeneric;

/**
 * ManualDynamGeneric - Generic typed ManualDynam, which executes outside the
 * main loop of the SimulationModel, but is called from another Dynam within the
 * main loop.
 * 
 * @author Isaac Griffith
 * @param <CLASS>
 *            Type of the ManualDynamGeneric class
 */
public abstract class ManualDynamGeneric<CLASS> extends ManualDynam implements ICalculatorGeneric<CLASS>,
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
