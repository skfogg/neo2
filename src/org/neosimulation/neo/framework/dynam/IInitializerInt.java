package org.neosimulation.neo.framework.dynam;

/**
 * IInitializerInt - Interface defining the initialize() method which is used by
 * StateVal Initializers to provide the connection between InitDynams and
 * StateVals to ensure that if the StateVal's initial value is not provided by
 * either a default values table or an initial values table, it will still have
 * an initial value.
 * 
 * @author Clemente Izurieta
 * @author Isaac Griffith
 */
public interface IInitializerInt {

    /**
     * Initializes the Initializers attached StateVal using user defined logic
     * in a Dynam to set it to the proper value.
     * 
     * @return the computed Initial value for the attached StateVal
     */
    public int initialize();
}
