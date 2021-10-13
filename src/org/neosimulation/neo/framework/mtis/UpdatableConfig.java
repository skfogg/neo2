/**
 * 
 */
package org.neosimulation.neo.framework.mtis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.neosimulation.neo.framework.behcurr.Behavior;
import org.neosimulation.neo.framework.behcurr.Currency;
import org.neosimulation.neo.framework.behcurr.CurrencyManager;
import org.neosimulation.neo.framework.config.RegExProcessor;
import org.neosimulation.neo.framework.config.RegExProcessorException;
import org.neosimulation.neo.framework.dynam.Dynam;
import org.neosimulation.neo.framework.model.IUpdatable;
import org.neosimulation.neo.framework.model.Matrix;
import org.neosimulation.neo.framework.model.Network;
import org.neosimulation.neo.framework.model.SimulationModel;

/**
 * UpdatableConfig - An object encapsulating the information necessary to constrain a topological sort of the model's dependency graph based on a set of regular
 * expressions for a given interval of the tick.
 * 
 * @author Isaac Griffith
 */
public class UpdatableConfig {

    /**
     * The tick interval associated with this updatable config
     */
    private UpdatableConfigInterval timeInterval;
    /**
     * The unique identifying string associated with this UpdatableConfig
     */
    private final String UID;
    /**
     * The set of dynam names covered by this config
     */
    private Set<String> dynams;
    /**
     * The set of holon type names covered by this config
     */
    private Set<String> holonTypes;
    /**
     * The set of holon names covered by this config
     */
    private Set<String> holonNames;
    /**
     * The set of currency names covered by this config
     */
    private Set<String> currencies;
    /**
     * The set of behavior names convered by this config
     */
    private Set<String> behaviors;
    /**
     * The regular expression limiting dynam names covered by this config
     */
    private Pattern dynamRegEx;
    /**
     * The regular expression limiting holon type names covered by this config
     */
    private Pattern holonTypeRegEx;
    /**
     * The regular expression limiting holon names covered by this config
     */
    private Pattern holonNameRegEx;
    /**
     * The regular expression limiting currency names covered by this config
     */
    private Pattern currencyRegEx;
    /**
     * The regular expression limiting behavior names covered by this config
     */
    private Pattern behaviorRegEx;
    /**
     * The currency manager used by this config
     */
    private CurrencyManager manager;

    /**
     * Constructs a new UpdatableConfig using the provided currency manager and with the provided interval, unique identifier, holon type regex, holon regex,
     * currency regex, behavior regxe, and dynam regex.
     * 
     * @param manager
     *            CurrencyManager used to extract information from
     * @param timeInterval
     *            Interval
     * @param UID
     *            Unique Identfier
     * @param holonType
     *            string representation of the holon type name regular expression
     * @param holon
     *            string representation of the holon type name regular expression
     * @param currency
     *            string representation of the holon type name regular expression
     * @param behavior
     *            string representation of the holon type name regular expression
     * @param dynam
     *            string representation of the holon type name regular expression
     * @throws UpdatableConfigException
     *             thrown if an invalid unique identifier is provided, an invalid interval is provided, or if any of the regular expressions are invalid.
     */
    public UpdatableConfig(CurrencyManager manager, int timeInterval, String UID, String holonType, String holon,
            String currency, String behavior, String dynam) throws UpdatableConfigException
    {
        this.manager = manager;
        try
        {
            this.timeInterval = new UpdatableConfigInterval(this, timeInterval);
        }
        catch (UpdatableConfigIntervalException e)
        {
            throw new UpdatableConfigException(e);
        }

        if (UID == null || UID.isEmpty())
        {
            throw new UpdatableConfigException("The UID: " + UID
                    + " is not a valid unique idetifier for an output configuration. "
                    + "Please change this to a string which adequately represents a unique name"
                    + " for this output configuration.");
        }
        else
        {
            this.UID = UID;
        }

        currencies = new HashSet<>();
        behaviors = new HashSet<>();
        holonNames = new HashSet<>();
        holonTypes = new HashSet<>();
        dynams = new HashSet<>();

        try
        {
            this.holonTypeRegEx = Pattern.compile(holonType);
            this.holonNameRegEx = Pattern.compile(holon);
            this.currencyRegEx = Pattern.compile(currency);
            this.behaviorRegEx = Pattern.compile(behavior);
            this.dynamRegEx = Pattern.compile(dynam);
        }
        catch (PatternSyntaxException e)
        {
            throw new UpdatableConfigException("The syntax for one of the regular expressions for OutputConfig: " + UID
                    + " is syntactically incorrect, please fix it.");
        }
        catch (NullPointerException e)
        {
            throw new UpdatableConfigException(
                    "All cells in the Updatable Configuration table must be filled in. Please review the table and correct this problem.");
        }
    }

