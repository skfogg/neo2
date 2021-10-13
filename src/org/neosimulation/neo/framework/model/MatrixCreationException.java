package org.neosimulation.neo.framework.model;

/**
 * MatrixCreationException - An exception that is thrown when a problem occurs
 * during the creation of the model's empty landscape. Examples of such problems
 * could be
 * 
 * @author Isaac Griffith
 */
public class MatrixCreationException extends SimulationModelException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public MatrixCreationException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new MatrixCreationException with the default message.
     */
    public MatrixCreationException()
    {
        super();
    }

    /**
     * Constructs a new MatrixCreationException with the specified string as the
     * message returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public MatrixCreationException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new MatrixCreationException which passes the provided
     * Throwable up the call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            MatrixCreationException
     */
    public MatrixCreationException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new MatrixCreationException which passes the provided
     * message and throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public MatrixCreationException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
