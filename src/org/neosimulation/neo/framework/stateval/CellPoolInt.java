package org.neosimulation.neo.framework.stateval;

import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.model.StateValUpdater;

/**
 * CellPoolInt - An integer based StateVal which is specific to Cells
 * 
 * @author Isaac Griffith
 */
public class CellPoolInt extends StateInt {

    /**
     * Constructs a new CellPoolInt with the given name, attached to the
     * provided holon
     * 
     * @param name
     *            Name of this CellPoolInt
     * @param holon
     *            Holon this CellPoolInt is attached to
     */
    public CellPoolInt(String name, Holon holon)
    {
        super(name, holon);
        updater = new StateValUpdater(this);
    }

    /**
     * Constructs a new initialized CellPoolInt with the given name, attached to
     * provided holon, and initialized with the provided value.
     * 
     * @param name
     *            Name of the new CellPoolInt
     * @param holon
     *            Holon to which this CellPoolInt belongs
     * @param value
     *            The new unique id for this CellPoolInt
     */
    public CellPoolInt(String name, Holon holon, int value)
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
