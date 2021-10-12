/**
 * 
 */
package org.neosimulation.neo.framework.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.neosimulation.neo.framework.solver.StoppingCondition;

/**
 * TimingTableProcessor - Provides an encapsulation and processing logic for the Timing Table of the Model Database
 * 
 * @author Isaac Griffith
 */
public class TimingTableProcessor extends TableProcessor {

    /**
     * The default time step used when no provided time step can be found in the timing table
     */
    public static final double DEFAULT_TIMESTEP = 1.0;
    /**
     * The default initial value for model time when no such value is provided in the timing table
     */
    public static final double DEFAULT_TIMESEED = 0.0;
    /**
     * The default tick interval when no such value is provided in the timing table
     */
    public static final long DEFAULT_TICK_INTERVAL = 1L;
    /**
     * The default initial tick value when no such value is provided in the timing table.
     */
    public static final long DEFAULT_TICKSEED = 0L;

    /**
     * the query necessary to process the stopping conditions table
     */
    private String query;

    /**
     * Constructs a StoppingConditionTableProcessor processing class associated with the provided NEODbController and DbMediator.
     * 
     * @param dbControl
     *            NEODbController which contains the needed table name to ensure processing occurs
     * @param mediator
     *            Mediator which allows the data to be stored in the proper place
     */
    public TimingTableProcessor(NEODbController dbControl, DbMediator mediator)
    {
        super(mediator);
        String tableName = dbControl.getTimingTableName();
        query = String.format(dbControl.getQuery("TIMING_TABLE_QUERY"), tableName, tableName);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.database.TableProcessor#processTable(java .sql.Statement)
     */
    @Override
    public void processTable(Statement statement)
    {
        try
        {
            double timeStep = DEFAULT_TIMESTEP;
            long tickInterval = DEFAULT_TICK_INTERVAL;
            double timeSeed = DEFAULT_TIMESEED;
            long tickSeed = DEFAULT_TICKSEED;

            ResultSet r = statement.executeQuery(query);
            while (r.next())
            {
                String key = r.getString("Key");
                double value = 0.0d;
                if (key.toLowerCase().startsWith("stoppingcondition"))
                {
                    String condition = "";
                    if (key.contains("."))
                    {
                        condition = key.substring(key.indexOf(".") + 1);
                    }
                    else
                    {
                        continue;
                    }

                    if (!key.endsWith("OnError"))
                    {
                        value = r.getDouble("Value");
                    }

                    StoppingCondition temp = new StoppingCondition(StoppingCondition.ConditionType.valueOf(condition),
                            value);
                    mediator.addStoppingCondition(temp);
                }
                else
                {
                    value = r.getDouble("Value");
                    if (key.equalsIgnoreCase("TimeInterval"))
                    {
                        timeStep = value;
                    }
                    else if (key.equalsIgnoreCase("InitialTimeValue"))
                    {
                        timeSeed = value;
                    }
                    else if (key.equalsIgnoreCase("TickInterval"))
                    {
                        tickInterval = (long) value;
                    }
                    else if (key.equalsIgnoreCase("InitialTickValue"))
                    {
                        tickSeed = (long) value;
                    }
                }

                mediator.initializeTimeKeeper(timeStep, timeSeed, tickInterval, tickSeed);
            }
        }
        catch (SQLException sqlex)
        {
            // TODO change this to log the error
            sqlex.printStackTrace();
        }
    }
}
