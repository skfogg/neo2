/**
 * 
 */
package org.neosimulation.neo.framework.stateval;

import org.neosimulation.neo.framework.dynam.Dynam;
import org.neosimulation.neo.framework.dynam.ICalculatorInt;
import org.neosimulation.neo.framework.dynam.IInitializerInt;
import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.model.StateValUpdater;

/**
 * StateInt - A class representing a StateVal typed specifically to maintain the
 * control over a int value.
 * 
 * @author Clemente Izurieta
 * @author Isaac Griffith
 */
public class StateInt extends StateVal {

    /**
     * The initializer which controls the initialization of this StateInt's
     * value
     */
    protected IInitializerInt initializer;
    /**
     * The calculator which controls the calculation of this StateInt's value
     */
    protected ICalculatorInt calculator;
    /**
     * This is the value of the StateVal
     */
    public int value;

    /**
     * Constructs a new StateInt with the given name and contained in the
     * provided Holon's StateValContainer
     * 
     * @param name
     *            Name of this StateInt
     * @param holon
     *            Holon containing this StateInt
     */
    public StateInt(String name, Holon holon)
    {
        super(name);
        this.holon = holon;
        this.updater = new StateValUpdater(this);

        uniqueID = holon.getSimulationModel().getNextStateValID();
    }

    /**
     * Constructs a new initialized StateInt with the given name, attached to
     * provided holon, and initialized with the provided value.
     * 
     * @param name
     *            Name of the new StateInt
     * @param holon
     *            Holon to which this StateInt belongs
     * @param value
     *            The new unique id for this StateInt
     */
    public StateInt(String name, Holon holon, int value)
    {
        this(name, holon);
        this.updater = new StateValUpdater(this);
        this.value = value;
        this.currentState = this.initializedState;
    }

    /**
     * Constructs a new StateInt in the provided simulation model and with the
     * given name. Note that the associated holon will be null.
     * 
     * @param name
     *            Name of this StateInt
     * @param model
     *            Associated simulation model for this holon
     */
    public StateInt(String name, SimulationModel model)
    {
        super(name);
        holon = null;
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

        if (dynam instanceof ICalculatorInt)
        {
            calculator = (ICalculatorInt) dynam;
        }

        if (dynam instanceof IInitializerInt)
        {
            initializer = (IInitializerInt) dynam;
        }
    }

    /**
     * Sets this StateInt's IIitializerInt to the provided one
     * 
     * @param initializer
     *            The new Initializer for this StateInt
     */
    public void setInitializer(IInitializerInt initializer)
    {
        this.initializer = initializer;
    }

    /**
     * Returns the Initializer that controls initialization of this StateVal's
     * value
     * 
     * @return IInitializerInt used to control initialization of this StateInt
     */
    public IInitializerInt getInitializer()
    {
        return initializer;
    }

    /**
     * Sets this StateInt's ICalculatorInt to the provided one
     * 
     * @param calculator
     *            The new Calculator for this StateInt
     */
    public void setCalculator(ICalculatorInt calculator)
    {
        this.calculator = calculator;
    }

    /**
     * Returns this StateInt's ICalculatorInt
     * 
     * @return this StateInt's calculator
     */
    public ICalculatorInt getCalculator()
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
        return Integer.toString(value);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateVal#initializeState()
     */
    @Override
    protected void initializeState()
    {
        nullState = new NullStateIntState(this);
        initializedState = new InitializedStateIntState(this);
        currentState = nullState;
    }
}
