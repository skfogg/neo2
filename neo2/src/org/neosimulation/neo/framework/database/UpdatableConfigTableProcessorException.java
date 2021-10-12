/**
 * 
 */
package org.neosimulation.neo.framework.database;

/**
 * UpdatableConfigTableProcessorException - An exception thrown by the UpdatableConfigTableProcessor class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class UpdatableConfigTableProcessorException extends TableProcessorException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public UpdatableConfigTableProcessorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new UpdatableConfigTableProcessorException with no message specified
     */
    public UpdatableConfigTableProcessorException()
    {
    }

    /**
     * Constructs a new UpdatableConfigTableProcessorException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public UpdatableConfigTableProcessorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new UpdatableConfigTableProcessorException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this UpdatableConfigTableProcessorException.
     */
    public UpdatableConfigTableProcessorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new UpdatableConfigTableProcessorException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this UpdatableConfigTableProcessorException.
     */
    public UpdatableConfigTableProcessorException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
