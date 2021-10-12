package org.neosimulation.neo.framework.output;

/**
 * OutputType - The type of sql statement that will be generated.
 * 
 * @author Michael Paulson
 * @author Isaac Griffith
 */
public enum OutputType {

    /**
     * The output (SQL statement) is an insert.
     */
    Insert,
    /**
     * The output (SQL statement) is an update.
     */
    Update;
}
