/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

/**
 * @author Isaac
 *
 */
public class NullStateGenericState<CLASS> extends NullStateValState {

    private StateGeneric<CLASS> stateGeneric;
    
    /**
     * @param stateOwner
     */
    public NullStateGenericState(StateVal stateOwner)
    {
        super(stateOwner);
        stateGeneric = (StateGeneric<CLASS>) stateOwner;
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.NullStateValState#initialize()
     */
    @Override
    public void initialize()
    {
        stateGeneric.value = stateGeneric.getInializer().initialize();
        stateOwner.currentState = stateOwner.initializedState;
    }

}
