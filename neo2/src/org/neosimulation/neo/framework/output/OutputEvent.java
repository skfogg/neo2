package org.neosimulation.neo.framework.output;

import java.util.EventObject;

/**
 * OutputEvent - an encapsulation the information an IOutputProvider needs to be
 * outputted by an IOutputListener.
 * 
 * @author Michael Paulson
 * @author Isaac Griffith
 */
public class OutputEvent extends EventObject {

    /**
     * The OUTPUT_FORMAT = "tick time holonName stateVal value"
     */
    private static final String OUTPUT_FORMAT = "%d %f %s %s %s";
    /**
     * The object that was responsible for this input.
     */
    private IOutputProvider model;
    /**
     * The current time from the model.
     */
    private double time;
    /**
     * The type of the input
     */
    private OutputType type;
    /**
     * The name of the holon.
     */
    private String holonName;
    /**
     * The providing stateval.
     */
    private String stateVal;
    /**
     * The value.
     */
    private String value;
    /**
     * Name of the table to which the output encapsulated by this event is to be
     * stored.
     */
    private String tableName;
    /**
     * The current tick of the model
     */
    private long tick;

    /**
     * Creates an OutputEvent that carries all the information needed to be
     * inserted into a database.
     * 
     * @param outputProvider
     *            The source of the output event
     * @param type
     *            The type of OutputType.
     * @param tick
     *            The tick component to the update/insert.
     * @param time
     *            The time component to the update/insert.
     * @param holonName
     *            The holon name component to the update/insert.
     * @param stateVal
     *            the stateVal component to the update/insert.
     * @param value
     *            the value component to the update/insert.
     */
    public OutputEvent(IOutputProvider outputProvider, String tableName, OutputType type, long tick, double time,
            String holonName, String stateVal, String value)
    {
        super(outputProvider);

        this.model = outputProvider;
        this.tick = tick;
        this.time = time;
        this.type = type;
        this.holonName = holonName;
        this.stateVal = stateVal;
        this.value = value;
        this.tableName = tableName;
    }

    /**
     * The output from the model.
     * 
     * @return The output from the model in string format.
     */
    public String getOutput()
    {
        return String.format(OUTPUT_FORMAT, tick, time, holonName, stateVal, value);
    }

    /**
     * returns the IOutputProvider that the OutputEvent came from.
     * 
     * @return The one who created the event.
     */
    public IOutputProvider getModel()
    {
        return model;
    }

    /**
     * The type of output that will be performed on the database.
     * 
     * @return The OutputType (either an INSERT or and UPDATE)
     */
    public OutputType getType()
    {
        return type;
    }

    /**
     * Returns the table name into which the output associated with this event
     * is to be stored.
     * 
     * @return the name of the table into which output for this event is to be
     *         stored.
     */
    public String getTableName()
    {
        return tableName;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "OutputEvent [model=" + model + ", time=" + time + ", type=" + type + ", holonName=" + holonName
                + ", stateVal=" + stateVal + ", value=" + value + ", tableName=" + tableName + "]";
    }
}
