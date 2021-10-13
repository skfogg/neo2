package org.neosimulation.neo.framework.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neosimulation.neo.framework.database.Triple;
import org.neosimulation.neo.framework.dynam.Dynam;
import org.neosimulation.neo.framework.stateval.StateVal;
import org.neosimulation.neo.user.ManualDynam;

/**
 * Network - An Object used to represent the Dynams and IUpdatables created
 * during the population of the Matrix by the NetworkBuilder and Populator
 * Objects.<br>
 * <br>
 * TODO Requires a restructuring to be a view of the matrix as a graph.
 * 
 * @author Isaac Griffith
 */
public class Network implements Serializable {

    /**
     * The Master List of unordered IUpdatables StateVals
     */
    private Set<IUpdatable> updatables;
    /**
     * The Master List of Dynams
     */
    private List<Dynam> dynams;
    /**
     * The Master List of all StateVals;
     */
    private List<StateVal> stateVals;
    /**
     * Map Relating a type name to a Triple representing information necessary
     * to build default value StateVals for cells
     */
    private Map<String, List<Triple<String, String, Object>>> defaultCellEntries = new HashMap<>();
    /**
     * Map Relating a type name to a Triple representing information necessary
     * to build default value StateVals for from sides of edges.
     */
    private Map<String, List<Triple<String, String, Object>>> defaultFromEdgeEntries = new HashMap<String, List<Triple<String, String, Object>>>();
    /**
     * Map Relating a type name to a Triple representing information necessary
     * to build default value StateVals for to sides of edges.
     */
    private Map<String, List<Triple<String, String, Object>>> defaultToEdgeEntries = new HashMap<String, List<Triple<String, String, Object>>>();
    /**
     * Map relating Holon IDs to StateVal information triples
     */
    private Map<String, Map<String, Set<Triple<String, String, Object>>>> initialEntries = new HashMap<String, Map<String, Set<Triple<String, String, Object>>>>();

    /**
     * Creates a new Network object with empty master lists of Dynams and
     * IUpdatables
     */
    public Network()
    {
        updatables = new HashSet<IUpdatable>();
        dynams = new ArrayList<Dynam>();
        stateVals = new ArrayList<StateVal>();
    }

    /**
     * Returns the master set of unordered IUpdatables contained in the Model.
     * Note that this includes IUpdatables attached to Manual Dynams
     * 
     * @return The master (complete) unordered set of IUpdatables
     */
    public Set<IUpdatable> getAllUpdatables()
    {
        return updatables;
    }

    /**
     * Returns the master set of unordered IUpdatables which are to be used in
     * the Grand Loop. It should be noted that the returned set does not contain
     * any IUpdatables associated with StateVal's whose Dynam is a ManualDynam
     * 
     * @return The master unordered set of IUpdatables (non ManualDynam
     *         IUpdatables)
     */
    public Set<IUpdatable> getNonManualDynamUpdatables()
    {
        Set<IUpdatable> ups = getAllUpdatables();
        Set<IUpdatable> ret = new HashSet<>();
        for (IUpdatable up : ups)
        {
            Dynam d = ((StateValUpdater) up).getStateVal().getDynam();
            if (d != null && !(d instanceof ManualDynam))
                ret.add(up);
        }

        return ret;
    }

    /**
     * Returns the master list of dynams generated during the population of the
     * matrix object by the Populator and NetworkBuilder classes
     * 
     * @return The master list of Dynams
     */
    public List<Dynam> getDynams()
    {
        return dynams;
    }

    /**
     * Returns the master list of statevals generated during the population of
     * the matrix object by the Populator and NetworkBuilder classes
     * 
     * @return The master list of StateVals
     */
    public List<StateVal> getStateVals()
    {
        return stateVals;
    }

