/**
 * 
 */
package org.neosimulation.neo.framework.output;

/**
 * BasicOutputQueueException - An exception thrown by the BasicOutputQueue class
 * when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class BasicOutputQueueException extends OutputQueueException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public BasicOutputQueueException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new BasicOutputQueueException with no message specified
     */
    public BasicOutputQueueException()
    {
    }

    /**
     * Constructs a new BasicOutputQueueException with the specified string as
     * the message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public BasicOutputQueueException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new BasicOutputQueueException which passes the provided
     * Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this BasicOutputQueueException.
     */
    public BasicOutputQueueException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new BasicOutputQueueException which passes the provided
     * message and throwable up the call chain in order to specify the cause of
     * this exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this BasicOutputQueueException.
     */
    public BasicOutputQueueException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
