/**
 * 
 */
package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.ICalculatorLong;
import org.neosimulation.neo.framework.dynam.IInitializerLong;

/**
 * ManualDynamLong - Long version of the ManualDynam which is called outside of
 * the main loop from an IUpdatable inside the loop or as part of a chain of
 * ManualDynams each passing the calling AutoDynam's IUpdatable down the chain.
 * 
 * @author Isaac Griffith
 */
public abstract class ManualDynamLong extends ManualDynam implements ICalculatorLong, IInitializerLong {

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.IInitializerLong#initialize()
     */
    @Override
    public long initialize()
    {
        return calculate();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.ICalculatorLong#calculate()
     */
    @Override
    public abstract long calculate();

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynam#setCalcDeps()
     */
    @Override
    public abstract void setCalcDeps();

}