    /**
     * Adds the provided IUpdatable parameter to the end of the master list of
     * IUpdatables.
     * 
     * @param updatable
     *            An IUpdatable to be added to the master list of IUpdatables
     */
    public void addIUpdatable(IUpdatable updatable)
    {
        if (updatable == null)
            return;

        if (updatable instanceof StateValUpdater)
        {
            StateValUpdater svu = (StateValUpdater) updatable;
            StateVal sv = svu.getStateVal();

            if (sv.getDynam() != null)
            {
                updatables.add(updatable);
            }

            this.addStateVal(sv);
        }
    }

    /**
     * Adds a list of IUpdatable objects to the end of the master list of
     * unordered IUpdatable objects stored in this Network Object
     * 
     * @param updatables
     *            List of IUpdatable objects to be added to the tail end of the
     *            master list of unordered IUpdatables
     */
    public void addIUpdatables(Set<IUpdatable> updatables)
    {
        for (IUpdatable updatable : updatables)
        {
            this.addIUpdatable(updatable);
        }
    }

    /**
     * Adds a dynam to the list of dynams stored by this Network Object
     * 
     * @param dynam
     *            Dynam to be inserted into the unordered list of dynams
     */
    public void addDynam(Dynam dynam)
    {
        dynams.add(dynam);
    }

    /**
     * Adds a list of Dynam objects to the end of the master list of Dynams
     * 
     * @param dynams
     *            List of Dynam objects to be added to the tail end of the
     *            master list of Dynams
     */
    public void addDynams(List<Dynam> dynams)
    {
        this.dynams.addAll(dynams);
    }

    /**
     * Adds a single StateVal to the master list of StateVals
     * 
     * @param stateVal
     *            The StateVal to be added to the tail-end of the master list of
     *            StateVals
     */
    public void addStateVal(StateVal stateVal)
    {
        stateVals.add(stateVal);
    }

    /**
     * Removes the provided StateVal from the network.
     * 
     * @param stateVal
     *            StateVal to be removed
     */
    public void removeStateVal(StateVal stateVal)
    {
        stateVals.remove(stateVal);
    }

    /**
     * Gets the List of Triples defining the Default Edge StateVals for the
     * provided edge type's to side.
     * 
     * @param holonType
     *            the edge type
     * @return List of Triples defining the default edge StateVals
     */
    public List<Triple<String, String, Object>> getDefaultToEdgeTriples(String holonType)
    {
        return this.defaultToEdgeEntries.get(holonType);
    }

    /**
     * Gets the List of Triples defining the Default Edge StateVals for the
     * provided edge type's from side.
     * 
     * @param holonType
     *            the edge type
     * @return List of Triples defining the default edge StateVals
     */
    public List<Triple<String, String, Object>> getDefaultFromEdgeTriples(String holonType)
    {
        return this.defaultFromEdgeEntries.get(holonType);
    }

    /**
     * Gets the List of Triples defining the Default Cell StateVals for the
     * provided cell type.
     * 
     * @param holonType
     *            the cell type
     * @return List of Triples defining the default cell StateVals
     */
    public List<Triple<String, String, Object>> getDefaultCellTriples(String holonType)
    {
        return this.defaultCellEntries.get(holonType);
    }

    /**
     * Gests the List of Triples defining the StateVals with initial values
     * associated with the behavior with the provided behavior name to be
     * installed the holon with the specified Holon ID
     * 
     * @param behaviorName
     *            The behavior name
     * @param holonID
     *            The Holon ID
     * @return List of Triples describing initial value StateVals
     */
    public List<Triple<String, String, Object>> getInitialValueTriples(String behaviorName, String holonID)
    {
        List<Triple<String, String, Object>> retVal = new ArrayList<Triple<String, String, Object>>();
        if (initialEntries.containsKey(behaviorName))
        {
            Map<String, Set<Triple<String, String, Object>>> triples = initialEntries.get(behaviorName);
            if (triples.containsKey(holonID))
                retVal.addAll(triples.get(holonID));
        }

        return retVal;
    }

