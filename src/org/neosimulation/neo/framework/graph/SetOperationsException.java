/**
 * 
 */
package org.neosimulation.neo.framework.graph;

import org.neosimulation.neo.framework.NEOException;


/**
 * SetOperationsException - An exception thrown by the SetOperations class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class SetOperationsException extends NEOException {

    /**
     * Constructs a new SetOperationsException with no message specified
     */
    public SetOperationsException()
    {
    }

    /**
     * Constructs a new SetOperationsException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public SetOperationsException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new SetOperationsException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this SetOperationsException.
     */
    public SetOperationsException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new SetOperationsException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this SetOperationsException.
     */
    public SetOperationsException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
