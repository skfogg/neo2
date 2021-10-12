/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * DependencyGraphModifiedException - An exception thrown by the DependencyGraphModified class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class DependencyGraphModifiedException extends TableProcessorException {

    /**
     * Constructs a new DependencyGraphModifiedException with no message specified
     */
    public DependencyGraphModifiedException()
    {
    }

    /**
     * Constructs a new DependencyGraphModifiedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public DependencyGraphModifiedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new DependencyGraphModifiedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DependencyGraphModifiedException.
     */
    public DependencyGraphModifiedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new DependencyGraphModifiedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this DependencyGraphModifiedException.
     */
    public DependencyGraphModifiedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
