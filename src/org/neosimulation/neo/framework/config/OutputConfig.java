/**
 * 
 */
package org.neosimulation.neo.framework.config;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.neosimulation.neo.framework.behcurr.CurrencyManager;
import org.neosimulation.neo.framework.model.Matrix;

/**
 * OutputConfig - Used to store configuration information used to determine when
 * and where statevals should output.
 * 
 * @author Isaac Griffith
 */
public class OutputConfig {

    /**
     * Time interval that describes the ticks of the model when this
     * configuration will be activated.
     */
    private OutputConfigInterval timeInterval;
    /**
     * The unique id of this configuration
     */
    private final String UID;
    /**
     * The set of currency names to be used in determining whether output for a
     * given stateval should occur
     */
    private Set<String> currencies;
    /**
     * The set of behavior names to be used in determining whether output for a
     * given stateval should occur
     */
    private Set<String> behaviors;
    /**
     * The set of holon type names to be used in determining whether output for
     * a given stateval should occur
     */
    private Set<String> types;
    /**
     * The set of holon names to be used in determining whether output for a
     * given stateval should occur
     */
    private Set<String> holonNames;
    /**
     * The set of StateVal names to be used in determining whether output for a
     * given stateval should occur
     */
    private Set<String> stateValNames;
    /**
     * The name of the table to which output for this configuration should be
     * directed.
     */
    private String tableName;
    /**
     * The compiled holon type regular expression.
     */
    private Pattern holonTypeRegEx;
    /**
     * The compiled holon name regular expression.
     */
    private Pattern holonRegEx;
    /**
     * The compiled currency name regular expression.
     */
    private Pattern currencyRegEx;
    /**
     * The compiled behavior name regular expression.
     */
    private Pattern behaviorRegEx;
    /**
     * The compile stateval name regular expression.
     */
    private Pattern stateValRegEx;
    /**
     * The currency manager used to extract currency information.
     */
    private CurrencyManager manager;

    /**
     * Constructs a new OutputConfig objects.
     * 
     * @param manager
     *            CurrencyManager from which currency information is extracted.
     * @param timeInterval
     *            Tick interval when this OutputConfig is activated
     * @param UID
     *            The ID for this OutputConfig
     * @param tableName
     *            Output table name associated with this OutputConfig
     * @param holonType
     *            Holon Type names regular expression
     * @param holon
     *            Holon names regular expression
     * @param currency
     *            Currency names regular expression
     * @param behavior
     *            Behavior names regular expression
     * @throws OutputConfigException
     *             thrown if the provide interval is not within the range 1 to
     *             Integer.MAX_SIZE
     */
    public OutputConfig(CurrencyManager manager, int timeInterval, String UID, String tableName, String holonType,
            String holon, String currency, String behavior, String stateVal) throws OutputConfigException
    {
        this.manager = manager;
        this.timeInterval = new OutputConfigInterval(this, timeInterval);

        if (UID == null || UID.isEmpty())
        {
            throw new OutputConfigException("The UID: " + UID
                    + " is not a valid unique idetifier for an output configuration. "
                    + "Please change this to a string which adequately represents a unique name"
                    + " for this output configuration.");
        }
        else
        {
            this.UID = UID;
        }

        if (tableName == null || tableName.isEmpty())
        {
            throw new OutputConfigException("The Table Name: " + tableName
                    + " is not a valid table name for an output configuration. "
                    + "Please change this to a string which names a known table.");
        }
        else
        {
            this.tableName = tableName;
        }

        currencies = new HashSet<>();
        behaviors = new HashSet<>();
        holonNames = new HashSet<>();
        types = new HashSet<>();
        stateValNames = new HashSet<>();

        try
        {
            this.holonTypeRegEx = Pattern.compile(holonType);
            this.holonRegEx = Pattern.compile(holon);
            this.currencyRegEx = Pattern.compile(currency);
            this.behaviorRegEx = Pattern.compile(behavior);
            this.stateValRegEx = Pattern.compile(stateVal);
        }
        catch (PatternSyntaxException e)
        {
            throw new OutputConfigException("The syntax for one of the regular expressions for OutputConfig: " + UID
                    + " is syntactically incorrect, please fix it.");
        }
        catch (NullPointerException e)
        {
            throw new OutputConfigException(
                    "All cells in the Output Configuration table must be filled in. Please review the table and correct this problem.");
        }
    }

