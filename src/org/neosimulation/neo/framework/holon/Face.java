package org.neosimulation.neo.framework.holon;

import org.neosimulation.neo.framework.model.SimulationModel;

/**
 * Face - Represents the connection between a Cell and an DependencyEdge, or the
 * connection of a cell in the model to that which is beyond the model (a
 * boundary of the system)
 * 
 * @author Isaac Griffith
 */
public class Face extends Holon {

    /**
     * The DependencyEdge that this face is connect it's cell to 
     */
    protected Edge edge;
    /** 
     * The Cell to which this face is attached
     */
    protected Cell cell;

    /**
     * Creates a new Instance of a Face attached to the provided cell and having
     * no connected DependencyEdge, thus creating a system boundary.
     * 
     * @param cell
     *            Cell instance that this Face is to be attached to.
     */
    public Face(String name, SimulationModel model, Cell cell)
    {
        this(name, model, cell, null);
    }

    /**
     * Creates a new Instance of a Face connecting the provided cell and edge.
     * If edge is null, this face acts as a boundary of the model.
     * 
     * @param cell
     *            Cell instance to which this Face is to be attached
     * @param edge
     *            DependencyEdge instance this face connects to.
     */
    public Face(String name, SimulationModel model, Cell cell, Edge edge)
    {
        super(name, model);
        this.edge = edge;
        this.cell = cell;
        // cell.attachFace(this);
    }

    /**
     * Returns the DependencyEdge to which this face connects its cell to
     * 
     * @return The DependencyEdge this Face is connected to
     */
    public Edge getEdge()
    {
        return edge;
    }

    /**
     * Returns the Cell to which this Face is attached.
     * 
     * @return The Cell instance this Face is attached to.
     */
    public Cell getCell()
    {
        return cell;
    }
}
