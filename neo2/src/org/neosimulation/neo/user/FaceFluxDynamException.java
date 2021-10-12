package org.neosimulation.neo.user;

/**
 * FaceFluxDynamException - An exception thrown by the FaceFluxDynam class when
 * a problem occurs
 * 
 * @author Isaac Griffith
 */
public class FaceFluxDynamException extends AutoDynamException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public FaceFluxDynamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new FaceFluxDynamException with the default message.
     */
    public FaceFluxDynamException()
    {
        super();
    }

    /**
     * Constructs a new FaceFluxDynamException with the specified string as the
     * message returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public FaceFluxDynamException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new FaceFluxDynamException which passes the provided
     * Throwable up the call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            FaceFluxDynamException
     */
    public FaceFluxDynamException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new FaceFluxDynamException which passes the provided message
     * and throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public FaceFluxDynamException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
