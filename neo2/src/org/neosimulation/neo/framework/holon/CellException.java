package org.neosimulation.neo.framework.holon;

/**
 * CellException - An exception thrown by the Cell class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class CellException extends HolonException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CellException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new CellException with the default message.
     */
    public CellException()
    {
        super();
    }

    /**
     * Constructs a new CellException with the specified string as the message
     * returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public CellException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new CellException which passes the provided Throwable up the
     * call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            CellException
     */
    public CellException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new CellException which passes the provided message and
     * throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public CellException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
