/**
 * 
 */
package org.neosimulation.neo.framework.populator;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.neosimulation.neo.framework.behcurr.Behavior;
import org.neosimulation.neo.framework.behcurr.BehaviorPair;
import org.neosimulation.neo.framework.behcurr.Currency;
import org.neosimulation.neo.framework.database.DbMediator;
import org.neosimulation.neo.framework.model.SimulationModel;

/**
 * @author Isaac
 */
public class BehaviorDynamIdentifier {

    /**
     * String to determine where in the classpath to load dynams from
     */
    protected static final String CELL_TYPE = "cell";
    /**
     * String to determine where in the classpath to load dynams from
     */
    protected static final String FACE_TYPE = "face";
    /**
     * String to determine where in the classpath to load dynams from
     */
    protected static final String EDGE_TYPE = "edge";
    /**
     * The simulation model
     */
    private SimulationModel model;
    /**
     * Map relating Behaviors to the Class of Dynams they require to instantiate
     */
    @SuppressWarnings("rawtypes")
    protected Map<Currency, Map<Behavior, Set<Class>>> cellBehaviorDynams;
    /**
     * Map relating a behavior name to the classes to be instantiated as Dynams
     * for symmetric edges
     */
    @SuppressWarnings("rawtypes")
    protected Map<Currency, Map<Behavior, Set<Class>>> edgeSymBehaviorDynams;
    /**
     * Map relating a behavior name to the classes to be instantiated as Dynams
     * for asymmetric edges
     */
    @SuppressWarnings("rawtypes")
    protected Map<Currency, Map<Behavior, Set<Class>>> edgeAsymBehaviorDynams;
    /**
     * Map relating a behavior name to the classes to be instantiated as Dynams
     * for boundary edges
     */
    @SuppressWarnings("rawtypes")
    protected Map<Currency, Map<Behavior, Set<Class>>> edgeBoundBehaviorDynams;

    public BehaviorDynamIdentifier(SimulationModel model)
    {
        this.model = model;
        edgeSymBehaviorDynams = new HashMap<>();
        edgeAsymBehaviorDynams = new HashMap<>();
        edgeBoundBehaviorDynams = new HashMap<>();
        cellBehaviorDynams = new HashMap<>();
    }

    private Map<Currency, Map<Behavior, Set<Class>>> createDynamMap(String type, Set<Behavior> behaviors)
    {
        Map<Currency, Map<Behavior, Set<Class>>> behDynMap = new HashMap<>();

        for (Behavior beh : behaviors)
        {
            Currency curr = beh.getCurrency();

            if (behDynMap.containsKey(curr))
            {
                Map<Behavior, Set<Class>> behClassMap = behDynMap.get(curr);
                if (behClassMap.containsKey(beh))
                {
                    Set<Class> set = behClassMap.get(beh);
                    set.addAll(getDynamClasses(curr, beh, type));
                }
                else
                {
                    behClassMap.put(beh, getDynamClasses(curr, beh, type));
                }
            }
            else
            {
                Map<Behavior, Set<Class>> behClassMap = new HashMap<>();
                behClassMap.put(beh, getDynamClasses(curr, beh, type));
                behDynMap.put(curr, behClassMap);
            }
        }

        return behDynMap;
    }

    /**
     * Returns a list of Class objects (using reflection) for the dynams to be
     * instantiated and installed for a particular holon behavior.<br>
     * <br>
     * PreConditions: pkgName is specified as a complete package name down to
     * the holon type behavior is a valid behavior type as specified in the
     * package. <br>
     * <br>
     * PostConditions: the returned list is all the class types of the dynams to
     * be installed in the holon
     * 
     * @param currency
     *            The Currency object for which behaviors are to be installed.
     * @param behavior
     *            The specific behavior to implement
     * @param type
     *            The type of StateVal and Dynam to intall (Double, Integer,
     *            Generic)
     * @return The list of dynams class types for a given resource package
     *         behavior
     */
    @SuppressWarnings("rawtypes")
    protected Set<Class> getDynamClasses(Currency currency, Behavior behavior, String type)
    {
        Set<Class> dynams = new HashSet<>();
        DynamLoadingStrategy loader = null;

        // Generate the correct package name based on the base package
        // location
        // and the behavior and type(edge, face, cell) of holon
        String pkgName = convertPackageName(currency.getBasePackage(), type, behavior.getName());
        String basePkgName = pkgName;

        // Need to determine if the behavior is a deviant behavior
        String behaviorPkg = pkgName;
        String deviantPkg = pkgName;
        boolean deviant = isPackageADeviant(behavior.getName());

        if (deviant)
        {
            int index = deviantPkg.lastIndexOf(".");
            behaviorPkg = deviantPkg.substring(0, index);
        }

        File filename = new File(currency.getLocation().replace('\\', File.separatorChar));
        DynamLoaderParams params = new DynamLoaderParams(model, currency, filename, basePkgName, behaviorPkg,
                deviantPkg, pkgName, deviant);

        if (filename.exists())
        {
            // Generate the names of the classes necessary for the behavior
            loader = new JarDynamLoadingStrategy();
            if (filename.isDirectory())
            {
                loader = new DirectoryDynamLoadingStrategy();
            }

            dynams.addAll(loader.load(params));
        }

        return dynams;
    }

