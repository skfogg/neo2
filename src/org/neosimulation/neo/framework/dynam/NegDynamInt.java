/**
 * 
 */
package org.neosimulation.neo.framework.dynam;

import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateInt;
import org.neosimulation.neo.user.AutoDynamInt;

/**
 * NegDynamInt - An Integer implementation of the default Dynam installed in the
 * From Face of an DependencyEdge when that edge is a symmetric edge. In a
 * symmetric edge the to face simply a negation of the from face.
 * 
 * @author Isaac Griffith
 */
public class NegDynamInt extends AutoDynamInt {

    /**
     * The StateInt to which this NegDynamInt is to represent a Negation of.
     */
    private StateInt otherInt;

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamInt#calculate()
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
     * @see org.neosimulation.neo.user.CalcDynamInt#setCalcDeps()
     */
    @Override
    public void setCalcDeps()
    {
        otherInt = (StateInt) createDependency(((Face) this.holon).getEdge().getToFace(), stateVal.getName());
    }

}