    /**
     * Processes the regular expressions in order to derive the correct set of IUpdatables limited by the provided regular expressions
     * 
     * @param network
     *            Network object from the model used to extract information
     * @param matrix
     *            Matrix object from the model used to extract information
     * @param processor
     *            Regular Expression processor used to process the regular expressions
     * @throws UpdatableConfigException
     *             thrown if the regular expression processor encounters problems.
     */
    public void processRegEx(Network network, Matrix matrix, RegExProcessor processor) throws UpdatableConfigException
    {
        try
        {
            holonTypes.addAll(processor.trimForHolonType(holonTypeRegEx, matrix, true));
            holonTypes.addAll(processor.trimForHolonType(holonTypeRegEx, matrix, false));
            behaviors.addAll(processor.trimForBehaviors(behaviorRegEx, matrix, false));
            behaviors.addAll(processor.trimForBehaviors(behaviorRegEx, matrix, true));
            currencies = processor.trimForCurrencies(currencyRegEx, manager);
            holonNames = processor.trimForHolonName(holonNameRegEx, matrix);
            dynams = processor.trimForDynams(dynamRegEx, network);
        }
        catch (RegExProcessorException e)
        {
            throw new UpdatableConfigException("There was a problem processing OutputConfig: " + this.UID
                    + "'s regular expressions. The problem was: " + e.getMessage());
        }
    }

    /**
     * @return the time interval of this config
     */
    public long getTimeInterval()
    {
        return timeInterval.getInterval();
    }

    /**
     * @return the unique identifier of this config
     */
    public String getUID()
    {
        return UID;
    }

    /**
     * @return the set of dynam names applicable to this config
     */
    public Set<String> getDynams()
    {
        return dynams;
    }

    /**
     * @return the holon type names applicable to this config
     */
    public Set<String> getHolonTypes()
    {
        return holonTypes;
    }

    /**
     * @return the holon names applicable to this config
     */
    public Set<String> getHolonNames()
    {
        return holonNames;
    }

    /**
     * @return the currency names applicable to this config
     */
    public Set<String> getCurrencies()
    {
        return currencies;
    }

    /**
     * @return the behavior names applicable to this config
     */
    public Set<String> getBehaviors()
    {
        return behaviors;
    }

    /**
     * Creates a view of the topological sort for the given Simulation Model that is consistent with the IUpdatables limited by the constraints of this config.
     * 
     * @return SortView representing a limited topological sort for the given model at ticks consistent with this config's interval.
     */
    public SortView createView(SimulationModel model)
    {
        CurrencyManager manager = model.getCurrencyManager();
        Set<IUpdatable> viewSet = new TreeSet<IUpdatable>();
        SortView retVal = null;

        for (String name : currencies)
        {
            Currency curr = manager.get(name);
            for (Behavior beh : curr.getCellBehaviors())
            {
                if (behaviors.contains(beh.getName()))
                {
                    List<Dynam> lstDynams = beh.getDynams();

                    for (Dynam dynam : lstDynams)
                    {
                        if (holonNames.contains(dynam.getStateVal().getHolon().getName()))
                        {
                            if (dynams.contains(dynam.getClass().getSimpleName()))
                            {
                                viewSet.add(dynam.getStateVal().getUpdater());
                            }
                        }
                    }
                }
            }
        }

        if (!viewSet.isEmpty())
        {
            retVal = new SortView(this.getTimeInterval(), viewSet);
        }

        return retVal;
    }
}
