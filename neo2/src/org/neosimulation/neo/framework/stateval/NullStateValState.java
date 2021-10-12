/**
 * 
 */
package org.neosimulation.neo.framework.stateval;



/**
 * @author Isaac
 */
public abstract class NullStateValState extends StateValState {

    /**
     * @param stateOwner
     */
    public NullStateValState(StateVal stateOwner)
    {
        super(stateOwner);
        // TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateValState#isNull()
     */
    @Override
    public boolean isNull()
    {
        return true;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateValState#update()
     */
    @Override
    public void update() throws StateValException
    {
        throw new StateValException();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateValState#initialize()
     */
    @Override
    public abstract void initialize();
}
