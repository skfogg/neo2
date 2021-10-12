/**
 * 
 */
package org.neosimulation.neo.framework.output;

/**
 * BasicOutputProcessorException - An exception thrown by the
 * BasicOutputProcessor class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class BasicOutputProcessorException extends OutputProcessorException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public BasicOutputProcessorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new BasicOutputProcessorException with no message specified
     */
    public BasicOutputProcessorException()
    {
    }

    /**
     * Constructs a new BasicOutputProcessorException with the specified string
     * as the message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public BasicOutputProcessorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new BasicOutputProcessorException which passes the provided
     * Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this BasicOutputProcessorException.
     */
    public BasicOutputProcessorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new BasicOutputProcessorException which passes the provided
     * message and throwable up the call chain in order to specify the cause of
     * this exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this BasicOutputProcessorException.
     */
    public BasicOutputProcessorException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
