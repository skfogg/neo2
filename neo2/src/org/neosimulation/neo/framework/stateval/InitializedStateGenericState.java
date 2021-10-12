/**
 * 
 */
package org.neosimulation.neo.framework.stateval;


/**
 * @author Isaac
 *
 */
public class InitializedStateGenericState<CLASS> extends InitializedStateValState {

    private StateGeneric<CLASS> stateGeneric;
    
    /**
     * @param stateOwner
     */
    public InitializedStateGenericState(StateVal stateOwner)
    {
        super(stateOwner);
        stateGeneric = (StateGeneric<CLASS>) stateOwner;
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.InitializedStateValState#update()
     */
    @Override
    public void update() throws StateValException
    {
        stateGeneric.value = stateGeneric.getCalculator().calculate();
        stateOwner.currentState = stateOwner.initializedState;
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.InitializedStateValState#initialize()
     */
    @Override
    public void initialize()
    {
        stateGeneric.value = stateGeneric.getInializer().initialize();
        stateOwner.currentState = stateOwner.initializedState;
    }

}
