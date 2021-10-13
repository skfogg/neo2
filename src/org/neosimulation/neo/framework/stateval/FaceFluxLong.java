/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.model.StateValContainerException;
import org.neosimulation.neo.framework.model.StateValUpdater;
import org.neosimulation.neo.user.CellPoolDynamException;
import org.neosimulation.neo.user.CellPoolDynamLong;

/**
 * FaceFluxLong - A StateVal to be contained within a Face, representing the
 * transient flux of a specific value. Where the type of that value is an
 * long.
 * 
 * @author Isaac Griffith
 */
public class FaceFluxLong extends StateLong {

    /**
     * Constructs a new FaceFluxLong with the provided name and attached to the
     * provided Holon.
     * 
     * @param name
     *            Name of the new FaceFluxLong
     * @param holon
     *            Holon to which the newly created FaceFluxLong belongs
     */
    public FaceFluxLong(String name, Holon holon)
    {
        super(name, holon);
        updater = new StateValUpdater(this);
    }

    /**
     * Constructs a new initialized FaceFluxLong with the given name, attached
     * to provided holon, and initialized with the provided value.
     * 
     * @param name
     *            Name of the new FaceFluxLong
     * @param holon
     *            Holon to which this FaceFluxLong belongs
     * @param value
     *            The new unique id for this FaceFluxLong
     */
    public FaceFluxLong(String name, Holon holon, long value)
    {
        super(name, holon, value);
        updater = new StateValUpdater(this);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateLong#update()
     */
    @Override
    public void update()
    {
        super.update();

        Face face = (Face) holon;
        Cell cell = face.getCell();
        try
        {
            CellPoolLong pool = (CellPoolLong) cell.getStateVal(getName());
            CellPoolDynamLong dynam = (CellPoolDynamLong) pool.dynam;

            dynam.updateTransientValue(this);
        }
        catch (StateValContainerException svce)
        {
            svce.printStackTrace();
        }
        catch (CellPoolDynamException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
