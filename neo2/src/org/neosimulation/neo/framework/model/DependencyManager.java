/**
 * 
 */
package org.neosimulation.neo.framework.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.neosimulation.neo.framework.dynam.Dynam;
import org.neosimulation.neo.framework.graph.AllCycleSearch;
import org.neosimulation.neo.framework.graph.GraphSort;
import org.neosimulation.neo.framework.graph.SetOperationsException;
import org.neosimulation.neo.framework.graph.TopologicalSort;
import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.mtis.SortView;
import org.neosimulation.neo.framework.stateval.CellPoolDouble;
import org.neosimulation.neo.framework.stateval.CellPoolGeneric;
import org.neosimulation.neo.framework.stateval.CellPoolInt;
import org.neosimulation.neo.framework.stateval.FaceFluxDouble;
import org.neosimulation.neo.framework.stateval.FaceFluxGeneric;
import org.neosimulation.neo.framework.stateval.FaceFluxInt;
import org.neosimulation.neo.framework.stateval.StateVal;
import org.neosimulation.neo.user.AutoDynam;
import org.neosimulation.neo.user.CalcDynam;
import org.neosimulation.neo.user.CellPoolDynam;
import org.neosimulation.neo.user.FaceFluxDynam;
import org.neosimulation.neo.user.ManualDynam;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

/**
 * DependencyManager - Class which manages the dependecies of a model and
 * correctly generates the graphs which direct the operation of the solver.
 * 
 * @author Isaac Griffith
 */
public class DependencyManager {

    /**
     * Priority Queue of all dependencies managed
     */
    private Queue<Dependency> depQ;
    /**
     * Model for which this dependency manager is managing dependencies
     */
    private SimulationModel model;
    /**
     * Update dependency graph
     */
    private DirectedGraph<IUpdatable, Dependency> updaterGraph;
    /**
     * Store phase dependency graph
     */
    private DirectedGraph<IUpdatable, Dependency> storeGraph;
    /**
     * Trade phase dependency graph
     */
    private DirectedGraph<IUpdatable, Dependency> tradeGraph;

    /**
     * Constructs a new DependencyManager for the provided SimulationModel.
     * 
     * @param model
     *            SimulationModel which this DependencyManager is for.
     */
    public DependencyManager(SimulationModel model)
    {
        depQ = new PriorityQueue<>();
        this.model = model;
        updaterGraph = new DirectedSparseMultigraph<>();
        tradeGraph = new DirectedSparseMultigraph<>();
        storeGraph = new DirectedSparseMultigraph<>();
    }

    /**
     * Creates a dependency between the Dynam's associated IUpdatable to the
     * IUpdatable associated with the StateVal, with the given name in the
     * provided Holon, if such a StateVal exists. If the dependency to be
     * created is between an Updatable with a Dynam, and one without, then no
     * dependency is created and the named stateval is returned (if one exists)
     * or a null is returned (if one does not).
     * 
     * @param holon
     *            Holon in which to look for the StateVal with the given name
     * @param otherValName
     *            StateVal name to look up
     * @return The found StateVal, or null if no such StateVal exists in the
     *         holon provided
     */
    private final StateVal createDependency(StateVal source, StateVal dest, Holon holon, String otherValName)
    {
        StateVal tempStateVal = null;

        try
        {
            tempStateVal = holon.getStateVal(otherValName);
            if ((source == null && dest == null) || tempStateVal == null)
                return tempStateVal;
            else if (source == null)
                source = tempStateVal;
            else if (dest == null)
                dest = tempStateVal;

            queueDependency(source.getUpdater(), dest.getUpdater());
        }
        catch (StateValContainerException e)
        {
            NEOLogger.logStateVal(model, this.getClass().getName(), "createDependency()", otherValName);
        }

        return tempStateVal;
    }

