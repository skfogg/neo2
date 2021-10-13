/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * DependencyGraphModified - An event signaling that some dependency graph has
 * been modified.
 * 
 * @author Isaac Griffith
 */
public class DependencyGraphModified extends NEOEvent {

    /**
     * Constructs a new DependencyGraphModified event.
     * 
     * @param source
     *            Graph that was modified.
     */
    public DependencyGraphModified(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
