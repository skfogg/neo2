/**
 * 
 */
package org.neosimulation.neo.framework.manager;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.neosimulation.neo.framework.config.OutputConfig;
import org.neosimulation.neo.framework.config.OutputConfigException;
import org.neosimulation.neo.framework.config.RegExProcessor;
import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.model.IUpdatable;
import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.model.StateValUpdater;
import org.neosimulation.neo.framework.time.TimeKeeper;

/**
 * OutputConfigManager - Manages the storage and use of OutputConfigs. It also
 * provides an interface for utilizing this information to determine what and
 * when to output.
 * 
 * @author Isaac Griffith
 */
public class OutputConfigManager {

    /**
     * The SimulationModel this OutputConfigManger is associated with
     */
    private SimulationModel model;
    /**
     * The OutputConfigs stored by this OutputConfigManager and indexed by the
     * unique id of each OutputConfig
     */
    private Map<String, OutputConfig> configs;
    /**
     * Maps a set of OutputConfigs to the tick interval at which they become
     * active.
     */
    private Map<Integer, Set<OutputConfig>> configMap;
    /**
     * The names of the output tables to be used for output
     */
    private Set<String> outputTableNames;
    /**
     * Flag used to determine if local console output is enabled
     */
    private boolean localOutput = true;
    /**
     * Current set of holon names
     */
    private Set<String> currentHolonNames;
    /**
     * Current set of behavior names
     */
    private Set<String> currentBehaviorNames;
    /**
     * Current set of holon types
     */
    private Set<String> currentHolonTypes;
    /**
     * Current set of currencies
     */
    private Set<String> currentCurrencies;
    /**
     * Set of configurations currently supplying a values to determine if an
     * IUpdatable should output
     */
    private Set<OutputConfig> currentConfigs;
    /**
     * Current set of stateval names
     */
    private Set<String> currentStateValNames;
    /**
     * flag indicating whether table names are unique and should include a
     * time_date stamp
     */
    private boolean uniqueTableNames;
    /**
     * The name of the table into which initial values will be placed.
     */
    private String initialValuesTable;

    /**
     * Constructs a new empty instance of OutputConfigManager associated with
     * the provided SimulationModel
     * 
     * @param model
     *            associated SimulationModel
     */
    public OutputConfigManager(SimulationModel model)
    {
        this.model = model;
        configs = new HashMap<>();
        configMap = new HashMap<>();

        currentHolonNames = new HashSet<>();
        currentHolonTypes = new HashSet<>();
        currentCurrencies = new HashSet<>();
        currentBehaviorNames = new HashSet<>();
        outputTableNames = new HashSet<>();
        currentStateValNames = new HashSet<>();
        currentConfigs = new HashSet<>();
        uniqueTableNames = false;
    }

    /**
     * Sets the tick used to determine the OutputConfigs to be used
     * 
     * @param l
     *            current tick
     */
    public void setTimeTick(long l)
    {
        currentHolonNames.clear();
        currentHolonTypes.clear();
        currentCurrencies.clear();
        currentBehaviorNames.clear();
        currentStateValNames.clear();
        currentConfigs.clear();

        for (int interval : configMap.keySet())
        {
            if (l % interval == 0)
            {
                for (OutputConfig config : configMap.get(interval))
                {
                    currentHolonNames.addAll(config.getHolonNames());
                    currentHolonTypes.addAll(config.getTypes());
                    currentCurrencies.addAll(config.getCurrencies());
                    currentBehaviorNames.addAll(config.getBehaviors());
                    currentStateValNames.addAll(config.getStateValNames());
                    currentConfigs.add(config);
                }
            }
        }
    }

    /**
     * Tests whether the provided StateValUpdater is associated with one of the
     * TimeKeeper's StateVals.
     * 
     * @param svu
     *            StateValUpdater under test.
     * @return true if the StateVal attached to the provided StateValUpdater is
     *         also associated with the TimeKeeper, false otherwise.
     */
    private boolean isTimeStateValUpdater(StateValUpdater svu)
    {
        return (svu.getStateVal().getName().equals(TimeKeeper.TICK_STATEVAL_NAME) || svu.getStateVal().getName()
                .equals(TimeKeeper.TIME_STATEVAL_NAME));
    }

    /**
     * Determines whether the provided IUpdatable should output or not.
     * 
     * @param updatable
     *            Updatable under determination for output.
     * @return true if the provided IUpdatable should output on this timestep,
     *         false otherwise
     */
    public synchronized boolean shouldOutput(long tick, IUpdatable updatable)
    {
        boolean retVal = false;

        if (updatable instanceof StateValUpdater)
        {
            StateValUpdater svu = (StateValUpdater) updatable;
            if (isTimeStateValUpdater(svu))
            {
                retVal = false;
            }
            else
            {
                Map<String, String> info = extractStateValUpdaterInfo(svu);

                retVal = meetsOutputCriteria(info);
            }
        }

        return retVal;
    }

