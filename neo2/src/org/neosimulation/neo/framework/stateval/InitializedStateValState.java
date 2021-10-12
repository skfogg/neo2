/**
 * 
 */
package org.neosimulation.neo.framework.stateval;


/**
 * @author Isaac
 *
 */
public abstract class InitializedStateValState extends StateValState {

    /**
     * @param stateOwner
     */
    public InitializedStateValState(StateVal stateOwner)
    {
        super(stateOwner);
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateValState#isNull()
     */
    @Override
    public boolean isNull()
    {
        return false;
    }

    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateValState#update()
     */
    @Override
    public abstract void update() throws StateValException;
    
    /* (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateValState#initialize()
     */
    @Override
    public abstract void initialize();
}
