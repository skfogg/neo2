/**
 * 
 */
package org.neosimulation.neo.framework.dynam;

import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.FaceFluxDynamDouble;

/**
 * NegDynamDoulbe - A Double implementation of the default Dynam installed in
 * the From Face of an DependencyEdge when that edge is a symmetric edge. In a
 * symmetric edge the to face simply a negation of the from face.
 * 
 * @author Isaac Griffith
 */
public class NegFaceFluxDynamDouble extends FaceFluxDynamDouble {

    /**
     * The StateDouble to which this NegFaceFluxDynamDouble is to represent a
     * Negation of
     */
    protected StateDouble otherDouble;

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamDouble#calculate()
     */
    @Override
    public double calculate()
    {
        if (otherDouble.isNull())
            otherDouble.initialize();
        return (otherDouble.value * -1);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamDouble#setCalcDeps()
     */
    @Override
    public void setCalcDeps()
    {
        otherDouble = (StateDouble) createDependency(((Face) this.holon).getEdge().getToFace(), stateVal.getName());
    }
}
