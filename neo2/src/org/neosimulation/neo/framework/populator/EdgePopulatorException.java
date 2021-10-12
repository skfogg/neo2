/**
 * 
 */
package org.neosimulation.neo.framework.populator;

/**
 * EdgePopulatorException - An exception thrown by the EdgePopulator class when
 * a problem occurs
 * 
 * @author Isaac Griffith
 */
public class EdgePopulatorException extends PopulatorException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public EdgePopulatorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new EdgePopulatorException with no message specified
     */
    public EdgePopulatorException()
    {
    }

    /**
     * Constructs a new EdgePopulatorException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public EdgePopulatorException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new EdgePopulatorException which passes the provided
     * Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this EdgePopulatorException.
     */
    public EdgePopulatorException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new EdgePopulatorException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this EdgePopulatorException.
     */
    public EdgePopulatorException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
