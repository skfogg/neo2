/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

/**
 * @author Isaac
 *
 */
public class NullStateDoubleState extends NullStateValState {

    private StateDouble stateDouble;
    
    /**
     * @param stateOwner
     */
    public NullStateDoubleState(StateVal stateOwner)
    {
        super(stateOwner);
        stateDouble = (StateDouble) stateOwner;
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.NullStateValState#initialize()
     */
    @Override
    public void initialize()
    {
        stateDouble.value = stateDouble.getInitializer().initialize();
        stateOwner.currentState = stateOwner.initializedState;        
    }

}
