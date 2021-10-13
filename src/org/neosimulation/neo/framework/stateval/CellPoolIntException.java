/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

/**
 * CellPoolIntException - An exception thrown by the CellPoolInt class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class CellPoolIntException extends StateIntException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CellPoolIntException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new CellPoolIntException with no message specified
     */
    public CellPoolIntException()
    {
    }

    /**
     * Constructs a new CellPoolIntException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public CellPoolIntException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new CellPoolIntException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this CellPoolIntException.
     */
    public CellPoolIntException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new CellPoolIntException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this CellPoolIntException.
     */
    public CellPoolIntException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
