/**
 * 
 */
package org.neosimulation.neo.user.interpolator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.neosimulation.neo.framework.database.DatabaseManager;
import org.neosimulation.neo.framework.database.DatabaseManagerException;
import org.neosimulation.neo.framework.database.JDBCConnectionFactory;
import org.neosimulation.neo.framework.logging.Loggable;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.model.SimulationModel;

/**
 * InterpolationFactory - A factory which provides a means to correctly
 * construct Interpolators for use in Models. Implemented as a singleton.
 * 
 * @author Isaac Griffith
 */
public class InterpolatorFactory implements Loggable {

    // TODO solidify the api of this class

    /**
     * The default maximum window size used for Input Interpolators, the number
     * of lines to read from a file or records to be read from a database.
     */
    private static int DEFAULT_MAX_WINDOW = 20;
    /**
     * The singleton instance of this factory
     */
    private static InterpolatorFactory instance;
    /**
     * The Logger associated with this InterpolationFactory.
     */
    private NEOLogger logger;

    /**
     * The private constructor for this Singleton
     */
    private InterpolatorFactory()
    {
        enableLogging();
    }

    /**
     * @return The singleton instance of this factory.
     */
    public static InterpolatorFactory getInstance()
    {
        if (instance == null)
        {
            instance = new InterpolatorFactory();
        }

        return instance;
    }

    /**
     * Constructs a new InputInterpolator, which uses the default
     * LinearInerpolation strategy.
     * 
     * @param name
     *            Either a file name or a database name.
     * @param manager
     *            A DatabaseManager to be used if the provided name is a table
     *            name in the Model Control database.
     * @return new InputInterpolator.
     * @throws InterpolatorFactoryException
     *             thrown if the interpolator cannot be created, or if the
     *             amount of available data is insufficient to provide
     *             interpolation.
     */
    public InputInterpolator createInputInterpolator(String name, DatabaseManager manager)
            throws InterpolatorFactoryException
    {
        return createInputInterpolator(name, new LinearInterpolation(), manager);
    }

    /**
     * Constructs a new InputInterpolator using the provied name and
     * interpolation strategy.
     * 
     * @param name
     *            table or file name where the data is stored
     * @param strategy
     *            the interpolation method to be used
     * @param manager
     *            A DatabaseManager to be used if the provided name is a table
     *            name in the Model Control database.
     * @return a new InputInterpolator for the given data set.
     * @throws InterpolatorFactoryException
     *             thrown if the interpolator cannot be created, or if the
     *             amount of available data is insufficient to provide
     *             interpolation.
     */
    public InputInterpolator createInputInterpolator(String name, InterpolationStrategy strategy,
            DatabaseManager manager) throws InterpolatorFactoryException
    {
        InputInterpolator interpolator = null;
        // try to determine whether this is a file interpolator or db
        // interpolator
        // try file first.
        // File file = new File(name);
        // if (file.exists())
        // {
        // interpolator = new FileInputInterpolator(strategy, name);
        // if (interpolator.canInterpolate())
        // {
        // return interpolator;
        // }
        // else
        // {
        // // interpolator = new DbInputInterpolator(name, )
        // }
        // }
        // else
        // {
        try
        {
            interpolator = new DbInputInterpolator(strategy, name, manager.getControlDbConnection(), DEFAULT_MAX_WINDOW);
        }
        catch (SQLException | DatabaseManagerException e)
        {
            NEOLogger.logException(this, e.getMessage(), e);
        }
        // }

        if (interpolator.canInterpolate())
            return interpolator;
        else
            throw new InterpolatorFactoryException("Cannot create interpolator for: " + name);
    }

    /**
     * Constructs a new InputInterpolator for data stored in a database at the
     * provided table name and accessed using the provided connection. The
     * provided strategy is used to define the means of interpolation.
     * 
     * @param tableName
     *            Database table name where the information is stored.
     * @param strategy
     *            Interpolation method
     * @param conn
     *            Database connection to be used
     * @return InputInterpolator to interpolate across the stored data.
     */
    public InputInterpolator createInputInterpolator(String tableName, InterpolationStrategy strategy, Connection conn)
    {
        return new DbInputInterpolator(strategy, tableName, conn, DEFAULT_MAX_WINDOW);
    }

    /**
     * Creates a new InputInterpolator for the provided tableName in a database
     * connected to by the provided connection.
     * 
     * @param tableName
     *            Table name in the database, accessed by the provided
     *            connection, where the data is stored.
     * @param conn
     *            Connection to the database.
     * @return A new InputInterpolator for the provided data source
     */
    public InputInterpolator createInputInterpolator(String tableName, Connection conn)
    {
        return new DbInputInterpolator(new LinearInterpolation(), tableName, conn, DEFAULT_MAX_WINDOW);
    }

