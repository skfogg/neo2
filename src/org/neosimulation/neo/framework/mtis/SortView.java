/**
 * 
 */
package org.neosimulation.neo.framework.mtis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.neosimulation.neo.framework.graph.SetOperations;
import org.neosimulation.neo.framework.graph.SetOperationsException;
import org.neosimulation.neo.framework.model.IUpdatable;
import org.neosimulation.neo.framework.model.SimulationModel;

/**
 * SortView - A type of sort observer that contains as reduced version of the topological sort applicable only at specific intervals of the main execution loop.
 * 
 * @author Isaac Griffith
 */
public class SortView implements SortObserver, Comparable<SortView> {

    /**
     * Interval when this view becomes active
     */
    private long tickInterval;
    /**
     * Set of IUpdatables
     */
    private Set<IUpdatable> updatableSet;
    /**
     * List of IUpdatables representing this constrained view of the topological sort
     */
    private List<IUpdatable> processList;

    /**
     * Creates the default sort view for the provided simulation model. Where the default view is with a tick interval of 1 and contains all known IUpdatables.
     * 
     * @param model
     *            SimulationModel for which the default view is created
     * @return the default sort view
     */
    public static SortView createDefaultSortView(SimulationModel model)
    {
        Set<IUpdatable> updatables = model.getNetwork().getNonManualDynamUpdatables();

        return new SortView(1, updatables);
    }

    /**
     * Constructs a new Sort View for the given interval and set of IUpdatables
     * 
     * @param interval
     *            interval when this sort view is active
     * @param updatables
     *            set of IUpdatables contained in this view
     */
    public SortView(long interval, Set<IUpdatable> updatables)
    {
        this.tickInterval = interval;
        this.updatableSet = updatables;
        this.processList = new ArrayList<>();
    }

    /**
     * Constructs a new SortView for the given interval and comprised of the unition of the two provided sets of IUpdatables
     * 
     * @param l
     *            interval when this view is activated
     * @param sortView
     * @param sortView2
     * @throws SetOperationsException
     *             thrown if the union cannot be performed
     */
    public SortView(long l, Set<IUpdatable> sortView, Set<IUpdatable> sortView2) throws SetOperationsException
    {
        this(l, SetOperations.union(sortView, sortView2));
    }

    /**
     * Cosntructs a new SortView for the given interval and comprised of the union of the sets contained in the provided SortViews
     * 
     * @param interval
     *            interval when this view is activated
     * @param viewA
     * @param viewB
     * @throws SetOperationsException
     *             thrown if the union cannot be performed
     */
    public SortView(long interval, SortView viewA, SortView viewB) throws SetOperationsException
    {
        this(interval, viewA.getSet(), viewB.getSet());
    }

    /**
     * @return the contained set of IUpdatables in this view
     */
    public Set<IUpdatable> getSet()
    {
        return updatableSet;
    }

    /**
     * @return the time interval when this sortview becomes active
     */
    public long getTickInterval()
    {
        return tickInterval;
    }

    /**
     * @return the process list embodied in this view
     */
    public List<IUpdatable> getProcessList(SimulationModel model)
    {
        if (processList == null)
            processList = new ArrayList<IUpdatable>();

        if (processList.isEmpty())
        {
            Iterator<IUpdatable> iter = updatableSet.iterator();

            while (iter.hasNext())
            {
                processList.add(iter.next());
            }

            Collections.sort(processList);

            processList.add(0, model.getTimeKeeper().getTickUpdatable());
            processList.add(0, model.getTimeKeeper().getTimeUpdatable());
        }

        return processList;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.mtis.SortObserver#update()
     */
    @Override
    public void update()
    {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(SortView o)
    {
        return Long.compare(tickInterval, o.getTickInterval());
    }
}
