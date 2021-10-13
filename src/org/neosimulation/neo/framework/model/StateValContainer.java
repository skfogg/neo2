/**
 * 
 */
package org.neosimulation.neo.framework.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neosimulation.neo.framework.stateval.StateVal;


/**
 * StateValContainer - Container which is designed specifically to hold and
 * provided access to StateVals. This allows the ability to extend a class and
 * provide capabilities such as storing parameters of the model in StateVals, or
 * adding such concepts as the TimeKeeper to the SimulationModel.
 * 
 * @author Isaac Griffith
 */
public class StateValContainer implements Serializable {

    /**
     * HashMap<String, StateVal> mapping the name of a stateval to the StateVal
     */
    protected Map<String, StateVal> statevals;

    /**
     * Constructs a new StateValContainer which is empty.
     */
    public StateValContainer()
    {
        statevals = new HashMap<String, StateVal>();
    }

    /**
     * Returns the StateVal with the given name or null if there exists no
     * StateVal by that name within this StateValContainer.
     * 
     * @param name
     *            name of the StateVal to be returned
     * @return StateVal with the given name, or null if there is no such
     *         StateVal in this StateValContainer.
     */
    public StateVal getStateVal(String name) throws StateValContainerException
    {
        StateVal retVal = statevals.get(name.toUpperCase());

        if (retVal == null)
        {
            throw new StateValContainerException();
        }

        return retVal;
    }

    /**
     * Adds the given StateVal to this StateValContainer
     * 
     * @param stateval
     *            The StateVal to be added to this StateValContainer
     */
    public void addStateVal(StateVal stateval)
    {
        statevals.put(stateval.getName().toUpperCase(), stateval);
    }

    /**
     * Removes the provided StateVal from this StateValContainer.
     * 
     * @param stateval
     *            StateVal to be removed
     */
    public void removeStateVal(StateVal stateval)
    {
        statevals.remove(stateval.getName().toUpperCase());
    }

    /**
     * Returns a List of all statevals contained within this StateValContainer
     * 
     * @return List of StateVals that this StateValContainer contains
     */
    public List<StateVal> getStateVals()
    {
        List<StateVal> vals = new ArrayList<StateVal>();

        for (String key : statevals.keySet())
        {
            vals.add(statevals.get(key));
        }

        return vals;
    }
}
