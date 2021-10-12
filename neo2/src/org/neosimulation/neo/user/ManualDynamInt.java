package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.ICalculatorInt;
import org.neosimulation.neo.framework.dynam.IInitializerInt;

/**
 * ManualDynamInt - Integer based ManualDynam, which executes outside the main
 * loop of the SimulationModel, but is called from another Dynam within the main
 * loop
 * 
 * @author Isaac Griffith
 */
public abstract class ManualDynamInt extends ManualDynam implements ICalculatorInt, IInitializerInt {

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

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynam#setCalcDeps()
     */
    @Override
    public abstract void setCalcDeps();
}
