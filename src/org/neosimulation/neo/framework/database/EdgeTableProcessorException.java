/**
 * 
 */
package org.neosimulation.neo.framework.database;


/**
 * EdgeTableProcessorException - An exception thrown by the EdgeTableProcessor class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class EdgeTableProcessorException extends TableProcessorException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public EdgeTableProcessorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new EdgeTableProcessorException with no message specified
     */
    public EdgeTableProcessorException()
    {
    }

    /**
     * Constructs a new EdgeTableProcessorException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public EdgeTableProcessorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new EdgeTableProcessorException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this EdgeTableProcessorException.
     */
    public EdgeTableProcessorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new EdgeTableProcessorException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this EdgeTableProcessorException.
     */
    public EdgeTableProcessorException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
