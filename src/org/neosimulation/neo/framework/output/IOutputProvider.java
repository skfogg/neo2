package org.neosimulation.neo.framework.output;

import java.util.List;
import java.util.Set;

/**
 * IOutputProvider - A source that will provide output to an IOutputListener in
 * the form of OutputEvent.
 * 
 * @author Michael Paulson
 * @author Isaac Griffith
 */
public interface IOutputProvider {

    /**
     * Ensures that the OutputProvider can pause its output without crashing.
     */
    void pause();

    /**
     * Ensures that the OutputProvider can resume providing output.
     */
    void resume();

    /**
     * Adds an output listener to the OutputProvider. This way an event can be
     * tied into the OutputProvider.
     * 
     * @param listener
     *            Listener that this IOutputProvider should send OutputEvents to
     */
    void addOutputListener(IOutputListener listener);

    /**
     * Removes the output listener that is currently listening to the input.
     * This is usually done during model switching when a queue is overflowed or
     * the simulation is finished.
     * 
     * @param listener
     *            Listener that this IOutputProvider was sending OutputEvents to
     */
    void removeOutputListener(IOutputListener listener);

    /**
     * The id of the output queue. This should be universally unique among all
     * output queues. The ID is determined by the Outputter
     * 
     * @return long value representing the unique id of the OutputProvider
     */
    long getId();

    /**
     * Returns a set of strings representing the names of tables into which the
     * output for this IOutputProvider can be stored.
     * 
     * @return Table names in the database in which the outputter is to store
     *         output for this IOutputProvider
     */
    Set<String> getOutputTableNames();
}
