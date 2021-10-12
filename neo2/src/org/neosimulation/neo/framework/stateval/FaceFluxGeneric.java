/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.model.StateValUpdater;

/**
 * FaceFluxGeneric - A StateVal to be contained within a Face, representing the
 * transient flux of a specific value.
 * 
 * @author Isaac Griffith
 * @param <CLASS>
 *            Type of this FaceFluxGeneric
 */
public class FaceFluxGeneric<CLASS> extends StateGeneric<CLASS> {

    /**
     * Constructs a new FaceFluxGeneric with the provided name and attached to
     * the provided Holon.
     * 
     * @param name
     *            Name of the new FaceFluxGeneric
     * @param holon
     *            Holon to which the newly created FaceFluxGeneric belongs
     */
    public FaceFluxGeneric(String name, Holon holon)
    {
        super(name, holon);
        updater = new StateValUpdater(this);
    }

    /**
     * Constructs a new initialized FaceFluxGeneric with the given name, attached to
     * provided holon, and initialized with the provided value.
     * 
     * @param name
     *            Name of the new FaceFluxGeneric
     * @param holon
     *            Holon to which this FaceFluxGeneric belongs
     * @param value
     *            The new unique id for this FaceFluxGeneric
     */
    public FaceFluxGeneric(String name, Holon holon, CLASS value)
    {
        super(name, holon, value);
        updater = new StateValUpdater(this);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateGeneric#update()
     */
    @Override
    public void update()
    {
        super.update();
    }
}
