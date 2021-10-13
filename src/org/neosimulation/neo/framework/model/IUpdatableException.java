/**
 * 
 */
package org.neosimulation.neo.framework.model;

/**
 * IUpdatableException - An exception thrown by the IUpdatable
 * class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class IUpdatableException extends SimulationModelException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public IUpdatableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new IUpdatableException with the default message.
     */
    public IUpdatableException()
    {
    }

    /**
     * Constructs a new IUpdatableException with the specified string as
     * the message returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public IUpdatableException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new IUpdatableException which passes the provided
     * Throwable up the call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            IUpdatableException
     */
    public IUpdatableException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new IUpdatableException which passes the provided
     * message and throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public IUpdatableException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
