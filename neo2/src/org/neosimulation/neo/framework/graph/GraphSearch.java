/**
 * 
 */
package org.neosimulation.neo.framework.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;

/**
 * GraphSearch - Utilizes the Template Pattern, to provide a template algorithm that can be used to search through a graph until either a goal (finishing
 * criteria) has been met, or all nodes in the graph have been explored.
 * 
 * @author Isaac Griffith
 */
public abstract class GraphSearch<V, E> {

    /**
     * List of all nodes in the graph that are to be explored.
     */
    protected List<V> nodes;
    /**
     * Resulting list of nodes representing the path from start to finish through the graph. Where the finish node is either a goal node or the last node
     * explored after all nodes have been exhausted.
     */
    protected List<V> searchPath;
    /**
     * g(n) - A Heuristic used to estimate the cost from the current node to the goal.
     */
    protected SearchHeuristic<V, E> estimateToGoal;
    /**
     * h(n) - A Heuristic used to estimate the cost from the start node to the current node.
     */
    protected SearchHeuristic<V, E> estimateToCurrent;
    /**
     * f(n) - A Heuristic used to estimate the total cost from the start node to the goal node.
     */
    protected SearchHeuristic<V, E> estimateTotal;
    /**
     * The current node
     */
    protected V currentNode;

    /**
     * Constructs and initializes a new Search algorithm for a Graph.
     */
    public GraphSearch()
    {
        nodes = new ArrayList<>();
        searchPath = new ArrayList<>();
    }

    /**
     * Template algorithm used to search the provided graph.
     * 
     * @param graph
     *            Graph to be searched.
     * @return List of nodes representing the path the search algorithm took in order from start to finish.
     * @throws GraphSearchException
     *             thrown if the provided graph is empty or null
     */
    public List<V> search(Graph<V, E> graph) throws GraphSearchException
    {
        if (graph == null || graph.getVertexCount() <= 0)
        {
            throw new GraphSearchException("Cannot search on an empty or null graph.");
        }

        nodes.addAll(graph.getVertices());
        List<V> searchPath = new LinkedList<>();

        while (!nodes.isEmpty())
        {
            currentNode = removeFront();

            if (!searchPath.contains(currentNode))
                searchPath.add(currentNode);

            if (goalTest(currentNode))
            {
                return searchPath;
            }

            enqueue(expand(currentNode, graph));
            afterEnqueue();
        }

        return searchPath;
    }

    /**
     * Method used to select the next node from the remaining nodes in the list of nodes from graph. This node is then returned.
     * 
     * @return next Node to be explored.
     */
    public abstract V removeFront();

    /**
     * Method which describes how the search method inserts items into the search path.
     * 
     * @param nextSet
     *            List of items to be placed onto the search path.
     * @throws GraphSearchException
     *             if the provided list of nodes is null.
     */
    public abstract void enqueue(List<V> nextSet) throws GraphSearchException;

    /**
     * Method which describes how the search method selects the next nodes to be explored.
     * 
     * @param node
     *            Node whose neighbors are to be explored
     * @param graph
     *            Graph currently being searched
     * @return List of nodes from the provided graph, which are neighbors of the node. The list also contains the correct ordering of nodes that are to be
     *         explored.
     * @throws GraphSearchException
     *             if the provided node is null, or if the provided graph is null or empty.
     */
    public abstract List<V> expand(V node, Graph<V, E> graph) throws GraphSearchException;

    /**
     * Method used to determine whether or not the search method can stop based on the provided node. That is if the Node provided meets the finishing (goal)
     * criteria, then this method determines if the search is complete.
     * 
     * @param node
     *            Node to be tested.
     * @return true if the search criteria has been met and the search can be stopped, false otherwise.
     * @throws GraphSearchException
     *             if the node under test is null.
     */
    public abstract boolean goalTest(V node) throws GraphSearchException;

    /**
     * Method used to determine what to do after the enqueueing process.
     */
    public abstract void afterEnqueue();

    /**
     * Sets the heuristic to be used to estimate the cost from the current node to the goal.
     * 
     * @param estimateToGoal
     *            the SearchHeuristic to be used.
     */
    public void setEstimateToGoal(SearchHeuristic<V, E> estimateToGoal)
    {
        this.estimateToGoal = estimateToGoal;
    }

    /**
     * Sets the heuristic to be used to estimate the cost from the start to the current node.
     * 
     * @param estimateToCurrent
     *            the SearchHeuristic to be used.
     */
    public void setEstimateToCurrent(SearchHeuristic<V, E> estimateToCurrent)
    {
        this.estimateToCurrent = estimateToCurrent;
    }

    /**
     * Retrieves the SearchHeuristic used to estimate the total cost from start to goal.
     * 
     * @return the SearchHeuristic
     */
    public SearchHeuristic<V, E> getEstimateTotal()
    {
        return estimateTotal;
    }

    /**
     * Sets the SearchHeuristic to be used to estimate the total cost from start to goal.
     * 
     * @param estimateTotal
     *            the SearchHeuristic
     */
    public void setEstimateTotal(SearchHeuristic<V, E> estimateTotal)
    {
        this.estimateTotal = estimateTotal;
    }

    /**
     * Retrieves the SearchHeuristic used to estimate the cost from the current node to the goal.
     * 
     * @return the SearchHeuristic
     */
    public SearchHeuristic<V, E> getEstimateToGoal()
    {
        return estimateToGoal;
    }

    /**
     * Retrieves the SearchHeuristic used to estimate the cost from the start to the current node.
     * 
     * @return the SearchHeuristic
     */
    public SearchHeuristic<V, E> getEstimateToCurrent()
    {
        return estimateToCurrent;
    }
}