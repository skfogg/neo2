/**
 * 
 */
package org.neosimulation.neo.framework.graph;

import java.util.Iterator;

import edu.uci.ics.jung.graph.Graph;

/**
 * GraphIterator - Provides an iteration across a graph where each node is
 * exposed once and only once.
 * 
 * @author Isaac Griffith
 */
public class GraphIterator<V, E> implements Iterator<V> {

    /**
     * The associated graph to iterate across.
     */
    private Graph<V, E> graph;
    /**
     * The number of nodes in the iteration
     */
    private int numNodes = 0;
    /**
     * The current node the iterator is at.
     */
    private V current;
    /**
     * The order of the nodes to iterate across.
     */
    private V[] order;
    /**
     * The current index in the order of iteration.
     */
    private int index = 0;

    /**
     * Constructs a new GraphIterator for this provided graph, and starting at
     * the specified node.
     * 
     * @param graph
     *            The graph to iterate across.
     * @param start
     *            The starting node.
     * @throws GraphIteratorException
     *             Thrown if the graph provided is null or empty, or if the the
     *             start node is null or not contained in the provided graph.
     */
    @SuppressWarnings("unchecked")
    public GraphIterator(Graph<V, E> graph, V start) throws GraphIteratorException
    {
        if (graph == null || graph.getVertexCount() == 0)
        {
            throw new GraphIteratorException("Cannot iterator over graph that is null or empty.");
        }
        else if (start == null || !graph.containsVertex(start))
        {
            throw new GraphIteratorException(
                    "Start node for graph iterator is either null or not contained in the provided graph.");
        }
        this.graph = graph;
        numNodes = graph.getVertexCount();
        GraphSort<V, E> sorter = new TopologicalSort<>();

        order = (V[]) sorter.sort(graph).toArray();
    }

    /*
     * (non-Javadoc)
     * @see java.util.Iterator#hasNext()
     */
    @Override
    public boolean hasNext()
    {
        boolean retVal = false;

        if (index < order.length)
            retVal = true;

        return retVal;
    }

    /*
     * (non-Javadoc)
     * @see java.util.Iterator#next()
     */
    @Override
    public V next()
    {
        current = order[index];
        index++;

        return current;
    }

    /*
     * (non-Javadoc)
     * @see java.util.Iterator#remove()
     */
    @Override
    public void remove()
    {
        throw new RuntimeException("Method not yet implemented.");
    }
}
