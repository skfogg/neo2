/**
 * 
 */
package org.neosimulation.neo.framework.populator;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * DirectoryDynamLoadingStrategy - An implementation of DynamLoadingStrategy
 * with the specifically designed to load dynam classes from a directory
 * location.
 * 
 * @author Isaac Griffith
 */
public class DirectoryDynamLoadingStrategy extends DynamLoadingStrategy {

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.DynamLoadingStrategy#load(org.neosimulation
     * .neo.framework.DynamLoaderParams)
     */
    @Override
    public Set<Class> load(DynamLoaderParams params)
    {
        Set<Class> classes = new HashSet<>();
        String path = params.getFilename().getAbsolutePath();
        String behPath = params.getBaseBehaviorPkg().replace('.', File.separatorChar);
        String basePath = params.getBehaviorPkg().replace('.', File.separatorChar);

        Map<String, String> classLocMap = new HashMap<>();

        recLoad(classLocMap, path, basePath, behPath);

        for (String simpleName : classLocMap.keySet())
        {
            addClass(classLocMap.get(simpleName), classes, params.getCurrency().getClassLoader(), params.getModel());
        }

        return classes;
    }

    private void recLoad(Map<String, String> classLocMap, String relPath, String basePath, String behPath)
    {
        File behaviorDir = new File(relPath + File.separator + behPath);
        if (behaviorDir.isDirectory())
        {
            File[] files = behaviorDir.listFiles(new FilenameFilter() {

                @Override
                public boolean accept(File dir, String name)
                {
                    return name.endsWith(".class");
                }
            });

            for (File entry : files)
            {
                String className = entry.getAbsolutePath().substring(entry.getAbsolutePath().indexOf(behPath));

                while (className.contains(File.separator))
                    className = className.replace(File.separatorChar, '.');

                className = className.substring(0, className.length() - 6);
                String simpleName = className.substring(className.lastIndexOf(".") + 1);

                if (!classLocMap.containsKey(simpleName))
                    classLocMap.put(simpleName, className);
            }

            if (!behPath.equals(basePath))
            {
                recLoad(classLocMap, relPath, basePath, behPath.substring(0, behPath.lastIndexOf(File.separatorChar)));
            }
        }
    }
}
