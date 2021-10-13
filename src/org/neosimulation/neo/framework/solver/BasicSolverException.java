/**
 * 
 */
package org.neosimulation.neo.framework.solver;

/**
 * BasicSolverException - An exception thrown by the BasicSolver class when a
 * problem occurs
 * 
 * @author Isaac Grffith
 *
 */
public class BasicSolverException extends SolverException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public BasicSolverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new BasicSolverException with no message specified
     */
    public BasicSolverException()
    {
        super();
    }

    /**
     * Constructs a new BasicSolverException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this BasicSolverException.
     */
    public BasicSolverException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Constructs a new BasicSolverException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public BasicSolverException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new BasicSolverException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this BasicSolverException.
     */
    public BasicSolverException(Throwable cause)
    {
        super(cause);
    }
}
