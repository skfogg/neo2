package org.neosimulation.neo.framework.time;

import org.neosimulation.neo.framework.model.IUpdatable;
import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.framework.stateval.StateLong;

/**
 * TimeKeeper - An encapsulation of the logic which controls and tracks the iteration tick and simulation time of a model. The TimeKeeper class can only be
 * accessed by other classes in the same package.
 * 
 * @author Isaac Griffith
 */
public class TimeKeeper {

    /**
     * The Name of the Tick StateVal
     */
    public static final String TICK_STATEVAL_NAME = "Tick";
    /**
     * The Name of the Time StateVal
     */
    public static final String TIME_STATEVAL_NAME = "Current Time";
    /**
     * The Name of the Time Step Interval StateVal
     */
    public static final String TIMESTEP_STATEVAL_NAME = "Time Step";

    /**
     * The StateDouble representing the current iteration value of a Model
     */
    private StateLong tick;
    /**
     * The StateDouble representing the current value for model Time
     */
    private StateDouble currentTime;
    /**
     * The update constant for model time
     */
    private StateDouble timeStep;
    /**
     * Dynam associated to the Tick StateDouble
     */
    private TickDynam tickDynam;
    /**
     * Dynam associated to the Time StateDouble
     */
    private TimeDynam timeDynam;
    /**
     * The SimulationModel to which this TimeKeeper belongs.
     */
    private SimulationModel model;

    /**
     * Constructs a new TimeKeeper with the default Time Tick of 1 tick per time step and with an initial value of 0, which is associated with the provided
     * SimulationModel.
     * 
     * @param model
     *            The SimulationModel in which this TimeKeeper is contained.
     */
    public TimeKeeper(SimulationModel model)
    {
        this(1, 0, 1, 0, model);
    }

    /**
     * Constructs a new TimeKeeper with the specified time step, initial time seed, and to be contained in the specified SimulationModel.
     * 
     * @param timestep
     *            default time step
     * @param seed
     *            initial time value
     * @param tickSeed
     *            the initial iteration value
     * @param tickInterval
     *            the iteration interval
     * @param model
     *            containing SimulationModel
     */
    public TimeKeeper(double timestep, double seed, long tickInterval, long tickSeed, SimulationModel model)
    {
        this.model = model;
        tick = new StateLong(TICK_STATEVAL_NAME, model);
        tick.value = tickSeed;

        currentTime = new StateDouble(TIME_STATEVAL_NAME, model);
        currentTime.value = seed;

        timeStep = new StateDouble(TIMESTEP_STATEVAL_NAME, model);
        timeStep.value = timestep;

        tickDynam = new TickDynam(tickInterval, model);
        timeDynam = new TimeDynam(timestep, tickDynam, model);

        tick.attach(tickDynam);
        tickDynam.attach(tick);
        currentTime.attach(timeDynam);
        timeDynam.attach(currentTime);

        timeDynam.setInitDeps();
        tickDynam.setInitDeps();
    }

    /**
     * Provides the current tick of this TimeKeeper. The tick is not necessarily the same value as the time value and is determined by the TickDynam associated
     * with this TimeKeeper's StateVal named "Tick".
     * 
     * @return TimeKeeper's current tick.
     */
    public synchronized long getTick()
    {
        return tick.value;
    }

    /**
     * Retrieves the interval used to update the tick.
     * 
     * @return The value of the current interval between iteration ticks.
     */
    public synchronized long getTickInterval()
    {
        return tickDynam.getTickInterval();
    }

    /**
     * Provides the current time of this TimeKeeper. The time is not necessarily the same value as the tick value and is determined by the TimeDynam associated
     * with this TimeKeeper's StateVal named "Current Time".
     * 
     * @return TimeKeeper's current time.
     */
    public synchronized double getCurrentTime()
    {
        return currentTime.value;
    }

    /**
     * Provides access to this TimeKeeper's TimeTick StateVal's value
     * 
     * @return The TimeStep's current value
     */
    public synchronized double getTimeStep()
    {
        return timeStep.value;
    }

    /**
     * Used to modify the current TimeStep value. In essence, this multiplies the current time step by the provided factor. This method should mainly be used to
     * avoid model instability.
     * 
     * @param factor
     *            Factor by which to multiply and modify the current time step by.
     */
    public synchronized void modifyTimeStep(double factor)
    {
        timeStep.value *= factor;
    }

    /**
     * Used to set the TimeStep to a new value, not to be confused with the concept of modifying the current time step by a provided factor. The TimeStep value
     * will from this point on be the provided value.
     * 
     * @param value
     *            Value to change the time step to.
     */
    public synchronized void setTimeStep(double value)
    {
        timeStep.value = value;
    }

    /**
     * Retrieves the IUpdatable that controls the tick stateval.
     * 
     * @return Tick Updatable
     */
    public synchronized IUpdatable getTickUpdatable()
    {
        return tick.getUpdater();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "TimeKeeper [tick=" + tick + ", currentTime=" + currentTime + ", timeStep=" + timeStep + "]";
    }

    /**
     * Retrieves the IUpdatable that controls the time stateval.
     * 
     * @return Current Time Updatable
     */
    public synchronized IUpdatable getTimeUpdatable()
    {
        return currentTime.getUpdater();
    }
}
