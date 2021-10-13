/**
 * 
 */
package org.neosimulation.neo.framework.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.neosimulation.neo.framework.logging.NEOLogger;

/**
 * ConfigKeysVerifier - A utility class used to verify that the control table
 * for a simulation model contains the necessary keys to provide the necessary
 * information to build and run a model.
 * 
 * @author Isaac Griffith
 */
public class ConfigKeysVerifier {

    /**
     * The ConfigKeys object used to verify the control table
     */
    private ConfigKeys keys;
    /**
     * The DbMediator connecting this ConfigKeysVerifier to information loaded
     * from the input database
     */
    private DbMediator mediator;

    /**
     * Constructs a new ConfigKeysVerifier connected to the provided DbMediator
     * 
     * @param mediator
     *            DbMediator allowing access to the information loaded from the
     *            input database
     */
    public ConfigKeysVerifier(DbMediator mediator)
    {
        this.mediator = mediator;
    }

    /**
     * Verify's that the Database Control Table contains all necessary
     * configuration keys. This method uses the provided statement to connect to
     * and query the database control table. If the database does not contain
     * the proper keys a ContolTableProcessorError will be thrown and the system
     * will halt.
     * 
     * @param dbControl
     *            The NEODbControl which provides access to information about
     *            the NEO Input database
     * @param statement
     *            Statement used to connect to and query the database.
     * @param keys
     *            The ConfigKeys instance used to verify the control table
     *            information
     */
    private void verifyConfigKeys(NEODbController dbControl, Statement statement, ConfigKeys keys)
    {
        this.keys = keys;
        String keyQuery = "SELECT control.Key, control.Val FROM control WHERE(control.Active = true);";
        List<String> resourceNames = new ArrayList<String>();
        HashMap<String, String> kvPairs = new HashMap<String, String>();
        try
        {
            ResultSet results = statement.executeQuery(dbControl.getQuery("AVAILABLE_RESOURCES_QUERY"));
            while (results.next())
            {
                resourceNames.add(results.getString(1));
            }
            results.close();

            results = statement.executeQuery(keyQuery);

            while (results.next())
            {
                kvPairs.put(results.getString(1), results.getString(2));
            }

            if (resourceNames.size() > 0)
            {
                verifyKeys(kvPairs.keySet(), resourceNames.get(0));
            }
            else
            {
                verifyKeys(kvPairs.keySet(), null);
            }
            verifyValues(kvPairs);
        }
        catch (SQLException e)
        {
            NEOLogger.logException(mediator.getSimulationModel(), e.getMessage(), e);
        }
    }

    /**
     * Method used to verify that each required key is present in the control
     * table. If the keys cannot be verified, necessary keys are missing, or
     * there are no selected currencies then a ControlTableProcessorError will be
     * thrown and the system halted.
     * 
     * @param foundKeys
     *            set of keys found in the control table which need to be
     *            verified
     * @param knownCurrencyName
     *            name of a currency used to validate the key set
     */
    public void verifyKeys(Set<String> foundKeys, String knownCurrencyName)
    {
        StringBuilder errors = new StringBuilder();
        Set<String> ring = keys.keyRing(knownCurrencyName);
        for (String key : ring)
        {
            if (!foundKeys.contains(key))
            {
                errors.append('\t');
                errors.append(key);
                errors.append('\n');
            }
        }

        if (errors.length() > 0)
        {
            errors.insert(0, "The following required keys where not found in the Control Table.\n");
            throw new ControlTableProcessorError(errors.toString());
        }
    }

    /**
     * Method used to verify that values provided for each key are valid.
     * 
     * @param kvPairs
     *            HashMap of key and value pairs from the control table
     */
    public void verifyValues(HashMap<String, String> kvPairs)
    {
        // TODO Implement this method
    }
}