    /**
     * Method used to process the regular expressions contained within this
     * output configuration in order to generate the behaviors, currencies,
     * holon types, and holon names associated with this output configuration.
     * 
     * @param matrix
     *            Matrix used to retrieve the information necessary to populate
     *            this output configuration.
     * @param processor
     *            RegExProcessor instance used to process the regular
     *            expressions.
     * @throws OutputConfigException
     *             thrown if any of the regular expression processing methods
     *             throws an exception due to improper syntax in the regular
     *             expression.
     */
    @SuppressWarnings("unchecked")
    public void processRegEx(Matrix matrix, RegExProcessor processor) throws OutputConfigException
    {
        try
        {
            types.addAll(processor.trimForHolonType(holonTypeRegEx, matrix, true));
            types.addAll(processor.trimForHolonType(holonTypeRegEx, matrix, false));
            behaviors.addAll(processor.trimForBehaviors(behaviorRegEx, matrix, false));
            behaviors.addAll(processor.trimForBehaviors(behaviorRegEx, matrix, true));
            currencies = processor.trimForCurrencies(currencyRegEx, manager);
            holonNames = processor.trimForHolonName(holonRegEx, matrix);
            stateValNames = processor.trimForStateValNames(stateValRegEx, matrix);
        }
        catch (RegExProcessorException e)
        {
            throw new OutputConfigException("There was a problem processing OutputConfig: " + this.UID
                    + "'s regular expressions. The problem was: " + e.getMessage());
        }
    }

    /**
     * Retrieves the time interval on which this output configuration is to be
     * activated. The number returned is the tick interval between activations
     * of this output configuration. For example if this output configuration's
     * time interval is set at 2 then it will fire every 2 time ticks.
     * 
     * @return the time interval when this output configuration is to be
     *         activated.
     */
    public int getTimeInterval()
    {
        return timeInterval.getInterval();
    }

    /**
     * Sets the time interval of when this output configuration should be used.
     * 
     * @param timeInterval
     *            the new time interval for this output configuration.
     * @throws OutputConfigException
     *             thrown if the provided interval is not within the range: 1 -
     *             Integer.MAX_SIZE
     */
    public void setTimeInterval(int timeInterval) throws OutputConfigException
    {
        this.timeInterval = new OutputConfigInterval(this, timeInterval);
    }

    /**
     * Retrieves the unique identifying string of this output configuration
     * 
     * @return the Unique ID of this output configuration
     */
    public String getUID()
    {
        return UID;
    }

    /**
     * Retrieves the set of names of currencies to which this output
     * configuration is associated
     * 
     * @return the names of currencies associated with this output
     *         configuration.
     */
    public Set<String> getCurrencies()
    {
        return currencies;
    }

    /**
     * Retrieves the name of the table to which output associated with this
     * output configuration is to be stored
     * 
     * @return the name of the table the output associated with this output
     *         configuration should be sent to.
     */
    public String getTableName()
    {
        return tableName;
    }

    /**
     * Retrieves the behavior names associated with this output configuration
     * 
     * @return the names of behaviors associated with this output configuration
     */
    public Set<String> getBehaviors()
    {
        return behaviors;
    }

    /**
     * Retrieves the set of holon names associated with this output
     * configuration
     * 
     * @return the names of the holons associated with this output configuration
     */
    public Set<String> getHolonNames()
    {
        return holonNames;
    }

    /**
     * Retrieves the set of stateval names associated with this output
     * configuration.
     * 
     * @return the set of available names to be used for statevals whose output
     *         is associated with this output configuration
     */
    public Set<String> getStateValNames()
    {
        return stateValNames;
    }

    /**
     * Retrieves the set of holon types associated with this output
     * configuration
     * 
     * @return the names of the edge types associated with this output
     *         configuration
     */
    public Set<String> getTypes()
    {
        return types;
    }
}
