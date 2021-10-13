/**
 * 
 */
package org.neosimulation.neo.framework.output;

/**
 * SingleOutputProcessorException - An exception thrown by the SingleOutputProcessor class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class SingleOutputProcessorException extends OutputProcessorException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SingleOutputProcessorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new SingleOutputProcessorException with no message specified
     */
    public SingleOutputProcessorException()
    {
    }

    /**
     * Constructs a new SingleOutputProcessorException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public SingleOutputProcessorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new SingleOutputProcessorException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this SingleOutputProcessorException.
     */
    public SingleOutputProcessorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new SingleOutputProcessorException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this SingleOutputProcessorException.
     */
    public SingleOutputProcessorException(String message, Throwable cause)
    {
        super(message, cause);
    }

}