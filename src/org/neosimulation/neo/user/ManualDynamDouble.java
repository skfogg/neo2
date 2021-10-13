/*
 *
 */

package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.ICalculatorDouble;
import org.neosimulation.neo.framework.dynam.IInitializerDouble;

/**
 * ManualDynamDouble - Double version of the ManualDynam which is called outside
 * of the main loop from an IUpdatable inside the loop or as part of a chain of
 * ManualDynams each passing the calling AutoDynam's IUpdatable down the chain.
 * 
 * @author Isaac Griffith
 */
public abstract class ManualDynamDouble extends ManualDynam implements ICalculatorDouble, IInitializerDouble {

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.IInitializerDouble#initialize()
     */
    @Override
    public double initialize()
    {
        return calculate();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.ICalculatorDouble#calculate()
     */
    @Override
    public abstract double calculate();

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynam#setCalcDeps()
     */
    @Override
    public abstract void setCalcDeps();

}
