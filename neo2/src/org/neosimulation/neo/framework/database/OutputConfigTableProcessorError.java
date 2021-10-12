/**
 * 
 */
package org.neosimulation.neo.framework.database;

/**
 * OutputConfigTableProcessorError - An error thrown by the
 * OutputConfigTableProcessor class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class OutputConfigTableProcessorError extends Error {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public OutputConfigTableProcessorError(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new OutputConfigTableProcessorError with no message specified
     */
    public OutputConfigTableProcessorError()
    {
    }

    /**
     * Constructs a new OutputConfigTableProcessorError with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this error as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            error.
     */
    public OutputConfigTableProcessorError(String message)
    {
        super(message);
    }

    /**
     * Constructs a new OutputConfigTableProcessorError which passes the provided Throwable
     * up the call chain as the cause of this error.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputConfigTableProcessorError.
     */
    public OutputConfigTableProcessorError(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new OutputConfigTableProcessorError which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * error.
     * 
     * @param message
     *            The message returned by this error as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            error.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputConfigTableProcessorError.
     */
    public OutputConfigTableProcessorError(String message, Throwable cause)
    {
        super(message, cause);
    }

}
