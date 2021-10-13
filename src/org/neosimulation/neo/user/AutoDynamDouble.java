package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.ICalculatorDouble;
import org.neosimulation.neo.framework.dynam.IInitializerDouble;

/**
 * AutoDynamDouble - An Double based Dynam whose calculate method is invoked
 * (automatically) by the main loop manager of the SimulationModel
 * 
 * @author Isaac Griffith
 */
public abstract class AutoDynamDouble extends AutoDynam implements ICalculatorDouble, IInitializerDouble {

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
