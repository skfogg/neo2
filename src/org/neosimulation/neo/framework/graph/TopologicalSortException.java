/**
 * 
 */
package org.neosimulation.neo.framework.graph;

/**
 * TopologicalSortException - An exception thrown by the TopologicalSort class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class TopologicalSortException extends GraphSortException {

    /**
     * Constructs a new TopologicalSortException with no message specified
     */
    public TopologicalSortException()
    {
    }

    /**
     * Constructs a new TopologicalSortException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public TopologicalSortException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new TopologicalSortException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this TopologicalSortException.
     */
    public TopologicalSortException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new TopologicalSortException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this TopologicalSortException.
     */
    public TopologicalSortException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
