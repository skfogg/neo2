/**
 * 
 */
package org.neosimulation.neo.framework.dynam;

import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateInt;
import org.neosimulation.neo.user.FaceFluxDynamInt;

/**
 * NegFaceFluxDynamInt - An Integer based Dynam installed into Symmetric Edges.
 * Neg Dynams negate an associated dynam's value, associated with a StateVal
 * with the same name as this Dynam's StateVal. This particular Dynam negate's a
 * FaceFluxVal and thus is a currencey fluxing dynam.
 * 
 * @author Isaac Grffith
 */
public class NegFaceFluxDynamInt extends FaceFluxDynamInt {

    /**
     * The StateInt to which this NegFaceFluxDynamInt is to represent a Negation
     * of
     */
    protected StateInt otherInt;

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamDouble#calculate()
     */
    @Override
    public int calculate()
    {
        if (otherInt.isNull())
            otherInt.initialize();
        return (otherInt.value * -1);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamDouble#setCalcDeps()
     */
    @Override
    public void setCalcDeps()
    {
        otherInt = (StateInt) createDependency(((Face) this.holon).getEdge().getToFace(), stateVal.getName());
    }
}
