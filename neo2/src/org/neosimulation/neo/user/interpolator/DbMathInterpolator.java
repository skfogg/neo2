/**
 * 
 */
package org.neosimulation.neo.user.interpolator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;

import org.neosimulation.neo.framework.logging.NEOLogger;

/**
 * DbMathInterpolator - A Database based Math Interpolator that uses a database
 * as its data repository. This class assumes that the size of the data in the
 * database is not too large and that all the records can be stored in memory to
 * avoid repeated calls to the database. That is we are assuming that the cost
 * of using the extra memory out weighs the cost to continually refer back to
 * the database.
 * 
 * @author Isaac Griffith
 */
public class DbMathInterpolator extends MathInterpolator {

    /**
     * Database connection to be used to retrieve data to be interpolated.
     */
    private Connection connection;
    /**
     * Name of the table where the data is to be extracted.
     */
    private String tableName;

    /**
     * Constructs a new DbMathInterpolator which uses the provided name as a
     * table name to connect to and which uses the provided strategy as an
     * interpolation method.
     * 
     * @param strategy
     *            Interpolation method
     * @param name
     *            Table Name where the data is located
     * @param conn
     *            Connection used to retrieved the data.
     */
    protected DbMathInterpolator(InterpolationStrategy strategy, String name, Connection conn)
    {
        super(strategy, true);
        this.connection = conn;
        this.tableName = name;

        initialize();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.interpolator.InputInterpolator#
     * canInterpolate()
     */
    @Override
    public boolean canInterpolate()
    {
        boolean retVal = false;

        if (valuesMap.keySet().size() >= strategy.numValuesReqd())
        {
            retVal = true;
        }

        return retVal;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.interpolator.InputInterpolator#initialize
     * (java.lang.String)
     */
    @Override
    protected void initialize()
    {
        try (Statement statement = connection.createStatement())
        {
            ResultSet results = statement.executeQuery("select * from " + tableName + ";");
            updateValuesMap(results);

            results.close();
        }
        catch (SQLException e)
        {
            NEOLogger.logException(this, "could not extract values from the database.", e);
        }
        finally
        {
            try
            {
                connection.close();
            }
            catch (SQLException e)
            {
                NEOLogger.logException(this, "Could not close the connection to the database.", e);
            }
        }

        sortKeys();
    }

    /**
     * Method used to sort the keys into ascending order within in order to
     * perform the interpolation
     */
    private void sortKeys()
    {
        sortedKeys = new double[valuesMap.keySet().size()];
        int i = 0;
        for (double d : valuesMap.keySet())
        {
            if (i >= sortedKeys.length)
            {
                break;
            }
            sortedKeys[i] = d;
            i++;
        }
        Arrays.sort(sortedKeys);
    }

    /**
     * This method updates the map of key value pairs with results from the
     * result set
     * 
     * @param results
     *            Result set from querying the database
     * @throws SQLException
     *             thrown if the resultset cannot be read.
     */
    private void updateValuesMap(ResultSet results) throws SQLException
    {
        HashMap<Double, Double> tempMap = new HashMap<>();

        while (results.next())
        {
            double key = results.getDouble("key");
            double value = results.getDouble("value");

            tempMap.put(key, value);
        }

        valuesMap = tempMap;
    }
}
