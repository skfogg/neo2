/**
 * 
 */
package org.neosimulation.neo.framework.database;

/**
 * TimingTableProcessorException - An exception thrown by the TimingTableProcessor class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class TimingTableProcessorException extends TableProcessorException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public TimingTableProcessorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new TimingTableProcessorException with no message specified
     */
    public TimingTableProcessorException()
    {
    }

    /**
     * Constructs a new TimingTableProcessorException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public TimingTableProcessorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new TimingTableProcessorException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this TimingTableProcessorException.
     */
    public TimingTableProcessorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new TimingTableProcessorException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this TimingTableProcessorException.
     */
    public TimingTableProcessorException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
