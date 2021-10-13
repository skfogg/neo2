/**
 * 
 */
package org.neosimulation.neo.framework.mtis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.neosimulation.neo.framework.graph.SetOperations;
import org.neosimulation.neo.framework.graph.SetOperationsException;
import org.neosimulation.neo.framework.model.IUpdatable;
import org.neosimulation.neo.framework.model.SimulationModel;

/**
 * UpdatableConfigManager - Class which encapsulates the logic necessary to create and manage updatable views. Essentially this is the controller.
 * 
 * @author Isaac Griffith
 */
public class UpdatableConfigManager implements SortObserver {

    /**
     * map of all intervals to the configs they represent
     */
    private Map<Long, Set<UpdatableConfig>> configMap;
    /**
     * map of all unique identifiers to configs
     */
    private Map<String, UpdatableConfig> configs;
    /**
     * Model this UpdatableConfigManager is associated with
     */
    private SimulationModel model;
    /**
     * Map of all intervals to views
     */
    private Map<Long, SortView> views;
    /**
     * List of all intervals supported
     */
    private List<Long> keys;
    /**
     * The default view (used if no configs are defined and to capture all IUpdatables not specified in configs)
     */
    private SortView defaultView;

    /**
     * Constructs a new UpdatableConfigManager associated with the provided model
     * 
     * @param model
     *            SimulationModel this UpdatableConfigManger is associated with
     */
    public UpdatableConfigManager(SimulationModel model)
    {
        this.model = model;
        views = new HashMap<>();
        keys = new ArrayList<>();
        configs = new HashMap<>();
        configMap = new HashMap<>();
    }

    /**
     * Adds the specified UpdatableConfig to this manager
     * 
     * @param newConfig
     *            Config to be added
     * @throws UpdatableConfigManagerException
     *             thrown if the provided config is null or the Unique ID of the config is not actually unique.
     */
    public void addConfig(UpdatableConfig newConfig) throws UpdatableConfigManagerException
    {
        if (newConfig == null)
        {
            throw new UpdatableConfigManagerException("Cannot add a null output config.");
        }
        else if (configs.containsKey(newConfig.getUID()))
        {
            throw new UpdatableConfigManagerException("UpdatableConfig ID: " + newConfig.getUID()
                    + " is already in use.");
        }
        else
        {
            configs.put(newConfig.getUID(), newConfig);
        }

        if (configMap.containsKey(newConfig.getTimeInterval()))
        {
            configMap.get(newConfig.getTimeInterval()).add(newConfig);
        }
        else
        {
            Set<UpdatableConfig> temp = new HashSet<>();
            temp.add(newConfig);
            configMap.put(newConfig.getTimeInterval(), temp);
        }

    }

    /**
     * Generates all necessary views for the model.
     * 
     * @throws SetOperationsException
     *             thrown if union between different views cannot occur.
     */
    public void generateViews() throws SetOperationsException
    {
        defaultView = SortView.createDefaultSortView(model);

        createInitialViews();

        List<SortView> list = loadList();
        Map<Long, SortView> temp = new HashMap<>();

        do
        {
            temp.clear();
            for (int i = 0; i < list.size() - 1; i++)
            {
                long valI = list.get(i).getTickInterval();
                for (int j = i + 1; j < list.size(); j++)
                {
                    long valJ = list.get(i).getTickInterval();
                    if (!configs.keySet().contains(valI * valJ) && (valI % valJ != 0) && temp.containsKey(valI * valJ))
                    {
                        temp.put(valI * valJ, new SortView(valI * valJ, views.get(valI), views.get(valJ)));
                    }
                }
            }

            views.putAll(temp);
        }
        while (!temp.isEmpty());

        Set<IUpdatable> updatables = new TreeSet<IUpdatable>();
        for (Long key : views.keySet())
        {
            updatables = SetOperations.union(updatables, views.get(key).getSet());
        }

        updatables = SetOperations.difference(defaultView.getSet(), updatables);

        defaultView = new SortView(1, updatables);

        keys.clear();
        keys.addAll(views.keySet());
        Collections.sort(keys);
    }

    /**
     * builds a list of views
     */
    private List<SortView> loadList()
    {
        List<SortView> list = new ArrayList<SortView>();

        for (long key : views.keySet())
        {
            list.add(views.get(key));
        }

        return list;
    }

    /**
     * Creates all the initial views as specified from the configs.
     */
    private void createInitialViews()
    {
        views = new HashMap<Long, SortView>();

        for (String uid : configs.keySet())
        {
            UpdatableConfig config = configs.get(uid);
            views.put(config.getTimeInterval(), config.createView(model));
        }
    }

    /**
     * Retrieves the view associated with the largest interval that captures the given time step.
     * 
     * @param timeStep
     *            time step
     * @return View view with the largest time interval that captures the given time step.
     */
    public SortView getView(long timeStep)
    {
        for (int i = keys.size() - 1; i >= 0; i--)
        {
            if (timeStep % keys.get(i) == 0)
            {
                return views.get(keys.get(i));
            }
        }

        return defaultView;
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

    public List<SortView> getViews()
    {
        List<SortView> retVal = new ArrayList<>();

        for (long key : views.keySet())
        {
            retVal.add(views.get(key));
        }

        return retVal;
    }
}
