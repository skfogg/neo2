/**
 * 
 */
package org.neosimulation.neo.framework.model;

/**
 * SimulationModelInitializationException - An exception thrown by the
 * SimulationModel Initialization process when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class SimulationModelInitializationException extends SimulationModelException {

    /**
     * Constructs a new SimulationModelInitializationException with no message
     * specified
     */
    public SimulationModelInitializationException()
    {
    }

    /**
     * Constructs a new SimulationModelInitializationException with the
     * specified string as the message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public SimulationModelInitializationException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new SimulationModelInitializationException which passes the
     * provided Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this SimulationModelInitializationException.
     */
    public SimulationModelInitializationException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new SimulationModelInitializationException which passes the
     * provided message and throwable up the call chain in order to specify the
     * cause of this exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this SimulationModelInitializationException.
     */
    public SimulationModelInitializationException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
