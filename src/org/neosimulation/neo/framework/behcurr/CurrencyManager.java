/**
 * 
 */
package org.neosimulation.neo.framework.behcurr;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CurrencyManager - A class associated with a SimulationModel which maintains a
 * collection of all currencies being used in the SimulationModel
 * 
 * @author Isaac Griffith
 */
public class CurrencyManager implements Serializable {

    /**
     * List of known currencies used by a SimulationModel
     */
    private List<Currency> currencies;

    /**
     * Constructs a new Currency Manager with no registered currencies
     */
    public CurrencyManager()
    {
        currencies = new CopyOnWriteArrayList<>();
    }

    /**
     * Registers a currency with the given name with this CurrencyManager.
     * 
     * @param name
     *            Name of currency to be registered
     * @return The currency that has been registered.
     */
    public Currency registerCurrency(String name)
    {
        Currency temp = new Currency(name);

        if (!currencies.contains(temp))
        {
            currencies.add(temp);
            return temp;
        }
        else
        {
            int index = currencies.indexOf(temp);
            return currencies.get(index);
        }
    }

    /**
     * Returns the Currency contained herein with the specified name
     * 
     * @param name
     *            Name of a resource registered with this resource manager
     * @return The Currency with the given name, if it has been registered with
     *         this CurrencyManager, otherwise null is returned
     */
    public Currency get(String name)
    {
        Currency temp = new Currency(name);

        int index = currencies.indexOf(temp);
        
        return currencies.get(index);
    }

    /**
     * Retrieves a list of all currency objects currently managed by this
     * CurrencyManager.
     * 
     * @return List of all registered currencies associated with the
     *         SimulationModel this CurrencyManager is Associated with
     */
    public List<Currency> getCurrencies()
    {
        return currencies;
    }
}
