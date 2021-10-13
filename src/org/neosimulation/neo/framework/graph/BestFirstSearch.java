/**
 * 
 */
package org.neosimulation.neo.framework.graph;

import java.util.List;

/**
 * BestFirstSearch - An implementation of the Best First Search graph search
 * algorithm.
 * 
 * @author Isaac Griffith
 */
public class BestFirstSearch<V, E> extends DepthFirstSearch<V, E> {

    public BestFirstSearch()
    {
        super();
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

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.graph.operations.GraphSearch#afterEnqueue
     * ()
     */
    @Override
    public void afterEnqueue()
    {
        sort(this.nodes);
    }

    /**
     * Sorts the list of nodes based on the search heuristics
     * 
     * @param queue
     *            List of nodes to be sorted
     */
    private void sort(List<V> queue)
    {
        // TODO implement this method
    }
}