    /**
     * Checks whether the provided package name is a deviant, that is whether
     * the provided package name has two or more periods.
     * 
     * @param pkgName
     *            the name of the package to be checked
     * @return true if the package name has been found to be a deviant
     */
    private boolean isPackageADeviant(String pkgName)
    {
        return (pkgName.indexOf(".") < pkgName.lastIndexOf("."));
    }

    /**
     * Converts a given basePkg name, holon type, and behavior name into a fully
     * qualified package name for use with the installation of dynams
     * 
     * @param basePkg
     *            The base package name found in the Control table for a given
     *            resource
     * @param holonType
     *            Holon type (cell, edge, face) for which behavior dynam class
     *            names is required
     * @param behavior
     *            Behavior for which dynams are to be installed
     * @return The converted fully qualified package name representing the
     *         specific behavior for a holon type in the resource package
     */
    private String convertPackageName(String basePkg, String holonType, String behavior)
    {
        String pkgName = "";

        String temp = behavior.substring(behavior.indexOf(".") + 1);
        temp = holonType.toLowerCase() + "." + temp;
        pkgName = basePkg + "." + temp;

        return pkgName;
    }

    public void lookupEdgeDynams(DbMediator mediator)
    {
        for (String edgeID : mediator.getEdgeIDs())
        {
            for (BehaviorPair pair : mediator.getEdgeBehaviors(mediator.getEdgeType(edgeID)))
            {
                Behavior toBehavior = pair.getToBehavior();
                Behavior fromBehavior = pair.getFromBehavior();
                if (toBehavior.equals(fromBehavior))
                {
                    // Symmetric DependencyEdge
                    lookupDynams(edgeSymBehaviorDynams, toBehavior, EDGE_TYPE);
                }
                else if (!toBehavior.equals(fromBehavior) && fromBehavior != null)
                {
                    // Asymmetric or Transformation DependencyEdge
                    lookupDynams(edgeAsymBehaviorDynams, toBehavior, FACE_TYPE);
                    lookupDynams(edgeAsymBehaviorDynams, fromBehavior, FACE_TYPE);
                }
                else if (toBehavior != null && fromBehavior == null)
                {
                    // Boundary DependencyEdge
                    lookupDynams(edgeBoundBehaviorDynams, toBehavior, FACE_TYPE);
                }
            }
        }
    }

    public void lookupCellDynams(DbMediator mediator)
    {
        for (String cellID : mediator.getCellIDs())
        {
            for (Behavior beh : mediator.getCellBehaviors(mediator.getCellType(cellID)))
            {
                lookupDynams(cellBehaviorDynams, beh, CELL_TYPE);
            }
        }
    }

    public void lookupDynams(Map<Currency, Map<Behavior, Set<Class>>> map, Behavior beh, String type)
    {
        Currency curr = beh.getCurrency();

        if (map.containsKey(curr))
        {
            Map<Behavior, Set<Class>> behClassMap = map.get(curr);
            if (behClassMap.containsKey(beh))
            {
                Set<Class> set = behClassMap.get(beh);
                set.addAll(getDynamClasses(curr, beh, type));
            }
            else
            {
                behClassMap.put(beh, getDynamClasses(curr, beh, type));
            }
        }
        else
        {
            Map<Behavior, Set<Class>> behClassMap = new HashMap<>();
            behClassMap.put(beh, getDynamClasses(curr, beh, type));
            map.put(curr, behClassMap);
        }
    }

    /**
     * @return
     */
    public Map<Currency, Map<Behavior, Set<Class>>> getCellBehaviorMap()
    {
        // TODO Auto-generated method stub
        return cellBehaviorDynams;
    }

    /**
     * @return
     */
    public Map<Currency, Map<Behavior, Set<Class>>> getSymEdgeBehaviorMap()
    {
        // TODO Auto-generated method stub
        return edgeSymBehaviorDynams;
    }

    /**
     * @return
     */
    public Map<Currency, Map<Behavior, Set<Class>>> getAsymEdgeBehaviorMap()
    {
        // TODO Auto-generated method stub
        return edgeAsymBehaviorDynams;
    }

    /**
     * @return
     */
    public Map<Currency, Map<Behavior, Set<Class>>> getBoundEdgeBehaviorMap()
    {
        // TODO Auto-generated method stub
        return edgeBoundBehaviorDynams;
    }
}
