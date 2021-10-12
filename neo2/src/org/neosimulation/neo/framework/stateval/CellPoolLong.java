/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.model.StateValUpdater;

/**
 * CellPoolLong - An long based StateVal which is specific to Cells
 * 
 * @author Isaac Griffith
 */
public class CellPoolLong extends StateLong {

    /**
     * Constructs a new CellPoolLong with the given name, attached to the
     * provided holon
     * 
     * @param name
     *            Name of this CellPoolLong
     * @param holon
     *            Holon this CellPoolLong is attached to
     */
    public CellPoolLong(String name, Holon holon)
    {
        super(name, holon);
        updater = new StateValUpdater(this);
    }

    /**
     * Constructs a new initialized CellPoolLong with the given name, attached to
     * provided holon, and initialized with the provided value.
     * 
     * @param name
     *            Name of the new CellPoolLong
     * @param holon
     *            Holon to which this CellPoolLong belongs
     * @param value
     *            The new unique id for this CellPoolLong
     */
    public CellPoolLong(String name, Holon holon, long value)
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
    }
}
