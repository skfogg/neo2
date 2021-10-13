package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.ICalculatorInt;
import org.neosimulation.neo.framework.dynam.IInitializerInt;

/**
 * AutoDynamInt - An Integer based Dynam whose calculate method is invoked
 * (automatically) by the main loop manager of the SimulationModel
 * 
 * @author Isaac Griffith
 */
public abstract class AutoDynamInt extends AutoDynam implements ICalculatorInt, IInitializerInt {

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.IInitializerInt#initialize()
     */
    @Override
    public int initialize()
    {
        return calculate();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.ICalculatorInt#calculate()
     */
    @Override
    public abstract int calculate();

    /* (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynam#setCalcDeps()
     */
    @Override
    public abstract void setCalcDeps();

}
