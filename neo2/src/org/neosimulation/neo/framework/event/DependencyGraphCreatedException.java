/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * DependencyGraphCreatedException - An exception thrown by the DependencyGraphCreated class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class DependencyGraphCreatedException extends TableProcessorException {

    /**
     * Constructs a new DependencyGraphCreatedException with no message specified
     */
    public DependencyGraphCreatedException()
    {
    }

    /**
     * Constructs a new DependencyGraphCreatedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public DependencyGraphCreatedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new DependencyGraphCreatedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DependencyGraphCreatedException.
     */
    public DependencyGraphCreatedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new DependencyGraphCreatedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DependencyGraphCreatedException.
     */
    public DependencyGraphCreatedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
