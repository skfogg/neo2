/**
 * 
 */
package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.ICalculatorLong;
import org.neosimulation.neo.framework.dynam.IInitializerLong;

/**
 * AutoDynamLong - An Long based Dynam whose calculate method is invoked
 * (automatically) by the main loop manager of the SimulationModel
 * 
 * @author Isaac Griffith
 *
 */
public abstract class AutoDynamLong extends AutoDynam implements ICalculatorLong, IInitializerLong {

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.IInitializerLogn#initialize()
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

    /* (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynam#setCalcDeps()
     */
    @Override
    public abstract void setCalcDeps();

}
