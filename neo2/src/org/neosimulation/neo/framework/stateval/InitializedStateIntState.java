/**
 * 
 */
package org.neosimulation.neo.framework.stateval;


/**
 * @author Isaac
 *
 */
public class InitializedStateIntState extends InitializedStateValState {

    private StateInt stateInt;
    
    /**
     * @param stateOwner
     */
    public InitializedStateIntState(StateVal stateOwner)
    {
        super(stateOwner);
        stateInt = (StateInt) stateOwner;
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.InitializedStateValState#update()
     */
    @Override
    public void update() throws StateValException
    {
        if (stateInt.getCalculator() != null)
        {
            stateInt.value = stateInt.getCalculator().calculate();
        }
        
        stateOwner.currentState = stateOwner.initializedState;
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.InitializedStateValState#initialize()
     */
    @Override
    public void initialize()
    {
        stateInt.value = stateInt.getInitializer().initialize();
        stateOwner.currentState = stateOwner.initializedState;
    }

}
