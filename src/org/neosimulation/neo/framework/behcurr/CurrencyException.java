/**
 * 
 */
package org.neosimulation.neo.framework.behcurr;

/**
 * CurrencyException - An exception thrown by the Currency class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class CurrencyException extends CurrencyManagerException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CurrencyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new CurrencyException with no message specified
     */
    public CurrencyException()
    {
    }

    /**
     * Constructs a new CurrencyException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public CurrencyException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new CurrencyException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this CurrencyException.
     */
    public CurrencyException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new CurrencyException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this CurrencyException.
     */
    public CurrencyException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
