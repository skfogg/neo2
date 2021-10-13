/**
 * 
 */
package org.neosimulation.neo.framework.stateval;


/**
 * StateValState - An abstract class which defines the current state of a
 * StateVal (initialized or null). It also defines the operations applicable and
 * state transitions between different states.
 * 
 * @author Isaac Griffith
 */
public abstract class StateValState {

    /**
     * The StateVal to which this StateValState is attached
     */
    protected StateVal stateOwner;

    /**
     * Constructs a new StateValState object attached to the provided StateVal
     * 
     * @param stateOwner
     *            StateVal to which this StateValState is to be attached.
     */
    public StateValState(StateVal stateOwner)
    {
        this.stateOwner = stateOwner;
    }

    /**
     * Returns true if this is a NullState for a StateVal
     * 
     * @return true if this is a null state
     */
    public abstract boolean isNull();

    /**
     * Controls the updating of the associated StateVal of this StateValState.
     */
    public abstract void update() throws StateValException;

    /**
     * Controls the initialization of the associated StateVal of this
     * StateValState.
     */
    public abstract void initialize();
}
