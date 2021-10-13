/**
 * 
 */
package org.neosimulation.neo.framework.time;

import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.stateval.StateLong;
import org.neosimulation.neo.user.AutoDynamLong;

/**
 * TickDynam - Dynam which is used to both initialize and update the "Tick"
 * StateVal of the TimeKeeper, each update adjusts the SimulationModel's time by
 * the interval of the Tick.
 * 
 * @author Isaac Griffith
 */
public class TickDynam extends AutoDynamLong {

    /**
     * The interval between each tick of the TimeKeeper
     */
    private long interval;
    /**
     * The associated TimeKeeper's iteration tick stateval
     */
    private StateLong tick;

    /**
     * Constructs a new TickDynam with interval of 1
     */
    public TickDynam()
    {
        super();
        interval = 1;
    }

    /**
     * Constructs a new TickDynam with the specified interval
     * 
     * @param interval
     *            Value of each tick of the TickDynam
     */
    public TickDynam(long interval, SimulationModel model)
    {
        this();
        this.interval = interval;
        this.setModel(model);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.AutoDynamLong#setInitDeps()
     */
    @Override
    public void setInitDeps()
    {
        super.setInitDeps();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.AutoDynamLong#calculate()
     */
    @Override
    public long calculate()
    {
        return (tick.value + interval);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.AutoDynamLong#setCalcDeps()
     */
    @Override
    public void setCalcDeps()
    {
        tick = (StateLong) stateVal;
    }

    /**
     * Retrieves the value of the interval between tick updates.
     * 
     * @return The TimeStep of the model as associated with this TickDynam
     */
    public long getTickInterval()
    {
        return interval;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.IInitializerDouble#initialize()
     */
    @Override
    public long initialize()
    {
        return calculate();
    }
}
