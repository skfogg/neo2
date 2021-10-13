/**
 * 
 */
package org.neosimulation.neo.user.interpolator;


/**
 * InterpolationError - An error thrown by the Interpolation system when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class InterpolationError extends Error {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public InterpolationError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new InterpolationError with no message specified
     */
    public InterpolationError()
    {
    }

    /**
     * Constructs a new InterpolationError with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this error as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            error.
     */
    public InterpolationError(String message)
    {
        super(message);
    }

    /**
     * Constructs a new InterpolationError which passes the provided Throwable
     * up the call chain as the cause of this error.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this InterpolationError.
     */
    public InterpolationError(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new InterpolationError which passes the provided message and
     * throwable up the call chain in order to specify the cause of this error.
     * 
     * @param message
     *            The message returned by this error as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            error.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this InterpolationError.
     */
    public InterpolationError(String message, Throwable cause)
    {
        super(message, cause);
    }

}
