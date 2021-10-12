/**
 * 
 */
package org.neosimulation.neo.framework.database;

/**
 * ControlTableProcessorError - An error thrown by the ControlTableProcessor
 * class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class ControlTableProcessorError extends Error {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ControlTableProcessorError(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new ControlTableProcessorError with no message specified
     */
    public ControlTableProcessorError()
    {
    }

    /**
     * Constructs a new ControlTableProcessorError with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this error as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            error.
     */
    public ControlTableProcessorError(String message)
    {
        super(message);
    }

    /**
     * Constructs a new ControlTableProcessorError which passes the provided Throwable
     * up the call chain as the cause of this error.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ControlTableProcessorError.
     */
    public ControlTableProcessorError(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new ControlTableProcessorError which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * error.
     * 
     * @param message
     *            The message returned by this error as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            error.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ControlTableProcessorError.
     */
    public ControlTableProcessorError(String message, Throwable cause)
    {
        super(message, cause);
    }

}
