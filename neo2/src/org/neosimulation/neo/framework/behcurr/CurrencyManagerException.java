/**
 * 
 */
package org.neosimulation.neo.framework.behcurr;

import org.neosimulation.neo.framework.model.SimulationModelException;

/**
 * CurrencyManagerException - An exception thrown by the CurrencyManager class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class CurrencyManagerException extends SimulationModelException {

    /**
     * Constructs a new CurrencyManagerException with no message specified
     */
    public CurrencyManagerException()
    {
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CurrencyManagerException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new CurrencyManagerException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public CurrencyManagerException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new CurrencyManagerException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this CurrencyManagerException.
     */
    public CurrencyManagerException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new CurrencyManagerException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this CurrencyManagerException.
     */
    public CurrencyManagerException(String message, Throwable cause)
    {
        super(message, cause);
    }

}