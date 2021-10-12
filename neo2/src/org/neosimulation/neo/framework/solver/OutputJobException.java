/**
 * 
 */
package org.neosimulation.neo.framework.solver;

/**
 * OutputJobException - An exception thrown by the OutputJob class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class OutputJobException extends OutputAgentException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public OutputJobException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new OutputJobException with no message specified
     */
    public OutputJobException()
    {
    }

    /**
     * Constructs a new OutputJobException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public OutputJobException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new OutputJobException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputJobException.
     */
    public OutputJobException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new OutputJobException which passes the provided message and
     * throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this OutputJobException.
     */
    public OutputJobException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
