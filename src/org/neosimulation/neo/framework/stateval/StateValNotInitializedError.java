/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

/**
 * StateValNotInitializedError - Error to be thrown when an update to a StateVal
 * is required but that StateVal has not yet been initialized. This error will
 * cause the model to fail.
 * 
 * @author Isaac Griffith
 */
public class StateValNotInitializedError extends Error {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public StateValNotInitializedError(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new StateValNotInitializedError with no message specified
     */
    public StateValNotInitializedError()
    {
    }

    /**
     * Constructs a new StateValNotInitializedError with the specified string as
     * the message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public StateValNotInitializedError(String message)
    {
        super(message);
    }

    /**
     * Constructs a new StateValNotInitializedError which passes the provided
     * Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this StateValNotInitializedError.
     */
    public StateValNotInitializedError(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new StateValNotInitializedError which passes the provided
     * message and throwable up the call chain in order to specify the cause of
     * this exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this StateValNotInitializedError.
     */
    public StateValNotInitializedError(String message, Throwable cause)
    {
        super(message, cause);
    }

}
