/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * OutputterCompleteException - An exception thrown by the OutputterComplete class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class OutputterCompleteException extends TableProcessorException {

    /**
     * Constructs a new OutputterCompleteException with no message specified
     */
    public OutputterCompleteException()
    {
    }

    /**
     * Constructs a new OutputterCompleteException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public OutputterCompleteException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new OutputterCompleteException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputterCompleteException.
     */
    public OutputterCompleteException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new OutputterCompleteException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputterCompleteException.
     */
    public OutputterCompleteException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
