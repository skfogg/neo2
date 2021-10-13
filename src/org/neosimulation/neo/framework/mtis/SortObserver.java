/**
 * 
 */
package org.neosimulation.neo.framework.mtis;

/**
 * SortObserver - Interface defining the operations observers of a topological
 * sort should be updated.
 * 
 * @author Isaac Griffith
 */
public interface SortObserver {

    /**
     * Updates the SortObserver when the model has changed.
     */
    void update();
}
