/*
 *
 */

package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.dynam.IInitializerDouble;

/**
 * InitDynamDouble - Double typed version of the InitDynam which initializes a
 * StateDouble to its initial value.
 * 
 * @author Isaac Griffith
 */
public abstract class InitDynamDouble extends InitDynam implements IInitializerDouble {

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.IInitializerDouble#initialize()
     */
    public abstract double initialize();

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.Dynam#setInitDeps()
     */
    @Override
    public abstract void setInitDeps();
}