    /**
     * Creates a basic MathInterpolator using linear interpolation. The provided
     * string can either represent a specific file location (for a text file) or
     * the name of a table within the main input database connection for the
     * model. Note: if a table name is specified, but a file exists within the
     * current working directory with the same name, the system will attempt to
     * load the file and will disregard the table. Use with caution.
     * 
     * @param name
     *            Name of a file or table which in which the data is stored.
     * @param model
     *            The model for which the interpolator is needed.
     * @return A MathInterpolator using the linear interpolation strategy, and
     *         which uses either a file or database table with the provided name
     *         to retrieve data for the interpolation process.
     * @throws InterpolatorFactoryException
     *             Thrown if neither a file or table on the input database
     *             connection, with viable data can be found.
     */
    public MathInterpolator createMathInterpolator(String name, SimulationModel model)
            throws InterpolatorFactoryException
    {
        MathInterpolator interpolator = null;
        // try to determine whether this is a file interpolator or db
        // interpolator
        // try file first
        // interpolator = new FileMathInterpolator(name, new
        // LinearInterpolation());
        //
        // if (interpolator.canInterpolate())
        // {
        // return interpolator;
        // }
        // else
        // {
        try
        {
            interpolator = new DbMathInterpolator(new LinearInterpolation(), name,
                    JDBCConnectionFactory.openConnection(model.getRunConfig().getInputDbConfig()));
        }
        catch (SQLException | IOException e)
        {
            NEOLogger.logException(this, "Could not access the table to create the interpolator.", e);
        }
        // }

        if (interpolator.canInterpolate())
            return interpolator;
        else
            throw new InterpolatorFactoryException("Cannot create interpolator for: " + name);
    }

    /**
     * Sets the Default Maximum Window Size to be the provided size.
     * 
     * @param size
     *            maximum window size for input interpolators.
     */
    public void setMaxWindowSize(int size)
    {
        DEFAULT_MAX_WINDOW = size;
    }

    /**
     * Creates a InputInterpolator using the provided keys (the known value to
     * query against) and their known associated values to create an
     * Interpolator based on Vector store. The interpolation is controlled by
     * the provided strategy. Note that due to using a vector based storage,
     * there is no sliding window function for this InputInterpolator, since all
     * the data is already in memory.
     * 
     * @param keys
     *            The set of numbers which will be queried against.
     * @param values
     *            The set of known values corresponding to the keys list.
     * @return An InputInterpolator based on vector storage which utilizes the
     *         keys and values arrays to provide the initial data. The provided
     *         strategy is utilized to provide interpolation over the keys and
     *         values to achieve the result. Note that the queried value must be
     *         within the range of contained values in the keys list.
     */
    public InputInterpolator createInputInterpolator(double[] keys, double[] values)
    {
        Map<Double, Double> valMap = new HashMap<>();

        for (int i = Math.min(keys.length, values.length) - 1; i >= 0; i--)
        {
            valMap.put(keys[i], values[i]);
        }

        return createInputInterpolator(valMap, new LinearInterpolation());
    }

    /**
     * Creates a InputInterpolator using the provided keys (the known value to
     * query against) and their known associated values to create an
     * Interpolator based on Vector store. The interpolation is controlled by
     * the provided strateg. Note that due to using a vector based storage,
     * there is no sliding window function for this InputInterpolator, since all
     * the data is already in memory.
     * 
     * @param keys
     *            The set of numbers which will be queried against.
     * @param values
     *            The set of known values corresponding to the keys list.
     * @return An InputInterpolator based on vector storage which utilizes the
     *         keys and values arrays to provide the initial data. The provided
     *         strategy is utilized to provide interpolation over the keys and
     *         values to achieve the result. Note that the queried value must be
     *         within the range of contained values in the keys list.
     */
    public InputInterpolator createInputInterpolator(int[] keys, int[] values)
    {
        Map<Double, Double> valMap = new HashMap<>();

        for (int i = Math.min(keys.length, values.length) - 1; i >= 0; i--)
        {
            valMap.put((double) keys[i], (double) values[i]);
        }

        return createInputInterpolator(valMap, new LinearInterpolation());
    }

