package org.neosimulation.neo.user;

/**
 * ManualDynamException - An exception thrown by the ManualDynam class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class ManualDynamException extends CalcDynamException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ManualDynamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new ManualDynamException with the default message.
     */
    public ManualDynamException()
    {
        super();
    }

    /**
     * Constructs a new ManualDynamException with the specified string as the
     * message returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public ManualDynamException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new ManualDynamException which passes the provided Throwable
     * up the call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            ManualDynamException
     */
    public ManualDynamException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new ManualDynamException which passes the provided message
     * and throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public ManualDynamException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
