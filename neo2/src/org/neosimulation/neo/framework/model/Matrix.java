package org.neosimulation.neo.framework.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.neosimulation.neo.framework.behcurr.Behavior;
import org.neosimulation.neo.framework.behcurr.BehaviorPair;
import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Edge;

/**
 * FIXME: Requires refactoring from current structure to underlying graph data structure.<br>
 * Matrix - Used to provide structure and methods to obtain data about the empty landscape of the model. This class is responsible for providing a storage of
 * all of the cells and edges within a model (as extracted from the Matrix tables) and to provide a means of accessing this information.
 * 
 * @author Isaac Griffith
 */
public class Matrix implements Serializable {

    /**
     * Map of all cells in the model indexed by their CellID
     */
    private Map<String, Cell> tmCells = new TreeMap<>();
    /**
     * Map of all of the cell types indexed by CellID
     */
    private Map<String, String> tmCellTypes = new TreeMap<>();
    /**
     * Map of all of the edges in the model indexed by their EdgeID
     */
    private Map<String, Edge> tmEdges = new TreeMap<>();
    /**
     * Map of all of the edge types indexed by EdgeID
     */
    private Map<String, String> tmEdgeTypes = new TreeMap<>();
    /**
     * The Simulation Model
     */
    private SimulationModel model;
    /**
     * Map of CellTypes to Behaviors
     */
    private Map<String, Set<Behavior>> tmCellTypeBehaviors = new TreeMap<>();
    /**
     * Map of edge type behavior pairs
     */
    private Map<String, Set<BehaviorPair>> edgeTypeBehaviors = new HashMap<>();
    /**
     * Set of all cell behavior types
     */
    private Set<Behavior> cellBehaviors;
    /**
     * Set of all Cell types
     */
    private Set<String> cellTypes;
    /**
     * Set of all DependencyEdge types
     */
    private Set<String> edgeTypes;

    /**
     * Constructs a new Matrix for the provided SimulationModel
     * 
     * @param model
     *            model to be structurally represented by this matrix object
     */
    public Matrix(SimulationModel model)
    {
        this.model = model;
        cellBehaviors = new HashSet<>();
        cellTypes = new HashSet<>();
        edgeTypes = new HashSet<>();
    }

    /**
     * Generates and adds a new Cell to the Matrix of the Model, where this new Cell has the specified ID and type.
     * 
     * @param cellID
     *            ID of the cell to be added to the matrix
     * @param cellType
     *            Type of cell to be added to the matrix
     * @return Cell A created cell with the passed values
     * @throws MatrixCreationException
     *             if a cell with the specified ID already exists in the matrix
     */
    public Cell addCell(String cellID, String cellType) throws MatrixCreationException
    {
        Cell temp = new Cell(cellID, model);
        temp.setName(cellID);

        if (tmCells.containsKey(cellID))
            throw new MatrixCreationException("Cell ID: " + cellID + " already exists.");

        tmCells.put(cellID, temp);
        tmCellTypes.put(cellID, cellType);
        cellTypes.add(cellType);
        return temp;
    }

    /**
     * Adds a new DependencyEdge instance to the Matrix of the model, where this new instance will have the provided DependencyEdge ID, type, and connects the
     * two cells with the provided CellIDs
     * 
     * @param edgeID
     *            ID of the edge to be added
     * @param edgeType
     *            Type of the edge to be added
     * @param fromCellID
     *            ID of the cell on the "from" side of the edge to be added
     * @param toCellID
     *            ID of the cell on the "to" side of the edge to be added
     * @throws MatrixCreationException
     *             if an edge with the specified ID already exists
     */
    public void addEdge(String edgeID, String edgeType, String fromCellID, String toCellID)
            throws MatrixCreationException
    {

        Cell toCell = null;
        Cell fromCell = null;
        if (toCellID != null)
            toCell = tmCells.get(toCellID);

        if (fromCellID != null)
            fromCell = tmCells.get(fromCellID);

        Edge temp = new Edge(edgeID, model, toCell, fromCell);

        if (tmEdges.containsKey(edgeID))
            throw new MatrixCreationException("DependencyEdge ID: " + edgeID + " already exists.");

        tmEdges.put(edgeID, temp);
        // Add type to DependencyEdge types map. Indexing it with the
        // DependencyEdge ID.
        tmEdgeTypes.put(edgeID, edgeType);
        edgeTypes.add(edgeType);
    }

