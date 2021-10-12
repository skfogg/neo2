package org.neosimulation.neo.framework.output;

import org.neosimulation.neo.framework.config.DbConfig;

/**
 * OutputterFactory - A Factory designed to generate the outputter requested by
 * NEORuntime. Implementation of this interface generates the necessary
 * outputter to handle single threaded or multithreaded (or otherwise)
 * operations.
 * 
 * @author Ryan Nix
 * @author Isaac Griffith
 */
public interface OutputterFactory {

    /**
     * Factory Method to create a Single Threaded outputter.
     * 
     * @param config
     *            The file containing the information for the connection to the
     *            database this program sends its output to.
     * @return An outputter of type requested
     */
    public Outputter createOutputter(DbConfig config);
}
