/**
 * 
 */
package org.neosimulation.neo.framework.dynam;



/**
 * NegDynamException - An exception thrown by the NegDynam class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class NegDynamException extends DynamException {

    /**
     * Constructs a new NegDynamException with no message specified
     */
    public NegDynamException()
    {
    }

    /**
     * Constructs a new NegDynamException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public NegDynamException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new NegDynamException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NegDynamException.
     */
    public NegDynamException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new NegDynamException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NegDynamException.
     */
    public NegDynamException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
