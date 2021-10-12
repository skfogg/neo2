/**
 * 
 */
package org.neosimulation.neo.user.interpolator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.neosimulation.neo.framework.logging.NEOLogger;

/**
 * DbInputInterpolator - A time-series data interpolator that uses a database as
 * its data repository. This input interpolator uses a scrolling window design
 * to minimize the amount of memory used by available data.
 * 
 * @author Isaac Griffith
 */
public class DbInputInterpolator extends InputInterpolator {

    /**
     * Database connection to be used to retrieve data to be interpolated.
     */
    private Connection connection;
    /**
     * Name of the table where the data is to be extracted.
     */
    private String tableName;
    /**
     * These provide the boundary of the window of data in the record set of the
     * database. This assumes that the data remains in some ordered fashion.
     */
    private int maxRecordNum, minRecordNum;

    /**
     * Constructs a new DbInputInterpolator using the provided interpolation
     * strategy and connected to the provided tablename using the provided
     * connection. The integer winSize is used to provide the correct scrolling
     * window size which is used to constrain the amount of records available at
     * any given time.
     * 
     * @param strategy
     *            Interpolation method
     * @param name
     *            Table Name where the data is located
     * @param conn
     *            Connection used to retrieved the data.
     * @param winSize
     *            Maximum number of records to be loaded at any given time.
     */
    protected DbInputInterpolator(InterpolationStrategy strategy, String name, Connection conn, int winSize)
    {
        super(strategy, true);
        this.connection = conn;
        this.tableName = name;
        this.maxWindowSize = winSize;
        this.maxRecordNum = 0;
        this.minRecordNum = 0;

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
        try (Statement test = connection.createStatement())
        {
            ResultSet results = test.executeQuery("select * from " + tableName + ";");

            results.next();
            if (!results.isLast())
            {
                retVal = true;
            }
        }
        catch (SQLException e)
        {
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
        try(Statement statement = connection.createStatement())
        {
            statement.setMaxRows(maxWindowSize);

            ResultSet results = statement.executeQuery("select * from " + tableName + ";");
            results.next();
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
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.interpolator.InputInterpolator#
     * scrollRecord(double, boolean)
     */
    @Override
    protected void scrollRecord(double key)
    {
        try (Statement statement = connection.createStatement())
        {
            ResultSet results = statement.executeQuery("select * from " + tableName + ";");
            if (key > maxKey)
            {
                results.absolute(maxRecordNum);
            }
            else if (key < minKey)
            {
                results.absolute(minRecordNum);
                if (results.getRow() == 1)
                    throw new InterpolationError();
                else if (results.getRow() - maxWindowSize < 1)
                    results.absolute(1);
            }

            int min = results.getRow();
            if (min == 1)
                results.beforeFirst();
            else
                results.previous();

            updateValuesMap(results, min, min + maxWindowSize);
            results.close();
        }
        catch (SQLException e)
        {
            NEOLogger.logException(this, "Could not retrieve next set of values.", e);
        }
    }

    /**
     * This method updates the map of key value pairs with results from the
     * result set to be from 1 to maxWindowSize. Effectively it resets the
     * window to the beginning of the dataset.
     * 
     * @param results
     *            Result set from querying the database
     * @throws SQLException
     *             thrown if the resultset cannot be read.
     */
    private void updateValuesMap(ResultSet results) throws SQLException
    {
        updateValuesMap(results, 1, maxWindowSize);
    }

    /**
     * This method updates the map of key value pairs with results from the
     * result set to be from minRow to maxRow.
     * 
     * @param results
     *            Result set from querying the database
     * @param minRow
     *            lower bound record number of the result set to be read
     * @param maxRow
     *            upper bound record number of the result set to be read
     * @throws SQLException
     *             thrown if the result set cannot be read
     */
    private void updateValuesMap(ResultSet results, int minRow, int maxRow) throws SQLException
    {
        HashMap<Double, Double> tempMap = new HashMap<>();

        this.minRecordNum = minRow;

        if (maxRow == minRow)
            maxRow = Integer.MAX_VALUE;

        while (results.next() && results.getRow() <= maxRow)
        {
            double key = results.getDouble("key");
            double value = results.getDouble("value");

            tempMap.put(key, value);
        }

        valuesMap = tempMap;

        this.maxRecordNum = results.getRow();
    }
}
