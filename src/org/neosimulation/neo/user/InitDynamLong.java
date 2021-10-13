/**
 * 
 */
package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.IInitializerLong;

/**
 * InitDynamLong - Long based Dynam which can only initialize the StateVal
 * associated with it. These Dynams are not a part of the main execution loop.
 * 
 * @author Isaac Griffith
 */
public abstract class InitDynamLong extends InitDynam implements IInitializerLong {

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.IInitializerLong#initialize()
     */
    public abstract long initialize();

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Dynam#setInitDeps()
     */
    @Override
    public abstract void setInitDeps();

}
