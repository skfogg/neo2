/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.NEOException;
import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * NEOEventException - An exception thrown by the NEOEvent class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class NEOEventException extends TableProcessorException {

    /**
     * Constructs a new NEOEventException with no message specified
     */
    public NEOEventException()
    {
    }

    /**
     * Constructs a new NEOEventException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public NEOEventException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new NEOEventException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOEventException.
     */
    public NEOEventException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new NEOEventException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOEventException.
     */
    public NEOEventException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
