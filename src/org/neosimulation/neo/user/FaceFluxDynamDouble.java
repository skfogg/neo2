/*
 *
 */

package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.ICalculatorDouble;
import org.neosimulation.neo.framework.dynam.IInitializerDouble;

/**
 * FaceFluxDynamDouble - Double typed Dynam which is attached to a
 * FaceFluxValDouble and is responsible for updating that StateVals value during
 * SimulationModel main loop execution.
 * 
 * @author Isaac Griffith
 */
public abstract class FaceFluxDynamDouble extends FaceFluxDynam implements ICalculatorDouble, IInitializerDouble {

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
