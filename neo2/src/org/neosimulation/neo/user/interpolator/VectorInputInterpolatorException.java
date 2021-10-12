/**
 * 
 */
package org.neosimulation.neo.user.interpolator;

/**
 * VectorInputInterpolatorException - An exception thrown by the VectorInputInterpolator class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class VectorInputInterpolatorException extends InputInterpolatorException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public VectorInputInterpolatorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new VectorInputInterpolatorException with no message specified
     */
    public VectorInputInterpolatorException()
    {
    }

    /**
     * Constructs a new VectorInputInterpolatorException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public VectorInputInterpolatorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new VectorInputInterpolatorException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this VectorInputInterpolatorException.
     */
    public VectorInputInterpolatorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new VectorInputInterpolatorException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this VectorInputInterpolatorException.
     */
    public VectorInputInterpolatorException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
