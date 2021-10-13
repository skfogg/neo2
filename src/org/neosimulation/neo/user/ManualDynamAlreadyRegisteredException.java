/**
 * 
 */
package org.neosimulation.neo.user;


/**
 * ManualDynamAlreadyRegisteredException - An exception thrown by the ManualDynamAlreadyRegistered class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class ManualDynamAlreadyRegisteredException extends ManualDynamException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ManualDynamAlreadyRegisteredException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new ManualDynamAlreadyRegisteredException with no message specified
     */
    public ManualDynamAlreadyRegisteredException()
    {
    }

    /**
     * Constructs a new ManualDynamAlreadyRegisteredException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public ManualDynamAlreadyRegisteredException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new ManualDynamAlreadyRegisteredException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ManualDynamAlreadyRegisteredException.
     */
    public ManualDynamAlreadyRegisteredException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new ManualDynamAlreadyRegisteredException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ManualDynamAlreadyRegisteredException.
     */
    public ManualDynamAlreadyRegisteredException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