    /**
     * Creates a dependency within a single holon between the Dynam and the
     * StateVal with the given name in the Dynam's Holon.
     * 
     * @param stateValName
     *            Name of the StateVal to lookup
     * @return StateVal found, or null if no such StateVal with the given name
     *         exists.
     */
    public final StateVal createDependency(Dynam dynam, String stateValName)
    {
        Holon holon = dynam.getStateVal().getHolon();
        StateVal dest = dynam.getStateVal();

        return createDependency(null, dest, holon, stateValName);
    }

    /**
     * Creates a dependency between the provided Dynam and the dynam associated
     * with the StateVal of the given name in the provided holon.
     * 
     * @param dynam
     *            Dynam that dependends on the Dynam associated with the named
     *            StateVal in the provided holon
     * @param holon
     *            Holon to look for the named StateVal in
     * @param stateValName
     *            Name of the StateVal which the provided Dynam is dependent on.
     * @return The StateVal on which the provided dynam depends.
     */
    public final StateVal createDependency(Dynam dynam, Holon holon, String stateValName)
    {
        StateVal dest = dynam.getStateVal();

        return createDependency(null, dest, holon, stateValName);
    }

    /**
     * Handles the dependency setting operation when a manual dynam is detected.
     * 
     * @param source
     *            The source IUpdatable
     * @param dest
     *            The destination IUpdatable (dependent on source)
     */
    private void handleManualDynamDeps(IUpdatable source, IUpdatable dest)
    {
        StateValUpdater svuSource = (StateValUpdater) source;
        StateValUpdater svuDest = (StateValUpdater) dest;

        Dynam sourceDynam = svuSource.getStateVal().getDynam();
        Dynam destDynam = svuDest.getStateVal().getDynam();

        IUpdatable upDest = dest;
        IUpdatable upSource = source;

        if (sourceDynam instanceof ManualDynam)
        {
            upSource = getRegisteredAutoDynamUpdatable((ManualDynam) sourceDynam);
        }

        if (destDynam instanceof ManualDynam)
        {
            upDest = getRegisteredAutoDynamUpdatable((ManualDynam) destDynam);
        }

        if (upSource == null || upDest == null)
            return;
        queueDependency(upSource, upDest);
    }

    /**
     * Retrieves the provided ManualDynam registered with CalcDynam's
     * IUpdatable.
     * 
     * @param dynam
     *            ManualDynam
     * @return IUpdatable of the Dynam to which the provided ManualDynam is
     *         registered with
     */
    private IUpdatable getRegisteredAutoDynamUpdatable(ManualDynam dynam)
    {
        CalcDynam cd = dynam.registeredWith();
        if (cd == null)
        {
            NEOLogger.logManualDynamNotRegistered(model, this.getClass().getName(),
                    "getRegisteredAutoDynamUpdatable()", dynam);
            return null;
        }

        while (!(cd instanceof AutoDynam))
        {
            if (cd instanceof ManualDynam)
            {
                cd = ((ManualDynam) cd).registeredWith();
            }
            else
            {
                break;
            }
        }

        return cd.getStateVal().getUpdater();
    }

    /**
     * Passes the setDependency command between the two provided
     * StateValUpdaters to the correct LoopManger depending on the current phase
     * of execution. <br>
     * <br>
     * If in the initialization phase the initLoopManger sets the dependency,
     * otherwise the runLoopManger does.
     * 
     * @param sourceUpdater
     *            The StateValUpdater dependent upon destinationUpdater
     * @param destinationUpdater
     *            The StateValUpdater upon which sourceUpdater depends
     */
    public final synchronized void queueDependency(IUpdatable sourceUpdater, IUpdatable destinationUpdater)
    {
        if (((StateValUpdater) sourceUpdater).getStateVal().getDynam() == null
                || ((StateValUpdater) sourceUpdater).getStateVal().getDynam()
                        .equals(((StateValUpdater) destinationUpdater).getStateVal().getDynam()))
        {
            return;
        }

        Dependency dep = Dependency.createDependency(sourceUpdater, destinationUpdater);
        if (!depQ.contains(dep))
            depQ.add(dep);
    }

