/*
 *
 */

package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.IInitializerGeneric;

/**
 * InitDynamGeneric - Generic version of a Dynam which only performs StateVal
 * initialization and is not included in the main excecution loop of the
 * SimulationModel
 * 
 * @author Isaac Griffith
 * @param <CLASS>
 *            the generic type of InitDynamGeneric
 */
public abstract class InitDynamGeneric<CLASS> extends InitDynam implements IInitializerGeneric<CLASS> {

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.IInitializerGeneric#initialize()
     */
    public abstract CLASS initialize();

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Dynam#setInitDeps()
     */
    @Override
    public abstract void setInitDeps();
}
