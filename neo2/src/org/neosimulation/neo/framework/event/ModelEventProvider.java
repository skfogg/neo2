/**
 * 
 */
package org.neosimulation.neo.framework.event;

/**
 * ModelEventProvider - Interface describing which event methods a model event
 * provider must have in order to fire the appropriate model related events.
 * 
 * @author Isaac Griffith
 */
public interface ModelEventProvider extends NEOEventProvider {

    /**
     * Fires a new ModelExecutionComplete event and updates all listeners
     */
    void fireModelExecutionComplete();

    /**
     * Fires a new ModelExecutionStarted event and updates all listeners
     */
    void fireModelExecutionStarted();

    /**
     * Fires a new ModelInitializationComplete event and updates all listeners
     */
    void fireModelInitializationComplete();

    /**
     * Fires a new ModelIntializationFailed event and updates all listeners
     */
    void fireModelInitializationFailed();

    /**
     * Fires a new ModelIntializationStarted event and updates all listeners
     */
    void fireModelInitializationStarted();
}
