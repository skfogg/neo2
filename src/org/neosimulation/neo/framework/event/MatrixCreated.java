/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * MatrixCreated - An event signaling the creation of the structural
 * representation of a model.
 * 
 * @author Isaac Griffith
 */
public class MatrixCreated extends NEOEvent {

    /**
     * Constructs a new MatrixCreated event.
     * 
     * @param source
     *            Simulation Model whose matrix was just created.
     */
    public MatrixCreated(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
