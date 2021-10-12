/**
 * 
 */
package org.neosimulation.neo.framework.mtis;

import java.util.HashSet;
import java.util.Set;

import org.neosimulation.neo.framework.graph.TopologicalSort;

/**
 * SortModel - A container class for the topological sort for a SimulationModel
 * execution.
 * 
 * @author Isaac Griffith
 */
public class SortModel {

    /**
     * Contained topological sort object
     */
    private TopologicalSort sort;
    /**
     * Set of sort observers
     */
    private Set<SortObserver> observers;

    public SortModel(TopologicalSort sort) {
        this.sort = sort;
        this.observers = new HashSet<>();
    }
    
    /**
     * Attaches a sort observer to this model
     * 
     * @param observer
     *            observer to attach
     */
    public void attach(SortObserver observer)
    {
        observers.add(observer);
    }

    /**
     * Detaches the provided observer from this model
     * 
     * @param observer
     *            observer to detach
     */
    public void detach(SortObserver observer)
    {
        observers.remove(observer);
    }

    /**
     * Notifies all attached observers that the model has been updated
     */
    public void notifyObservers()
    {
        for (SortObserver observer : observers)
            observer.update();
    }

    /**
     * @return the contained sort
     */
    public TopologicalSort getSort()
    {
        return sort;
    }
}
