/**
 * 
 */
package org.neosimulation.neo.framework.output;

/**
 * SingleOutputQueueException - An exception thrown by the SingleOutputQueue class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class SingleOutputQueueException extends HybridOutputQueueException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SingleOutputQueueException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new SingleOutputQueueException with no message specified
     */
    public SingleOutputQueueException()
    {
    }

    /**
     * Constructs a new SingleOutputQueueException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public SingleOutputQueueException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new SingleOutputQueueException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this SingleOutputQueueException.
     */
    public SingleOutputQueueException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new SingleOutputQueueException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this SingleOutputQueueException.
     */
    public SingleOutputQueueException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
