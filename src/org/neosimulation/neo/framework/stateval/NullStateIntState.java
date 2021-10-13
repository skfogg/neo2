/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

/**
 * @author Isaac
 *
 */
public class NullStateIntState extends NullStateValState {

    private StateInt stateInt;
    
    /**
     * @param stateOwner
     */
    public NullStateIntState(StateVal stateOwner)
    {
        super(stateOwner);
        stateInt = (StateInt) stateOwner;
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.NullStateValState#initialize()
     */
    @Override
    public void initialize()
    {
        stateInt.value = stateInt.getInitializer().initialize();
        stateOwner.currentState = stateOwner.initializedState;
    }

}
