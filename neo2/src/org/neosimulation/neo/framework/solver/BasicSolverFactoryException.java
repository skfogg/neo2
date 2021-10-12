/**
 * 
 */
package org.neosimulation.neo.framework.solver;

/**
 * BasicSolverFactoryException - An exception thrown by the BasicSolverFactory
 * class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class BasicSolverFactoryException extends SolverFactoryException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public BasicSolverFactoryException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new BasicSolverFactoryException with no message specified
     */
    public BasicSolverFactoryException()
    {
    }

    /**
     * Constructs a new BasicSolverFactoryException with the specified string as
     * the message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public BasicSolverFactoryException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new BasicSolverFactoryException which passes the provided
     * Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this BasicSolverFactoryException.
     */
    public BasicSolverFactoryException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new BasicSolverFactoryException which passes the provided
     * message and throwable up the call chain in order to specify the cause of
     * this exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this BasicSolverFactoryException.
     */
    public BasicSolverFactoryException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
