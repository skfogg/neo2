/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * DependencyGraphCreated - NEOEvent signifying that a dependcy graph was
 * created for some model.
 * 
 * @author Isaac Griffith
 */
public class DependencyGraphCreated extends NEOEvent {

    /**
     * Constructs a new DependencyGraphCreated event
     * 
     * @param source
     *            The Graph created.
     */
    public DependencyGraphCreated(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
