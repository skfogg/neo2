/**
 * 
 */
package org.neosimulation.neo.framework.database;

import org.neosimulation.neo.framework.NEOException;

/**
 * TripleException - An exception thrown by the Triple class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class TripleException extends NEOException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public TripleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new TripleException with no message specified
     */
    public TripleException()
    {
    }

    /**
     * Constructs a new TripleException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public TripleException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new TripleException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this TripleException.
     */
    public TripleException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new TripleException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this TripleException.
     */
    public TripleException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
