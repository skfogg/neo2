/**
 * 
 */
package org.neosimulation.neo.framework.behcurr;

/**
 * BehaviorException - An exception thrown by the Behavior class when a problem
 * occurs
 * 
 * @author Isaac Griffith
 */
public class BehaviorException extends CurrencyManagerException {

    /**
     * Constructs a new BehaviorException with no message specified
     */
    public BehaviorException()
    {
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public BehaviorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new BehaviorException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public BehaviorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new BehaviorException which passes the provided Throwable up
     * the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this BehaviorException.
     */
    public BehaviorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new BehaviorException which passes the provided message and
     * throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this BehaviorException.
     */
    public BehaviorException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
