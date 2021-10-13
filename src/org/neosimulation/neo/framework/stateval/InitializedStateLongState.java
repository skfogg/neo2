/**
 * 
 */
package org.neosimulation.neo.framework.stateval;


/**
 * @author Isaac
 *
 */
public class InitializedStateLongState extends InitializedStateValState {

    private StateLong stateLong;
    
    /**
     * @param stateOwner
     */
    public InitializedStateLongState(StateVal stateOwner)
    {
        super(stateOwner);
        stateLong = (StateLong) stateOwner;
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.InitializedStateValState#update()
     */
    @Override
    public void update() throws StateValException
    {
        if (stateLong.getCalculator() != null)
        {
            stateLong.value = stateLong.getCalculator().calculate();
        }
        
        stateOwner.currentState = stateOwner.initializedState;
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.InitializedStateValState#initialize()
     */
    @Override
    public void initialize()
    {
        stateLong.value = stateLong.getInitializer().initialize();
        stateOwner.currentState = stateOwner.initializedState;
    }

}
