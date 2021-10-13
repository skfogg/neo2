/**
 * 
 */
package org.neosimulation.neo.framework.dynam;

import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateGeneric;
import org.neosimulation.neo.user.FaceFluxDynamGeneric;

/**
 * NegFaceFluxDynamGeneric - An Generic based Dynam installed into Symmetric
 * Edges. Neg Dynams negate an associated dynam's value, associated with a
 * StateVal with the same name as this Dynam's StateVal. This particular Dynam
 * negate's a FaceFluxVal and thus is a currencey fluxing dynam.
 * 
 * @author Isaac Griffith
 * @param <CLASS>
 *            The generic type of this NegFaceFluxDynamGeneric
 */
public class NegFaceFluxDynamGeneric<CLASS> extends FaceFluxDynamGeneric<CLASS> {

    /**
     * The StateGeneric to which this NegFaceFluxDynamGeneric is to represent a
     * Negation of
     */
    private StateGeneric<CLASS> otherGeneric;

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamDouble#calculate()
     */
    @Override
    public CLASS calculate()
    {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamDouble#setCalcDeps()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setCalcDeps()
    {
        otherGeneric = (StateGeneric<CLASS>) createDependency(((Face) this.holon).getEdge().getToFace(),
                stateVal.getName());
    }
}