/**
 * 
 */
package org.neosimulation.neo.user;


/**
 * ManualDynam - The base class for all manual dynams. This class provides a
 * common inheritance hierarchy for manual dynams. It extends the base class for
 * CalcDynams. This class provides the basic non-type-specific functionality for
 * manual dynams.
 * 
 * @author Isaac Griffith
 */
public abstract class ManualDynam extends CalcDynam {

    /**
     * Dynam to which this ManualDynam is registered
     */
    protected CalcDynam registeredWith;

    /**
     * Allows this ManualDynam to be updated by the IUpdatable
     */
    public void doUpdate()
    {
        stateVal.getUpdater().update();
    }
    
    /**
     * Allows this ManualDynam to be initialized by the IUpdatable
     */
    public void doInitialize()
    {
        stateVal.getUpdater().initialize();
    }

    /**
     * Retrieves the CalcDynam to which this ManualDynam has been registered
     * with.
     * 
     * @return the Dynam to which this ManualDynam is currently registered
     */
    public CalcDynam registeredWith()
    {
        return registeredWith;
    }

    /**
     * Sets the Dynam to which this ManualDynam is registered with.
     * 
     * @param registerTo
     *            Dynam to which this ManualDynam is registered
     */
    public void registerTo(CalcDynam registerTo) throws ManualDynamAlreadyRegisteredException
    {
        if (registeredWith == null)
            registeredWith = registerTo;
        else
            throw new ManualDynamAlreadyRegisteredException();
    }
}
