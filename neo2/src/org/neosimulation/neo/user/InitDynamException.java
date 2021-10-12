package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.DynamException;

/**
 * InitDynamException - An exception thrown by the InitDynam class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class InitDynamException extends DynamException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public InitDynamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new InitDynamException with the default message.
     */
    public InitDynamException()
    {
        super();
    }

    /**
     * Constructs a new InitDynamException with the specified string as the
     * message returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public InitDynamException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new InitDynamException which passes the provided Throwable
     * up the call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            InitDynamException
     */
    public InitDynamException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new InitDynamException which passes the provided message and
     * throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public InitDynamException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
