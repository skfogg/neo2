/**
 * 
 */
package org.neosimulation.neo.framework.database;

/**
 * KeyException - An exception thrown by the Key class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class KeyException extends ConfigKeysException {

    /**
     * Constructs a new KeyException with no message specified
     */
    public KeyException()
    {
    }

    /**
     * Constructs a new KeyException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public KeyException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new KeyException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this KeyException.
     */
    public KeyException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new KeyException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this KeyException.
     */
    public KeyException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
