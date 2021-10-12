/**
 * 
 */
package org.neosimulation.neo.framework.database;

import java.sql.Statement;

/**
 * TableProcessor - Interface which defines the necessary method which when
 * implemented describes the logic required to correctly process a specific
 * table in a database.
 * 
 * @author Isaac Griffith
 */
public abstract class TableProcessor {

    /**
     * The DbMediator which provides connections to other subsystems of the NEO
     * framework and coordinates information sharing.
     */
    protected DbMediator mediator;

    /**
     * Provides a means by which to control the processing of a table from the
     * database provided by the Statement parameter
     * 
     * @param statement
     *            The connection to the database for which processing is needed
     */
    public abstract void processTable(Statement statement);

    /**
     * Constructs a new TableProcessor connected to the associated DbMediator.
     * 
     * @param mediator
     *            Mediator which provides a means to store the information
     *            extracted from the database.
     */
    public TableProcessor(DbMediator mediator)
    {
        this.mediator = mediator;
    }
}
