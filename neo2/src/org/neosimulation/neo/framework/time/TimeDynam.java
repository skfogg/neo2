/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */

package org.neosimulation.neo.framework.time;

import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.framework.stateval.StateLong;
import org.neosimulation.neo.user.AutoDynamDouble;

/**
 * TimeDynam - Dynam Used to represent the algorithm to update StateVals
 * associated with the TimeKeeper object of a SimulationModel
 * 
 * @author Isaac Griffith
 */
public class TimeDynam extends AutoDynamDouble {

    /**
     * The TimeKeeper's model iteration tick stateval
     */
    private StateLong tickVal;
    /**
     * The TimeKeeper's model time stateval
     */
    private StateDouble timeVal;
    /**
     * Associated TickDynam providing the update tick for this TimeDynam
     */
    private TickDynam tick;
    /**
     * The interval between successive updates of the time stateval
     */
    private double timeStep;

    /**
     * Constructs a new TimeDynam associated with the specified TickDynam and
     * the specified SimulationModel
     * 
     * @param tick
     *            TickDynam which provides the necessary information to adjust
     *            the Model's notion of Time.
     * @param model
     *            SimulationModel to which this TimeDynam belongs
     */
    public TimeDynam(double timeStep, TickDynam tick, SimulationModel model)
    {
        super();
        this.tick = tick;
        this.timeStep = timeStep;
        this.setModel(model);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamInt#initialize()
     */
    @Override
    public double initialize()
    {
        return timeVal.value;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamInt#calculate()
     */
    @Override
    public double calculate()
    {
        return timeVal.value + (tick.getTickInterval() * timeStep);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamInt#setCalcDeps()
     */
    @Override
    public void setCalcDeps()
    {
        // model.execSolver.setDependency(stateVal.getUpdater(),
        // tick.stateVal.getUpdater());
        timeVal = (StateDouble) this.stateVal;
        tickVal = (StateLong) tick.getStateVal();
    }
}
