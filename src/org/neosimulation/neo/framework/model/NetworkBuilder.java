package org.neosimulation.neo.framework.model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

import org.neosimulation.neo.framework.config.DbConfig;
import org.neosimulation.neo.framework.database.DbMediator;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.populator.BehaviorDynamIdentifier;
import org.neosimulation.neo.framework.populator.CellPopulator;
import org.neosimulation.neo.framework.populator.EdgePopulator;
import org.neosimulation.neo.framework.populator.Populator;

/**
 * NetworkBuilder - Used to transform information in a Matrix and the Databases
 * representing the information about a Network into the Network Object. The
 * NetworkBuilder provides a Database Connection and statement objects to the
 * requisite Cell and DependencyEdge populators which then provide the lists of
 * IUpdatatble and Dynam in order to comprise the Network object. NetworkBuilder
 * provides the necessary logic to control the process of populating Edges and
 * Cells that exist in the Matrix. Once the NetworkBuilder has completed
 * processing a complete Network representing the model is ready to be handed
 * off to the SimulationModel.
 * 
 * @author Isaac Griffith
 */
public class NetworkBuilder {

    /**
     * Matrix Object from the Simulation Model, for which population of Dynams
     * and Statevals is required
     */
    protected Matrix matrix;
    /**
     * The Network Object responsible for maintaining the lists of IUpdatables
     * and Dynams
     */
    protected Network network;
    /**
     * The mediator which controls the interaction and distribution of
     * information between classes outside the Database subsystem and classes
     * within the subsystem.
     */
    protected DbMediator mediator;

    /**
     * Creates a new NetworkBuilder instance which will use the supplied String
     * to open a properties file which describes the means by which a connection
     * to the database can be established.
     * 
     * @param dbConfig
     *            String representing the location of the file describing the
     *            properties necessary to open a connection to the database
     *            describing the model
     */
    public NetworkBuilder(Properties queries, DbConfig dbConfig, SimulationModel model)
    {
        network = new Network();
        matrix = new Matrix(model);
        mediator = new DbMediator(queries, model, matrix, network);

        mediator.processDb(dbConfig);
    }

    /**
     * Generates the Populator objects used to creates the StateVals and Dynams
     * for each Holon in the Matrix and takes these objects and installs them
     * into the lists stored in the Network Object. It then returns this Network
     * object once every Holon has been fully populated.
     * 
     * @return A Fully Populated Network Object which contains all of the
     *         IUpdatables and Dynams created for each of the resources in a
     *         model and all of the Holons found in the Model's associated
     *         Matrix Object.
     */
    public Network generateNetwork()
    {
        try
        {
            // Populate each cell in the matrix
            Populator cellPop = new CellPopulator(mediator.getSimulationModel());
            BehaviorDynamIdentifier bdi = new BehaviorDynamIdentifier(mediator.getSimulationModel());
            bdi.lookupCellDynams(mediator);
            for (String cellID : mediator.getCellIDs())
            {
                network.addIUpdatables(cellPop.installStateValsAndDynams(cellID, mediator, bdi));
                network.addDynams(cellPop.getInstalledDynams());
            }

            // Populate each edge in the matrix
            Populator edgePop = new EdgePopulator(mediator.getSimulationModel());
            bdi.lookupEdgeDynams(mediator);
            for (String edgeID : mediator.getEdgeIDs())
            {
                network.addIUpdatables(edgePop.installStateValsAndDynams(edgeID, mediator, bdi));
                network.addDynams(edgePop.getInstalledDynams());
            }
        }
        catch (IOException iox)
        {
            NEOLogger.logException(mediator.getSimulationModel(), "Network Builder: Problem with Network Generation",
                    iox);
        }
        catch (SQLException sqlex)
        {
            NEOLogger.logException(mediator.getSimulationModel(), "Network Builder: SQL Error", sqlex);
        }
        catch (IllegalAccessException iaex)
        {
            NEOLogger.logException(mediator.getSimulationModel(), "Network Builder: Database Access Error", iaex);
        }
        catch (InstantiationException iex)
        {
            NEOLogger.logException(mediator.getSimulationModel(), "Network Builder: Database Instantiation Error", iex);
        }

        return network;
    }

    /**
     * Retrieves the holon topology describing the underlying structure of the
     * network, which is the Matrix Object.
     * 
     * @return The matrix used as the underlying structure for the Network
     */
    public Matrix getMatrix()
    {
        return matrix;
    }

    /**
     * Returns the DbMediator used to extract the information from the database
     * which created both the Matrix and the Network. This DatabaseManager also
     * provides the necessary information to rebuild the Network or to find
     * Simulation Control information not contained within either the Matrix or
     * the Network.
     * 
     * @return The DbMediator for this Network
     */
    public DbMediator getMediator()
    {
        return mediator;
    }
}