    /**
     * Processes the set of dependencies in the the Queue.
     */
    public final synchronized void setDependencies()
    {
        while (!depQ.isEmpty())
        {
            Dependency dep = depQ.poll();
            IUpdatable source = dep.getSrc();
            IUpdatable dest = dep.getDest();

            switch (dep.getValue())
            {
            case Dependency.AUTO_AUTO:
            case Dependency.AUTO_INIT:
            case Dependency.INIT_AUTO:
            case Dependency.INIT_INIT:
                if (!source.equals(dest))
                    setDependency(source, dest);
                break;
            case Dependency.MANUAL_MANUAL:
            case Dependency.AUTO_MANUAL:
            case Dependency.MANUAL_AUTO:
            case Dependency.INIT_MANUAL:
            case Dependency.MANUAL_INIT:
                handleManualDynamDeps(source, dest);
                break;
            }
        }

        setIUpdatableIndexesAndGenerateViews();
    }

    /**
     * Retrieves the graph that represents the update phase of this solver
     * 
     * @return The update phase graph
     */
    public DirectedGraph<IUpdatable, Dependency> getUpdaterGraph()
    {
        return updaterGraph;
    }

    /**
     * Retrieves the graph representing the Trade phase of this solver
     * 
     * @return the trade phase graph
     */
    public DirectedGraph<IUpdatable, Dependency> getTradeGraph()
    {
        return tradeGraph;
    }

    /**
     * Retrieves the graph representing the Store phase of this solver.
     * 
     * @return the store phase graph
     */
    public DirectedGraph<IUpdatable, Dependency> getStoreGraph()
    {
        return storeGraph;
    }

    /**
     * Adds a node representing the provided Updatable in the provided Graph
     * 
     * @param updatable
     *            to be inserted
     * @param graph
     *            graph into which the new node is to be added
     */
    private void addNodeToGraph(IUpdatable updatable, DirectedGraph<IUpdatable, Dependency> graph)
    {
        if (updatable instanceof StateValUpdater)
        {
            StateValUpdater svu = (StateValUpdater) updatable;
            Dynam dynam = svu.getStateVal().getDynam();

            if (!model.isInitializing() && !((AutoDynam.class.isInstance(dynam))))
                return;
        }

        if (!graph.containsVertex(updatable))
            graph.addVertex(updatable);
    }

    /**
     * Builds the TradeStore and Update phase DependencyGraphs to be used to run
     * the model correctly and concurrently.
     */
    public synchronized void createGraphs()
    {
        addNodesToGraph(model.getNetwork().getNonManualDynamUpdatables());

        createUpdatableDependencies(model.getNetwork().getAllUpdatables());

        setDependencies();

        AllCycleSearch cycleSearch = new AllCycleSearch(model);
        cycleSearch.checkForCycles(tradeGraph);
        cycleSearch.checkForCycles(storeGraph);
        cycleSearch.checkForCycles(updaterGraph);
    }

    /**
     * Call the appropriate setInitDeps or setCalcDeps methods to connect the
     * nodes of the graph based on dependencies between nodes
     * 
     * @param updatables
     *            Set of IUpdatables for which dependencies should be set
     */
    private void createUpdatableDependencies(Set<IUpdatable> updatables)
    {
        for (IUpdatable updatable : updatables)
        {
            if (updatable instanceof StateValUpdater)
            {
                StateValUpdater svUpdater = (StateValUpdater) updatable;
                Dynam d = svUpdater.getStateVal().getDynam();

                if (model.isInitializing())
                {
                    d.setInitDeps();
                }
                else if (isCalcDynam(d))
                {
                    ((CalcDynam) d).setCalcDeps();
                }
            }
        }
    }

