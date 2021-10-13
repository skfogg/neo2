/**
 * 
 */
package org.neosimulation.neo.user.interpolator;

import org.neosimulation.neo.framework.NEOException;

/**
 * MathInterpolatorException - An exception thrown by the MathInterpolator class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class MathInterpolatorException extends NEOException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public MathInterpolatorException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new MathInterpolatorException with no message specified
     */
    public MathInterpolatorException()
    {
    }

    /**
     * Constructs a new MathInterpolatorException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public MathInterpolatorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new MathInterpolatorException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this MathInterpolatorException.
     */
    public MathInterpolatorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new MathInterpolatorException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this MathInterpolatorException.
     */
    public MathInterpolatorException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