    /**
     * Returns the Cell instance with the provided string ID.
     * 
     * @param id
     *            ID of the cell to be retrieved from the matrix
     * @return a Cell with the specified ID or null if no cell with the specified ID exists
     */
    public Cell getCell(String id)
    {
        Cell retVal = null;

        if (tmCells.containsKey(id))
            retVal = tmCells.get(id);

        return retVal;
    }

    /**
     * Returns the String representing the CellType of the cell with the provided unique ID string.
     * 
     * @param cellID
     *            ID of the cell whose Cell type is required
     * @return Cell type of the cell with the specified cell uniqueID or null if no cell with the specified cell uniqueID exists
     */
    public String getCellType(String cellID)
    {
        String retVal = null;

        if (tmCellTypes.containsKey(cellID))
            retVal = tmCellTypes.get(cellID);

        return retVal;
    }

    /**
     * Queries the internal structure of the landscape and returns the edge instance with the provided ID.
     * 
     * @param id
     *            ID of the DependencyEdge
     * @return The DependencyEdge instance with the specified ID or null if there is no edge with the specified ID
     */
    public Edge getEdge(String id)
    {
        Edge retVal = null;

        if (tmEdges.containsKey(id))
            retVal = tmEdges.get(id);

        return retVal;
    }

    /**
     * Returns the String representing the EdgeType of the edge with the provided unique ID string.
     * 
     * @param edgeID
     *            ID of the edge whose edge type is requested
     * @return a String representing the edge type of the edge with the provided ID or null if no such edge exists
     */
    public String getEdgeType(String edgeID)
    {
        String retVal = null;

        if (tmEdgeTypes.containsKey(edgeID))
            retVal = tmEdgeTypes.get(edgeID);

        return retVal;
    }

    /**
     * Returns a set of all the ID strings of all known unique Cells in the Matrix
     * 
     * @return set of unique cell ids for all cells in the matrix
     */
    public Set<String> getCellIDs()
    {
        return tmCells.keySet();
    }

    /**
     * Returns a set of all the ID strings of all known unique Edges in the Matrix
     * 
     * @return set of unique edge ids for all edges in the matrix
     */
    public Set<String> getEdgeIDs()
    {
        return tmEdges.keySet();
    }

    /**
     * Retrieves the set of behaviors that is associated with a cell of the provided type.
     * 
     * @param type
     *            Cell type to retrieve the set of behavior for
     * @return Set of behaviors associated with a cell of the provided type, or null if no cell exits with the provided type.
     */
    public Set<Behavior> getCellBehaviors(String type)
    {
        return this.tmCellTypeBehaviors.get(type);
    }

    /**
     * Adds the provided set of behaviors and associates this set to the provided cell type.
     * 
     * @param cellType
     *            Cell type to which the provided set of behaviors is associated with.
     * @param behaviors
     *            Set of behaviors to be added for the provided cell type.
     */
    public void setCellBehaviors(String cellType, Set<Behavior> behaviors)
    {
        this.tmCellTypeBehaviors.put(cellType, behaviors);
        this.cellBehaviors.addAll(behaviors);
    }

    /**
     * Retrieves the set of behaviors representing all behaviors to which any cell in this matrix is associated with.
     * 
     * @return Set of behaviors representing all of the behaviors associated with the cells in this matrix.
     */
    public Set<Behavior> getAllCellBehaviors()
    {
        return cellBehaviors;
    }

    /**
     * Retrieves all the types for the cells contained within this matrix.
     * 
     * @return a set of strings representing the names of all known cell types in this matrix.
     */
    public Set<String> getAllCellTypes()
    {
        return cellTypes;
    }

    /**
     * Retrieves all the types for the edges contained within this matrix.
     * 
     * @return a set of strings representing the names of all known edge types in this matrix.
     */
    public Set<String> getAllEdgeTypes()
    {
        return edgeTypes;
    }

