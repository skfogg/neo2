/**
 * 
 */
package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.Dynam;
import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.model.StateValContainerException;
import org.neosimulation.neo.framework.stateval.StateVal;

/**
 * CalcDynam - The base class for all calc dynams. This class provides a common inheritance hierarchy for calc dynams. It extends the base class for Dynams.
 * This class provides the basic non-type-specific functionality for calc dynams.
 * 
 * @author Isaac Griffith
 */
public abstract class CalcDynam extends Dynam {

    /**
     * Contains the user defined logic to set the dependencies of the logic in the calculate method to statevals existing in the network.
     */
    abstract public void setCalcDeps();

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Dynam#setInitDeps()
     */
    @Override
    public void setInitDeps()
    {
        setCalcDeps();
    }

    /**
     * Registers a ManualDynam (in the provided holon and associated with the named stateval) to this CalcDynam.
     * 
     * @param holon
     *            Holon containing the stateval whose dynam is attempting to be registered.
     * @param stateVal
     *            name of the stateval with the manual dynam to be registered.
     * @return the ManualDynam associated with the named stateval, if such a ManualDynam exists.
     * @throws UnableToRegisterException
     *             if no such stateval with the given name exists, the dynam with that stateval is already registered with another CalcDynam, or the Dynam of
     *             the StateVal in question is not a ManualDynam.
     */
    public ManualDynam setRegistration(Holon holon, String stateVal) throws UnableToRegisterException
    {
        ManualDynam retVal = null;

        StateVal sv;
        try
        {
            sv = holon.getStateVal(stateVal);
            if (sv.getDynam() instanceof ManualDynam)
            {
                retVal = (ManualDynam) sv.getDynam();
                retVal.registerTo(this);
            }
            else
            {
                throw new UnableToRegisterException();
            }
        }
        catch (StateValContainerException e)
        {
            throw new UnableToRegisterException(e);
        }
        catch (ManualDynamAlreadyRegisteredException e)
        {
            throw new UnableToRegisterException(e);
        }

        return retVal;
    }

    /**
     * Registers the ManualDynam associated with the stateval (with the provided name), in the same holon as this dynam, to this CalcDynam.
     * 
     * @param stateVal
     *            name of the stateval with the manual dynam to be registered.
     * @return the ManualDynam associated with the named stateval, if such a ManualDynam exists.
     */
    public ManualDynam setRegistration(String stateVal)
    {
        ManualDynam retVal = null;

        StateVal sv;
        try
        {
            sv = this.holon.getStateVal(stateVal);
            if (sv.getDynam() instanceof ManualDynam)
            {
                retVal = (ManualDynam) sv.getDynam();
                retVal.registerTo(this);
            }
            else
            {
                throw new UnableToRegisterException();
            }
        }
        catch (StateValContainerException | ManualDynamAlreadyRegisteredException | UnableToRegisterException e)
        {
            NEOLogger.logException(model, "Exception occurred while try to set the registration of Manual Dynam: "
                    + retVal.stateVal.getHolon().getName() + "." + retVal.stateVal.getName() + "."
                    + retVal.getClass().getSimpleName() + " to " + this.stateVal.getHolon().getName() + "."
                    + this.getStateVal().getName() + "." + this.getClass().getSimpleName(), e);
        }

        return retVal;
    }

    /**
     * An optional method used to register manual dynams correctly. If you need to register a manual dynam you should override this method.
     */
    public void setRegistrations()
    {
    }
}
