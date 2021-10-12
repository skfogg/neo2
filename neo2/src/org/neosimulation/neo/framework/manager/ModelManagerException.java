/**
 * 
 */
package org.neosimulation.neo.framework.manager;

import org.neosimulation.neo.framework.NEOException;

/**
 * ModelManagerException - An exception thrown by the ModelManager class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class ModelManagerException extends NEOException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ModelManagerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new ModelManagerException with no message specified
     */
    public ModelManagerException()
    {
    }

    /**
     * Constructs a new ModelManagerException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public ModelManagerException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new ModelManagerException which passes the provided
     * Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ModelManagerException.
     */
    public ModelManagerException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new ModelManagerException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ModelManagerException.
     */
    public ModelManagerException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
