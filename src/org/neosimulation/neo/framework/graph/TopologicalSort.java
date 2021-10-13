/**
 * 
 */
package org.neosimulation.neo.framework.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * TopologicalSort - A implementation of the GraphSort Strategy, which provides a topological sort of a graph. Here we are assuming that the graph is a DAG and
 * do not check either that the graph is not directed or if cycles are present. In future versions this will change.
 * 
 * @author Isaac Griffith
 */
public class TopologicalSort<V, E> extends GraphSort<V, E> {

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.graph.operations.GraphSort#sort(org. neosimulation.neo.framework.graph.DiGraph)
     */
    @Override
    public List<V> sort(Graph<V, E> graph)
    {
        Graph<V, E> graphCopy = copyGraph(graph);
        // L <- Empty list that will contain the sorted elements
        List<V> L = new ArrayList<>();

        // S <- Set of all nodes with no incoming edges
        HashSet<V> S = new HashSet<>();
        for (V n : graphCopy.getVertices())
        {
            if (graphCopy.getInEdges(n).size() == 0)
            {
                S.add(n);
            }
        }

        // while S is non-empty do
        while (!S.isEmpty())
        {
            // remove a node n from S
            V n = S.iterator().next();
            S.remove(n);

            // insert n into L
            L.add(n);

            // for each node m with an edge e from n to m do
            List<E> edges = new LinkedList<>(graphCopy.getOutEdges(n));
            for (E edge : edges)
            {
                V m = graphCopy.getDest(edge);
                graphCopy.removeEdge(edge);// Remove edge from m

                // if m has no other incoming edges then insert m into S
                if (graphCopy.getInEdges(m).isEmpty())
                {
                    S.add(m);
                }
            }
        }
        // Check to see if all edges are removed
        boolean cycle = false;
        for (V n : graphCopy.getVertices())
        {
            if (!graphCopy.getInEdges(n).isEmpty())
            {
                cycle = true;
                break;
            }
        }
        if (cycle)
        {
            System.out.println("Cycle present, topological sort not possible");
        }

        return L;
    }

    /**
     * Creates a duplicate graph which can then be modified in order to generate the topological sort
     * 
     * @param graph
     *            Graph to be copied
     * @return Copy of the existing graph.
     */
    private Graph<V, E> copyGraph(Graph<V, E> graph)
    {
        Graph<V, E> copy = new DirectedSparseGraph<V, E>();
        for (V node : graph.getVertices())
        {
            copy.addVertex(node);
        }

        for (E edge : graph.getEdges())
        {
            copy.addEdge(edge, graph.getSource(edge), graph.getDest(edge), EdgeType.DIRECTED);
        }
        assert copy.getVertexCount() == graph.getVertexCount();
        return copy;
    }
}
