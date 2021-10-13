/**
 * 
 */
package org.neosimulation.neo.framework.graph;

import java.util.List;

import edu.uci.ics.jung.graph.Graph;

/**
 * IDAStarSearch - An implementation of the Iterative Deepening A* Search
 * algorithm for graphs.
 * 
 * @author Isaac Griffith
 */
public class IDAStarSearch<V, E> extends GraphSearch<V, E> {

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.graph.operations.GraphSearch#removeFront
     * ()
     */
    @Override
    public V removeFront()
    {
        // TODO Implement this method
        return null;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.graph.operations.GraphSearch#enqueue(
     * java.util.List)
     */
    @Override
    public void enqueue(List<V> nextSet) throws GraphSearchException
    {
        // TODO Implement this method

    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.graph.operations.GraphSearch#expand(org
     * .neosimulation.neo.framework.graph.Node,
     * org.neosimulation.neo.framework.graph.Graph)
     */
    @Override
    public List<V> expand(V node, Graph<V, E> graph) throws GraphSearchException
    {
        // TODO Implement this method
        return null;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.graph.operations.GraphSearch#goalTest
     * (org.neosimulation.neo.framework.graph.Node)
     */
    @Override
    public boolean goalTest(V node) throws GraphSearchException
    {
        // TODO Implement this method
        return false;
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.graph.operations.GraphSearch#afterEnqueue()
     */
    @Override
    public void afterEnqueue()
    {
        // TODO Auto-generated method stub
        
    }

}
