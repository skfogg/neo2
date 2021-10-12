/**
 * 
 */
package org.neosimulation.neo.framework.graph;

import org.neosimulation.neo.framework.NEOException;

/**
 * GraphIteratorException - A general purpose exception thrown whenever there is
 * an unhandled issue with a GraphIterator.
 * 
 * @author Isaac Griffith
 */
public class GraphIteratorException extends NEOException {

    /**
     * Constructs a new GraphIteratorException with the default message.
     */
    public GraphIteratorException()
    {
        super();
    }

    /**
     * Constructs a new GraphIteratorException with the specified string as the
     * message returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public GraphIteratorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new GraphIteratorException which passes the provided
     * Throwable up the call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            GraphIteratorException
     */
    public GraphIteratorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new GraphIteratorException which passes the provided message
     * and throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public GraphIteratorException(String message, Throwable cause)
    {
        super(message, cause);
    }
}