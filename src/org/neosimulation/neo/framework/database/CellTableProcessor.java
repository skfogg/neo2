package org.neosimulation.neo.framework.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.neosimulation.neo.framework.logging.NEOLogger;

/**
 * CellTableProcessor - Provides an encapsulation and processing logic for the
 * Matrix Cell Table of the Model Database
 * 
 * @author Isaac Griffith
 */
public class CellTableProcessor extends TableProcessor {

    /**
     * The SQL query string to obtain information from the Matrix Cell Table
     */
    private String cellQuery;

    /**
     * Constructs a new CellTableProcessor instance for the given table name
     * 
     * @param dbControl
     *            Associated NEODbController containing the necessary tablename
     *            information
     * @param mediator
     *            DbMediator used to store processed data
     */
    public CellTableProcessor(NEODbController dbControl, DbMediator mediator)
    {
        super(mediator);

        cellQuery = String.format(dbControl.getQuery("CELL_TABLE_QUERY"), dbControl.getCellTableName());
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.database.TableProcessor#processTable(
     * java.sql.Statement)
     */
    @Override
    public void processTable(Statement statement)
    {
        try
        {
            ResultSet entries = statement.executeQuery(cellQuery);

            while (entries.next())
            {
                String cellID = entries.getString("ID");
                String cellType = entries.getString("CellType");

                mediator.addCell(cellID, cellType);
            }
        }
        catch (SQLException sqlex)
        {
            NEOLogger.logWarningException(mediator.getSimulationModel(), "Cell Table: SQL Error", sqlex);
        }
    }
}
