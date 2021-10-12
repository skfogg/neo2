/**
 * 
 */
package org.neosimulation.neo.framework.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import edu.uci.ics.jung.graph.Graph;

/**
 * DepthFirstSearch - An implementation of the GraphSearch Template Patter. This
 * class provides an implmentation of depth first search to search a provided
 * graph.
 * 
 * @author Isaac Griffith
 */
public class DepthFirstSearch<V, E> extends GraphSearch<V, E> {

    /**
     * Creates and intializes a new instance of DepthFirstSearch.
     */
    public DepthFirstSearch()
    {
        super();
        this.nodes = new Stack<V>();
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
        Stack<V> stackNodes = (Stack<V>) nodes;
        return stackNodes.pop();
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
            throw new GraphSearchException("Cannot enqueue a null list of nodes.");

        Stack<V> stackNodes = (Stack<V>) nodes;
        for (int i = 0; i < nextSet.size(); i++)
        {
            stackNodes.push(nextSet.get(i));
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
        // Always return false, if you only want to generate the DFS path for
        // the
        // whole graph.
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
    }
}
