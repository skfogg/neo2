/**
 * 
 */
package org.neosimulation.neo.framework.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.model.Dependency;
import org.neosimulation.neo.framework.model.IUpdatable;
import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.model.StateValUpdater;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;

/**
 * @author Isaac Griffith
 */
public class AllCycleSearch {

    private SimulationModel model;

    /**
     * @param model
     */
    public AllCycleSearch(SimulationModel model)
    {
        this.model = model;
    }

    /**
     * Looks for cycles in the provided graph
     * 
     * @param graph
     *            graph to check for cycles in
     */
    public void checkForCycles(Graph<IUpdatable, Dependency> graph)
    {

        boolean adjMatrix[][] = getAdjMatrix(graph);
        String nodes[] = getNodeNames(graph);

        ElementaryCycleSearch<String> ecs = new ElementaryCycleSearch<>(getAdjacencyList(adjMatrix), nodes);
        List<List<String>> cycles = ecs.getElementaryCycles();

        if (!cycles.isEmpty())
        {
            StringBuilder builder = new StringBuilder("Found the following cycles between dynams:\n");
            for (List<String> cycle : cycles)
            {
                for (String node : cycle)
                {
                    builder.append(node.substring(0, node.lastIndexOf(".")) + " -> ");
                }
                builder.append("\n");
            }

            NEOLogger.logWarning(model, builder.toString());
            System.out.println(builder.toString());
        }
    }

    /**
     * @param graph
     * @return
     */
    private boolean[][] getAdjMatrix(Graph<IUpdatable, Dependency> graph)
    {
        boolean matrix[][] = new boolean[graph.getVertexCount()][graph.getVertexCount()];
        for (int i = 0; i < graph.getVertexCount(); i++)
        {
            for (int j = 0; j < graph.getVertexCount(); j++)
            {
                matrix[i][j] = false;
            }
        }

        List<IUpdatable> ups = new ArrayList<>(graph.getVertices());
        List<Dependency> edges = new ArrayList<>(graph.getEdges());

        for (Dependency dep : edges)
        {
            Pair<IUpdatable> points = graph.getEndpoints(dep);
            IUpdatable source = points.getFirst();
            IUpdatable dest = points.getSecond();

            matrix[ups.indexOf(source)][ups.indexOf(dest)] = true;
            if (graph.getEdgeType(dep).equals(EdgeType.UNDIRECTED))
                matrix[ups.indexOf(dest)][ups.indexOf(source)] = true;
        }

        return matrix;
    }

    /**
     * @param adjacencyMatrix
     * @return
     */
    private int[][] getAdjacencyList(boolean[][] adjacencyMatrix)
    {
        int[][] list = new int[adjacencyMatrix.length][];

        for (int i = 0; i < adjacencyMatrix.length; i++)
        {
            List<Integer> v = new LinkedList<>();
            for (int j = 0; j < adjacencyMatrix[i].length; j++)
            {
                if (adjacencyMatrix[i][j])
                {
                    v.add(j);
                }
            }

            list[i] = new int[v.size()];
            for (int j = 0; j < v.size(); j++)
            {
                list[i][j] = v.get(j);
            }
        }

        return list;
    }

    /**
     * @param graph
     * @return
     */
    private String[] getNodeNames(Graph<IUpdatable, Dependency> graph)
    {
        String array[] = new String[graph.getVertexCount()];
        List<IUpdatable> ups = new ArrayList<>(graph.getVertices());

        for (int i = 0; i < ups.size(); i++)
        {
            if (ups.get(i) instanceof StateValUpdater)
            {
                StateValUpdater svu = (StateValUpdater) ups.get(i);
                String svName = svu.getStateVal().getName();
                String hName = svu.getStateVal().getHolon().getName();

                array[i] = hName + "." + svName + "." + svu.getStateVal().getID();
            }
            else
            {
                array[i] = "";
            }
        }

        return array;
    }

}
