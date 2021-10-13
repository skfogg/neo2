/**
 * 
 */
package org.neosimulation.neo.framework.populator;

/**
 * PopulatorStrategyException - An exception thrown by the PopulatorStrategy
 * class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class PopulatorStrategyException extends PopulatorException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public PopulatorStrategyException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new PopulatorStrategyException with no message specified
     */
    public PopulatorStrategyException()
    {
    }

    /**
     * Constructs a new PopulatorStrategyException with the specified string as
     * the message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public PopulatorStrategyException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new PopulatorStrategyException which passes the provided
     * Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this PopulatorStrategyException.
     */
    public PopulatorStrategyException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new PopulatorStrategyException which passes the provided
     * mesage and throwable up the call chain in order to specify the cause of
     * this exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this PopulatorStrategyException.
     */
    public PopulatorStrategyException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
