/**
 * 
 */
package org.neosimulation.neo.framework.model;

/**
 * NetworkBuilderException - An exception thrown by the NetworkBuilder class
 * when a problem occurs
 * 
 * @author Isaac Griffith
 */
public class NetworkBuilderException extends NetworkException {

    /**
     * Constructs a new NetworkBuilderException with no message specified
     */
    public NetworkBuilderException()
    {
    }

    /**
     * Constructs a new NetworkBuilderException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public NetworkBuilderException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new NetworkBuilderException which passes the provided
     * Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NetworkBuilderException.
     */
    public NetworkBuilderException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new NetworkBuilderException which passes the provided
     * message and throwable up the call chain in order to specify the cause of
     * this exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this NetworkBuilderException.
     */
    public NetworkBuilderException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
