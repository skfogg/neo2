package org.neosimulation.neo.framework.holon;

/**
 * EdgeException - An exception thrown by the DependencyEdge class when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class EdgeException extends HolonException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public EdgeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new EdgeException with the default message.
     */
    public EdgeException()
    {
        super();
    }

    /**
     * Constructs a new EdgeException with the specified string as the message
     * returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public EdgeException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new EdgeException which passes the provided Throwable up the
     * call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            CellException
     */
    public EdgeException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new EdgeException which passes the provided message and
     * throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public EdgeException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
