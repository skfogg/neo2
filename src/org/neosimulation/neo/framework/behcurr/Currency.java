/**
 * 
 */
package org.neosimulation.neo.framework.behcurr;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.neosimulation.neo.framework.populator.NEOClassLoader;

/**
 * Currency - An encapsulation of the information about resources as stored in
 * the input database.
 * 
 * @author Isaac Griffith
 */
public class Currency implements Serializable {

    /**
     * Name of this Currency
     */
    private String name;
    /**
     * List of all Behaviors for this resource that are to be installed in Cells
     */
    private List<Behavior> cellBehaviors;
    /**
     * List of all Behaviors for this resource that are to be installed in the
     * to side of Edges
     */
    private List<Behavior> toEdgeBehaviors;
    /**
     * List of all Behaviors for this resource that are to be installed in the
     * from side of Edges
     */
    private List<Behavior> fromEdgeBehaviors;
    /**
     * Location of this resource's package
     */
    private String packageLocation;
    /**
     * Base package name of this resource
     */
    private String basePackage;
    /**
     * The ClassLoader associated with this resource and to be used to load
     * dynams associated with this resources package.
     */
    private NEOClassLoader loader;

    /**
     * Creates a new Currency with the given name.
     * 
     * @param name
     *            Name of this Currency
     */
    public Currency(String name)
    {
        this.name = name;
        this.packageLocation = "";
        this.basePackage = "";

        cellBehaviors = new CopyOnWriteArrayList<>();
        toEdgeBehaviors = new CopyOnWriteArrayList<>();
        fromEdgeBehaviors = new CopyOnWriteArrayList<>();

        loader = null;
    }

    /**
     * Sets the package location for this Currency
     * 
     * @param packageLocation
     *            location of this Resources jar file or package directory
     */
    public void setLocation(String packageLocation)
    {
        this.packageLocation = packageLocation;
    }

    /**
     * Returns the location of this Resources jar file or package directory
     * 
     * @return Location of the package
     */
    public String getLocation()
    {
        return packageLocation;
    }

    /**
     * Sets the base package name
     * 
     * @param basePackage
     *            The base package name
     */
    public void setBasePackage(String basePackage)
    {
        this.basePackage = basePackage;
    }

    /**
     * Returns the base package name
     * 
     * @return Base package name
     */
    public String getBasePackage()
    {
        return basePackage;
    }

    /**
     * Registers a Cell Behavior with the provided name with this Currency
     * 
     * @param name
     *            Name of the Behavior to register
     * @return The newly created Cell Behavior with that given name
     */
    public Behavior registerCellBehavior(String name)
    {
        return registerBehavior(name, cellBehaviors);
    }

    /**
     * Unregisters a Cell Behavior from this Currency
     * 
     * @param name
     *            Name of the Cell Behavior to unregister
     */
    public void unregisterCellBehavior(String name)
    {
        unregisterBehavior(name, cellBehaviors);
    }

    /**
     * Retrieves the associated Behavior for the Cell with the provided name.
     * 
     * @param name
     *            Name of the Cell for which a CellBehavior is required
     * @return CellBehavior of the named Cell or null if no cell by the provided
     *         name exists.
     */
    public Behavior getCellBehavior(String name)
    {
        return getBehavior(name, cellBehaviors);
    }

    /**
     * Retrieves the associated Behavior for the TO side of an edge with the
     * provided name.
     * 
     * @param name
     *            Name of the edge whose To side Behavior is Required
     * @return Behavior Object associated with the named edge's to Face
     */
    public Behavior getToEdgeBehavior(String name)
    {
        return getBehavior(name, fromEdgeBehaviors);
    }

    /**
     * Retrieves the associated Behavior for the FROM side of an edge with the
     * provided name.
     * 
     * @param name
     *            Name of the edge whose From side Behavior is Required
     * @return Behavior Object associated with the named edge's from Face
     */
    public Behavior getFromEdgeBehavior(String name)
    {
        return getBehavior(name, toEdgeBehaviors);
    }

    /**
     * Method used to provide retrieve a named Behavior from a list of Behavior
     * objects.
     * 
     * @param name
     *            Name of the Behavior to find
     * @param list
     *            List to look for the Behavior in
     * @return The found Behavior object if it exists in the provided list, null
     *         otherwise
     */
    private Behavior getBehavior(String name, List<Behavior> list)
    {
        Behavior temp = new Behavior(name, this);

        if (list.contains(temp))
        {
            int index = list.indexOf(temp);
            return list.get(index);
        }

        return null;
    }

