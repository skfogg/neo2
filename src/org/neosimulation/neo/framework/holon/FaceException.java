package org.neosimulation.neo.framework.holon;

/**
 * FaceException - An exception thrown by the Face class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class FaceException extends HolonException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public FaceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new FaceException with the default message.
     */
    public FaceException()
    {
        super();
    }

    /**
     * Constructs a new FaceException with the specified string as the message
     * returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public FaceException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new FaceException which passes the provided Throwable up the
     * call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            CellException
     */
    public FaceException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new FaceException which passes the provided message and
     * throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public FaceException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