    /**
     * Creates a InputInterpolator using the provided keys (the known value to
     * query against) and their known associated values to create an
     * Interpolator based on Vector store. The interpolation is controlled by
     * the provided strategy. Note that although this method is a generic method
     * taking any type that derives from Number it will convert the values to
     * double. Note that due to using a vector based storage, there is no
     * sliding window function for this InputInterpolator, since all the data is
     * already in memory.
     * 
     * @param <T>
     *            The type of values passed in, the values will ultimately be
     *            converted to doubles.
     * @param keys
     *            The set of numbers which will be queried against.
     * @param values
     *            The set of known values corresponding to the keys list.
     * @return An InputInterpolator based on vector storage which utilizes the
     *         keys and values lists to provide the initial data. The provided
     *         strategy is utilized to provide interpolation over the keys and
     *         values to achieve the result. Note that the queried value must be
     *         within the range of contained values in the keys list.
     */
    public <T extends Number> InputInterpolator createInputInterpolator(Vector<T> keys, Vector<T> values)
    {
        Map<Double, Double> valMap = new HashMap<>();

        for (int i = Math.min(keys.size(), values.size()) - 1; i >= 0; i++)
        {
            valMap.put(keys.get(i).doubleValue(), values.get(i).doubleValue());
        }

        return createInputInterpolator(valMap, new LinearInterpolation());
    }

    /**
     * Creates a InputInterpolator using the provided keys (the known value to
     * query against) and their known associated values to create an
     * Interpolator based on Vector store. The interpolation is controlled by
     * the provided strategy. Note that although this method is a generic method
     * taking any type that derives from Number it will convert the values to
     * double. Note that due to using a vector based storage, there is no
     * sliding window function for this InputInterpolator, since all the data is
     * already in memory.
     * 
     * @param <T>
     *            The type of values passed in, the values will ultimately be
     *            converted to doubles.
     * @param keys
     *            The set of numbers which will be queried against.
     * @param values
     *            The set of known values corresponding to the keys list.
     * @return An InputInterpolator based on vector storage which utilizes the
     *         keys and values lists to provide the initial data. The provided
     *         strategy is utilized to provide interpolation over the keys and
     *         values to achieve the result. Note that the queried value must be
     *         within the range of contained values in the keys list.
     */
    public <T extends Number> InputInterpolator createInputInterpolator(List<T> keys, List<T> values)
    {
        Map<Double, Double> valMap = new HashMap<>();

        for (int i = Math.min(keys.size(), values.size()) - 1; i >= 0; i++)
        {
            valMap.put(keys.get(i).doubleValue(), values.get(i).doubleValue());
        }

        return createInputInterpolator(valMap, new LinearInterpolation());
    }

    /**
     * Creates a new InputInterpolator which utilizes a vector to store the data
     * and interpolate from. The data is provided as a map of values indexed by
     * keys. For example, in a 2D cartesian system the keys would be the X
     * values and the values (of the map) would be the y values.
     * 
     * @param valuesMap
     *            a map of known related value pairs, keys by the column in
     *            which we want to provide values to the interpolator with.
     * @param strategy
     *            The specific algorithmic strategy to be used to perform the
     *            interpolation (e.g., LinearInterpolation)
     * @return An InputInterpolator utilizing a Vector for storing the values in
     *         the provided map and using the provided strategy fo0r
     *         interpolation.
     */
    public InputInterpolator createInputInterpolator(Map<Double, Double> valuesMap, InterpolationStrategy strategy)
    {
        return new VectorInputInterpolator(valuesMap, strategy);
    }

    /**
     * Creates a MathInterpolator using the provided keys (the known value to
     * query against) and their known associated values to create an
     * Interpolator based on Vector storage. The interpolation is controlled by
     * the provided strategy.
     * 
     * @param keys
     *            The set of numbers which will be queried against.
     * @param values
     *            The set of known values corresponding to the keys array.
     * @return A MathInterpolator based on vector storage which utilizes the
     *         keys and values lists to provide the initial data. The provided
     *         strategy is utilized to provide interpolation over the keys and
     *         values to achieve the result. Note that the queried value must be
     *         within the range of contained values in the keys array.
     */
    public MathInterpolator createMathInterpolator(double[] keys, double[] values)
    {
        Map<Double, Double> valMap = new HashMap<>();

        for (int i = Math.min(keys.length, values.length) - 1; i >= 0; i++)
        {
            valMap.put(keys[i], values[i]);
        }

        return createMathInterpolator(valMap, new LinearInterpolation());
    }

    /**
     * Creates a MathInterpolator using the provided keys (the known value to
     * query against) and their known associated values to create an
     * Interpolator based on Vector storage. The interpolation is controlled by
     * the provided strategy.
     * 
     * @param keys
     *            The set of numbers which will be queried against.
     * @param values
     *            The set of known values corresponding to the keys array.
     * @return A MathInterpolator based on vector storage which utilizes the
     *         keys and values lists to provide the initial data. The provided
     *         strategy is utilized to provide interpolation over the keys and
     *         values to achieve the result. Note that the queried value must be
     *         within the range of contained values in the keys array.
     */
    public MathInterpolator createMathInterpolator(int[] keys, int[] values)
    {
        Map<Double, Double> valMap = new HashMap<>();

        for (int i = Math.min(keys.length, values.length) - 1; i >= 0; i--)
        {
            valMap.put((double) keys[i], (double) values[i]);
        }

        return createMathInterpolator(valMap, new LinearInterpolation());
    }

