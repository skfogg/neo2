/*
 *
 */

package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.ICalculatorInt;
import org.neosimulation.neo.framework.dynam.IInitializerInt;

/**
 * FaceFluxDynamInt - Integer typed Dynam which is attached to a FaceFluxValInt
 * and is responsible for updating that StateVals value during SimulationModel
 * main loop execution.
 * 
 * @author Isaac Griffith
 */
public abstract class FaceFluxDynamInt extends FaceFluxDynam implements ICalculatorInt, IInitializerInt {

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
