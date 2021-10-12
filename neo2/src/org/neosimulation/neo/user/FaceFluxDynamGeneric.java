/**
 * 
 */
package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.ICalculatorGeneric;
import org.neosimulation.neo.framework.dynam.IInitializerGeneric;

/**
 * FaceFluxDynamGeneric - Generic typed Dynam which is attached to a
 * FaceFluxValDouble and is responsible for updating that StateVals value during
 * SimulationModel main loop execution.
 * 
 * @author Isaac Griffith
 * @param <CLASS>
 *            The type of this FaceFluxDynam
 */
public abstract class FaceFluxDynamGeneric<CLASS> extends FaceFluxDynam implements ICalculatorGeneric<CLASS>,
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
