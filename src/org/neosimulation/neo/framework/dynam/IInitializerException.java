/**
 * 
 */
package org.neosimulation.neo.framework.dynam;

/**
 * IInitializerException - An exception thrown by the IInitializer class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class IInitializerException extends DynamException {

    /**
     * Constructs a new IInitializerException with no message specified
     */
    public IInitializerException()
    {
    }

    /**
     * Constructs a new IInitializerException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public IInitializerException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new IInitializerException which passes the provided
     * Throwable up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this IInitializerException.
     */
    public IInitializerException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new IInitializerException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this IInitializerException.
     */
    public IInitializerException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
