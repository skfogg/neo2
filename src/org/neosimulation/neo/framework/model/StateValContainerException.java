package org.neosimulation.neo.framework.model;

import org.neosimulation.neo.framework.NEOException;

/**
 * StateValContainerException - An exception thrown by the StateValContainer
 * class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class StateValContainerException extends NEOException {

    /**
     * Constructs a new StateValContainerException with the default message.
     */
    public StateValContainerException()
    {
    }

    /**
     * Constructs a new StateValContainerException with the specified string as
     * the message returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public StateValContainerException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new StateValContainerException which passes the provided
     * Throwable up the call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            StateValContainerException
     */
    public StateValContainerException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new StateValContainerException which passes the provided
     * message and throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public StateValContainerException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
