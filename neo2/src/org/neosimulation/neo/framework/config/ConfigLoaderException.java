/**
 * 
 */
package org.neosimulation.neo.framework.config;

import org.neosimulation.neo.framework.manager.ModelManagerException;


/**
 * ConfigLoaderException - An exception thrown by the ConfigLoader class when a
 * problem occurs
 * 
 * @author Isaac Griffith
 */
public class ConfigLoaderException extends ModelManagerException {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ConfigLoaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new ConfigLoaderException with no message specified
     */
    public ConfigLoaderException()
    {
    }

    /**
     * Constructs a new ConfigLoaderException with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     */
    public ConfigLoaderException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new ConfigLoaderException which passes the provided Throwable
     * up the call chain as the cause of this exception.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ConfigLoaderException.
     */
    public ConfigLoaderException(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new ConfigLoaderException which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * exception.
     * 
     * @param message
     *            The message returned by this exception as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            exception.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ConfigLoaderException.
     */
    public ConfigLoaderException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
