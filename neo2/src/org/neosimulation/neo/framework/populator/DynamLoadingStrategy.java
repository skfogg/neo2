/**
 * 
 */
package org.neosimulation.neo.framework.populator;

import java.util.List;
import java.util.Set;

import org.neosimulation.neo.framework.dynam.Dynam;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.model.SimulationModel;

/**
 * DynamLoadingStrategy - An implementation of the Strategy Pattern designed to
 * be used by the Populator class in order to load Dynams from different
 * location sources.
 * 
 * @author Isaac Griffith
 */
public abstract class DynamLoadingStrategy {

    /**
     * Begins the process of Loading Dynams using the parameters encapsulated in
     * the provided DynamLoaderParams object.
     * 
     * @param params
     *            An encapsulation of the parameters for a DynamLoader
     * @return List of Class objects representing the dynams to be loaded
     */
    public abstract Set<Class> load(DynamLoaderParams params);

    /**
     * Adds the class specified by the provided classname to the list of Dynams.
     * This method first generates the Class object for the provided class name
     * and then adds it to the provided list.
     * 
     * @param classname
     *            Class name for which a Class object is to be generated (where
     *            the class object must be a descendant of type Dynam)
     * @param classes
     *            List of Class objects (all of which are descendants of Dynam)
     *            to which the generated Class object is to be added
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void addClass(String classname, Set<Class> classes, NEOClassLoader loader, SimulationModel model)
    {
        Class dynam = Dynam.class;

        try
        {
            // Retrieve the Class object representing the named class
            // Throws a ClassNotFoundException if the class does not exist
            // Class temp = Class.forName(classname);
            Class temp = loader.loadClass(classname);

            // If this class inherits from Dynam then added it to the list of
            // dynams to be installed
            if (dynam.isAssignableFrom(temp))
            {
                classes.add(temp);
            }
        }
        catch (ClassNotFoundException cnfex)
        {
            NEOLogger.logWarning(model, "Cannot find class: " + classname);
        }
    }

    /**
     * Adds a Devian Dynam specified by the String classname to the provided
     * list of dynams. It also checks to see whether the list of dynams requires
     * dynams to be removed if they have the same simple name as the new deviant
     * class.
     * 
     * @param classname
     *            Class name of the dynam to be added to the list of dynams
     * @param dynams
     *            The List<Class> to which the deviant dynam is to be added or
     *            replace from classes of the same name.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void addDeviantClass(String classname, List<Class> dynams, NEOClassLoader loader, SimulationModel model)
    {
        Class dynam = Dynam.class;

        try
        {
            // Class deviant = Class.forName(classname);
            Class deviant = loader.loadClass(classname);

            if (dynam.isAssignableFrom(deviant))
            {
                // handle the replacing of class names from base behaviors with
                // those of deviants
                boolean replaced = false;
                for (int i = 0; i < dynams.size(); i++)
                {
                    Class clDynam = dynams.get(i);

                    // Checks if the deviant dynam class name is equal
                    // to the
                    // current class at the index i
                    // in the list of dynams from the base behavior. If it is we
                    // replace the item at index
                    // i with the deviant dynam. Since there should only be one
                    // instance of any dynam with
                    // the given name, if we have replaced it then we
                    // are done
                    if (deviant.getSimpleName().equals(clDynam.getSimpleName()))
                    {
                        dynams.set(i, deviant);
                        replaced = true;
                        break;
                    }
                }

                // if no replacement occurred, then we still need to add the
                // deviant dynam to the list
                if (!replaced)
                {
                    dynams.add(deviant);
                }
            }
        }
        catch (ClassNotFoundException cnfex)
        {
            NEOLogger.logWarning(model, "Could not find class: " + classname);
        }
    }

    /**
     * Retrieves the correct package name from the DynamLoaderParams (either
     * behavior or deviant).
     * 
     * @param params
     *            DynamLoaderParams object containing the correct information
     * @return Package name to be used to load dynams
     */
    protected String getPackageName(DynamLoaderParams params)
    {
        String packageName;
        if (params.isDeviant())
        {
            packageName = params.getDeviantPkg();
        }
        else
        {
            packageName = params.getBehaviorPkg();
        }

        return packageName;
    }
}
