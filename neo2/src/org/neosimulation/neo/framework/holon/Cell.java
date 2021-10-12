package org.neosimulation.neo.framework.holon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neosimulation.neo.framework.dynam.Dynam;
import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.model.StateValContainerException;
import org.neosimulation.neo.framework.stateval.StateVal;

/**
 * Cell - A Holon which represents a flux storage repository during a model run.
 * 
 * @author Isaac Griffith
 */
public class Cell extends Holon {

    /** List of Faces attached to and coming into this Cell */
    protected HashMap<Long, Face> toFaces;
    /** Map face ids and Faces attached to and coming from this Cell */
    protected Map<Long, Face> fromFaces;

    /**
     * Constructs a new instance of Cell with the given name and is a part of
     * the provided SimulationModel.
     * 
     * @param name
     *            Name of this instance of Cell
     * @param model
     *            The SimulationModel to associate the Cell with
     */
    public Cell(String name, SimulationModel model)
    {
        super(name, model);
        toFaces = new HashMap<>();
        fromFaces = new HashMap<>();
    }

    /**
     * Attaches the provided face to this cell
     * 
     * @param face
     *            Face to add to this Cell
     */
    public void attachFace(Face face)
    {
        Edge edge = face.getEdge();

        if (edge != null && face == edge.toFace)
        {
            toFaces.put(face.getID(), face);
        }
        else
        // This assumes that the edge can be null?
        {
            fromFaces.put(face.getID(), face);
        }
    }

    /**
     * Get all faces linked to this cell
     * 
     * @return Array of faces linked to this cell
     */
    public Face[] getFacesArray()
    {
        List<Face> faces = new ArrayList<>();
        faces.addAll(toFaces.values());
        faces.addAll(fromFaces.values());

        Face[] retVal = new Face[faces.size()];
        faces.toArray(retVal);
        return retVal;
    }

    /**
     * Get all faces linked to this cell that contain the provided currency
     * 
     * @param currency
     *            Name of the currency whose faces are requested
     * @return Array of faces linked to this cell containing the provided
     *         currency
     */
    public Face[] getFacesArray(String currency)
    {
        List<Face> faces = new ArrayList<>();
        faces.addAll(toFaces.values());
        faces.addAll(fromFaces.values());

        List<Face> temp = new ArrayList<>();
        for (Face f : faces)
        {
            try
            {
                f.getStateVal(currency);
                temp.add(f);
            }
            catch (StateValContainerException svce)
            {
            }
        }

        Face[] retVal = new Face[temp.size()];
        temp.toArray(retVal);
        return retVal;
    }

    /**
     * Get all faces linked to this cell with the given currency and that have
     * the given behavior
     * 
     * @param currency
     *            Name of the currency whose faces are required
     * @param behavior
     *            Name of the behavior within the provided currency whose
     * @return Array of faces
     */
    public Face[] getFacesArray(String currency, String behavior)
    {
        List<Face> faces = new ArrayList<Face>();

        faces.addAll(toFaces.values());
        faces.addAll(fromFaces.values());
        List<Face> tempList = new ArrayList<>();

        for (Face face : faces)
        {
            try
            {
                StateVal temp = face.getStateVal(currency);
                if (temp != null)
                {
                    Dynam dynam = temp.getDynam();
                    if (dynam.getBehaviorName().equalsIgnoreCase(behavior))
                    {
                        tempList.add(face);
                    }
                }
            }
            catch (StateValContainerException e)
            {
            }
        }

        Face[] retVal = new Face[tempList.size()];
        tempList.toArray(retVal);
        return retVal;
    }

    /**
     * Returns whether the provided face id is registered as a To Face
     * 
     * @param faceID
     *            ID of the face to check whether it is a To Face
     * @return true if a Face exists with the provided id in the list of to
     *         faces
     */
    public boolean isToFace(long faceID)
    {
        return toFaces.containsKey(faceID);
    }

    /**
     * Returns whether the provided face id is registered as a To Face
     * 
     * @param faceID
     *            ID of the face to check whether it is a To Face
     * @return true if a Face exists with the provided id in the list of to
     *         faces
     */
    public boolean isFromFace(long faceID)
    {
        return fromFaces.containsKey(faceID);
    }
}
