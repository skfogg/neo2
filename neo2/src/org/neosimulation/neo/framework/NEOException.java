package org.neosimulation.neo.framework;

/**
 * NEOException - An exception thrown by the AutoDynam class when a problem
 * occurs
 * 
 * @author Isaac Griffith
 */
public class NEOException extends Exception {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public NEOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new NEOException with the default message.
     */
    public NEOException()
    {
    }

    /**
     * Constructs a new NEOException with the specified string as the message
     * returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public NEOException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new NEOException which passes the provided Throwable up the
     * call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            AutoDynamException
     */
    public NEOException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new NEOException which passes the provided message and
     * throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public NEOException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
