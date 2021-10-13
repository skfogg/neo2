/**
 * 
 */
package org.neosimulation.neo.framework.graph;

import java.util.List;

import edu.uci.ics.jung.graph.Graph;

/**
 * GraphSort - Utilizes the Strategy Pattern, in order to provide a means to
 * access the nodes of graph in some algorithmically derived order.
 * 
 * @author Isaac Griffith
 */
public abstract class GraphSort<V, E> {

    /**
     * Graph to be sorted.
     */
    protected Graph<V, E> graph;

    /**
     * Method representing the algorithm to be used to sort the graph.
     * 
     * @param graph
     *            The Graph to be sorted.
     * @return sorted ordering of all the nodes in the provided graph.
     */
    public abstract List<V> sort(Graph<V, E> graph);
}
