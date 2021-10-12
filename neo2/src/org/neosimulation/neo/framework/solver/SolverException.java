package org.neosimulation.neo.framework.solver;

import org.neosimulation.neo.framework.model.SimulationModelException;

/**
 * SolverException - An exception thrown by the ParallelSolver class when a
 * problem occurs
 * 
 * @author Clemente Izurieta
 * @author Isaac Griffith
 */
public class SolverException extends SimulationModelException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SolverException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new SolverException with the default message.
     */
    public SolverException()
    {
    }

    /**
     * Constructs a new SolverException with the specified string as the message
     * returned.
     * 
     * @param message
     *            The message to be returned by the exception
     */
    public SolverException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new SolverException which passes the provided Throwable up
     * the call chain
     * 
     * @param cause
     *            The Throwable to be passed along with the current
     *            SolverException
     */
    public SolverException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new SolverException which passes the provided message and
     * throwable up the call chain
     * 
     * @param message
     *            Message to be passed which describes the exception that
     *            occurred
     * @param cause
     *            Throwable to be passed along
     */
    public SolverException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
