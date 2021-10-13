package org.neosimulation.neo.framework.output;

import java.io.Serializable;
import java.util.List;
import java.util.StringTokenizer;

/**
 * OutputProcessStrategy - Output process strategies provide a way to customize
 * the way to output.
 * 
 * @author Michael Paulson
 * @author Isaac Griffith
 */
public abstract class OutputProcessStrategy implements Serializable {

    /**
     * The outputter that provides the context for this strategy.
     */
    protected Outputter outputter;

    /**
     * Constructs a new OutputProcessStrategy within the context of the provided
     * outputter.
     * 
     * @param outputter
     *            The outputter to which this OutputProcessStrategy is
     *            associated.
     */
    public OutputProcessStrategy(Outputter outputter)
    {
        this.outputter = outputter;
    }

    /**
     * Processes the output by inserting the data into the database that is
     * provided by the OutputSQLStatement.
     * 
     * @param statement
     *            SQL Statement to process
     */
    protected abstract void processOutput(String statement);

    /**
     * Handles the formatting of "insert" output. Inserts a new piece of data
     * into the database.
     * 
     * @param tableName
     *            The table name in the connected database into which the
     *            output, encapsulaed in the provided StringTokenizer, should be
     *            directed to.
     * @param eventSt
     *            of all parts of the output: (modelTime, holonName, stateVal,
     *            svValue)
     * @return String formatted in required way for the specific output strategy
     */
    protected abstract String formatInsertOutput(String tableName, StringTokenizer eventSt);

    protected abstract String formatInsertOutput(String tableName, List<StringTokenizer> eventSt);

    /**
     * Handles the formatting of "update" output. Updates existing fields in the
     * database.
     * 
     * @param tableName
     *            The table name in the connected database into which the
     *            output, encapsulaed in the provided StringTokenizer, should be
     *            directed to.
     * @param eventSt
     *            of all parts of the output: (modelTime, holonName, stateVal,
     *            svValue)
     * @return String formatted in the required way for the specific output
     *         strategy.
     */
    protected abstract String formatUpdateOutput(String tableName, StringTokenizer eventSt);

    /**
     * @param tableName
     * @return
     */
    public abstract String formatInsertOutput(String tableName);

    /**
     * @param tableName
     * @return
     */
    public abstract String formatUpdateOutput(String tableName);

    /**
     * @param eventSt
     * @return
     */
    public abstract String formatValueString(StringTokenizer eventSt);
}
