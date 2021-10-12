package org.neosimulation.neo.framework.stateval;

import org.neosimulation.neo.framework.dynam.Dynam;
import org.neosimulation.neo.framework.dynam.ICalculatorGeneric;
import org.neosimulation.neo.framework.dynam.IInitializerGeneric;
import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.model.StateValUpdater;

/**
 * StateGeneric - A Generic version of the StateVal class, providing a means to
 * contain a value that is neither a double or an int
 * 
 * @author Clemente Izurieta
 * @author Isaac Griffith
 * @param <CLASS>
 *            Generic class for data in value field
 */
public class StateGeneric<CLASS> extends StateVal {

    /**
     * The initializer which controls the initialization of this StateGeneric's
     * value
     */
    protected IInitializerGeneric<CLASS> initializer;
    /**
     * The calculator which controls the calculation of this StateGeneric's
     * value
     */
    protected ICalculatorGeneric<CLASS> calculator;
    /**
     * This is the value of the StateVal
     */
    public CLASS value;

    /**
     * Constructs a new StateGeneric<CLASS> in the provided holon and with the
     * given name.
     * 
     * @param name
     *            Name of this StateGeneric
     * @param holon
     *            Holon to install it into
     */
    public StateGeneric(String name, Holon holon)
    {
        super(name);
        this.holon = holon;
        this.updater = new StateValUpdater(this);
        uniqueID = holon.getSimulationModel().getNextStateValID();
        value = null;
    }

    /**
     * Constructs a new initialized StateGeneric with the given name, attached to
     * provided holon, and initialized with the provided value.
     * 
     * @param name
     *            Name of the new StateGeneric
     * @param holon
     *            Holon to which this StateGeneric belongs
     * @param value
     *            The new unique id for this StateGeneric
     */
    public StateGeneric(String name, Holon holon, CLASS value)
    {
        this(name, holon);
        this.value = value;
        this.updater = new StateValUpdater(this);
        this.currentState = this.initializedState;
    }

    /**
     * Constructs a new StateGeneric<CLASS> in the provided simulation model and
     * with the given name. Note that the associated holon will be null.
     * 
     * @param name
     *            Name of this StateGeneric
     * @param model
     *            Associated simulation model for this StateGeneric
     */
    public StateGeneric(String name, SimulationModel model)
    {
        super(name);
        this.holon = null;
        this.updater = new StateValUpdater(this);
        uniqueID = model.getNextStateValID();
        value = null;
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
     * @see
     * org.neosimulation.neo.framework.StateVal#attach(org.neosimulation.neo
     * .framework.Dynam)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void attach(Dynam dynam)
    {
        this.dynam = dynam;

        if (dynam instanceof ICalculatorGeneric)
        {
            calculator = (ICalculatorGeneric<CLASS>) dynam;
        }

        if (dynam instanceof IInitializerGeneric)
        {
            initializer = (IInitializerGeneric<CLASS>) dynam;
        }
    }

    /**
     * Sets this StateGeneric's IIitializerGeneric to the provided one
     * 
     * @param initializer
     *            The new Initializer for this StateGeneric
     */
    public void setInitializer(IInitializerGeneric<CLASS> initializer)
    {
        this.initializer = initializer;
    }

    /**
     * Returns the Initializer that controls initialization of this StateVal's
     * value
     * 
     * @return IInitializerGeneric used to control initialization of this
     *         StateGeneric
     */
    public IInitializerGeneric<CLASS> getInializer()
    {
        return initializer;
    }

    /**
     * Sets this StateGeneric's ICalculatorGeneric to the provided one
     * 
     * @param calculator
     *            The new Calculator for this StateGeneric
     */
    public void setCalculator(ICalculatorGeneric<CLASS> calculator)
    {
        this.calculator = calculator;
    }

    /**
     * Returns this StateGeneric's ICalculatorGeneric
     * 
     * @return this StateGeneric's calculator
     */
    public ICalculatorGeneric<CLASS> getCalculator()
    {
        return calculator;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateVal#getStringValue()
     */
    @Override
    public String getStringValue()
    {
        if (value == null)
            return "NULL";
        return value.toString();
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.StateVal#initializeState()
     */
    @Override
    protected void initializeState()
    {
        nullState = new NullStateGenericState<CLASS>(this);
        initializedState = new InitializedStateGenericState<CLASS>(this);
        currentState = nullState;
    }
}