    /**
     * Tests if the provided Map<String, String> contains information which
     * meets the criteria for output. That is whether it contains the
     * information of a StateVal which can output at the current time.
     * 
     * @param updaterInfo
     *            Map<String,String> containing the necessary information used
     *            to determine if some StateVal should output during this time
     *            tick.
     * @return true if the provided stateval information matches some output
     *         configuration, false otherwise.
     */
    private boolean meetsOutputCriteria(Map<String, String> updaterInfo)
    {
        return (currentHolonTypes.contains(updaterInfo.get("HolonType"))
                && currentHolonNames.contains(updaterInfo.get("HolonName"))
                && currentCurrencies.contains(updaterInfo.get("Currency"))
                && currentBehaviorNames.contains(updaterInfo.get("Behavior")) && currentStateValNames
                    .contains(updaterInfo.get("StateValName")));
    }

    /**
     * Returns the set of tablenames that will be used to store output for the
     * SimulationModel object associated with this OutputConfigManager.
     * 
     * @return The set of output table names to output to
     */
    public synchronized Set<String> getOutputTableNames()
    {
        if (outputTableNames.size() < configs.size())
        {
            setOutputTableNames();
        }
        return outputTableNames;
    }

    /**
     * Sets the available list of output table names to include each output
     * config's table name
     */
    public synchronized void setOutputTableNames()
    {
        outputTableNames.clear();
        for (String name : configs.keySet())
        {
            outputTableNames.add(configs.get(name).getTableName());
        }
    }

    /**
     * Adds the provided OutputConfig to this OutputConfigManager
     * 
     * @param newConfig
     *            new OutputConfig to add
     * @throws OutputConfigManagerException
     *             thrown if the new config's unique id is not unique
     */
    public void addConfig(OutputConfig newConfig) throws OutputConfigManagerException
    {
        if (newConfig == null)
        {
            throw new OutputConfigManagerException("Cannot add a null output config.");
        }
        else if (configs.containsKey(newConfig.getUID()))
        {
            throw new OutputConfigManagerException("OutputConfig ID: " + newConfig.getUID() + " is already in use.");
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
            Set<OutputConfig> temp = new HashSet<>();
            temp.add(newConfig);
            configMap.put(newConfig.getTimeInterval(), temp);
        }
    }

    /**
     * Processes each OutputConfig to ensure that its data will be available
     * during model execution
     */
    public void processConfigs()
    {
        for (String uid : configs.keySet())
        {
            OutputConfig config = configs.get(uid);
            try
            {
                config.processRegEx(model.getMatrix(), RegExProcessor.getInstance());
            }
            catch (OutputConfigException e)
            {
                NEOLogger.logException(model, "Problem processing output configuration: " + config.getUID() + ". ", e);
            }
        }
    }

    /**
     * Tests whether local console output is enabled or not.
     * 
     * @return true if local console output has been enabled for this
     *         OutputConfigManager
     */
    public boolean isLocalOutput()
    {
        return localOutput;
    }

    /**
     * Sets the local console output flag to the provided boolean value
     * 
     * @param localOutput
     *            true if local console output is to be enabled.
     */
    public void setLocalOutput(boolean localOutput)
    {
        this.localOutput = localOutput;
    }

    /**
     * Retrieves a map of output configurations indexed by their names.
     * 
     * @return Map<String, OutputConfig> relating the name of an output config
     *         to the actual output configuration.
     */
    public Map<String, OutputConfig> getOutputConfigs()
    {
        return configs;
    }

    /**
     * Returns the set of table names used to store the provided updatable at
     * the provided time tick
     * 
     * @param tick
     *            Current tick of the model
     * @param updatable
     *            IUpdatable in question
     * @return Set of Strings representing the name of the table where the
     *         information contained in the updatable should be stored, or the
     *         empty string if no table name was found.
     */
    public Set<String> getTableNames(long tick, IUpdatable updatable)
    {
        Set<String> retVal = new HashSet<>();
        if (updatable != null && updatable instanceof StateValUpdater)
        {
            StateValUpdater svu = (StateValUpdater) updatable;
            Map<String, String> info = extractStateValUpdaterInfo(svu);

            boolean useAll = false;
            if (svu.getStateVal().getDynam() != null)
            {
                useAll = true;
            }
            retVal = findTableName(info, useAll);
        }

        return retVal;
    }

