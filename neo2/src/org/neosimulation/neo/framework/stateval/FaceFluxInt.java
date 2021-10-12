package org.neosimulation.neo.framework.stateval;

import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.model.StateValContainerException;
import org.neosimulation.neo.framework.model.StateValUpdater;
import org.neosimulation.neo.user.CellPoolDynamException;
import org.neosimulation.neo.user.CellPoolDynamInt;

/**
 * FaceFluxInt - A StateVal to be contained within a Face, representing the
 * transient flux of a specific value. Where the type of that value is an
 * integer.
 * 
 * @author Isaac Griffith
 */
public class FaceFluxInt extends StateInt {

    /**
     * Constructs a new FaceFluxInt with the provided name and attached to the
     * provided Holon.
     * 
     * @param name
     *            Name of the new FaceFluxInt
     * @param holon
     *            Holon to which the newly created FaceFluxInt belongs
     */
    public FaceFluxInt(String name, Holon holon)
    {
        super(name, holon);
        updater = new StateValUpdater(this);
    }

    /**
     * Constructs a new initialized FaceFluxInt with the given name, attached to
     * provided holon, and initialized with the provided value.
     * 
     * @param name
     *            Name of the new FaceFluxInt
     * @param holon
     *            Holon to which this FaceFluxInt belongs
     * @param value
     *            The new unique id for this FaceFluxInt
     */
    public FaceFluxInt(String name, Holon holon, int value)
    {
        super(name, holon, value);
        updater = new StateValUpdater(this);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateInt#update()
     */
    @Override
    public void update()
    {
        super.update();
        
        Face face = (Face) holon;
		Cell cell = face.getCell();
		try {
			CellPoolInt pool = (CellPoolInt) cell.getStateVal(getName());
			CellPoolDynamInt dynam = (CellPoolDynamInt) pool.dynam;

			dynam.updateTransientValue(this);
		} catch (StateValContainerException svce) {
			svce.printStackTrace();
		} catch (CellPoolDynamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
