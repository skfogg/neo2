/**
 * 
 */
package org.neosimulation.neo.framework.dynam;

import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateGeneric;
import org.neosimulation.neo.user.FaceFluxDynamGeneric;

/**
 * NegDynamGeneric - A Generic implementation of the default Dynam installed in
 * the From Face of an DependencyEdge when that edge is a symmetric edge. In a
 * symmetric edge the to face simply a negation of the from face.
 * 
 * @author Isaac Griffith
 * @param <CLASS>
 *            The generic type of this NegDynamGeneric
 */
public class NegDynamGeneric<CLASS> extends FaceFluxDynamGeneric<CLASS> {

    /**
     * The StateGeneric to which this NegDynamGeneric is to represent a Negation
     * of.
     */
    private StateGeneric<CLASS> otherGeneric;

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamGeneric#calculate()
     */
    @Override
    public CLASS calculate()
    {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamGeneric#setCalcDeps()
     */
    @SuppressWarnings("unchecked")
    @Override
    public void setCalcDeps()
    {
        otherGeneric = (StateGeneric<CLASS>) createDependency(((Face) this.holon).getEdge().getToFace(),
                stateVal.getName());
    }

}