    /**
     * Finds the set of table names to which a StateVal should output.
     * 
     * @param info
     *            StateVal information used in finding the table name
     * @param all
     *            if true, then all the information should be used, false only a
     *            restricted subset of the information is required.
     * @return the set of table names or an empty set if no such table names
     *         exist.
     */
    private Set<String> findTableName(Map<String, String> info, boolean all)
    {
        Set<String> retVal = new HashSet<>();
        for (OutputConfig config : currentConfigs)
        {
//            System.out.println("\n\ninfo vs config: " + config.getTableName() + " " + this.model.getCurrentTime());
//            System.out.println("R " + info.get("HolonType") + " | " + config.getTypes().toString());
//            System.out.println("R " + info.get("HolonName") + " | " + config.getHolonNames().toString());
//            System.out.println("A " + info.get("Currency") + " | " + config.getCurrencies().toString());
//            System.out.println("A " + info.get("Behavior") + " | " + config.getBehaviors().toString());
//            System.out.println("R " + info.get("StateValName") + " | " + config.getStateValNames().toString());
//            System.out.println(info.keySet());

            // FIXME Refactoring Required
            if (all && matchesAll(config, info))
            {
                retVal.add(config.getTableName());
            }
            else if (matchesRestricted(config, info) && !all)
            {
                retVal.add(config.getTableName());
            }
        }

        return retVal;
    }

    /**
     * Tests whether the provided information map is a match for the provided
     * OutputConfig
     * 
     * @param config
     *            OutputConfig to be matched
     * @param info
     *            Information Map of some StateVal
     * @return true if the provided config is a complete match for the provided
     *         information map.
     */
    private boolean matchesAll(OutputConfig config, Map<String, String> info)
    {
        return matchesRestricted(config, info) && config.getCurrencies().contains(info.get("Currency"))
                && config.getBehaviors().contains(info.get("Behavior"));
    }

    /**
     * Tests for a restricted match between the provided OutputConfig and
     * StateVal information Map. This method should only be used when a Dynam is
     * not attached to the stateval from which the information map was
     * generated.
     * 
     * @param config
     *            OutputConfig to be matched
     * @param info
     *            Information Map of some StateVal
     * @return true if the provided config is a restricted match for the
     *         provided information map.
     */
    private boolean matchesRestricted(OutputConfig config, Map<String, String> info)
    {
        return config.getTypes().contains(info.get("HolonType"))
                && config.getHolonNames().contains(info.get("HolonName"))
                && config.getStateValNames().contains(info.get("StateValName"));
    }

    /**
     * Extracts the information needed for processing a StateValUpdater into a
     * Map of String values.
     * 
     * @param svu
     *            StateValUpdater requiring processing
     * @return Map of Strings of extracted information
     */
    private Map<String, String> extractStateValUpdaterInfo(StateValUpdater svu)
    {
        Holon holon = svu.getStateVal().getHolon();
        String cellType = model.getMatrix().getCellType(holon.getName());
        String holonType = "";

        if (cellType == null)
            holonType = model.getMatrix().getEdgeType(holon.getName().substring(0, holon.getName().indexOf('-')));
        else
            holonType = cellType;

        Map<String, String> info = new HashMap<>();
        info.put("HolonName", holon.getName());
        info.put("HolonType", holonType);
        info.put("StateValName", svu.getStateVal().getName());
        if (svu.getStateVal().getDynam() != null)
        {
            info.put("Behavior", svu.getStateVal().getDynam().getBehaviorName());
            info.put("Currency", svu.getStateVal().getDynam().getCurrencyName());
        }

        return info;
    }

    /**
     * Tests whether the table names used to store output are to be unique and
     * include a time_date stamp in the name. If not, then the tables may be
     * dropped and recreated.
     * 
     * @return true if table names are to be made unique by including a
     *         date/time stamp
     */
    public boolean isUniqueTableNames()
    {
        return uniqueTableNames;
    }

    /**
     * Sets the flag controlling whether unique output table names will be used.
     * 
     * @param uniqueTableNames
     *            true to use unique table names, false otherwise.
     */
    public void setUniqueTableNames(boolean uniqueTableNames)
    {
        this.uniqueTableNames = uniqueTableNames;
    }

    /**
     * Retrieves the name of the initial values output table
     * 
     * @return Name of the initial values output table
     */
    public String getOutputInitialValuesTable()
    {
        return initialValuesTable;
    }

    /**
     * Sets the initial values output table to the name provided
     * 
     * @param name
     *            name of the initial values output table
     * @throws OutputConfigManagerException
     *             Thrown if the provided table name is null or empty
     */
    public void setOutputInitialValuesTable(String name) throws OutputConfigManagerException
    {
        if (name == null || name.isEmpty())
        {
            throw new OutputConfigManagerException(
                    "Cannot have a null or empty table name for the default initial values table.");
        }
        if (isUniqueTableNames())
        {
            initialValuesTable = model.getName() + "_" + name;
        }
        else
        {
            initialValuesTable = model.getName().substring(0, model.getName().lastIndexOf("-")) + "_" + name;
        }
    }

    /**
     * Retrieves the name of the initial values output table.
     * 
     * @return The name of the initial values table.
     */
    public String getInitialValuesTableName()
    {
        return initialValuesTable;
    }
}
