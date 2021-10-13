/**
 * 
 */
package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.manager.ModelManagerException;

/**
 * NEOAppException - An exception thrown by the NEOApp class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class NEOAppException extends ModelManagerException {

    /**
     * Constructs a new NEOAppException with no message specified
     */
    public NEOAppException()
    {
    }

    /**
     * Constructs a new NEOAppException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public NEOAppException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new NEOAppException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOAppException.
     */
    public NEOAppException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new NEOAppException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOAppException.
     */
    public NEOAppException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
