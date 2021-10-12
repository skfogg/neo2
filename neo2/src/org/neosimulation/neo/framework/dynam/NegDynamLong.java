/**
 * 
 */
package org.neosimulation.neo.framework.dynam;

import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.stateval.StateLong;
import org.neosimulation.neo.user.AutoDynamLong;

/**
 * NegDynamLong - An Long implementation of the default Dynam installed in the
 * From Face of an DependencyEdge when that edge is a symmetric edge. In a
 * symmetric edge the to face simply a negation of the from face.
 * 
 * @author Isaac Griffith
 */
public class NegDynamLong extends AutoDynamLong implements ICalculatorLong, IInitializerLong {

    private StateLong otherLong;
    
    /* (non-Javadoc)
     * @see org.neosimulation.neo.user.AutoDynamLong#calculate()
     */
    @Override
    public long calculate()
    {
        if (otherLong.isNull())
            otherLong.initialize();
        return (otherLong.value * -1);
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.user.AutoDynamLong#setCalcDeps()
     */
    @Override
    public void setCalcDeps()
    {
        otherLong = (StateLong) createDependency(((Face) this.holon).getEdge().getToFace(), stateVal.getName());
    }

}
