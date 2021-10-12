/**
 * 
 */
package org.neosimulation.neo.user;


/**
 * UnableToRegisterException - An exception thrown by the UnableToRegister class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class UnableToRegisterException extends ManualDynamException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public UnableToRegisterException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new UnableToRegisterException with no message specified
     */
    public UnableToRegisterException()
    {
    }

    /**
     * Constructs a new UnableToRegisterException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public UnableToRegisterException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new UnableToRegisterException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this UnableToRegisterException.
     */
    public UnableToRegisterException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new UnableToRegisterException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this UnableToRegisterException.
     */
    public UnableToRegisterException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
