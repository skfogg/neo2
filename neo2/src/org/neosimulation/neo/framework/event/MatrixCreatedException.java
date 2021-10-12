/**
 * 
 */
package org.neosimulation.neo.framework.event;

import org.neosimulation.neo.framework.database.TableProcessorException;

/**
 * MatrixCreatedException - An exception thrown by the MatrixCreated class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class MatrixCreatedException extends TableProcessorException {

    /**
     * Constructs a new MatrixCreatedException with no message specified
     */
    public MatrixCreatedException()
    {
    }

    /**
     * Constructs a new MatrixCreatedException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public MatrixCreatedException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new MatrixCreatedException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this MatrixCreatedException.
     */
    public MatrixCreatedException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new MatrixCreatedException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this MatrixCreatedException.
     */
    public MatrixCreatedException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
