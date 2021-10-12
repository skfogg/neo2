package org.neosimulation.neo.framework.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.neosimulation.neo.framework.behcurr.Behavior;

/**
 * DefaultTableProcessor - Provides an encapsulation of a Default Values table.
 * Where the Default Values Table are a set of tables that describe, for a given
 * behavior of resource, the default values to be held by statevals associated
 * with that resource.
 * 
 * @author Isaac Griffith
 */
public class DefaultTableProcessor extends TableProcessor {

    /**
     * SQL Query string used to extract information from a default values table
     */
    private String defaultQuery = "";
    /**
     * Flag which determines whether to process the table as an DependencyEdge
     * Defaults or Cell Defaults table.
     */
    private boolean isEdgeDefaults = false;

    /**
     * Constructs a new Default table for the specified table name
     * 
     * @param dbControl
     *            NEODbController which contains the needed table name to ensure
     *            processing occurs
     * @param mediator
     *            Mediator which allows the data to be stored in the proper
     *            place
     * @param isEdgeDefaults
     *            flag that if set true signifies that the EdgeDefault table is
     *            to processed, otherwise the cell default table is to be
     *            processed.
     */
    public DefaultTableProcessor(NEODbController dbControl, DbMediator mediator, boolean isEdgeDefaults)
    {
        super(mediator);
        this.isEdgeDefaults = isEdgeDefaults;
        String tableName = "";
        if (isEdgeDefaults)
            tableName = dbControl.getDefaultEdgeTableName();
        else
            tableName = dbControl.getDefaultCellTableName();

        defaultQuery = String.format(dbControl.getQuery("DEFAULT_VALUES_QUERY"), tableName, tableName);
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
            ResultSet defaultEntries = statement.executeQuery(defaultQuery);

            while (defaultEntries.next())
            {
                int index = 1;
                for (int i = 1; i <= defaultEntries.getMetaData().getColumnCount(); i++)
                {
                    if (defaultEntries.getMetaData().getColumnName(i).equals("Type"))
                        index = i;
                }

                if (isEdgeDefaults)
                    handleEdge(defaultEntries, index);
                else
                    handleCell(defaultEntries, index);
            }
        }
        catch (SQLException sqlex)
        {
            // mediator.getLogger().logWarning("Default Table: SQL Error");
            sqlex.printStackTrace();
        }
    }

    /**
     * Tests whether the named behavior exists within the provided set of
     * behaviors
     * 
     * @param behavior
     *            Name of behavior
     * @param behaviors
     *            Set of behaviors to query
     * @return true if the set of behaviors contains a behavior with the given
     *         name, false otherwise
     */
    private boolean containsBehavior(String behavior, Set<Behavior> behaviors)
    {
        for (Behavior beh : behaviors)
        {
            if (beh.getName().equalsIgnoreCase(behavior))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Handles processing of a default edge table
     * 
     * @param defaultEntries
     *            Result set containing the entries in the default table
     * @param index
     *            index of the column containing the edge type
     * @throws SQLException
     *             thrown if some exception occurs while processing the result
     *             set
     */
    private void handleEdge(ResultSet defaultEntries, int index) throws SQLException
    {
        String holonType = defaultEntries.getString(index);
        String behavior = defaultEntries.getString("Behavior");

        List<Triple<String, String, Object>> infoFrom = initializeDefaultTripleLists(holonType)[0];
        List<Triple<String, String, Object>> infoTo = initializeDefaultTripleLists(holonType)[1];
        validateDefaultTripleLists(infoTo, infoFrom, holonType);

        String svName = defaultEntries.getString("StateVal");
        String svType = defaultEntries.getString("DataType");
        Object svValue = getDefaultValue(defaultEntries, svType);

        if (infoTo == null)
        {
            infoTo = new ArrayList<>();
        }
        if (infoFrom == null)
        {
            infoFrom = new ArrayList<>();
        }

        boolean toSet = containsBehavior(behavior, mediator.getMatrix().getToBehaviors(holonType));
        boolean fromSet = containsBehavior(behavior, mediator.getMatrix().getFromBehaviors(holonType));

        if (toSet && fromSet)
        {
            infoTo.add(new Triple<String, String, Object>(svName, svType, svValue));
            infoFrom.add(new Triple<String, String, Object>(svName, svType, svValue));
        }
        else if (toSet)
        {
            infoTo.add(new Triple<String, String, Object>(svName, svType, svValue));
        }
        else
        {
            infoFrom.add(new Triple<String, String, Object>(svName, svType, svValue));
        }

        mediator.setToEdgeDefaultTriples(holonType, infoTo);
        mediator.setFromEdgeDefaultTriples(holonType, infoFrom);
    }

    /**
     * Handles processing of a default cell table
     * 
     * @param defaultEntries
     *            Result set containing the entries in the default table
     * @param index
     *            index of the column containing the cell type
     * @throws SQLException
     *             thrown if some exception occurs while processing the result
     *             set
     */
    private void handleCell(ResultSet defaultEntries, int index) throws SQLException
    {
        String holonType = defaultEntries.getString(index);
        List<Triple<String, String, Object>> info = initializeDefaultTripleList(isEdgeDefaults, holonType);
        validateDefaultTripleList(info, holonType);

        String svName = defaultEntries.getString("StateVal");
        String svType = defaultEntries.getString("DataType");
        Object svValue = getDefaultValue(defaultEntries, svType);

        if (info == null)
        {
            info = new ArrayList<>();
        }
        info.add(new Triple<String, String, Object>(svName, svType, svValue));

        mediator.setCellDefaultTriples(holonType, info);
    }

    /**
     * Extracts the correct type of value from the default value table based on
     * the type specified in the datatype column.
     * 
     * @param defaultEntries
     *            Result Set to proces
     * @param svType
     *            Data Type of the StateVal
     * @return Object representing the defalut entry's value
     * @throws SQLException
     *             thrown if a problem occurs while processing the result set
     */
    private Object getDefaultValue(ResultSet defaultEntries, String svType) throws SQLException
    {
        Object svValue = null;

        if (svType.equalsIgnoreCase("double"))
        {
            Double temp = defaultEntries.getDouble("DefaultVal");
            svValue = temp;
        }
        else if (svType.equalsIgnoreCase("integer"))
        {
            Integer temp = defaultEntries.getInt("DefaultVal");
            svValue = temp;
        }
        else if (svType.equalsIgnoreCase("bigint") || svType.equalsIgnoreCase("long"))
        {
            Long temp = defaultEntries.getLong("DefaultVal");
            svValue = temp;
        }
        else if (svType.equalsIgnoreCase("text") || svType.equalsIgnoreCase("string"))
        {
            String temp = defaultEntries.getString("DefaultVal");
            svValue = temp;
        }
        else
        {
            svValue = defaultEntries.getObject("DefaultVal");
        }

        return svValue;
    }

    /**
     * Initializes the list of default stateval triples.
     * 
     * @param forEdge
     *            Flag denoting that the list is for an edge if marked true.
     * @param holonType
     *            Type of holon
     * @return An initialized list of triples for default stateval values
     */
    private List<Triple<String, String, Object>> initializeDefaultTripleList(boolean forEdge, String holonType)
    {
        return mediator.getDefaultCellTriples(holonType);
    }

    /**
     * Initializes the default triple lists for a given edge type
     * 
     * @param edgeType
     *            Edge type to initialze the default triple list
     * @return Array containing the to and from edge type triples.
     */
    private List<Triple<String, String, Object>>[] initializeDefaultTripleLists(String edgeType)
    {
        List<Triple<String, String, Object>>[] retVal = new List[2];

        retVal[0] = mediator.getDefaultToEdgeTriples(edgeType);
        retVal[1] = mediator.getDefaultFromEdgeTriples(edgeType);

        return retVal;
    }

    /**
     * Validates that the provided list of default stateval triples is for the
     * specified holon type.
     * 
     * @param list
     *            List of default stateval triples
     * @param holonType
     *            holon type
     */
    private void validateDefaultTripleList(List<Triple<String, String, Object>> list, String holonType)
    {
        if (list == null)
        {
            list = new ArrayList<>();
            mediator.setCellDefaultTriples(holonType, list);
        }
    }

    /**
     * Validates that the to and from edge type default triple lists are not
     * null. If they are it will initialize them to be empty.
     * 
     * @param toList
     *            the to list
     * @param fromList
     *            the from list
     * @param holonType
     *            edge type
     */
    private void validateDefaultTripleLists(List<Triple<String, String, Object>> toList,
            List<Triple<String, String, Object>> fromList, String holonType)
    {
        if (toList == null)
        {
            toList = new ArrayList<>();
        }

        if (fromList == null)
        {
            fromList = new ArrayList<>();
        }

        mediator.setToEdgeDefaultTriples(holonType, toList);
        mediator.setFromEdgeDefaultTriples(holonType, fromList);
    }
}
