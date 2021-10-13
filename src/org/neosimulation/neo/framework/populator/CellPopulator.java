/**
 * 
 */
package org.neosimulation.neo.framework.populator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.neosimulation.neo.framework.behcurr.Behavior;
import org.neosimulation.neo.framework.behcurr.Currency;
import org.neosimulation.neo.framework.database.DbMediator;
import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.model.IUpdatable;
import org.neosimulation.neo.framework.model.SimulationModel;

/**
 * CellPopulator - Contains the logic necessary to provide a single Cell the
 * necessary internal objects to facilitate running a Model. This class is
 * responsible for installing necessary Dynams and StateVals into a provided
 * cell object. In essence it populates the cell with what it needs to be a
 * functioning member of the model.
 * 
 * @author Isaac Griffith
 */
public class CellPopulator extends Populator {

    /**
     * The current cell behavior
     */
    protected Behavior cellBehavior;

    /**
     * Provides the logic to install StateVals and Dynams into the Holon
     * specified by the uniqueID.
     * 
     * @param uniqueID
     *            ID of the holon to install statevals and dynams into
     * @param manager
     *            The Database Manager providing all the info required from the
     *            databases
     * @return Set of IUpdatables that have been installed
     */
    public Set<IUpdatable> installStateValsAndDynams(String uniqueID, DbMediator manager, BehaviorDynamIdentifier bdi)
            throws IOException, SQLException, IllegalAccessException, InstantiationException
    {
        updatables = new HashSet<>();

        // Get the cell with the corresponding uniqueID
        Cell cell = manager.getCell(uniqueID);
        String type = manager.getCellType(uniqueID);

        Set<Behavior> cellBehaviors = manager.getCellBehaviors(type);
        Map<Currency, Map<Behavior, Set<Class>>> cellBehaviorDynams = bdi.getCellBehaviorMap();
        for (Behavior behavior : cellBehaviors)
        {
            cellBehavior = behavior;

            if (cell != null)
            {
                if (!cell.isBehaviorInstalled(cellBehavior))
                {
                    // Install StateVals and initialize to values in initial
                    // values table
                    installStateVals(uniqueID, cell, cellBehavior, manager, false, false);

                    // Install Dynams and any necessary StateVals
                    installDynams(cellBehaviorDynams, cellBehavior.getCurrency(), cellBehavior, cell);
                }
                cell.addInstalledBehavior(cellBehavior);
            }
        }

        return updatables;
    }

    /**
     * Creates a new Instance of CellPopulator that works with the provided
     * SimulationModel
     * 
     * @param model
     *            The SimulationModel for which this populator is populating
     */
    @SuppressWarnings("rawtypes")
    public CellPopulator(SimulationModel model)
    {
        super(model);
    }
}
