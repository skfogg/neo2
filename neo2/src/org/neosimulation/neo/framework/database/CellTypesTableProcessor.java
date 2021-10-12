package org.neosimulation.neo.framework.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.neosimulation.neo.framework.behcurr.Behavior;
import org.neosimulation.neo.framework.behcurr.Currency;
import org.neosimulation.neo.framework.logging.NEOLogger;

/**
 * CellTypesTableProcessor - Provides encapsulation and processing logic for the
 * information stored in the Matrix Cell Types table of the model database
 * 
 * @author Isaac Griffith
 */
public class CellTypesTableProcessor extends TableProcessor {

    /**
     * The SQL Query string used to extract information from the matrix cell
     * types table
     */
    private String typeQuery;

    /**
     * Constructs a new instance of CellTypesTableProcessor for the given
     * database table name
     * 
     * @param dbControl
     *            Associated NEODbController containing the necessary tablename
     *            information
     * @param mediator
     *            DbMediator used to store processed data
     */
    public CellTypesTableProcessor(NEODbController dbControl, DbMediator mediator)
    {
        super(mediator);
        typeQuery = String.format(dbControl.getQuery("CELL_TYPE_TABLE_QUERY"), dbControl.getCellTypeTableName());
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.database.TableProcessor#processTable(java
     * .sql.Statement)
     */
    public void processTable(Statement statement)
    {
        try
        {
            ResultSet entries = statement.executeQuery(typeQuery);

            while (entries.next())
            {
                String cellType = entries.getString("CellType");
                String behavior = entries.getString("CellBehavior");

                Set<Behavior> behaviors = mediator.getCellBehaviors(cellType);
                if (behaviors == null)
                {
                    behaviors = new HashSet<>();
                }

                String resourceName = behavior.substring(0, behavior.indexOf("."));
                Currency currency = mediator.getCurrency(resourceName);
                Behavior newBehavior = currency.registerCellBehavior(behavior);
                behaviors.add(newBehavior);

                mediator.setCellBehaviors(cellType, behaviors);
            }
        }
        catch (SQLException sqlex)
        {
            NEOLogger.logWarningException(mediator.getSimulationModel(), "Cell Types Table: SQL Error", sqlex);
        }
    }
}
