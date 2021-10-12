/*
 *
 */

package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.IInitializerInt;

/**
 * InitDynamInt - Integer based Dynam which can only initialize the StateVal
 * associated with it. These Dynams are not a part of the main execution loop.
 * 
 * @author Isaac Griffith
 */
public abstract class InitDynamInt extends InitDynam implements IInitializerInt {

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.IInitializerInt#initialize()
     */
    public abstract int initialize();

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Dynam#setInitDeps()
     */
    @Override
    public abstract void setInitDeps();
}
