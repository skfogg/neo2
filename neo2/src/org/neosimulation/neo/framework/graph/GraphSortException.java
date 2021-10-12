/**
 * 
 */
package org.neosimulation.neo.framework.graph;

import org.neosimulation.neo.framework.NEOException;

/**
 * GraphSortException - An exception thrown by the GraphSort class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class GraphSortException extends NEOException {

    /**
     * Constructs a new GraphSortException with no message specified
     */
    public GraphSortException()
    {
    }

    /**
     * Constructs a new GraphSortException with the specified string as the message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to provide more detail in order to illuminate the cause of the exception.
     */
    public GraphSortException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new GraphSortException which passes the provided Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of this GraphSortException.
     */
    public GraphSortException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new GraphSortException which passes the provided message and throwable up the call chain in order to specify the cause of this exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to provide more detail in order to illuminate the cause of the exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of this GraphSortException.
     */
    public GraphSortException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
