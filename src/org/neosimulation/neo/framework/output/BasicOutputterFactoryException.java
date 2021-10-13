/**
 * 
 */
package org.neosimulation.neo.framework.output;

/**
 * BasicOutputterFactoryException - An exception thrown by the
 * BasicOutputterFactory class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class BasicOutputterFactoryException extends OutputterFactoryException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public BasicOutputterFactoryException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new BasicOutputterFactoryException with no message specified
     */
    public BasicOutputterFactoryException()
    {
    }

    /**
     * Constructs a new BasicOutputterFactoryException with the specified string
     * as the message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public BasicOutputterFactoryException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new BasicOutputterFactoryException which passes the provided
     * Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this BasicOutputterFactoryException.
     */
    public BasicOutputterFactoryException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new BasicOutputterFactoryException which passes the provided
     * message and throwable up the call chain in order to specify the cause of
     * this exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this BasicOutputterFactoryException.
     */
    public BasicOutputterFactoryException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
