/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * DatabaseConnectionOpened - NEOEvent signifying that a database connection has
 * been opened.
 * 
 * @author Isaac Griffith
 */
public class DatabaseConnectionOpened extends NEOEvent {

    /**
     * Constructs a new DatabaseConnectionOpened event
     * 
     * @param source
     *            The Connection that was opened
     */
    public DatabaseConnectionOpened(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
