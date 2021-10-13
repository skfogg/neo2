package org.neosimulation.neo.framework.output;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.neosimulation.neo.framework.logging.NEOLogger;

/**
 * OutputProcessDatabaseStrategy - A Strategy for outputting information. All
 * the information will be outputted to a database with the table name provided
 * by the IOutputProvider.
 * 
 * @author Michael Paulson
 * @author Isaac Griffith
 */
public class OutputProcessDatabaseStrategy extends OutputProcessStrategy {

    /**
     * SQL table creation format string
     */
    private static final String CREATE_TABLE_STRING = "CREATE TABLE `%s` (`modelTick` integer NOT NULL, `modelTime` double NOT NULL,`holonName` varchar(100) NOT NULL,`stateVal` varchar(100) NOT NULL,`svValue` varchar(100) NOT NULL,PRIMARY KEY (`modelTick`,`modelTime`,`holonName`,`stateVal`));";
    /**
     * SQL insert format string
     */
    private static final String INSERT_INTO_STRING = "INSERT INTO `%s` (modelTick, modelTime, holonName, stateVal, svValue) VALUES(%s, %s, \'%s\', \'%s\', \'%s\');";

    private static final String INSERT_MULT_INTO_STRING = "INSERT INTO `%s` (modelTick, modelTime, holonName, stateVal, svValue) VALUES";
    private static final String VALUES_STRING = "(%s, %s, \'%s\', \'%s\', \'%s\')";
    /**
     * SQL update format string
     */
    private static final String UPDATE_STRING = "UPDATE `%s` SET value = %s, value = %s WHERE id = %s AND time = %s;";
    /**
     * SQL drop format string
     */
    private static final String DROP_TABLE_STRING = "DROP TABLE `%s`;";
    /**
     * The connection to the database.
     */
    private Connection connection;
    /**
     * if the database strategy was successful or not.
     */
    private boolean successful = false;
    /**
     * Set of registered table names of tables in the associated database schema
     * to which this output process connects, where output is to be directed.
     */
    private Set<String> tableNames;
    /**
     * The runtime of this particular model. Multiple runtimes can exist in the
     * same output table
     */
    private String runTime;

    /**
     * Creates a new output strategy that maintains the connection to a database
     * and performs the processOutput action.
     * 
     * @param outputter
     *            The outputter to which this Strategy is associated, and for
     *            which this strategy processes output events for.
     * @param tableNames
     *            List of registered table names into which output can be
     *            potentially stored
     * @param attemptToCreateTable
     *            flag determining whether the provided tables should be created
     *            if they are non-existant.
     */
    protected OutputProcessDatabaseStrategy(Outputter outputter, Set<String> tableNames, boolean attemptToCreateTable)
    {
        super(outputter);
        this.runTime = Calendar.getInstance().getTime().toString();
        this.tableNames = tableNames;
        connection = outputter.getConnection();
        if (attemptToCreateTable)
        {
            createOutputTables();
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.OutputProcessStrategy#processOutput
     * (java.lang.String)
     */
    @Override
    protected void processOutput(String statement)
    {
        try (Statement sqlStatement = connection.createStatement())
        {
            sqlStatement.execute(statement);
            sqlStatement.close();
        }
        catch (SQLException ex)
        {
            NEOLogger.logException(outputter,
                    "Exception while trying to process output in OutputProcessDatabaseStrategy: " + statement, ex);
        }
    }

    /**
     * Drops all previous data and creates the particle table. with the same
     * prefix as the report data table.
     */
    private void createOutputTables()
    {
        processSqlQueryPerTableName(DROP_TABLE_STRING);
        processSqlQueryPerTableName(CREATE_TABLE_STRING);
    }

    private void processSqlQueryPerTableName(String query)
    {
        String tableName = "";
        try (Statement sqlStatement = connection.createStatement())
        {
            Iterator<String> iter = tableNames.iterator();
            while (iter.hasNext())
            {
                tableName = iter.next();
                String sql = String.format(query, tableName);
                sqlStatement.execute(sql);
            }

            sqlStatement.close();
        }
        catch (SQLException ex)
        {
            NEOLogger.logException(outputter, "Exception while processing table: " + tableName, ex);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.OutputProcessStrategy#
     * formatInsertOutput(java.util.StringTokenizer)
     */
    @Override
    public String formatInsertOutput(String tableName, StringTokenizer eventSt)
    {
        return String.format(INSERT_INTO_STRING, tableName, eventSt.nextToken(), eventSt.nextToken(),
                eventSt.nextToken(), eventSt.nextToken(), eventSt.nextToken());
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.OutputProcessStrategy#
     * formatUpdateOutput(java.util.StringTokenizer)
     */
    @Override
    public String formatUpdateOutput(String tableName, StringTokenizer eventSt)
    {
        return String.format(UPDATE_STRING, tableName, eventSt.nextToken(), eventSt.nextToken(), eventSt.nextToken(),
                eventSt.nextToken());
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.OutputProcessStrategy#
     * formatInsertOutput(java.lang.String, java.util.List)
     */
    @Override
    protected String formatInsertOutput(String tableName, List<StringTokenizer> eventSts)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(INSERT_MULT_INTO_STRING, tableName));
        Iterator<StringTokenizer> iter = eventSts.iterator();
        while (iter.hasNext())
        {
            StringTokenizer eventSt = iter.next();
            builder.append(String.format(VALUES_STRING, eventSt.nextToken(), eventSt.nextToken(), eventSt.nextToken(),
                    eventSt.nextToken(), eventSt.nextToken()));
            if (iter.hasNext())
                builder.append(", ");
        }
        builder.append(";");
        return builder.toString();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.OutputProcessStrategy#
     * formatInsertOutput(java.lang.String)
     */
    @Override
    public String formatInsertOutput(String tableName)
    {
        return String.format(INSERT_MULT_INTO_STRING, tableName);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.OutputProcessStrategy#
     * formatUpdateOutput(java.lang.String)
     */
    @Override
    public String formatUpdateOutput(String tableName)
    {
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.OutputProcessStrategy#
     * formatValueString(java.util.StringTokenizer)
     */
    @Override
    public String formatValueString(StringTokenizer eventSt)
    {
        return String.format(VALUES_STRING, eventSt.nextToken(), eventSt.nextToken(), eventSt.nextToken(),
                eventSt.nextToken(), eventSt.nextToken());
    }
}
