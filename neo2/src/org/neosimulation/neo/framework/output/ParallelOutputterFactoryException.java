/**
 * 
 */
package org.neosimulation.neo.framework.output;

/**
 * ParallelOutputterFactoryException - An exception thrown by the ParallelOutputterFactory class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class ParallelOutputterFactoryException extends OutputterFactoryException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ParallelOutputterFactoryException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new ParallelOutputterFactoryException with no message specified
     */
    public ParallelOutputterFactoryException()
    {
    }

    /**
     * Constructs a new ParallelOutputterFactoryException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public ParallelOutputterFactoryException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new ParallelOutputterFactoryException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ParallelOutputterFactoryException.
     */
    public ParallelOutputterFactoryException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new ParallelOutputterFactoryException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ParallelOutputterFactoryException.
     */
    public ParallelOutputterFactoryException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
