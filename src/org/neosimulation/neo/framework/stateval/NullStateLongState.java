/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

/**
 * @author Isaac
 *
 */
public class NullStateLongState extends NullStateValState {

    private StateLong stateLong;
    
    /**
     * @param stateOwner
     */
    public NullStateLongState(StateVal stateOwner)
    {
        super(stateOwner);
        stateLong = (StateLong) stateOwner;
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.NullStateValState#initialize()
     */
    @Override
    public void initialize()
    {
        stateLong.value = stateLong.getInitializer().initialize();
        stateOwner.currentState = stateOwner.initializedState;
    }

}
