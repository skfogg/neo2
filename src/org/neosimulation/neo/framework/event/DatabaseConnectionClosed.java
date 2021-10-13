/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * DatabaseConnectionClosed - An event signifying that the database connection
 * has been closed.
 * 
 * @author Isaac Griffith
 */
public class DatabaseConnectionClosed extends NEOEvent {

    /**
     * Constructs a new DatabaseConnectionClosed event for the provided source
     * object.
     * 
     * @param source
     *            Connection that was closed
     */
    public DatabaseConnectionClosed(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
