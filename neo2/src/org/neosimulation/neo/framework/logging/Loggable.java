/**
 * 
 */
package org.neosimulation.neo.framework.logging;

/**
 * @author Isaac
 */
public interface Loggable {

    /**
     * Retrieves the NEOLogger object responsible for logging errors and other
     * events for this Object
     * 
     * @return This objects runtime logger
     */
    NEOLogger getLogger();

    /**
     * Enables logging for this Object
     */
    void enableLogging();

    /**
     * Disables logging from this Object.
     */
    void disableLogging();

    /**
     * Checks whether or not the logger is currently enabled.
     * 
     * @return true if the logger is not null, false otherwise.
     */
    boolean isLoggingEnabled();
}
