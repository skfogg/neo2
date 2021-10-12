/**
 * 
 */
package org.neosimulation.neo.framework.solver;

/**
 * ParallelSolverFactoryException - An exception thrown by the
 * ParallelSolverFactory class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class ParallelSolverFactoryException extends SolverFactoryException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ParallelSolverFactoryException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new ParallelSolverFactoryException with no message specified
     */
    public ParallelSolverFactoryException()
    {
    }

    /**
     * Constructs a new ParallelSolverFactoryException with the specified string
     * as the message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public ParallelSolverFactoryException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new ParallelSolverFactoryException which passes the provided
     * Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ParallelSolverFactoryException.
     */
    public ParallelSolverFactoryException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new ParallelSolverFactoryException which passes the provided
     * message and throwable up the call chain in order to specify the cause of
     * this exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ParallelSolverFactoryException.
     */
    public ParallelSolverFactoryException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
