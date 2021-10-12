/**
 * 
 */
package org.neosimulation.neo.framework.stateval;



/**
 * @author Isaac
 *
 */
public class InitializedStateDoubleState extends InitializedStateValState {

    private StateDouble stateDouble;
    
    /**
     * @param stateOwner
     */
    public InitializedStateDoubleState(StateVal stateOwner)
    {
        super(stateOwner);
        stateDouble = (StateDouble) stateOwner;
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.InitializedStateValState#update()
     */
    @Override
    public void update() throws StateValException
    {
    	if (stateDouble.getCalculator() != null)
        {
    		stateDouble.value = stateDouble.getCalculator().calculate();
        }
        
        stateOwner.currentState = stateOwner.initializedState;
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.InitializedStateValState#initialize()
     */
    @Override
    public void initialize()
    {
        stateDouble.value = stateDouble.getInitializer().initialize();
        stateOwner.currentState = stateOwner.initializedState;
    }

}
