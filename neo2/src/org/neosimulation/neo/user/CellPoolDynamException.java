package org.neosimulation.neo.user;

/**
 * CellPoolDynamException - An exception thrown by the CellPoolDynam class when
 * a problem occurs
 * 
 * @author Isaac Griffith
 */
public class CellPoolDynamException extends AutoDynamException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CellPoolDynamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new CellPoolDynamException with the default message.
     */
    public CellPoolDynamException()
    {
        super();
    }

    /**
     * Constructs a new CellPoolDynamException with the specified string as the
     * message returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public CellPoolDynamException(String message)
    {
        super(message);
    }
    
    /**
     * Constructs a new CellPoolDynamException which passes the provided
     * Throwable up the call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            CellException
     */
    public CellPoolDynamException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new CellPoolDynamException which passes the provided message
     * and throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public CellPoolDynamException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
