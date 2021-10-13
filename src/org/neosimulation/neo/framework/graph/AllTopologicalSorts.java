/**
 * 
 */
package org.neosimulation.neo.framework.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.uci.ics.jung.graph.DirectedGraph;

/**
 * AllTopologicalSorts - An implementation of Varol and Rotem's algorithm to
 * generate all topological sorting arrangements.
 * 
 * @author Isaac Griffith
 */
public class AllTopologicalSorts<V, E> {

    /**
     * The set of all topological sorts for a graph.
     */
    private Set<List<V>> allTopos;

    /**
     * Constructs a new AllTopologicalSorts Object.
     */
    public AllTopologicalSorts()
    {
        allTopos = new HashSet<>();
    }

    /**
     * Generates a Set of all possible topological sorts for the given graph
     * 
     * @param graph
     *            Graph in which all possible topological sorts is needed.
     * @return An empty set if no topological sorts can be created. (When a
     *         cyclic dependency exists or if the graph is not directed).
     *         Otherwise a Set<List<Node>> representing all possible topological
     *         sorts for the given Graph
     */
    public Set<List<V>> generateAllTopologicalSorts(DirectedGraph<V, E> graph)
    {
        allTopos.clear();

        // 1. generate a single topological sort
        TopologicalSort<V, E> sort = new TopologicalSort<>();
        List<V> base = sort.sort(graph);
        allTopos.add(base);

        // 2. start the recursive algorithm
        recursiveAlgorithm(base, base.size() - 1, graph);

        return allTopos;
    }

    /**
     * Recursive portion of the algorithm which generates the permutations of
     * the topological sorts.
     * 
     * @param topoSort
     *            The current ToplogicalSort which is to be modified.
     * @param index
     *            The current index in the provided list from which to start.
     */
    public void recursiveAlgorithm(List<V> topoSort, int index, DirectedGraph<V, E> graph)
    {
        while (index > 0)
        {
            if (canMoveLeft(index, topoSort, graph))
            {
                List<V> copy = new LinkedList<>();
                Collections.copy(copy, topoSort);
                Collections.swap(copy, index, index - 1);
                allTopos.add(copy);

                recursiveAlgorithm(copy, copy.size() - 1, graph);
            }
            else
            {
                index--;
            }
        }
    }

    /**
     * Tests whether moving the node at the provided index left by one in the
     * provided list will maintain the topological sort property.
     * 
     * @param index
     *            start index
     * @param topoSort
     *            list representing a topological sort
     * @return true if the node at the provided index can move left and maintain
     *         the topological sort property
     */
    public boolean canMoveLeft(int index, List<V> topoSort, DirectedGraph<V, E> graph)
    {
        boolean retVal = false;

        if (index >= 1)
        {
            V node = topoSort.get(index);
            V other = topoSort.get(index - 1);
            if (!getPrerequisites(graph, node).contains(other))
                retVal = true;
        }

        return retVal;
    }
    
    public List<V> getPrerequisites(DirectedGraph<V, E> graph, V vertex) {
        List<V> prereqs = new ArrayList<>();
        
        for (E edge : graph.getInEdges(vertex)) {
            prereqs.add(graph.getSource(edge));
        }
        
        return prereqs;
    }
}
