/**
 * 
 */
package org.neosimulation.neo.framework.graph;


/**
 * SearchHeuristic - An encapsulation of the logic to define when a goal
 * condition has been met when searching a graph.
 * 
 * @author Isaac Griffith
 */
public interface SearchHeuristic<V, E> {
    
    double calculate(V n);
}
