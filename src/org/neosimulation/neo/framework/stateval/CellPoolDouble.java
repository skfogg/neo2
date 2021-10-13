package org.neosimulation.neo.framework.stateval;

import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.model.StateValUpdater;

/**
 * CellPoolDouble - A double based StateVal contained within a Cell
 * 
 * @author Isaac Griffith
 */
public class CellPoolDouble extends StateDouble {

    /**
     * Constructs a new CellPoolDouble with the given name and attached to the
     * provided holon
     * 
     * @param name
     *            Name of the new CellPoolDouble
     * @param holon
     *            Holon to attach this CellPoolDouble to
     */
    public CellPoolDouble(String name, Holon holon)
    {
        super(name, holon);
        updater = new StateValUpdater(this);
    }

    /**
     * Constructs a new initialized CellPool Double with the given name,
     * attached to provided holon, and initialized with the provided value.
     * 
     * @param name
     *            Name of the new CellPoolDouble
     * @param holon
     *            Holon to which this CellPoolDouble belongs
     * @param value
     *            The new unique id for this CellPoolDouble
     */
    public CellPoolDouble(String name, Holon holon, double value)
    {
        super(name, holon, value);
        updater = new StateValUpdater(this);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateDouble#update()
     */
    @Override
    public void update()
    {
        super.update();
    }
}
