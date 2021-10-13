/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * DatabaseConnectionFailed - NEOEvent signifying that the connection to a
 * database has failed
 * 
 * @author Isaac Griffith
 */
public class DatabaseConnectionFailed extends NEOEvent {

    /**
     * Constructs a new DatabaseConnectionFailed event
     * 
     * @param source
     *            Connection object that failed
     */
    public DatabaseConnectionFailed(Object source)
    {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