    /**
     * Sets the Triples defining edge default statevals to the list of Triples
     * provided for the given edge type's to side.
     * 
     * @param holonType
     *            The edge type name
     * @param triple
     *            The list of default edge triples
     */
    public void setToEdgeDefaultTriples(String holonType, List<Triple<String, String, Object>> triple)
    {
        this.defaultToEdgeEntries.put(holonType, triple);
    }

    /**
     * Sets the Triples defining edge default statevals to the list of Triples
     * provided for the given edge type's from side.
     * 
     * @param holonType
     *            The edge type name
     * @param triple
     *            The list of default edge triples
     */
    public void setFromEdgeDefaultTriples(String holonType, List<Triple<String, String, Object>> triple)
    {
        this.defaultFromEdgeEntries.put(holonType, triple);
    }

    /**
     * Sets the Triples defining cell default statevals to the list of Triples
     * provided for the given cell type.
     * 
     * @param holonType
     *            The cell type name
     * @param triple
     *            The list of default cell triples
     */
    public void setCellDefaultTriples(String holonType, List<Triple<String, String, Object>> triple)
    {
        this.defaultCellEntries.put(holonType, triple);
    }

    /**
     * Sets the list of initial value stateval defining triples for the provided
     * holon type for the provided behavior to the list of triples provided.
     * 
     * @param behavior
     *            The behavior name
     * @param holonType
     *            The holon type name
     * @param triple
     *            The list of triples
     */
    public void setInitialValueTriples(String behavior, String holonType, List<Triple<String, String, Object>> triple)
    {
        Set<Triple<String, String, Object>> tripleSet = new HashSet<Triple<String, String, Object>>(triple);
        if (initialEntries.containsKey(behavior))
        {
            Map<String, Set<Triple<String, String, Object>>> triples = initialEntries.get(behavior);
            triples.put(holonType, tripleSet);
        }
        else
        {
            Map<String, Set<Triple<String, String, Object>>> triples = new HashMap<String, Set<Triple<String, String, Object>>>();
            triples.put(holonType, tripleSet);
            initialEntries.put(behavior, triples);
        }
    }

    /**
     * Adds an initial value stateval defining triple for the provided holon for
     * the provided behavior to the list of initial value stateval triples.
     * 
     * @param behaviorName
     *            The behavior name
     * @param holonID
     *            The holon name
     * @param triple
     *            The triple to be added
     * @throws NetworkException
     *             if either the behaviorName or holonID is an empty string or
     *             if any of the parameters are null.
     */
    public void addInitialValueTriple(String behaviorName, String holonID, Triple<String, String, Object> triple)
            throws NetworkException
    {
        if (behaviorName == null || behaviorName.equals(""))
            throw new NetworkException(
                    "Behavior Name for new initial value was improperly specified as either empty or null.");
        if (holonID == null || holonID.equals(""))
            throw new NetworkException("Holon ID was improperly specified as either null or empty");
        if (triple == null)
            throw new NetworkException("Triple containing the specification of a new initial value stateval was null");

        if (this.initialEntries.containsKey(behaviorName))
        {
            Map<String, Set<Triple<String, String, Object>>> idTriples = initialEntries.get(behaviorName);

            if (idTriples.containsKey(holonID))
            {
                Set<Triple<String, String, Object>> triples = idTriples.get(holonID);
                triples.add(triple);
            }
            else
            {
                Set<Triple<String, String, Object>> triples = new HashSet<Triple<String, String, Object>>();
                triples.add(triple);
                idTriples.put(holonID, triples);
            }
        }
        else
        {
            Map<String, Set<Triple<String, String, Object>>> temp = new HashMap<String, Set<Triple<String, String, Object>>>();
            Set<Triple<String, String, Object>> list = new HashSet<Triple<String, String, Object>>();
            list.add(triple);
            temp.put(holonID, list);
            initialEntries.put(behaviorName, temp);
        }
    }
}
