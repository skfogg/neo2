/**
 * 
 */
package org.neosimulation.neo.framework.config;

/**
 * ConfigLoaderError - An error thrown by the ConfigLoader system when a problem
 * occurs
 * 
 * @author Isaac Griffith
 */
public class ConfigLoaderError extends Error {

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ConfigLoaderError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructs a new ConfigLoaderError with no message specified
     */
    public ConfigLoaderError()
    {
    }

    /**
     * Constructs a new ConfigLoaderError with the specified string as the
     * message returned
     * 
     * @param message
     *            The message returned by this error as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            error.
     */
    public ConfigLoaderError(String message)
    {
        super(message);
    }

    /**
     * Constructs a new ConfigLoaderError which passes the provided Throwable
     * up the call chain as the cause of this error.
     * 
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ConfigLoaderError.
     */
    public ConfigLoaderError(Throwable cause)
    {
        super(cause);
    }

    /**
     * Constructs a new ConfigLoaderError which passes the provided message
     * and throwable up the call chain in order to specify the cause of this
     * error.
     * 
     * @param message
     *            The message returned by this error as an explanation to
     *            provide more detail in order to illuminate the cause of the
     *            error.
     * @param cause
     *            The Throwable to be passed up the call chain as the cause of
     *            this ConfigLoaderError.
     */
    public ConfigLoaderError(String message, Throwable cause)
    {
        super(message, cause);
    }

}
