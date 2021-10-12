/**
 * 
 */
package org.neosimulation.neo.framework.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.uci.ics.jung.graph.Graph;

/**
 * BreadthFirstSearch - An implementation of the GraphSearch Template Patter.
 * This class provides an implementation of breadth first search to search a
 * provided graph.
 * 
 * @author Isaac Griffith
 */
public class BreadthFirstSearch<V, E> extends GraphSearch<V, E> {

    /**
     * Creates and intializes a new instance of BreadthFirstSearch.
     */
    public BreadthFirstSearch()
    {
        this.nodes = new LinkedList<V>();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.graph.operations.GraphSearch#removeFront
     * ()
     */
    @Override
    public V removeFront()
    {
        Queue<V> linkedNodes = (LinkedList<V>) nodes;
        return linkedNodes.poll();
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
        for (int i = 0; i < nextSet.size(); i++)
        {
            linkedNodes.offer(nextSet.get(i));
        }
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
        if (node == null)
            throw new GraphSearchException("Cannot expand a null node.");
        else if (graph == null || graph.getVertexCount() == 0)
            throw new GraphSearchException("Cannot expand a node within an empty or null graph.");
        else if (!graph.getVertices().contains(node))
            throw new GraphSearchException("Cannot expand a node not within the graph.");
        
        return new ArrayList<V>(graph.getNeighbors(node));
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
        // In order to simply generate all the BFS paths always return false;
        return false;
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.graph.operations.GraphSearch#afterEnqueue()
     */
    @Override
    public void afterEnqueue()
    {
    }
}
