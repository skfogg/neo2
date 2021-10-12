/**
 * 
 */
package org.neosimulation.neo.framework.model;

import org.neosimulation.neo.framework.NEOException;
import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * DependencyManagerException - An exception thrown by the DependencyManager class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class DependencyManagerException extends TableProcessorException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DependencyManagerException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new DependencyManagerException with no message specified
     */
    public DependencyManagerException()
    {
    }

    /**
     * Constructs a new DependencyManagerException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public DependencyManagerException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new DependencyManagerException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DependencyManagerException.
     */
    public DependencyManagerException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new DependencyManagerException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DependencyManagerException.
     */
    public DependencyManagerException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
