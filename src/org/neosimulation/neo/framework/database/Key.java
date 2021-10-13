/**
 * 
 */
package org.neosimulation.neo.framework.database;

import java.util.HashSet;
import java.util.Set;

/**
 * Key - A class representing either a parent of or a key stored within the
 * ConfigKeys data structure.
 * 
 * @author Isaac Griffith
 */
public class Key {

    /**
     * The Child Keys of this key (if it is a parent)
     */
    private Set<Key> children;
    /**
     * The parent key of this key
     */
    private Key parent;
    /**
     * The name of this key (not fully qualified)
     */
    private String name;
    /**
     * parent flag, if true this is a parent key
     */
    private boolean isParent;
    /**
     * the fully qualified (dot separated) name of this key, including all
     * parents back to the root of this keys tree.
     */
    private String fullName;

    /**
     * Constructs a new Key with the provided local name and parent Key. The
     * create key is assumed not to be a parent key.
     * 
     * @param name
     *            Local name of this key
     * @param parent
     *            Parent key for this key
     * @throws KeyException
     *             thrown if the provided parent key is not designated as a
     *             parent.
     */
    public Key(String name, Key parent) throws KeyException
    {
        this(name, parent, false);
    }

    /**
     * Constructs a new Key with the provided local name, parent key, and flag
     * set to indicate whether this key is a parent or not.
     * 
     * @param name
     *            Local name of this Key
     * @param parent
     *            Parent Key of this key, if this key is a root key, then parent
     *            should be null.
     * @param isParent
     *            boolean flag indicating if this key is a parent key
     * @throws KeyException
     *             thrown if the provided parent key is not designated as a
     *             parent
     */
    public Key(String name, Key parent, boolean isParent) throws KeyException
    {
        this.name = name;
        this.parent = parent;
        this.isParent = isParent;
        children = new HashSet<>();

        if (!isParent && parent == null)
        {
            throw new KeyException();
        }
        else if (parent != null)
        {
            parent.addChild(this);
        }

        generateFullName();
    }

    /**
     * Adds the providd child key to this key.
     * 
     * @param child
     *            Child key to be added to this key
     * @throws KeyException
     *             thrown if attempting to add a key to a non-parent key
     */
    public void addChild(Key child) throws KeyException
    {
        if (isParent)
        {
            children.add(child);
        }
        else
        {
            throw new KeyException();
        }
    }

    /**
     * Used to generate the fully qualified and dot separated name of this key.
     */
    private void generateFullName()
    {
        if (parent != null)
            fullName = parent.getFullName() + "." + name;
        else
            fullName = name;
    }

    /**
     * Retrieves the set of keys that are children of this Key.
     * 
     * @return the set of children keys associated with this key
     */
    public Set<Key> getChildren()
    {
        return children;
    }

    /**
     * Retrieves the Key that is the parent of this key.
     * 
     * @return the parent key of this key, unless this key is a root key and
     *         therefore the parent is null
     */
    public Key getParent()
    {
        return parent;
    }

    /**
     * Returns the unqualified local name of this key.
     * 
     * @return the local name of this key.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Tests whether this Key is a parent key.
     * 
     * @return true if this is a parent key
     */
    public boolean isParent()
    {
        return isParent;
    }

    /**
     * Retrieves the full name (as defined in the tree) for this key separating
     * each parent/child by a period up to this key.
     * 
     * @return the fully qualified (dot separated) name of this key which starts
     *         with the root containing this key and each parent key until the
     *         key is reached.
     */
    public String getFullName()
    {
        return fullName;
    }
}