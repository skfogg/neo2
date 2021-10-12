/**
 * 
 */
package org.neosimulation.neo.framework.graph;

/**
 * BreadthFirstSearchException - An exception thrown by the BreadthFirstSearch class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class BreadthFirstSearchException extends GraphSearchException {

    /**
     * Constructs a new BreadthFirstSearchException with no message specified
     */
    public BreadthFirstSearchException()
    {
    }

    /**
     * Constructs a new BreadthFirstSearchException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public BreadthFirstSearchException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new BreadthFirstSearchException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this BreadthFirstSearchException.
     */
    public BreadthFirstSearchException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new BreadthFirstSearchException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this BreadthFirstSearchException.
     */
    public BreadthFirstSearchException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
