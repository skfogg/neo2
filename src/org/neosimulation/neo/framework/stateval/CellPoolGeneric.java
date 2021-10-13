/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.model.StateValUpdater;

/**
 * CellPoolGeneric - An implementation of CellPoolGeneric that indicates that it
 * has yet to be initialized.
 * 
 * @author Isaac Griffith
 * @param <CLASS>
 *            The generic type of this CellPoolGeneric
 */
public class CellPoolGeneric<CLASS> extends StateGeneric<CLASS> {

    /**
     * Constructs a new CellPoolGeneric with the given name and attached to the
     * provided holon
     * 
     * @param name
     *            Name of the new CellPoolGeneric
     * @param holon
     *            Holon to attach this CellPoolGeneric to
     */
    public CellPoolGeneric(String name, Holon holon)
    {
        super(name, holon);
        updater = new StateValUpdater(this);
    }

    /**
     * Constructs a new initialized CellPoolGeneric with the given name, attached to
     * provided holon, and initialized with the provided value.
     * 
     * @param name
     *            Name of the new CellPoolGeneric
     * @param holon
     *            Holon to which this CellPoolGeneric belongs
     * @param value
     *            The new unique id for this CellPoolGeneric
     */
    public CellPoolGeneric(String name, Holon holon, CLASS value)
    {
        super(name, holon, value);
        updater = new StateValUpdater(this);
    }

    /**
     * Forces the CellPoolDouble to update its value
     */
    @Override
    public void update()
    {
        super.update();
    }
}
