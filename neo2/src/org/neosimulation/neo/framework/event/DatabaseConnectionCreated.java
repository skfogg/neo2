/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * DatabaseConnectionCreated - NEOEvent signifying that a database connection
 * has been created.
 * 
 * @author Isaac Griffith
 */
public class DatabaseConnectionCreated extends NEOEvent {

    /**
     * Constructs a new DatabaseConnectionCreated event.
     * 
     * @param source
     *            the Connection object that was created.
     */
    public DatabaseConnectionCreated(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
