package org.neosimulation.neo.framework.model;

import org.neosimulation.neo.framework.NEOException;

/**
 * SimulationModelException - An exception thrown by the SimulationModel class
 * when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class SimulationModelException extends NEOException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SimulationModelException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new SimulationModelException with the default message.
     */
    public SimulationModelException()
    {
    }

    /**
     * Constructs a new SimulationModelException with the specified string as
     * the message returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public SimulationModelException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new SimulationModelException which passes the provided
     * Throwable up the call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            SimulationModelException
     */
    public SimulationModelException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new SimulationModelException which passes the provided
     * message and throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public SimulationModelException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
