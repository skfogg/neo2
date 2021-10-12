/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * DependencyCreated - NEOEvent signifying that a dependency has been created.
 * 
 * @author Isaac Griffith
 */
public class DependencyCreated extends NEOEvent {

    /**
     * Constructs a new DependencyCreated event
     * 
     * @param source
     *            the Dependency that was created
     */
    public DependencyCreated(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
