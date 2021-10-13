/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

/**
 * FaceFluxIntException - An exception thrown by the FaceFluxInt class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class FaceFluxIntException extends StateIntException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public FaceFluxIntException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new FaceFluxIntException with no message specified
     */
    public FaceFluxIntException()
    {
    }

    /**
     * Constructs a new FaceFluxIntException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public FaceFluxIntException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new FaceFluxIntException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this FaceFluxIntException.
     */
    public FaceFluxIntException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new FaceFluxIntException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this FaceFluxIntException.
     */
    public FaceFluxIntException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
