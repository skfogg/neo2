/**
 * 
 */
package org.neosimulation.neo.framework.stateval;


/**
 * StateGenericException - An exception thrown by the StateGeneric class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class StateGenericException extends StateValException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public StateGenericException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new StateGenericException with no message specified
     */
    public StateGenericException()
    {
    }

    /**
     * Constructs a new StateGenericException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public StateGenericException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new StateGenericException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this StateGenericException.
     */
    public StateGenericException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new StateGenericException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this StateGenericException.
     */
    public StateGenericException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
