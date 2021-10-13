/**
 * 
 */
package org.neosimulation.neo.framework.solver;

import org.neosimulation.neo.framework.time.TimeKeeper;

/**
 * StoppingCondition - Represents and Encapsulation of a Condition upon
 * occurrence of which a model should stop executing
 * 
 * @author Isaac Griffith
 */
public class StoppingCondition {

    /**
     * Type of stopping condition
     */
    private ConditionType type;

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "StoppingCondition [type=" + type + ", value=" + value + "]";
    }

    /**
     * value of stopping condition if applicable
     */
    private double value = Double.MIN_VALUE;

    /**
     * Constructs a new Stopping Condition with the specified type and value
     * 
     * @param type
     *            Type of Stopping Condition
     * @param value
     *            Value of Stopping Condition
     */
    public StoppingCondition(ConditionType type, double value)
    {
        this.type = type;
        this.value = value;
    }

    /**
     * Constructs a new Stopping Condition with the specified type. Should only
     * be used for non value associated types.
     * 
     * @param type
     *            Type of Stopping Condition
     */
    public StoppingCondition(ConditionType type)
    {
        this.type = type;
    }

    /**
     * Tests whether the condition has been met based on the current state of
     * the TimeKeeper
     * 
     * @param time
     *            the TimeKeeper
     * @return true the given condition has been met, false otherwise.
     */
    public boolean shouldStop(TimeKeeper time)
    {
        boolean retVal = false;
        switch (type)
        {
        case OnError:
            retVal = true;
            break;
        case AtTime:
            retVal = (time.getCurrentTime() > value);
            break;
        case AtTick:
            retVal = (time.getTick() > value);
            break;
        }

        return retVal;
    }

    /**
     * ConditionType - Enumeration of possible types for a StoppingCondition
     * 
     * @author Isaac Griffith
     */
    public enum ConditionType {
        /**
         * if an OnError condition is generated (by whatever has thrown that
         * error condition, then it will always return true if shouldStop is
         * called
         */
        OnError,

        /**
         * Holds a value corresponding to some future or past value of the
         * CurrentTime StateVal in the TimeKeeper if this value is equal to or
         * less than the AtTime value shouldStop will return true;
         */
        AtTime,

        /**
         * Holds a value corresponding to some future iteration of the
         * TimeKeeper's Tick StateVal and if this value is less than or equal to
         * the AtTick value shouldStop will return true;
         */
        AtTick,

        /**
         * Exits after the point in which the package developer manually called
         * the exit function after which an entire model loop has completed,
         * allowing the system to exit completely.
         */
        UserInitiated,
    }
}
