package org.neosimulation.neo.framework.holon;

import org.neosimulation.neo.framework.NEOException;

/**
 * HolonException - An exception thrown by the Holon class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class HolonException extends NEOException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public HolonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new HolonException with no message specified
     */
    public HolonException()
    {
    }

    /**
     * Constructs a new HolonException with the specified string as the message
     * returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public HolonException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new HolonException which passes the provided Throwable up
     * the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this HolonException.
     */
    public HolonException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new HolonException which passes the provided message and
     * throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this HolonException.
     */
    public HolonException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
