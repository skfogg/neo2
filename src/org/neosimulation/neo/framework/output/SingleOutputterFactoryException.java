/**
 * 
 */
package org.neosimulation.neo.framework.output;

/**
 * SingleOutputterFactoryException - An exception thrown by the
 * SingleOutputterFactory class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class SingleOutputterFactoryException extends OutputterFactoryException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SingleOutputterFactoryException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new SingleOutputterFactoryException with no message
     * specified
     */
    public SingleOutputterFactoryException()
    {
    }

    /**
     * Constructs a new SingleOutputterFactoryException with the specified
     * string as the message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public SingleOutputterFactoryException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new SingleOutputterFactoryException which passes the
     * provided Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this SingleOutputterFactoryException.
     */
    public SingleOutputterFactoryException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new SingleOutputterFactoryException which passes the
     * provided message and throwable up the call chain in order to specify the
     * cause of this exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this SingleOutputterFactoryException.
     */
    public SingleOutputterFactoryException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
