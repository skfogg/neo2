/**
 * 
 */
package org.neosimulation.neo.framework.output;

/**
 * ParallelOutputterException - An exception thrown by the ParallelOutputter class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class ParallelOutputterException extends OutputterException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ParallelOutputterException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new ParallelOutputterException with no message specified
     */
    public ParallelOutputterException()
    {
    }

    /**
     * Constructs a new ParallelOutputterException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public ParallelOutputterException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new ParallelOutputterException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ParallelOutputterException.
     */
    public ParallelOutputterException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new ParallelOutputterException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ParallelOutputterException.
     */
    public ParallelOutputterException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
