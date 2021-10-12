package org.neosimulation.neo.framework.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.neosimulation.neo.framework.logging.NEOLogger;

/**
 * EdgeTableProcessor - Provides encapsulation and processing logic for the
 * Matix DependencyEdge Table for the model
 * 
 * @author Isaac Griffith
 */
public class EdgeTableProcessor extends TableProcessor {

    /**
     * The SQL Query string used to retrieve all information from the Matrix
     * edge table
     */
    private String edgeQuery;

    /**
     * Constructs a new instance of EdgeTableProcessor for the matrix edge table
     * with the given table name
     * 
     * @param dbControl
     *            Associated NEODbController containing the necessary tablename
     *            information
     * @param mediator
     *            DbMediator used to store processed data
     */
    public EdgeTableProcessor(NEODbController dbControl, DbMediator mediator)
    {
        super(mediator);

        edgeQuery = String.format(dbControl.getQuery("EDGE_TABLE_QUERY"), dbControl.getEdgeTableName());
    }

    /**
     * Processes the the table represented by this class
     * 
     * @param statement
     *            The Statment object representing a connection to the DB
     */
    public void processTable(Statement statement)
    {
        try
        {
            ResultSet entries = statement.executeQuery(edgeQuery);

            while (entries.next())
            {
                String edgeID = entries.getString("ID");
                String edgeType = entries.getString("EdgeType");
                String fromCellID = entries.getString("FromCell");
                String toCellID = entries.getString("ToCell");

                mediator.addEdge(edgeID, edgeType, fromCellID, toCellID);
            }
        }
        catch (SQLException sqlex)
        {
            NEOLogger.logWarningException(mediator.getSimulationModel(), "DependencyEdge Table: SQL Error", sqlex);
        }
    }
}
