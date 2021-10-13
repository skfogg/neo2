/**
 * 
 */
package org.neosimulation.neo.framework.database;

/**
 * ControlTableProcessorException - An exception thrown by the ControlTableProcessor class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class ControlTableProcessorException extends TableProcessorException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ControlTableProcessorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new ControlTableProcessorException with no message specified
     */
    public ControlTableProcessorException()
    {
    }

    /**
     * Constructs a new ControlTableProcessorException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public ControlTableProcessorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new ControlTableProcessorException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ControlTableProcessorException.
     */
    public ControlTableProcessorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new ControlTableProcessorException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ControlTableProcessorException.
     */
    public ControlTableProcessorException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
