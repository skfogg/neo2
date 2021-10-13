package org.neosimulation.neo.framework.populator;

import org.neosimulation.neo.framework.NEOException;

/**
 * PopulatorException - An exception thrown by the Populator class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class PopulatorException extends NEOException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public PopulatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new PopulatorException with the default message.
     */
    public PopulatorException()
    {
    }

    /**
     * Constructs a new PopulatorException with the specified string as the
     * message returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public PopulatorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new PopulatorException which passes the provided Throwable
     * up the call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            PopulatorException
     */
    public PopulatorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new PopulatorException which passes the provided message and
     * throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public PopulatorException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