    /**
     * Retrieves a list of all Behaviors associated with this currency that are
     * also associated with Cell's
     * 
     * @return The List of Cell Behaviors associated with this Currency
     */
    public List<Behavior> getCellBehaviors()
    {
        return cellBehaviors;
    }

    /**
     * Retrieves a list of all Behaviors associated with this currency that are
     * also associated with the To side of an Edge
     * 
     * @return The List of Edge To Behaviors associated with this Currency
     */
    public List<Behavior> getToEdgeBehaviors()
    {
        return toEdgeBehaviors;
    }

    /**
     * Registers the Behavior with the provided name as an Edge To side behavior
     * of this currency.
     * 
     * @param name
     *            Registers a new behavior with the specified name to this
     *            Currency
     * @return The newly created Behavior, or if the Behavior already exists,
     *         then that Behavior object is returned.
     */
    public Behavior registerToEdgeBehavior(String name)
    {
        return registerBehavior(name, toEdgeBehaviors);
    }

    /**
     * Unregisters the Behavior with the provided name as an Edge To side
     * behavior of this currency.
     * 
     * @param name
     *            The Name of a To Face to be unregistered from this resource
     */
    public void unregisterToEdgeBehavior(String name)
    {
        unregisterBehavior(name, toEdgeBehaviors);
    }

    /**
     * Registers the Behavior with the provided name as an Edge From side
     * behavior of this currency.
     * 
     * @param name
     *            Name of a From Face Behavior to be registered with this
     *            resource
     * @return The registered Behavior object
     */
    public Behavior registerFromEdgeBehavior(String name)
    {
        return registerBehavior(name, fromEdgeBehaviors);
    }

    /**
     * Unregisters the Behavior with the provided name as an Edge To side
     * behavior of this currency.
     * 
     * @param name
     *            Name of a From Face Behavior to be unregistered from this
     *            Currency
     */
    public void unregisterFromEdgeBehavior(String name)
    {
        registerBehavior(name, fromEdgeBehaviors);
    }

    /**
     * Method which registers a new behavior with the specified name, into the
     * specified list of Behaviors, associating the new behavior with this
     * Currency. If the behavior has been previously registered, then nothing
     * occurs. In either case the newly created, or found behavior is returned.
     * 
     * @param name
     *            Name of the behavior to be registered
     * @param list
     *            List of behaviors to include the registered behavior in
     */
    private Behavior registerBehavior(String name, List<Behavior> list)
    {
        Behavior temp = new Behavior(name, this);
        if (!list.contains(temp))
        {
            list.add(temp);
            return temp;
        }
        else
        {
            int index = list.indexOf(temp);
            return list.get(index);
        }
    }

    /**
     * Method which unregister's a Behavior and disassociates it from this
     * Currency. It uses the provided Name of the behavior and the list to find
     * and remove the behavior.
     * 
     * @param name
     *            Name of behavior to unregister from this Currency
     * @param list
     *            List to look for the behavior in
     */
    private void unregisterBehavior(String name, List<Behavior> list)
    {
        Behavior temp = new Behavior(name, this);
        if (list.contains(temp))
        {
            list.remove(temp);
        }
    }

    /**
     * Retrieves the name of this Currency
     * 
     * @return The name of this Currency
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Sets the NEOClassLoader to be used to load classes that are a part of
     * this Currency's package.
     * 
     * @param loader
     *            The NEOClassLoader to be used
     */
    public void setClassLoader(NEOClassLoader loader)
    {
        this.loader = loader;
    }

    /**
     * Retrieves the NEOClassLoader used to load dynams associated with this
     * currency.
     * 
     * @return The NEOClassLoader associated with loading behavior dynams
     *         associated with this Currency's package
     */
    public NEOClassLoader getClassLoader()
    {
        return loader;
    }

    /**
     * Retrieves a list of all Behaviors associated with this currency that are
     * also associated with the From side of an Edge
     * 
     * @return The List of Edge From Behaviors associated with this Currency
     */
    public List<Behavior> getFromEdgeBehaviors()
    {
        return fromEdgeBehaviors;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return name;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Currency))
        {
            return false;
        }
        Currency other = (Currency) obj;
        if (name == null)
        {
            if (other.name != null)
            {
                return false;
            }
        }
        else if (!name.equals(other.name))
        {
            return false;
        }
        return true;
    }

}
