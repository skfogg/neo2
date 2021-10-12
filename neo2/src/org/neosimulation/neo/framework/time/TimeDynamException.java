/**
 * 
 */
package org.neosimulation.neo.framework.time;

/**
 * TimeDynamException - An exception thrown by the TimeDynam class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class TimeDynamException extends TimeKeeperException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public TimeDynamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new TimeDynamException with no message specified
     */
    public TimeDynamException()
    {
    }

    /**
     * Constructs a new TimeDynamException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public TimeDynamException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new TimeDynamException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this TimeDynamException.
     */
    public TimeDynamException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new TimeDynamException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this TimeDynamException.
     */
    public TimeDynamException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