    /**
     * Gets the From Cell identifying string (name) of the edge with the identifying name specified.
     * 
     * @param edgeID
     *            ID of the edge to which the from cell id is required.
     * @return ID of the From Cell attached to this edge, null if there is no From cell attached to the edge.
     */
    public String getFromCellID(String edgeID)
    {
        Edge edge = getEdge(edgeID);
        if (edge != null)
        {
            Cell cell = edge.getFromCell();
            if (cell != null)
                return cell.getName();
            else
                return null;
        }
        else
        {
            return null;
        }
    }

    /**
     * Gets the To Cell identifying string (name) of the edge with the identifying name specified.
     * 
     * @param edgeID
     *            ID of the edge to which the to cell id is required.
     * @return ID of the To Cell attached to this edge, null if there is no To cell attached to the edge.
     */
    public String getToCellID(String edgeID)
    {
        Edge edge = getEdge(edgeID);
        if (edge != null)
        {
            Cell cell = edge.getToCell();
            if (cell != null)
                return cell.getName();
            else
                return null;
        }
        else
        {
            return null;
        }
    }

    /**
     * Retrieves the SimulationModel object to which this matrix is attached.
     * 
     * @return The SimulationModel that this matrix describes the topology of
     */
    public SimulationModel getSimulationModel()
    {
        return model;
    }

    /**
     * @param edgeTypeToBehaviorPairs
     */
    public void setEdgeBehaviors(Map<String, Set<BehaviorPair>> edgeTypeToBehaviorPairs)
    {
        this.edgeTypeBehaviors = edgeTypeToBehaviorPairs;
    }

    /**
     * @param edgeType
     * @return
     */
    public Set<BehaviorPair> getEdgeBehaviors(String edgeType)
    {
        Set<BehaviorPair> pairs = new HashSet<>();

        if (this.edgeTypeBehaviors.containsKey(edgeType))
        {
            pairs = edgeTypeBehaviors.get(edgeType);
        }

        return pairs;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Matrix [tmCells=" + tmCells + ", tmCellTypes=" + tmCellTypes + ", tmEdges=" + tmEdges
                + ", tmEdgeTypes=" + tmEdgeTypes + ", model=" + model + ", tmCellTypeBehaviors=" + tmCellTypeBehaviors
                + ", edgeTypeBehaviors=" + edgeTypeBehaviors + ", cellBehaviors=" + cellBehaviors + ", cellTypes="
                + cellTypes + ", edgeTypes=" + edgeTypes + "]";
    }

    /**
     * @param type
     * @return
     */
    public Set<Behavior> getFromBehaviors(String type)
    {
        Set<Behavior> fromBehaviors = new HashSet<>();

        if (edgeTypeBehaviors.containsKey(type))
        {
            for (BehaviorPair pair : edgeTypeBehaviors.get(type))
            {
                if (pair.getFromBehavior() != null)
                    fromBehaviors.add(pair.getFromBehavior());
            }
        }

        return fromBehaviors;
    }

    /**
     * @param type
     * @return
     */
    public Set<Behavior> getToBehaviors(String type)
    {
        Set<Behavior> toBehaviors = new HashSet<>();

        if (edgeTypeBehaviors.containsKey(type))
        {
            for (BehaviorPair pair : edgeTypeBehaviors.get(type))
            {
                if (pair.getToBehavior() != null)
                    toBehaviors.add(pair.getToBehavior());
            }
        }

        return toBehaviors;
    }

    /**
     * @return
     */
    public Set<Behavior> getAllFromBehaviors()
    {
        Set<Behavior> allFromBehaviors = new HashSet<>();

        for (String type : edgeTypeBehaviors.keySet())
        {
            Set<BehaviorPair> pairs = edgeTypeBehaviors.get(type);
            for (BehaviorPair pair : pairs)
            {
                if (pair.getFromBehavior() != null)
                    allFromBehaviors.add(pair.getFromBehavior());
            }
        }

        return allFromBehaviors;
    }

    /**
     * @return
     */
    public Set<Behavior> getAllToBehaviors()
    {
        Set<Behavior> allToBehaviors = new HashSet<>();

        for (String type : edgeTypeBehaviors.keySet())
        {
            Set<BehaviorPair> pairs = edgeTypeBehaviors.get(type);

            for (BehaviorPair pair : pairs)
            {
                if (pair.getToBehavior() != null)
                    allToBehaviors.add(pair.getToBehavior());
            }
        }

        return allToBehaviors;
    }
}