    /**
     * Add all the IUpdatables to the Graph as values stored in DependentNodes
     * 
     * @param updatables
     *            The set of IUpdatabls to be added to the graph
     */
    private void addNodesToGraph(Set<IUpdatable> updatables)
    {
        for (IUpdatable updatable : updatables)
        {
            if (updatable instanceof StateValUpdater)
            {
                StateValUpdater svUpdater = (StateValUpdater) updatable;
                Dynam d = svUpdater.getStateVal().getDynam();

                if (d == null)
                    continue;

                if (!model.isInitializing())
                {
                    if (isStoreDynam(d))
                    {
                        addNodeToGraph(updatable, storeGraph);
                    }
                    else if (isTradeDynam(d))
                    {
                        addNodeToGraph(updatable, tradeGraph);
                    }
                    else if (isCalcDynam(d))
                    {
                        addNodeToGraph(updatable, updaterGraph);
                    }
                }
                else
                {
                    addNodeToGraph(updatable, updaterGraph);
                }
            }
        }
    }

    /**
     * Tests whether the provided Dynam is an instance of CalcDynam.
     * 
     * @param d
     *            Dynam under test
     * @return true if the provided dynam is a CalcDynam, false otherwise.
     */
    private boolean isCalcDynam(Dynam d)
    {
        return (d instanceof CalcDynam);
    }

    /**
     * Tests whether the provided Dynam is a Trade PHase dynam
     * 
     * @param d
     *            Dynam to be tested
     * @return true if the dynam is a FaceFluxDynam, false otherwise
     */
    private boolean isTradeDynam(Dynam d)
    {
        return (d instanceof FaceFluxDynam);
    }

    /**
     * Tests whether the provided Dynam is a Store Phase dynam.
     * 
     * @param d
     *            Dynam to be tested
     * @return true if the dynam is a CellPoolDynam, false otherwise.
     */
    private boolean isStoreDynam(Dynam d)
    {
        return (d instanceof CellPoolDynam);
    }

    /**
     * Sets a dependency between the source and destination nodes. A dependency
     * is formed only if both nodes are in the same phase, otherwise there is no
     * point.
     * 
     * @param source
     *            Node that destination depends on
     * @param destination
     *            Node that is dependent upon source
     */
    public synchronized void setDependency(IUpdatable source, IUpdatable destination)
    {
        if (model.isInitializing() || !(isCurrencyUpdater(source) || isCurrencyUpdater(destination)))
        {
            addDependencyToGraph(updaterGraph, source, destination);
        }
        else
        {
            if (isStoreUpdater(source) && isStoreUpdater(destination))
            {
                addDependencyToGraph(storeGraph, source, destination);
            }
            else if (isTradeUpdater(source) && isTradeUpdater(destination))
            {
                addDependencyToGraph(tradeGraph, source, destination);
            }
        }
    }

    /**
     * Adds a dependency to the provided graph between the source updater and
     * the destination updater. This should be read that a the destination
     * updater is dependent upon the source updater and this is recoreded in the
     * provided graph.
     * 
     * @param graph
     *            Graph to contain the dependency.
     * @param source
     *            Source updater.
     * @param destination
     *            Destination updater.
     */
    private void addDependencyToGraph(Graph<IUpdatable, Dependency> graph, IUpdatable source, IUpdatable destination)
    {
        if (graph.containsVertex(source) && graph.containsVertex(destination))
        {
            graph.addEdge(Dependency.createDependency(source, destination), source, destination, EdgeType.DIRECTED);
        }
    }

    /**
     * Tests whether the provided IUpdatable is a Currency Updater
     * 
     * @param svu
     *            IUpdatable to be tested
     * @return true if the IUpdatable is either a trade or store phase updater
     */
    private boolean isCurrencyUpdater(IUpdatable svu)
    {
        return (isTradeUpdater(svu) || isStoreUpdater(svu));
    }

    /**
     * Tests whether the provided IUpdatable is a Trade Phase IUpdatable
     * 
     * @param svu
     *            IUpdatable to be tested
     * @return true if the IUpdatable is associated with a StateVal that is a
     *         FaceFluxVal, false otherwise
     */
    private boolean isTradeUpdater(IUpdatable svu)
    {
        if (svu instanceof StateValUpdater)
        {
            StateVal sv = ((StateValUpdater) svu).getStateVal();

            return (sv instanceof FaceFluxDouble || sv instanceof FaceFluxGeneric || sv instanceof FaceFluxInt);
        }

        return false;
    }

