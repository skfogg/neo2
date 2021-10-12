/**
 * 
 */
package org.neosimulation.neo.framework.mtis;

import org.neosimulation.neo.framework.NEOException;


/**
 * SortModelException - An exception thrown by the SortModel class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class SortModelException extends NEOException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SortModelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new SortModelException with no message specified
     */
    public SortModelException()
    {
    }

    /**
     * Constructs a new SortModelException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public SortModelException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new SortModelException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this SortModelException.
     */
    public SortModelException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new SortModelException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this SortModelException.
     */
    public SortModelException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
