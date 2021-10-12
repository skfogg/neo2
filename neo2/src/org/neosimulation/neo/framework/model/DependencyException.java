/**
 * 
 */
package org.neosimulation.neo.framework.model;


/**
 * DependencyException - An exception thrown by the Dependency class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class DependencyException extends SimulationModelException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DependencyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new DependencyException with no message specified
     */
    public DependencyException()
    {
    }

    /**
     * Constructs a new DependencyException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public DependencyException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new DependencyException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DependencyException.
     */
    public DependencyException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new DependencyException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DependencyException.
     */
    public DependencyException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
