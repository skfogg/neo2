/**
 * 
 */
package org.neosimulation.neo.framework.populator;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.neosimulation.neo.framework.logging.NEOLogger;

/**
 * JarDynamLoadingStrategy - An implementation of DynamLoadingStrategy with the
 * specifically designed to load dynam classes from a jar location.
 * 
 * @author Isaac Griffith
 */
public class JarDynamLoadingStrategy extends DynamLoadingStrategy {

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.DynamLoadingStrategy#load()
     */
    @Override
    public Set<Class> load(DynamLoaderParams params)
    {
        Set<Class> classes = new HashSet<>();
        String jarFileName = params.getFilename().getAbsolutePath();
        String behPkg = params.getBehaviorPkg();
        while (behPkg.contains("."))
        {
            behPkg = behPkg.replace('.', '/');
        }
        String basePkg = params.getBaseBehaviorPkg();
        while (basePkg.contains("."))
        {
            basePkg = basePkg.replace('.', '/');
        }

        Map<String, String> classLocMap = new HashMap<>();

        try (JarFile jar = new JarFile(jarFileName))
        {
            Enumeration<JarEntry> entries = jar.entries();
            boolean found = true;
            while (entries.hasMoreElements() && !found)
            {
                JarEntry entry = entries.nextElement();
                if (entry.getName().contains(behPkg))
                    found = true;
            }

            if (!found)
                throw new JarDynamLoadingStrategyException("Package: " + basePkg + " cannot be found in jar file: "
                        + jarFileName);
        }
        catch (IOException e)
        {
            NEOLogger.logWarning(params.getModel(), "Cannot find or read from the jar file: " + jarFileName);
        }
        catch (JarDynamLoadingStrategyException e)
        {
            NEOLogger.logException(params.getModel(), e.getMessage(), e);
        }

        recLoad(classLocMap, basePkg, behPkg, jarFileName, params);

        for (String simpleName : classLocMap.keySet())
        {
            addClass(classLocMap.get(simpleName), classes, params.getCurrency().getClassLoader(), params.getModel());
        }

        return classes;
    }

    /**
     * Recursive loading method. Basically this method starts at the most
     * deviant package and works its way up to the base package. Adding new
     * Dynam names to the provided classLocMap object. That way, if a dynam has
     * already been added (in a deviant package) then we will not add the class
     * to the map, otherwise we will.
     * 
     * @param classLocMap
     *            Map relating simple class names (dynam names) to fully
     *            specified class names
     * @param basePkg
     *            base behavior package towards which this method works
     * @param behPkg
     *            current behavior package
     * @param jarFileName
     *            Jar file to search
     * @param params
     *            DynamLoaderParams object used for access to the
     *            SimulationModel object (for logging purposes)
     */
    private void recLoad(Map<String, String> classLocMap, String behPkg, String basePkg, String jarFileName,
            DynamLoaderParams params)
    {
        try (JarFile jar = new JarFile(jarFileName))
        {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements())
            {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();

                if (name.startsWith(behPkg) && name.endsWith(".class"))
                {
                    if (!name.replace(behPkg + "/", "").contains("/"))
                    {
                        String simpleName = name.replace(behPkg + "/", "");
                        String fullName = name;
                        while (fullName.contains("/"))
                        {
                            fullName = fullName.replace('/', '.');
                        }
                        simpleName = simpleName.substring(0, simpleName.lastIndexOf("."));
                        fullName = fullName.substring(0, fullName.lastIndexOf("."));
                        
                        if (!classLocMap.containsKey(simpleName))
                        {
                            classLocMap.put(simpleName, fullName);
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            NEOLogger.logWarning(params.getModel(), "Cannot find or read from the jar file: " + jarFileName);
        }

        if (!behPkg.equals(basePkg))
            recLoad(classLocMap, basePkg, behPkg.substring(0, behPkg.lastIndexOf("/")), jarFileName, params);
    }

    /**
     * Using the initial behavior package this method searches the jar file for
     * the base package of the behavior. It does this by recursively removing a
     * folder name from the provided behavior package name and searching for
     * classes within that folder. If no classes are found (i.e., only folders
     * are found) then we know we went one folder too far and the previous
     * folder was the base behavor package. This allows us to have deeply nested
     * deviant structures. Note that if the base package and the initial package
     * are the same, it will find it with only one recursive call.
     * 
     * @param behPkg
     *            current behavior package to search through (initial behavior
     *            package is the value of this parameter on the initiating call
     *            of this method)
     * @param jarFileName
     *            file location for the jar file to be searched through.
     * @param params
     *            DynamLoaderParams used to acquire the model object (for
     *            logging purposes)
     * @return Base behavior package for the provided (initial) behavior package
     *         in the jar file at jarFileName. Note that it will return null if
     *         access to the jar file is unavailable (read permissions or file
     *         existence)
     */
    private String findBasePkg(String behPkg, String jarFileName, DynamLoaderParams params)
    {
        try (JarFile jar = new JarFile(jarFileName))
        {
            Enumeration<JarEntry> entries = jar.entries();

            boolean classFound = false;
            while (entries.hasMoreElements() && !classFound)
            {
                JarEntry entry = entries.nextElement();

                String name = entry.getName();
                if (name.startsWith(behPkg))
                {
                    if (name.replace(behPkg, "").contains("/"))
                        continue;
                    else if (name.endsWith(".class"))
                        classFound = true;
                }
            }

            if (classFound)
            {
                return findBasePkg(behPkg.substring(0, behPkg.lastIndexOf("/")), jarFileName, params);
            }
            else
            {
                return behPkg;
            }
        }
        catch (IOException e)
        {
            NEOLogger.logWarning(params.getModel(), "Cannot find or read from the jar file: " + jarFileName);
            return null;
        }
    }
}
