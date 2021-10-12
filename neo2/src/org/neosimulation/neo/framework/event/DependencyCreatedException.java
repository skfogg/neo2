/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * DependencyCreatedException - An exception thrown by the DependencyCreated class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class DependencyCreatedException extends TableProcessorException {

    /**
     * Constructs a new DependencyCreatedException with no message specified
     */
    public DependencyCreatedException()
    {
    }

    /**
     * Constructs a new DependencyCreatedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public DependencyCreatedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new DependencyCreatedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DependencyCreatedException.
     */
    public DependencyCreatedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new DependencyCreatedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DependencyCreatedException.
     */
    public DependencyCreatedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
