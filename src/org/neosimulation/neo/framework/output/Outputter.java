package org.neosimulation.neo.framework.output;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import org.neosimulation.neo.framework.config.DbConfig;
import org.neosimulation.neo.framework.database.JDBCConnectionFactory;
import org.neosimulation.neo.framework.logging.Loggable;
import org.neosimulation.neo.framework.logging.NEOLogger;

/**
 * Outputter - Interface to the output subsystem of the NEO Framework. This
 * class controls all aspects of outputter operation.
 * 
 * @author Michael Paulson
 * @author Isaac Griffith
 * @author Ryan Nix
 */
public abstract class Outputter implements Runnable, Serializable, Loggable {

    /**
     * The thread (if used) in which this Outputter operates.
     */
    protected Thread myThread;
    /**
     * Outputter Runtime Logger
     */
    protected NEOLogger logger;
    /**
     * The pools which the processors / queues run in.
     */
    protected ExecutorService threadPool;
    /**
     * the database connection that is from the properties file.
     */
    protected Connection connection;
    /**
     * A list of output processors that are associated with output providers.
     */
    protected Map<Long, OutputProcessStrategy> outputProcessStrategy;
    /**
     * The time in full maximum.
     */
    @SuppressWarnings("unused")
    private static final long TIME_IN_FULL_MAX = 2500;
    /**
     * the time that the queue can be in critical until another processor is
     * created.
     */
    @SuppressWarnings("unused")
    private static final long TIME_IN_CRITICAL_MAX = 3500;
    /**
     * The time it takes to add a processor
     */
    protected int timeMultiplier = 1;
    /**
     * The Queue of OutputEvents waiting to be processed.
     */
    protected OutputQueue queue;
    /**
     * 
     */
    protected OutputProcessor processor;
    /**
     * The boolean that tells the outputter its time to shutdown.
     */
    protected boolean kill = false;
    /**
     * Whether the outputter is dead or not.
     */
    protected boolean dead = false;

    /**
     * Creates an Outputter that will use the properties file to connect to a
     * database. When an IOutputProvider connects to the outputter, the Queue
     * and Processors will be created when needed.
     * 
     * @param config
     *            The DbConfig which encapsulates the necessary information to
     *            open the connection to the database where the output is to be
     *            written.
     */
    public Outputter(DbConfig config)
    {
        outputProcessStrategy = new ConcurrentHashMap<>();
        enableLogging();

        openConnection(config);
    }

    /**
     * An overrideable method in which the connection to the database is opened.
     * Utility of override is through junit testing to create mock connections.
     * 
     * @param config
     *            The DbConfig which encapsulates the necessary information to
     *            open the connection to the database where the output is to be
     *            written.
     */
    protected void openConnection(DbConfig config)
    {
        try
        {
            connection = JDBCConnectionFactory.openConnection(config.getDriver(), config.getUrl(),
                    config.getUsername(), config.getPassword());
        }
        catch (SQLException sqlex)
        {
            logger.logSevereException("Could not create connection.", sqlex);
        }
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        execute();
    }

    /**
     * Monitors the status of the current queues to determine of the solution to
     * a problem is add more queues or to just simply register any new models to
     */
    protected abstract void execute();

    /**
     * Gets the next output event that is available from the queues.
     * 
     * @return The next OutputEvent to be processed.
     */
    protected abstract OutputEvent getNextOutputEvent();

    /**
     * The number of remaining messages left in the queue.
     * 
     * @return the amount of messages left in the queue.
     */
    protected int remainingMessages()
    {
        return queue.getNumberOfOutputEvents();
    }

    /**
     * Registers the model with an available queue.
     * 
     * @param outputProvider
     *            the provider that will be outputting messages to the queue.
     */
    public void registerModel(IOutputProvider outputProvider)
    {
        queue.registerModel(outputProvider);
        outputProcessStrategy.put(outputProvider.getId(),
                new OutputProcessDatabaseStrategy(this, outputProvider.getOutputTableNames(), true));
    }

    /**
     * Determines if there is output available. If there is output available
     * then getting the next message should be done through
     * "determineOutputQueue"
     * 
     * @return true if the Outputter is still processing OutputEvents, false
     *         otherwise
     */
    protected abstract boolean isOutputEventAvailable();

    /**
     * switches the input from the current queue to another. ***This is on
     * reserve for a multiQueue system.***
     * 
     * @param provider
     *            OutputProvider to switch queues for
     */
    protected void switchOutputQueue(IOutputProvider provider)
    {
        throw new UnsupportedOperationException("Multi-queue output system not yet supported");
    }

    /**
     * Returns a connection to the output database.
     * 
     * @return Java SQL Object.
     */
    protected Connection getConnection()
    {
        return connection;
    }

    /**
     * The process strategy for updating a field of the database.
     * 
     * @param key
     *            The simulation model key.
     * @return the outputstrategy for the model.
     */
    protected OutputProcessStrategy getProcessStrategy(Long key)
    {
        return outputProcessStrategy.get(key);
    }

    /**
     * Retrieves the associated OutputProcessor used to process the OutputEvents
     * sent by the IOutputProvider registered with this Outputter.
     * 
     * @return The OutputProcessor in use with this Outputter.
     */
    public OutputProcessor getOutputProcessor()
    {
        return processor;
    }

    /**
     * Retrieves the associated NEOLogger associated with this Outputter, which
     * handles the logging of exceptions and other problems.
     * 
     * @return The NEOLogger associated with this Outputter.
     */
    public NEOLogger getLogger()
    {
        return logger;
    }

    /**
     * Kills the outputter. This will kill all the queues and processors.
     */
    public void kill()
    {
        kill = true;
    }

    /**
     * Method to return true or false based on if the main processing loop is
     * active
     */
    public boolean isDead()
    {
        return dead;
    }

    /**
     * Returns the OutputQueue object associated with this Outputter, which is
     * used to store OutputEvents prior to processing.
     * 
     * @return The Basic Output Queue used.
     */
    public OutputQueue getOutputQueue()
    {
        return queue;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Loggable#enableLogging()
     */
    @Override
    public void enableLogging()
    {
        logger = new NEOLogger("Outputter", true);
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

    /**
     * Kills queue and closes the database connection.
     */
    public void endAll()
    {
        try
        {
            flush();
            connection.close();
        }
        catch (SQLException e)
        {
            NEOLogger.logException(this, "Could not close the connection to the output database.", e);
        }
        finally
        {
            connection = null;
        }
    }

    public abstract void flush();
}