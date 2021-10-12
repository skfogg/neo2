/**
 * 
 */
package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.ICalculatorLong;
import org.neosimulation.neo.framework.dynam.IInitializerLong;

/**
 * FaceFluxDynamLong - Long typed Dynam which is attached to a FaceFluxValInt
 * and is responsible for updating that StateVals value during SimulationModel
 * main loop execution.
 * 
 * @author IsaacGriffith
 */
public abstract class FaceFluxDynamLong extends FaceFluxDynam implements ICalculatorLong, IInitializerLong {

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.dynam.IInitializerLong#initialize()
     */
    @Override
    public long initialize()
    {
        return calculate();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.dynam.ICalculatorLong#calculate()
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
