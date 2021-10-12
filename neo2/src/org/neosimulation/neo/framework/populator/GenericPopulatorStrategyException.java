/**
 * 
 */
package org.neosimulation.neo.framework.populator;

/**
 * GenericPopulatorStrategyException - An exception thrown by the
 * GenericPopulatorStrategy class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class GenericPopulatorStrategyException extends PopulatorStrategyException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public GenericPopulatorStrategyException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new GenericPopulatorStrategyException with no message
     * specified
     */
    public GenericPopulatorStrategyException()
    {
    }

    /**
     * Constructs a new GenericPopulatorStrategyException with the specified
     * string as the message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public GenericPopulatorStrategyException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new GenericPopulatorStrategyException which passes the
     * provided Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this GenericPopulatorStrategyException.
     */
    public GenericPopulatorStrategyException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new GenericPopulatorStrategyException which passes the
     * provided message and throwable up the call chain in order to specify the
     * cause of this exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this GenericPopulatorStrategyException.
     */
    public GenericPopulatorStrategyException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
