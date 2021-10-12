package org.neosimulation.neo.framework.stateval;

import org.neosimulation.neo.framework.dynam.Dynam;
import org.neosimulation.neo.framework.dynam.ICalculatorDouble;
import org.neosimulation.neo.framework.dynam.IInitializerDouble;
import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.model.StateValUpdater;

/**
 * StateDouble - A class representing a StateVal typed specifically to maintain
 * the control over a double value.
 * 
 * @author Clemente Izurieta
 * @author Isaac Griffith
 */
public class StateDouble extends StateVal {

    /**
     * The initializer which controls the initialization of this StateInt's
     * value
     */
    protected ICalculatorDouble calculator;
    /**
     * The calculator which controls the calculation of this StateInt's value
     */
    protected IInitializerDouble initializer;
    /**
     * This is the value of the StateVal
     */
    public double value;

    /**
     * Constructs a new StateDouble with the provided name and contained within
     * the provided Holon's StateValContainer
     * 
     * @param name
     *            Name of this StateDouble
     * @param holon
     *            Holon in which this StateDouble is to be contained
     */
    public StateDouble(String name, Holon holon)
    {
        this(name, holon, holon.getSimulationModel().getNextStateValID());
    }

    /**
     * Constructs a new initialized StateDouble with the given name, attached to
     * provided holon, and initialized with the provided value.
     * 
     * @param name
     *            Name of the new StateDouble
     * @param holon
     *            Holon to which this StateDouble belongs
     * @param value
     *            The new unique id for this StateDouble
     */
    public StateDouble(String name, Holon holon, double value)
    {
        this(name, holon);
        this.value = value;
        this.currentState = this.initializedState;
    }

    /**
     * Constructs a new StateDouble with the provided name and contained within
     * the provided Holon's StateValContainer
     * 
     * @param name
     *            Name of this StateDouble
     * @param holon
     *            Holon in which this StateDouble is to be contained
     * @param id
     *            The unique id for this StateDouble
     */
    public StateDouble(String name, Holon holon, long id)
    {
        super(name);
        this.holon = holon;
        this.updater = new StateValUpdater(this);
        this.uniqueID = id;
    }

    /**
     * Constructs a new StateDouble in the provided simulation model and with
     * the given name. Note that the associated holon will be null.
     * 
     * @param name
     *            Name of this StateDouble
     * @param model
     *            Associated simulation model for this StateDouble
     */
    public StateDouble(String name, SimulationModel model)
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

        if (dynam instanceof ICalculatorDouble)
        {
            calculator = (ICalculatorDouble) dynam;
        }

        if (dynam instanceof IInitializerDouble)
        {
            initializer = (IInitializerDouble) dynam;
        }
    }

    /**
     * Sets this StateDouble's IIitializerDouble to the provided one
     * 
     * @param initializer
     *            The new Initializer for this StateDouble
     */
    public void setInitializer(IInitializerDouble initializer)
    {
        this.initializer = initializer;
    }

    /**
     * Sets this StateDouble's ICalculatorDouble to the provided one
     * 
     * @param calculator
     *            The new Calculator for this StateDouble
     */
    public void setCalculator(ICalculatorDouble calculator)
    {
        this.calculator = calculator;
    }

    /**
     * Returns this StateDouble's ICalculatorDouble
     * 
     * @return this StateDouble's calculator
     */
    public ICalculatorDouble getCalculator()
    {
        return calculator;
    }

    /**
     * Returns this StateDouble's IInitializerDouble
     * 
     * @return this StateDouble's initializer
     */
    public IInitializerDouble getInitializer()
    {
        return initializer;
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
        return Double.toString(value);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateVal#initializeState()
     */
    @Override
    protected void initializeState()
    {
        nullState = new NullStateDoubleState(this);
        initializedState = new InitializedStateDoubleState(this);
        currentState = nullState;
    }
}
