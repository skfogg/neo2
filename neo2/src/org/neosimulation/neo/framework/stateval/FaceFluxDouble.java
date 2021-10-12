package org.neosimulation.neo.framework.stateval;

import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.model.StateValContainerException;
import org.neosimulation.neo.framework.model.StateValUpdater;
import org.neosimulation.neo.user.CellPoolDynamDouble;
import org.neosimulation.neo.user.CellPoolDynamException;

/**
 * FaceFluxDouble - Class representing a StateVal stored in a Face which is
 * based on the type double.
 * 
 * @author Isaac Griffith
 */
public class FaceFluxDouble extends StateDouble {

	/**
	 * Constructs a new FaceFluxDouble with the given name and attached to the
	 * provided Holon
	 * 
	 * @param name
	 *            Name of the new FaceFluxDouble
	 * @param holon
	 *            Holon to which this FaceFluxDouble belongs
	 */
	public FaceFluxDouble(String name, Holon holon) {
		super(name, holon);
		this.updater = new StateValUpdater(this);
	}

	/**
	 * Constructs a new FaceFluxDouble with the given name and attached to the
	 * provided Holon
	 * 
	 * @param name
	 *            Name of the new FaceFluxDouble
	 * @param holon
	 *            Holon to which this FaceFluxDouble belongs
	 * @param id
	 *            The new unique id for this FaceFluxDouble
	 */
	public FaceFluxDouble(String name, Holon holon, long id) {
		super(name, holon, id);
		this.updater = new StateValUpdater(this);
	}

	/**
	 * Constructs a new initialized FaceFlux Double with the given name,
	 * attached to provided holon, and initialized with the provided value.
	 * 
	 * @param name
	 *            Name of the new FaceFluxDouble
	 * @param holon
	 *            Holon to which this FaceFluxDouble belongs
	 * @param value
	 *            The new unique id for this FaceFluxDouble
	 */
	public FaceFluxDouble(String name, Holon holon, double value) {
		super(name, holon, value);
		updater = new StateValUpdater(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.neosimulation.neo.framework.StateDouble#update()
	 */
	@Override
	public void update() {
		super.update();

		Face face = (Face) holon;
		Cell cell = face.getCell();
		try {
			CellPoolDouble pool = (CellPoolDouble) cell.getStateVal(getName());
			CellPoolDynamDouble dynam = (CellPoolDynamDouble) pool.dynam;

			dynam.updateTransientValue(this);
		} catch (StateValContainerException svce) {
			svce.printStackTrace();
		} catch (CellPoolDynamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
