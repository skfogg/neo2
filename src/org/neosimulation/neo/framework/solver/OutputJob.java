/**
 * 
 */
package org.neosimulation.neo.framework.solver;

import java.io.Serializable;

/**
 * OutputJob - An encapsulation of the information necessary to produce a row in
 * the record of Output. It comprises the value of the TimeKeeper at which the
 * value to be output was generated. It also contains the unique Holon ID of the
 * Holon to which the StateVal with the given name is contained. It also
 * contains both the name of the StateVal whose value is to be outputted and the
 * value itself.
 * 
 * @author Isaac Griffith
 */
public class OutputJob implements Serializable {

    /**
     * Value of the timekeeper for which this Output is valid
     */
    private double time;
    /**
     * Unique Name of the Holon for which the named stateval is associated
     */
    private String holonName;
    /**
     * Name of the StateVal whose output is encapsulated in this OutputJob
     */
    private String stateVal;
    /**
     * Value of the named StateVal and the value of the output job
     */
    private String value;
    /**
     * Name of the table to be used to store the output encapsulated in this
     * job.
     */
    private String tableName;
    /**
     * Value of the timekeepr for tick for which this Output is valid.
     */
    private long tick;

    /**
     * Constructs a new OutputJob, comprising the information to be placed in
     * the Database, with the given SimulationModel TimeKeeper time value,
     * unique Holon ID, StateVal name, and value to be output.
     * 
     * @param tableName
     *            Name of the table used to store output in.
     * @param tick
     *            Tick of the SimulationModel run at which this value was
     *            generated
     * @param time
     *            Time of the SimulationModel run at which this value was
     *            generated
     * @param holonName
     *            Name of the Holon (provided by the modeler) in which the
     *            StateVal with the given value and name are contained
     * @param stateVal
     *            Name of the StateVal whose value is to be output
     * @param value
     *            Value of the StateVal with the given Name contained in the
     *            Holon with the given ID and retrieved at the given Time during
     *            the SimulationModel run
     * @throws OutputJobException
     *             Thrown if empty or null values are supplied for the table
     *             name, holon name, or stateval name.
     */
    public OutputJob(String tableName, long tick, double time, String holonName, String stateVal, String value)
            throws OutputJobException
    {
        if (tableName == null || tableName.isEmpty())
            throw new OutputJobException("Invalid Table Name for Output");

        if (holonName == null || holonName.isEmpty())
            throw new OutputJobException("Invalid name of holon for output");

        if (stateVal == null || stateVal.isEmpty())
            throw new OutputJobException("Invalid name of stateval for output");

        this.tableName = tableName;
        this.time = time;
        this.holonName = holonName;
        this.stateVal = stateVal;
        this.value = value;
        this.tick = tick;
    }

    /**
     * Returns the SimulationModel time for which this output is valid
     * 
     * @return The value of the Timekeeper at the time the output was generated
     */
    public double getTime()
    {
        return time;
    }

    /**
     * Returns the ID of the Holon whose StateVal by the given Name is to be
     * output to the database
     * 
     * @return Holon ID associated with this Output
     */
    public String getHolonName()
    {
        return holonName;
    }

    /**
     * Returns the name of the StateVal whose Output is required to be placedin
     * the database
     * 
     * @return Name of the StateVal to be output
     */
    public String getStateVal()
    {
        return stateVal;
    }

    /**
     * Returns the value to be output
     * 
     * @return the value of the StateVal associated with this output
     */
    public String getValue()
    {
        return value;
    }

    /**
     * Table name into which output will be stored.
     * 
     * @return name of the table this OutputJob is using to store output.
     */
    public String getTableName()
    {
        return tableName;
    }

    /**
     * Retrieves the tick component of this OutputJob.
     * 
     * @return The tick component of this OutputJob
     */
    public long getTick()
    {
        return tick;
    }
}
