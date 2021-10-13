/**
 * 
 */
package org.neosimulation.neo.framework.populator;

/**
 * DynamLoadingParamsException - An exception thrown by the DynamLoadingParams
 * class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class DynamLoadingParamsException extends DynamLoadingStrategyException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DynamLoadingParamsException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new DynamLoadingParamsException with no message specified
     */
    public DynamLoadingParamsException()
    {
    }

    /**
     * Constructs a new DynamLoadingParamsException with the specified string as
     * the message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public DynamLoadingParamsException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new DynamLoadingParamsException which passes the provided
     * Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DynamLoadingParamsException.
     */
    public DynamLoadingParamsException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new DynamLoadingParamsException which passes the provided
     * message and throwable up the call chain in order to specify the cause of
     * this exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DynamLoadingParamsException.
     */
    public DynamLoadingParamsException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
