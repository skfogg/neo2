/**
 * 
 */
package org.neosimulation.neo.framework.manager;

/**
 * NEOModelManagerException - An exception thrown by the NEOModelManager class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class NEOModelManagerException extends ModelManagerException {
    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public NEOModelManagerException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new NEOModelManagerException with no message specified
     */
    public NEOModelManagerException()
    {
    }

    /**
     * Constructs a new NEOModelManagerException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public NEOModelManagerException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new NEOModelManagerException which passes the provided
     * Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOModelManagerException.
     */
    public NEOModelManagerException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new NEOModelManagerException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NEOModelManagerException.
     */
    public NEOModelManagerException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
