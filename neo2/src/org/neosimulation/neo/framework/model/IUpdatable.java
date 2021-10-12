package org.neosimulation.neo.framework.model;

/**
 * IUpdatable - Any software artifact that is updatable by the grand loop will
 * implement this interface. The grand loop will then know to call the update()
 * and/or initialize() methods of the updatable object every time it goes
 * through the trade, update and store cycle.
 * 
 * @author Clemente Izurieta
 * @author Isaac Griffith
 */
public interface IUpdatable extends Comparable<IUpdatable> {

    /**
     * Initializes this Updatables associated StateVal by calling its associated
     * Dynam
     */
    public void initialize();

    /**
     * Updates this Updatables associated StateVal by calling its associated
     * Dynam
     */
    public void update();

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(IUpdatable updatable);

    /**
     * Sets the index representing the position of this IUpdatable in the
     * complete model execution loop.
     * 
     * @param index
     *            the new index of this IUpdatable
     */
    public void setIndex(int index);

    /**
     * @return The index representing the current position of this IUpdatable in
     *         the complete model execution loop, or Integer.MIN_VALUE
     *         otherwise.
     */
    public int getIndex();
    
    public void setMultiplier(double d);
    
    public double getMultiplier();
}
