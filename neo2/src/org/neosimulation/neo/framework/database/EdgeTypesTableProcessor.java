package org.neosimulation.neo.framework.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.neosimulation.neo.framework.behcurr.Behavior;
import org.neosimulation.neo.framework.behcurr.BehaviorPair;
import org.neosimulation.neo.framework.behcurr.Currency;
import org.neosimulation.neo.framework.logging.NEOLogger;

/**
 * EdgeTypesTableProcessor - Provides encapsulation and processing logic for the
 * Matrix DependencyEdge Type table of the model database.
 * 
 * @author Isaac Griffith
 */
public class EdgeTypesTableProcessor extends TableProcessor {

    /**
     * The SQL query string used to extract information from the Matrix
     * DependencyEdge Type table
     */
    private String typeQuery;

    /**
     * Constructs a new instance of edge type table used to represent the
     * information stored in the provided matrix edge type table name.
     * 
     * @param dbControl
     *            Associated NEODbController containing the necessary tablename
     *            information
     * @param mediator
     *            DbMediator used to store processed data
     */
    public EdgeTypesTableProcessor(NEODbController dbControl, DbMediator mediator)
    {
        super(mediator);

        typeQuery = String.format(dbControl.getQuery("EDGE_TYPE_TABLE_QUERY"), dbControl.getEdgeTypeTableName());
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.database.TableProcessor#processTable(java
     * .sql.Statement)
     */
    public void processTable(Statement statement)
    {
        Map<String, Set<BehaviorPair>> edgeTypeToBehaviorPairs = new HashMap<>();
        
        try(ResultSet type = statement.executeQuery(typeQuery))
        {
            while (type.next())
            {
                String edgeType = type.getString("EdgeType");
                String toBehavior = type.getString("ToEdgeBehavior");
                String fromBehavior = type.getString("FromEdgeBehavior");
                
                BehaviorPair pair = new BehaviorPair(getBehavior(toBehavior, true), getBehavior(fromBehavior, false));
                
                if (edgeTypeToBehaviorPairs.containsKey(edgeType)) {
                    Set<BehaviorPair> pairs = edgeTypeToBehaviorPairs.get(edgeType);
                    pairs.add(pair);
                    edgeTypeToBehaviorPairs.put(edgeType, pairs);
                } else {
                    Set<BehaviorPair> pairs = new HashSet<>();
                    pairs.add(pair);
                    edgeTypeToBehaviorPairs.put(edgeType, pairs);
                }
            }
            
            mediator.setEdgeBehaviors(edgeTypeToBehaviorPairs);
        }
        catch (SQLException sqlex)
        {
            NEOLogger.logWarningException(mediator.getSimulationModel(), "DependencyEdge Type Table: SQL Error", sqlex);
        }
    }
    
    /**
     * @param behaviorName
     * @param toSide
     * @return
     */
    private Behavior getBehavior(String behaviorName, boolean toSide) {
        Behavior retVal = null;
        
        if (behaviorName != null && !behaviorName.isEmpty()) {
            String resourceName = behaviorName.substring(0, behaviorName.indexOf("."));
            Currency currency = mediator.getCurrency(resourceName);
            
            if (toSide) {
                retVal = currency.registerToEdgeBehavior(behaviorName);
            } else {
                retVal = currency.registerFromEdgeBehavior(behaviorName);
            }
        }
        
        return retVal;
    }
}
