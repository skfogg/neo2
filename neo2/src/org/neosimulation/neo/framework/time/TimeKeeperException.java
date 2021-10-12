package org.neosimulation.neo.framework.time;

import org.neosimulation.neo.framework.model.SimulationModelException;

/**
 * TimeKeeperException - An exception thrown by the TimeKeeper class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class TimeKeeperException extends SimulationModelException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public TimeKeeperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new TimeKeeperException with the default message.
     */
    public TimeKeeperException()
    {
    }

    /**
     * Constructs a new TimeKeeperException with the specified string as the
     * message returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public TimeKeeperException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new TimeKeeperException which passes the provided Throwable
     * up the call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            TimeKeeperException
     */
    public TimeKeeperException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new TimeKeeperException which passes the provided message
     * and throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public TimeKeeperException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
