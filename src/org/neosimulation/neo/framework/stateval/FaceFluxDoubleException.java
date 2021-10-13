/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

/**
 * FaceFluxDoubleException - An exception thrown by the FaceFluxDouble class
 * when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class FaceFluxDoubleException extends StateDoubleException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public FaceFluxDoubleException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new FaceFluxDoubleException with no message specified
     */
    public FaceFluxDoubleException()
    {
    }

    /**
     * Constructs a new FaceFluxDoubleException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public FaceFluxDoubleException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new FaceFluxDoubleException which passes the provided
     * Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this FaceFluxDoubleException.
     */
    public FaceFluxDoubleException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new FaceFluxDoubleException which passes the provided
     * message and throwable up the call chain in order to specify the cause of
     * this exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this FaceFluxDoubleException.
     */
    public FaceFluxDoubleException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
