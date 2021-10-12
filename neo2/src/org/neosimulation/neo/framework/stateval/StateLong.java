/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

import org.neosimulation.neo.framework.dynam.Dynam;
import org.neosimulation.neo.framework.dynam.ICalculatorLong;
import org.neosimulation.neo.framework.dynam.IInitializerLong;
import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.model.StateValUpdater;

/**
 * StateLong - A class representing a StateVal typed specifically to maintain
 * the control over a long value.
 * 
 * @author Isaac Griffith
 */
public class StateLong extends StateVal {

    /**
     * The initializer which controls the initialization of this StateLong's
     * value
     */
    protected IInitializerLong initializer;
    /**
     * The calculator which controls the calculation of this StateLong's value
     */
    protected ICalculatorLong calculator;
    /**
     * This is the value of the StateVal
     */
    public long value;

    /**
     * Constructs a new StateLong with the provided name and contained within
     * the provided Holon's StateValContainer
     * 
     * @param name
     *            Name of this StateLong
     * @param holon
     *            Holon in which this StateLong is to be contained
     */
    public StateLong(String name, Holon holon)
    {
        this(name, holon, holon.getSimulationModel().getNextStateValID());
    }

    /**
     * Constructs a new StateLong with the provided name and contained within
     * the provided Holon's StateValContainer
     * 
     * @param name
     *            Name of this StateLong
     * @param holon
     *            Holon in which this StateLong is to be contained
     * @param id
     *            The unique id for this StateLong
     */
    public StateLong(String name, Holon holon, long id)
    {
        super(name);
        this.holon = holon;
        this.updater = new StateValUpdater(this);
        this.uniqueID = id;
    }

    /**
     * Constructs a new StateLong in the provided simulation model and with the
     * given name. Note that the associated holon will be null.
     * 
     * @param name
     *            Name of this StateLong
     * @param model
     *            Associated simulation model for this StateLong
     */
    public StateLong(String name, SimulationModel model)
    {
        super(name);
        this.holon = null;
        this.updater = new StateValUpdater(this);
        uniqueID = model.getNextStateValID();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.StateVal#attach(org.neosimulation.neo
     * .framework.Dynam)
     */
    @Override
    public void attach(Dynam dynam)
    {
        // super.attach(dynam);
        this.dynam = dynam;

        if (dynam instanceof ICalculatorLong)
        {
            calculator = (ICalculatorLong) dynam;
        }

        if (dynam instanceof IInitializerLong)
        {
            initializer = (IInitializerLong) dynam;
        }
    }

    /**
     * Sets this StateLong's IIitializerLong to the provided one
     * 
     * @param initializer
     *            The new Initializer for this StateLong
     */
    public void setInitializer(IInitializerLong initializer)
    {
        this.initializer = initializer;
    }

    /**
     * Returns the Initializer that controls initialization of this StateVal's
     * value
     * 
     * @return IInitializerLong used to control initialization of this StateLong
     */
    public IInitializerLong getInitializer()
    {
        return initializer;
    }

    /**
     * Sets this StateLong's ICalculatorLong to the provided one
     * 
     * @param calculator
     *            The new Calculator for this StateLong
     */
    public void setCalculator(ICalculatorLong calculator)
    {
        this.calculator = calculator;
    }

    /**
     * Returns this StateLong's ICalculatorLong
     * 
     * @return this StateLong's calculator
     */
    public ICalculatorLong getCalculator()
    {
        return calculator;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateVal#initialize()
     */
    public void initialize()
    {
        currentState.initialize();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateVal#update()
     */
    public void update()
    {
        try
        {
            currentState.update();
        }
        catch (StateValException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateVal#getStringValue()
     */
    @Override
    public String getStringValue()
    {
        return Long.toString(value);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateVal#initializeState()
     */
    @Override
    protected void initializeState()
    {
        nullState = new NullStateLongState(this);
        initializedState = new InitializedStateLongState(this);
        currentState = nullState;
    }

}