    /**
     * Creates a MathInterpolator using the provided keys (the known value to
     * query against) and their known associated values to create an
     * Interpolator based on Vector storage. The interpolation is controlled by
     * the provided strategy. Note that although this method is a generic method
     * taking any type that derives from Number it will convert the values to
     * double.
     * 
     * @param <T>
     *            The type of values passed in, the values will ultimately be
     *            converted to doubles.
     * @param keys
     *            The set of numbers which will be queried against.
     * @param values
     *            The set of known values corresponding to the keys list.
     * @return A MathInterpolator based on vector storage which utilizes the
     *         keys and values lists to provide the initial data. The provided
     *         strategy is utilized to provide interpolation over the keys and
     *         values to achieve the result. Note that the queried value must be
     *         within the range of contained values in the keys vector.
     */
    public <T extends Number> MathInterpolator createMathInterpolator(Vector<T> keys, Vector<T> values)
    {
        Map<Double, Double> valMap = new HashMap<>();

        for (int i = Math.min(keys.size(), values.size()) - 1; i >= 0; i++)
        {
            valMap.put(keys.get(i).doubleValue(), values.get(i).doubleValue());
        }

        return createMathInterpolator(valMap, new LinearInterpolation());
    }

    /**
     * Creates a MathInterpolator using the provided keys (the known value to
     * query against) and their known associated values to create an
     * Interpolator based on Vector storage. The interpolation is controlled by
     * the provided strategy. Note that although this method is a generic method
     * taking any type that derives from Numberit will convert the values to
     * double.
     * 
     * @param <T>
     *            The type of values passed in, the values will ultimately be
     *            converted to doubles.
     * @param keys
     *            The set of numbers which will be queried against.
     * @param values
     *            The set of known values corresponding to the keys list.
     * @return A MathInterpolator based on vector storage which utilizes the
     *         keys and values lists to provide the initial data. The provided
     *         strategy is utilized to provide interpolation over the keys and
     *         values to achieve the result. Note that the queried value must be
     *         within the range of contained values in the keys list.
     */
    public <T extends Number> MathInterpolator createMathInterpolator(List<T> keys, List<T> values)
    {
        Map<Double, Double> valMap = new HashMap<>();

        for (int i = Math.min(keys.size(), values.size()) - 1; i >= 0; i++)
        {
            valMap.put(keys.get(i).doubleValue(), values.get(i).doubleValue());
        }

        return createMathInterpolator(valMap, new LinearInterpolation());
    }

    /**
     * Creates a new MathInterpolator which utilizes a vector to store the data
     * and interpolate from. The data is provided as a map of values indexed by
     * keys. For example, in a 2D cartesian system the keys would be the X
     * values and the values (of the map) would be the y values.
     * 
     * @param valuesMap
     *            a map of known related value pairs, keys by the column in
     *            which we want to provide values to the interpolator with.
     * @param strategy
     *            The specific algorithmic strategy to be used to perform the
     *            interpolation (e.g., LinearInterpolation)
     * @return A MathInterpolator utilizing a Vector for storing the values in
     *         the provided map and using the provided strategy fo0r
     *         interpolation.
     */
    public MathInterpolator createMathInterpolator(Map<Double, Double> valuesMap, InterpolationStrategy strategy)
    {
        return new VectorMathInterpolator(valuesMap, strategy);
    }

    /**
     * Creates a new Math Interpolator using the provided Database Connection to
     * read the data from the provided table. The data is then interpolated
     * according to the provided InterpolationStrategy.
     * 
     * @param tableName
     *            Name of the table to be used to extract the data. It must be
     *            able to be read usin the connection provided.
     * @param strategy
     *            Strategy encapsulating the specific algorithm used to
     *            interpolate the data.
     * @param conn
     *            Connection to a database where the table with the provided
     *            name resides and can be read from.
     * @return A MathInterpolator based on the provided specifications which can
     *         interpolate the data.
     */
    public MathInterpolator createMathInterpolator(String tableName, InterpolationStrategy strategy, Connection conn)
    {
        return new DbMathInterpolator(strategy, tableName, conn);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Loggable#getLogger()
     */
    @Override
    public NEOLogger getLogger()
    {
        return logger;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Loggable#enableLogging()
     */
    @Override
    public void enableLogging()
    {
        logger = new NEOLogger("InterpolatorFactory");
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Loggable#disableLogging()
     */
    @Override
    public void disableLogging()
    {
        logger = null;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Loggable#isLoggingEnabled()
     */
    @Override
    public boolean isLoggingEnabled()
    {
        return (logger != null);
    }
}
