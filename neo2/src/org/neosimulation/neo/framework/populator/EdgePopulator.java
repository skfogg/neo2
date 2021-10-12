/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package org.neosimulation.neo.framework.populator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.neosimulation.neo.framework.behcurr.Behavior;
import org.neosimulation.neo.framework.behcurr.BehaviorPair;
import org.neosimulation.neo.framework.behcurr.Currency;
import org.neosimulation.neo.framework.database.DbMediator;
import org.neosimulation.neo.framework.holon.Edge;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.model.IUpdatable;
import org.neosimulation.neo.framework.model.SimulationModel;

/**
 * EdgePopulator - Contains the logic necessary to provide a single
 * DependencyEdge the necessary internal objects to facilitate running a Model.
 * This class is responsible for installing necessary Dynams and StateVals into
 * a provided edge object. In essence it populates the edge with what it needs
 * to be a functioning member of the model.
 * 
 * @author Isaac Griffith
 */
public class EdgePopulator extends Populator {

    /**
     * String representing the name of the current from face behavior
     */
    protected Behavior fromBehavior;
    /**
     * String representing the name of the current to face behavior
     */
    protected Behavior toBehavior;

    /**
     * Provides the logic to install StateVals and Dynams into the Holon
     * specified by the uniqueID.
     * 
     * @param edgeID
     *            ID of the holon to install statevals and dynams into
     * @param manager
     *            The Database Manager containing all the database info needed
     * @return The Set of IUpdatables installed into the Holon with the
     *         specified uniqueID
     * @throws IOException
     *             if there is a problem looking up the statevals to be
     *             installed
     * @throws SQLException
     *             if there is a problem connecting to or querying the database
     * @throws IllegalAccessException
     *             if there is a problem accessing dynam classes reflectively
     * @throws InstantiationException
     *             if there is a problem instantiating dynam classes
     *             reflectively
     */
    public Set<IUpdatable> installStateValsAndDynams(String edgeID, DbMediator manager, BehaviorDynamIdentifier bdi)
            throws IOException, SQLException, IllegalAccessException, InstantiationException
    {

        updatables = new HashSet<>();

        Edge edge = manager.getEdge(edgeID);
        String type = manager.getEdgeType(edgeID);
        Set<BehaviorPair> pairs = manager.getEdgeBehaviors(type);

        for (BehaviorPair pair : pairs)
        {
            toBehavior = pair.getToBehavior();
            fromBehavior = pair.getFromBehavior();

            // Ensure that the populator knows the behaviors to be used for
            // a given set of resources
            Map<Currency, Map<Behavior, Set<Class>>> edgeSymBehaviorDynams = bdi.getSymEdgeBehaviorMap();
            Map<Currency, Map<Behavior, Set<Class>>> edgeAsymBehaviorDynams = bdi.getAsymEdgeBehaviorMap();
            Map<Currency, Map<Behavior, Set<Class>>> edgeBoundBehaviorDynams = bdi.getBoundEdgeBehaviorMap();

            if (edge != null)
            {
                // Get the faces for the edge
                Face from = edge.getFromFace();
                Face to = edge.getToFace();

                // Install StateVals and initialize to values in initial
                // values table
                if (toBehavior.equals(fromBehavior))
                {
                    // Handle Symmetric edges
                    if (!to.isBehaviorInstalled(toBehavior))
                    {
                        installStateVals(edgeID, to, toBehavior, manager, true, true);
                        installDynams(edgeSymBehaviorDynams, toBehavior.getCurrency(), toBehavior, to);
                    }
                    if (!from.isBehaviorInstalled(fromBehavior))
                    {
                        installStateVals(edgeID, from, fromBehavior, manager, false, true);
                        installNegDynams(edgeSymBehaviorDynams, toBehavior.getCurrency(), toBehavior, from);
                    }

                    to.addInstalledBehavior(toBehavior);
                    from.addInstalledBehavior(fromBehavior);
                }
                else if (!toBehavior.equals(fromBehavior) && fromBehavior != null)
                {
                    // Handle Asymmetric and transformation edges
                    if (!to.isBehaviorInstalled(toBehavior))
                    {
                        installStateVals(edgeID, to, toBehavior, manager, true, false);
                        installDynams(edgeAsymBehaviorDynams, toBehavior.getCurrency(), toBehavior, to);
                    }
                    if (!from.isBehaviorInstalled(fromBehavior))
                    {
                        installStateVals(edgeID, from, fromBehavior, manager, false, false);
                        installDynams(edgeAsymBehaviorDynams, fromBehavior.getCurrency(), fromBehavior, from);
                    }

                    to.addInstalledBehavior(toBehavior);
                    from.addInstalledBehavior(fromBehavior);
                }
                else if (toBehavior != null && fromBehavior == null)
                {
                    // Handle boundary edges
                    if (!to.isBehaviorInstalled(toBehavior))
                    {
                        installStateVals(edgeID, to, toBehavior, manager, true, false);
                        installDynams(edgeBoundBehaviorDynams, toBehavior.getCurrency(), toBehavior, to);
                    }

                    to.addInstalledBehavior(toBehavior);
                }

            }
        }

        return updatables;
    }

    /**
     * Creates a new Instance of EdgePopulator for the provided SimulationModel
     * 
     * @param model
     *            The associated SimulationModel for which this populator is
     *            populating
     */
    public EdgePopulator(SimulationModel model)
    {
        super(model);
    }
}
