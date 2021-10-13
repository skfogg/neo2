/**
 * 
 */
package org.neosimulation.neo.framework.populator;

/**
 * CellPopulatorException - An exception thrown by the CellPopulator class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class CellPopulatorException extends PopulatorException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CellPopulatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new CellPopulatorException with no message specified
     */
    public CellPopulatorException()
    {
    }

    /**
     * Constructs a new CellPopulatorException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public CellPopulatorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new CellPopulatorException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this CellPopulatorException.
     */
    public CellPopulatorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new CellPopulatorException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this CellPopulatorException.
     */
    public CellPopulatorException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
