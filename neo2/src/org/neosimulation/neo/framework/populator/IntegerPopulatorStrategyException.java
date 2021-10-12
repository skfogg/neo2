/**
 * 
 */
package org.neosimulation.neo.framework.populator;

/**
 * IntegerPopulatorStrategyException - An exception thrown by the
 * IntegerPopulatorStrategy class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class IntegerPopulatorStrategyException extends PopulatorStrategyException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public IntegerPopulatorStrategyException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new IntegerPopulatorStrategyException with no message
     * specified
     */
    public IntegerPopulatorStrategyException()
    {
    }

    /**
     * Constructs a new IntegerPopulatorStrategyException with the specified
     * string as the message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public IntegerPopulatorStrategyException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new IntegerPopulatorStrategyException which passes the
     * provided Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this IntegerPopulatorStrategyException.
     */
    public IntegerPopulatorStrategyException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new IntegerPopulatorStrategyException which passes the
     * provided message and throwable up the call chain in order to specify the
     * cause of this exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this IntegerPopulatorStrategyException.
     */
    public IntegerPopulatorStrategyException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
