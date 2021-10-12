/**
 * 
 */
package org.neosimulation.neo.framework.dynam;

import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateLong;
import org.neosimulation.neo.user.FaceFluxDynamLong;

/**
 * NegFaceFluxDynamLong - An Long based Dynam installed into Symmetric Edges.
 * Neg Dynams negate an associated dynam's value, associated with a StateVal
 * with the same name as this Dynam's StateVal. This particular Dynam negate's a
 * FaceFluxVal and thus is a currencey fluxing dynam.
 * 
 * @author Isaac Grffith
 */
public class NegFaceFluxDynamLong extends FaceFluxDynamLong implements ICalculatorLong, IInitializerLong {

    /**
     * The StateLong to which this NegFaceFluxDynamLong is to represent the
     * Negation of
     */
    protected StateLong otherLong;

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamDouble#calculate()
     */
    @Override
    public long calculate()
    {
        if (otherLong.isNull())
            otherLong.initialize();
        return (otherLong.value * -1);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamDouble#setCalcDeps()
     */
    @Override
    public void setCalcDeps()
    {
        otherLong = (StateLong) createDependency(((Face) this.holon).getEdge().getToFace(), stateVal.getName());
    }

}
