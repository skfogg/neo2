/**
 * 
 */
package org.neosimulation.neo.framework.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * BeamSearch - An implementation of the Beam Search graph search algorithm.
 * 
 * @author Isaac Griffith
 */
public class BeamSearch<V, E> extends BreadthFirstSearch<V, E> {

    /**
     * Maximum size of queue.
     */
    private int beamWidth;

    public BeamSearch(int beamWidth)
    {
        super();

        this.beamWidth = beamWidth;
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
        if (nextSet == null)
            throw new GraphSearchException("Cannot enque a null list of nodes.");

        Queue<V> linkedNodes = (LinkedList<V>) nodes;
        for (int i = 0; i < nextSet.size() && i < beamWidth; i++)
        {
            linkedNodes.offer(nextSet.get(i));
        }
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
     * @see org.neosimulation.neo.framework.graph.operations.BreadthFirstSearch#
     * afterEnqueue()
     */
    @Override
    public void afterEnqueue()
    {
        selectBestPaths(nodes);
    }

    /**
     * Utilizes the SearchHeuristics to reduce the queue to within the size of
     * the beam by removing paths.
     */
    private void selectBestPaths(List<V> nodes)
    {
        // TODO implement this method
    }
}
