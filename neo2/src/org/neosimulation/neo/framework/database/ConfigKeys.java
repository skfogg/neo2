/**
 * 
 */
package org.neosimulation.neo.framework.database;

import java.util.HashSet;
import java.util.Set;

/**
 * ConfigKeys - A Class which at runtime encapsulates the necessary keys needed
 * to extract the control information from a NEO Input database. These keys can
 * be used to validate that the control table contains the requisite information
 * to potentially build a simulation.
 * 
 * @author Isaac Griffith
 */
public class ConfigKeys {

    /**
     * The set of parent keys.
     */
    private Set<Key> parents;
    /**
     * The actual set of keys used to validate the control table.
     */
    private Set<Key> keyRing;

    /**
     * Constructs a new and empty ConfigKeys instance.
     */
    public ConfigKeys()
    {
        parents = new HashSet<>();
        keyRing = new HashSet<>();
    }

    /**
     * As the provided key to the key ring
     * 
     * @param key
     *            key to be added.
     */
    public void addKey(Key key)
    {
        keyRing.add(key);
    }

    /**
     * Adds the provided key as a parent key
     * 
     * @param parent
     *            key to be added.
     */
    public void addParent(Key parent)
    {
        if (parent.getParent() == null)
        {
            parents.add(parent);
        }
    }

    /**
     * Returns the set of keys making up the standard required set of keys,
     * which is known as the key ring. The provided currency name is required to
     * finalize the keyring.
     * 
     * @param currency
     *            Name of a currency in the control table
     * @return A set of keys which can be used to validate that the available
     *         information in the control table meets a minimum standard.
     */
    public Set<String> keyRing(String currency)
    {
        Set<String> ring = new HashSet<>();
        for (Key k : keyRing)
        {
            String key = k.getFullName();
            if (key.startsWith("resources"))
            {
                key = String.format(key, currency);
            }

            ring.add(key);
        }

        return ring;
    }
}