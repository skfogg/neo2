/**
 * 
 */
package org.neosimulation.neo.framework.dynam;

import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.user.AutoDynamDouble;

/**
 * NegDynamDouble - An Double implementation of the default Dynam installed in
 * the From Face of an DependencyEdge when that edge is a symmetric edge. In a
 * symmetric edge the to face simply a negation of the from face.
 * 
 * @author Isaac Griffith
 */
public class NegDynamDouble extends AutoDynamDouble {

    /**
     * The StateDouble to which this NegDynamDouble is to represent a Negation
     * of.
     */
    private StateDouble otherDouble;

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamInt#calculate()
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
     * @see org.neosimulation.neo.user.CalcDynamInt#setCalcDeps()
     */
    @Override
    public void setCalcDeps()
    {
        otherDouble = (StateDouble) createDependency(((Face) this.holon).getEdge().getToFace(), stateVal.getName());
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.IInitializerDouble#initialize()
     */
    @Override
    public double initialize()
    {
        return calculate();
    }
}
