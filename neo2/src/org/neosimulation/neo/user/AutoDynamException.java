package org.neosimulation.neo.user;


/**
 * AutoDynamException - An exception thrown by the AutoDynam class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class AutoDynamException extends CalcDynamException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public AutoDynamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new AutoDynamException with the default message.
     */
    public AutoDynamException()
    {
        super();
    }

    /**
     * Constructs a new AutoDynamException with the specified string as the message
     * returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public AutoDynamException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new AutoDynamException which passes the provided Throwable up the
     * call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            AutoDynamException
     */
    public AutoDynamException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new AutoDynamException which passes the provided message and
     * throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public AutoDynamException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
