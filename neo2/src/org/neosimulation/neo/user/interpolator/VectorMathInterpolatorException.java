/**
 * 
 */
package org.neosimulation.neo.user.interpolator;

/**
 * VectorMathInterpolatorException - An exception thrown by the VectorMathInterpolator class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class VectorMathInterpolatorException extends MathInterpolatorException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public VectorMathInterpolatorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new VectorMathInterpolatorException with no message specified
     */
    public VectorMathInterpolatorException()
    {
    }

    /**
     * Constructs a new VectorMathInterpolatorException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public VectorMathInterpolatorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new VectorMathInterpolatorException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this VectorMathInterpolatorException.
     */
    public VectorMathInterpolatorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new VectorMathInterpolatorException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this VectorMathInterpolatorException.
     */
    public VectorMathInterpolatorException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
