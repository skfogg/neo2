package org.neosimulation.neo.framework.holon;

import org.neosimulation.neo.framework.model.SimulationModel;

/**
 * DependencyEdge - Represents connections between cells of the model. It is
 * through edges and associated faces that the flux of the model occurs. Edges
 * are Holons that can return a reference to shared StateVals.
 * 
 * @author Isaac Griffith
 */
public class Edge extends Holon {

    /**
     * The face corresponding to the To side of this edge (where the flux goes)
     */
    protected Face toFace = null;
    /**
     * The face corresponding to the From side of this edge (where the flux
     * comes from)
     */
    protected Face fromFace = null;

    /**
     * Creates a new instance of an DependencyEdge with the given unique ID.
     * This new instance is used to connect the two cells provided. This
     * constructor generates the necessary Face objects for the cells.
     * 
     * @param name
     *            Name of this DependencyEdge
     * @param model
     *            The SimulationModel this DependencyEdge is a part of
     * @param to
     *            The to cell connected via the to Face to this edge
     * @param from
     *            The from cell connected via the from Face to this edge
     */
    public Edge(String name, SimulationModel model, Cell to, Cell from)
    {
        super(name, model);       
        
        if (to != null)
        {
            toFace = new Face(name + "-to", model, to, this);
            to.attachFace(toFace);
        }
        if (from != null)
        {     
            fromFace = new Face(name + "-from", model, from, this);
            from.attachFace(fromFace);
        }
    }

    /**
     * Returns the To cell connected to this edge.
     * 
     * @return To cell connected to this edge by the To Face.
     */
    public Cell getToCell()
    {
        Cell retVal = null;
        if (toFace != null)
        {
            retVal = toFace.getCell();
        }

        return retVal;
    }

    /**
     * Returns the From cell connected to this edge.
     * 
     * @return From cell connected to this edge by the From Face
     */
    public Cell getFromCell()
    {
        Cell retVal = null;
        if (fromFace != null)
        {
            retVal = fromFace.getCell();
        }

        return retVal;
    }

    /**
     * Adds the provided Face instance as a To face to this edge
     * 
     * @param toFace
     *            A To face to be added to this DependencyEdge.
     */
    public void addToFace(Face toFace)
    {
        this.toFace = toFace;
    }

    /**
     * Adds the provided Face instance as a From face to this edge.
     * 
     * @param fromFace
     *            A From face to be added to this DependencyEdge.
     */
    public void addFromFace(Face fromFace)
    {
        this.fromFace = fromFace;
    }

    /**
     * Returns the Face on the to side of this DependencyEdge, as described in
     * the DependencyEdge table.
     * 
     * @return the Face on the to side of this DependencyEdge
     */
    public Face getToFace()
    {
        return toFace;
    }

    /**
     * Returns the Face on the from side of this DependencyEdge as describe in
     * the DependencyEdge table.
     * 
     * @return the Face on the from side of this DependencyEdge
     */
    public Face getFromFace()
    {
        return fromFace;
    }
}