    /**
     * Tests whether the provided IUpdatable is a Store Phase IUpdatable
     * 
     * @param svu
     *            IUpdatable to be tested
     * @return true if the IUpdatable is associated with a StateVal that is a
     *         CellPoolVal, false otherwise
     */
    private boolean isStoreUpdater(IUpdatable svu)
    {
        if (svu instanceof StateValUpdater)
        {
            StateVal sv = ((StateValUpdater) svu).getStateVal();

            return (sv instanceof CellPoolDouble || sv instanceof CellPoolGeneric || sv instanceof CellPoolInt);
        }

        return false;
    }

    /**
     * Returns the basic sequential processing list associated with the
     * dependencies in all three graphs.
     * 
     * @return Correct sequential ordering of all dynams in the system based on
     *         the dependencies that have been set.
     */
    public List<IUpdatable> getProcessList()
    {
        List<IUpdatable> processList = new ArrayList<>();
        List<IUpdatable> output = new ArrayList<>();

        IUpdatable tickUpdater = model.getTimeKeeper().getTickUpdatable();
        IUpdatable timeUpdater = model.getTimeKeeper().getTimeUpdatable();

        GraphSort<IUpdatable, Dependency> sort = new TopologicalSort<>();
        if (tradeGraph.getVertexCount() > 0)
        {
            List<IUpdatable> temp = sort.sort(tradeGraph);
            processList.addAll(temp);
        }

        if (storeGraph.getVertexCount() > 0)
        {
            List<IUpdatable> temp = sort.sort(storeGraph);
            processList.addAll(temp);
        }

        if (updaterGraph.getVertexCount() > 0)
        {
            List<IUpdatable> temp = sort.sort(updaterGraph);
            processList.addAll(temp);
        }

        processList.add(0, timeUpdater);
        processList.add(0, tickUpdater);

        for (int i = 0; i < processList.size(); i++)
        {
            output.add(processList.get(i));
        }

        return output;
    }

    /**
     * Retrieves the processing list of IUpdatables to be executed for the given
     * timestep.
     * 
     * @param timeStep
     *            time step
     * @return List of IUpdatables to be processed.
     */
    public List<IUpdatable> getProcessList(long timeStep)
    {
        return model.getUpdatableConfigManager().getView(timeStep).getProcessList(model);
    }

    /**
     * Sets the IUpdatableIndexes and Generates views based on these indexes.
     */
    public void setIUpdatableIndexesAndGenerateViews()
    {
        List<IUpdatable> masterProcessList = getProcessList();

        for (int i = 2; i < masterProcessList.size(); i++)
        {
            IUpdatable updatable = masterProcessList.get(i);
            updatable.setIndex(i);
        }

        try
        {
            model.getUpdatableConfigManager().generateViews();
            List<SortView> views = model.getUpdatableConfigManager().getViews();
            Collections.sort(views);
            for (IUpdatable updatable : masterProcessList)
            {
                updatable.setMultiplier(model.getTimeStep());
                for (SortView view : views)
                {
                    if (view.getSet().contains(updatable))
                    {
                        updatable.setMultiplier(view.getTickInterval() * model.getTimeStep());
                        break;
                    }
                }
            }
        }
        catch (SetOperationsException e)
        {

        }
    }

    /**
     * Clears the dependency manager's queue.
     */
    public void clear()
    {
        depQ.clear();
        updaterGraph = new DirectedSparseMultigraph<>();
        storeGraph = new DirectedSparseMultigraph<>();
        tradeGraph = new DirectedSparseMultigraph<>();
    }

    /**
     * Sets all registrations for all CalcDynams in the model.
     */
    public void setRegistrations()
    {
        List<Dynam> dynams = model.getNetwork().getDynams();
        Set<Dynam> set = new HashSet<>();
        set.addAll(dynams);
        for (Dynam d : set)
        {
            if (d instanceof CalcDynam)
                ((CalcDynam) d).setRegistrations();
        }
    }
}
