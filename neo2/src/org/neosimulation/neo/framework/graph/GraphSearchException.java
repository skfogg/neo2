/**
 * 
 */
package org.neosimulation.neo.framework.graph;

import org.neosimulation.neo.framework.NEOException;


/**
 * GraphSearchException - An exception thrown by the GraphSearch class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class GraphSearchException extends NEOException {

    /**
     * Constructs a new GraphSearchException with no message specified
     */
    public GraphSearchException()
    {
    }

    /**
     * Constructs a new GraphSearchException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public GraphSearchException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new GraphSearchException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this GraphSearchException.
     */
    public GraphSearchException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new GraphSearchException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this GraphSearchException.
     */
    public GraphSearchException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
