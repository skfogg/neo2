/**
 * 
 */
package org.neosimulation.neo.framework.populator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neosimulation.neo.framework.behcurr.Behavior;
import org.neosimulation.neo.framework.behcurr.Currency;
import org.neosimulation.neo.framework.database.DbMediator;
import org.neosimulation.neo.framework.database.Triple;
import org.neosimulation.neo.framework.dynam.Dynam;
import org.neosimulation.neo.framework.dynam.NegDynamDouble;
import org.neosimulation.neo.framework.dynam.NegDynamGeneric;
import org.neosimulation.neo.framework.dynam.NegDynamInt;
import org.neosimulation.neo.framework.dynam.NegDynamLong;
import org.neosimulation.neo.framework.dynam.NegFaceFluxDynamDouble;
import org.neosimulation.neo.framework.dynam.NegFaceFluxDynamGeneric;
import org.neosimulation.neo.framework.dynam.NegFaceFluxDynamInt;
import org.neosimulation.neo.framework.dynam.NegFaceFluxDynamLong;
import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Edge;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.holon.Holon;
import org.neosimulation.neo.framework.logging.NEOLogger;
import org.neosimulation.neo.framework.model.IUpdatable;
import org.neosimulation.neo.framework.model.SimulationModel;
import org.neosimulation.neo.framework.model.StateValContainerException;
import org.neosimulation.neo.framework.stateval.StateDouble;
import org.neosimulation.neo.framework.stateval.StateGeneric;
import org.neosimulation.neo.framework.stateval.StateInt;
import org.neosimulation.neo.framework.stateval.StateLong;
import org.neosimulation.neo.framework.stateval.StateVal;
import org.neosimulation.neo.user.AutoDynamDouble;
import org.neosimulation.neo.user.AutoDynamGeneric;
import org.neosimulation.neo.user.AutoDynamInt;
import org.neosimulation.neo.user.AutoDynamLong;
import org.neosimulation.neo.user.CellPoolDynamDouble;
import org.neosimulation.neo.user.CellPoolDynamInt;
import org.neosimulation.neo.user.CellPoolDynamLong;
import org.neosimulation.neo.user.FaceFluxDynam;
import org.neosimulation.neo.user.FaceFluxDynamDouble;
import org.neosimulation.neo.user.FaceFluxDynamGeneric;
import org.neosimulation.neo.user.FaceFluxDynamInt;
import org.neosimulation.neo.user.FaceFluxDynamLong;
import org.neosimulation.neo.user.InitDynamDouble;
import org.neosimulation.neo.user.InitDynamGeneric;
import org.neosimulation.neo.user.InitDynamInt;
import org.neosimulation.neo.user.InitDynamLong;
import org.neosimulation.neo.user.ManualDynamDouble;
import org.neosimulation.neo.user.ManualDynamInt;
import org.neosimulation.neo.user.ManualDynamLong;

/**
 * Populator - Contains the logic necessary to provide a single
 * StateValContainer the necessary internal objects to facilitate running a
 * Model. This class is responsible for installing necessary Dynams and
 * StateVals into a provided StateValContainer object. In essense it populates
 * the SVC with what it needs to be a functioning member of the model.
 * 
 * @author Isaac Griffith
 */
public abstract class Populator {

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
     * Static list of Dynam Class objects for use when determining which type of
     * dynam to install
     */
    @SuppressWarnings("rawtypes")
    private static List<Class> dynamClasses = new LinkedList<>();
    /**
     * Static list of Holon Class objects for use when determining types of
     * holons
     */
    @SuppressWarnings("rawtypes")
    private static List<Class> holonTypes = new LinkedList<>();

    // Static initialization of the dynamClasses List
    // This list is used to install the correct type of dynam into a StateVal
    static
    {
        dynamClasses.add(AutoDynamDouble.class);
        dynamClasses.add(ManualDynamDouble.class);
        dynamClasses.add(InitDynamDouble.class);
        dynamClasses.add(AutoDynamInt.class);
        dynamClasses.add(ManualDynamInt.class);
        dynamClasses.add(InitDynamInt.class);
        dynamClasses.add(InitDynamGeneric.class);
        dynamClasses.add(FaceFluxDynamDouble.class);
        dynamClasses.add(FaceFluxDynamInt.class);
        dynamClasses.add(CellPoolDynamDouble.class);
        dynamClasses.add(CellPoolDynamInt.class);
        dynamClasses.add(AutoDynamLong.class);
        dynamClasses.add(ManualDynamLong.class);
        dynamClasses.add(InitDynamLong.class);
        dynamClasses.add(FaceFluxDynamLong.class);
        dynamClasses.add(CellPoolDynamLong.class);

        // Sorts the list of dynams according to depth in the inheritance
        // hierarchy (deepest first)
        Collections.sort(dynamClasses, new InheritanceComparator());

        holonTypes.add(Face.class);
        holonTypes.add(Edge.class);
        holonTypes.add(Cell.class);
    }
    /**
     * List of IUpdatables that this populator generates
     */
    protected Set<IUpdatable> updatables;
    /**
     * List of Dynams that this populator generates
     */
    protected List<Dynam> installedDynams;
    /**
     * The associated model which is populated by this Populator
     */
    protected SimulationModel model;

    /**
     * Generates a new Populator object with the provided Matrix object
     * 
     * @param model
     *            The SimulationModel for which this populator is populating
     */
    public Populator(SimulationModel model)
    {
        updatables = new HashSet<>();
        installedDynams = new ArrayList<>();
        this.model = model;
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

    /**
     * Installs Dynams for a given behavior into the provided Holon. In order to
     * do this it determines if the requisite StateVal (with the same name as
     * the dynam) exists or if it needs to create it and then installs the
     * dynam. The list of dynams for the given behavior is provided by the
     * behaviorDynams HashMap.
     * 
     * @param cellBehaviorDynams
     *            HashMap<String, List<Class>> relating a behavior name to a
     *            list of Dynam Class objects for which are to be installed.
     * @param behavior
     *            Behavior to lookup in the behaviorDynams HashMap to install
     *            dynams into the provided holon
     * @param holon
     *            Holon object requiring Dynam installation (and possibly
     *            statevals) for the provided behavior
     * @throws IllegalAccessException
     *             Thrown if there is a problem reflectively accessing the dynam
     *             class
     * @throws InstantiationException
     *             Thrown if there is a problem reflectively instantiating a
     *             dynam that is to be installed
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void installDynams(Map<Currency, Map<Behavior, Set<Class>>> cellBehaviorDynams, Currency curr,
            Behavior behavior, Holon holon) throws IllegalAccessException, InstantiationException
    {
        if (checkForMissingBehavior(cellBehaviorDynams, behavior))
        {
            // TODO Add logging here
            return;
        }

        // Iterate through the inheritance hierarchy of Dynam until the correct
        // dynam type is found
        for (Class c : cellBehaviorDynams.get(curr).get(behavior))
        {
            // get the dynam with the same name from the holon under
            // question
            StateVal stateVal = null;
            try
            {
                stateVal = holon.getStateVal(c.getSimpleName());
            }
            catch (StateValContainerException svce)
            {
            }
            finally
            {
                createDynam(c, stateVal, behavior, holon, false);
            }

        }
    }

    /**
     * Constructs a new Dynam.
     * 
     * @param c
     *            Class object of the Dynam to be created.
     * @param stateVal
     *            StateVal to which the Dynam is to be attached to
     * @param behavior
     *            Behavior of the Dynam
     * @param holon
     *            Holon in which the StateVal, which this Dynam is to be
     *            attached to, is contained.
     * @param isNegDynam
     *            flag indicating whether this is a NegDynam or not.
     * @throws InstantiationException
     *             thrown if the Dynam cannot be instanciated from the provided
     *             class
     * @throws IllegalAccessException
     *             thrown if the Dynam's constructor method cannot be called.
     */
    protected void createDynam(Class c, StateVal stateVal, Behavior behavior, Holon holon, boolean isNegDynam)
            throws InstantiationException, IllegalAccessException
    {
        Dynam tempDynam = null;

        for (Class behaviorClass : dynamClasses)
        {
            // If behavior class inherits from Dynam then create a new
            // instance and attach it to the dynam
            if (behaviorClass.isAssignableFrom(c))
            {
                if (stateVal == null)
                {
                    stateVal = createStateVal(c, behavior, holon);
                    holon.addStateVal(stateVal);
                }

                if (isNegDynam)
                    tempDynam = createProperNegDynam(stateVal, behavior);
                else
                {
                    tempDynam = (Dynam) behaviorClass.cast(c.newInstance());
                }

                attachDynamsAndStateVals(tempDynam, stateVal, behavior);

                installedDynams.add(tempDynam);

                break;
            }
        }

        addStateValUpdaterToUpdatables(stateVal);
    }

    /**
     * Checks whether the provided behavior is know to the system or not.
     * 
     * @param behDynamMap
     *            Mapping of all known behaviors and the Dynam classes they
     *            represent
     * @param behavior
     *            Behavior in question
     * @return true if the behavior is unknown to the system, false otherwise.
     */
    protected boolean checkForMissingBehavior(Map<Currency, Map<Behavior, Set<Class>>> behDynamMap, Behavior behavior)
    {
        boolean retVal = false;
        if (!behDynamMap.containsKey(behavior.getCurrency())
                && !behDynamMap.get(behavior.getCurrency()).containsKey(behavior))
        {
            NEOLogger.logWarning(model, "Behavior: " + behavior + ", is not a known behavior name.");
            retVal = true;
        }

        return retVal;
    }

    /**
     * Connects the provided dynam and stateval together and associates the
     * provided dynam to the provided behavior.
     * 
     * @param dynam
     *            The Dynam
     * @param stateVal
     *            The StateVal
     * @param behavior
     *            The Behavior
     */
    protected void attachDynamsAndStateVals(Dynam dynam, StateVal stateVal, Behavior behavior)
    {
        stateVal.attach(dynam);
        dynam.attach(stateVal);
        dynam.setBehavior(behavior);
    }

    /**
     * Adds the provided StateVal's StateValUpdater to the list of known
     * StateValUpdaters.
     * 
     * @param stateVal
     *            The StateVal
     */
    protected void addStateValUpdaterToUpdatables(StateVal stateVal)
    {
        if (stateVal != null)
            updatables.add(stateVal.getUpdater());
    }

    /**
     * Method used to select the proper type of dynam to be constructed. (Used
     * for selecting the proper NegDynam type)
     * 
     * @param stateVal
     *            StateVal containing a comparable dynam type
     * @param class1
     *            Class of the first choice of Dynam to be installed
     * @param class2
     *            Class of the second choice of Dynam to be installed
     * @param dynam1
     *            Actual dynam for first choice
     * @param dynam2
     *            Actual dynam for second choice
     * @return Either the first choice dynam (if the dynam of the provided
     *         stateval is assignable from class1), the second choice dynam (if
     *         the dynam of the provided stateval is assignable from the second
     *         choice class), or null if neither is the case.
     */
    protected Dynam selectProperDynamType(StateVal stateVal, Class class1, Class class2, Dynam dynam1, Dynam dynam2)
    {
        Dynam retVal = null;
        if (class1.isAssignableFrom(stateVal.getDynam().getClass()))
            retVal = dynam1;
        else if (class2.isAssignableFrom(stateVal.getDynam().getClass()))
            retVal = dynam2;

        return retVal;
    }

    /**
     * Method used to create a stateval, based on the given populator strategy.
     * The stateval is then installed in the provided holon.
     * 
     * @param dynam
     *            Class of the Dynam to be used to determine which strategy type
     *            to instantiate and use to create statevals.
     * @param behavior
     *            Behavior associated with the stateval to be installed
     * @param holon
     *            Holon where the stateval is to be installed in.
     * @return The newly created StateVal
     */
    private StateVal createStateVal(Class dynam, Behavior behavior, Holon holon)
    {
        PopulatorStrategy strategy;
        StateVal stateVal;
        Class temp = dynam;
        // determine correct type of dynam (Double, Int, or Generic)
        while (!temp.getSimpleName().endsWith("Double") && !temp.getSimpleName().endsWith("Int")
                && !temp.getSimpleName().endsWith("Generic") && !temp.getSimpleName().endsWith("Long"))
            temp = temp.getSuperclass();

        if (temp.getSimpleName().endsWith("Double"))
        {
            strategy = new DoublePopulatorStrategy();
            stateVal = strategy.installStateVals(dynam, behavior, holon);
        }
        else if (temp.getSimpleName().endsWith("Int"))
        {
            strategy = new IntegerPopulatorStrategy();
            stateVal = strategy.installStateVals(dynam, behavior, holon);
        }
        else if (temp.getSimpleName().endsWith("Long"))
        {
            strategy = new LongPopulatorStrategy();
            stateVal = strategy.installStateVals(dynam, behavior, holon);
        }
        else
        {
            strategy = new GenericPopulatorStrategy();
            stateVal = strategy.installStateVals(dynam, behavior, holon);
        }

        return stateVal;
    }

    /**
     * Installs statevals (as provided by the behavior statevals map) for a
     * behavior in the provided Holon. The provided uniqueID should be the same
     * uniqueID as that of the provided Holon and is used to lookup the
     * statevals in the initial values table of the database.
     * 
     * @param uniqueID
     *            Id of the provided holon for looking up stateval initial
     *            values used to look up statevals from the behaviorStateVals
     *            map.
     * @param holon
     *            Holon object to which the statevals are to be installed
     * @param behavior
     *            behavior for which statevals are to be installed in the holon,
     *            and what is
     * @param manager
     *            The Database Manager who maintains the information from the
     *            database
     * @throws SQLException
     *             Thrown when a problem occurs when attempting to connect to or
     *             query from the database
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void installStateVals(String uniqueID, Holon holon, Behavior behavior, DbMediator manager,
            boolean isToFace, boolean isSymmetric)
    {
        String behaviorName = behavior.getName();
        String currencyName = behavior.getCurrency().getName();
        String tableName = getTableName(holon, behaviorName, isSymmetric);

        List<Triple<String, String, Object>> defaultStateValTriples = initializeDefaultStateValTriples(uniqueID,
                manager, holon, isToFace);
        List<Triple<String, String, Object>> initialStateValTriples = initializeInitialValueStateValTriples(uniqueID,
                tableName, manager);

        tableName = getTableName(holon, behaviorName, isSymmetric);

        // 1. Install Default Behavior StateVals
        for (Triple<String, String, Object> triple : defaultStateValTriples)
        {
            try
            {
                holon.getStateVal(triple.getName());
            }
            catch (StateValContainerException e)
            {
                installStateValFromTriple(currencyName, holon, triple);
            }
        }

        // 2. Install Initial StateVals (If already installed change the value)
        // for each Triple describing a stateval install it into the holon
        for (Triple<String, String, Object> triple : initialStateValTriples)
        {
            try
            {
                retrieveStateValAndSetValueFromTriple(holon, triple);
            }
            catch (StateValContainerException svce)
            {
                if (isToFace || (!isToFace && !isSymmetric))
                    installStateValFromTriple(currencyName, holon, triple);
            }
        }
    }

    /**
     * initialize the type and defSVTriples variables based on the type of holon
     * 
     * @param uniqueID
     *            Unique id of the holon into which these default triples will
     *            be installed to
     * @param manager
     *            DbMediator used to extract information from
     * @param holon
     *            Holon which will later contain the statevals
     * @return The list of Triples containing information from a default
     *         stateval table.
     */
    private List<Triple<String, String, Object>> initializeDefaultStateValTriples(String uniqueID, DbMediator manager,
            Holon holon, boolean isTo)
    {
        List<Triple<String, String, Object>> defaultStateValTriples = null;
        if (holon instanceof Cell)
        {
            String type = manager.getCellType(uniqueID);
            defaultStateValTriples = manager.getDefaultCellTriples(type);
        }
        else
        {
            String type = manager.getEdgeType(uniqueID);
            if (isTo)
                defaultStateValTriples = manager.getDefaultToEdgeTriples(type);
            else
                defaultStateValTriples = manager.getDefaultFromEdgeTriples(type);
        }

        if (defaultStateValTriples == null)
        {
            defaultStateValTriples = new ArrayList<Triple<String, String, Object>>();
        }

        return defaultStateValTriples;
    }

    /**
     * Generates a tablename used to extract initial values from based on the
     * holon name and the associated behavior name.
     * 
     * @param holon
     *            The Holon
     * @param behaviorName
     *            The name of the behavior
     * @return The table name, or null if no table name can be constructed.
     */
    private String getTableName(Holon holon, String behaviorName, boolean isSymmetric)
    {
        String tableName = null;

        for (Class holonType : holonTypes)
        {
            if (holonType.isInstance(holon))
            {
                if (holon instanceof Face && isSymmetric)
                {
                    tableName = behaviorName.replace('_', '.');
                    tableName = tableName.substring(0, tableName.indexOf(".")) + "_" + "edge" + "_"
                            + tableName.substring(tableName.indexOf(".") + 1);
                    tableName = tableName.replace('.', '_');
                }
                else
                {
                    tableName = behaviorName.replace('_', '.');
                    tableName = tableName.substring(0, tableName.indexOf(".")) + "_"
                            + holonType.getSimpleName().toLowerCase() + "_"
                            + tableName.substring(tableName.indexOf(".") + 1);
                    tableName = tableName.replace('.', '_');
                }
            }
        }

        return tableName;
    }

    /**
     * Creates a list of Triple objects representing the information needed to
     * create statevals. The list provided generates information from an initial
     * values table f
     * 
     * @param uniqueID
     *            unique id of the holon into which these statevals will be
     *            installed to
     * @param manager
     *            DbMediator used to access the database.
     * @return A list of initial value triples defining statevals to be
     *         installed in the model.
     */
    private List<Triple<String, String, Object>> initializeInitialValueStateValTriples(String uniqueID,
            String tableName, DbMediator manager)
    {
        List<Triple<String, String, Object>> initialStateValTriples = manager.getInitialValueTriples("init_"
                + tableName, uniqueID);

        if (initialStateValTriples == null)
        {
            initialStateValTriples = new ArrayList<Triple<String, String, Object>>();
        }

        return initialStateValTriples;
    }

    /**
     * Initializes and can even construct a state val associated with the
     * provided holon using information stored in the provided Triple.
     * 
     * @param holon
     *            Holon to contain the newly created StateVal
     * @param triple
     *            The triple containg the value of the stateval and information
     *            necessary to create a new stateval if no stateval by the
     *            contained name already exists in the provided holon.
     * @throws StateValContainerException
     *             Thrown if the provided holon does not contain a state val
     *             with the same name as stored in the triple.
     */
    private void retrieveStateValAndSetValueFromTriple(Holon holon, Triple<String, String, Object> triple)
            throws StateValContainerException
    {
        StateVal stateVal = holon.getStateVal(triple.getName());

        if (stateVal instanceof StateDouble)
        {
            StateDouble doubleVal = (StateDouble) stateVal;
            doubleVal.value = (Double) triple.getValue();
        }
        else if (stateVal instanceof StateInt)
        {
            StateInt intVal = (StateInt) stateVal;
            intVal.value = (Integer) triple.getValue();
        }
        else if (stateVal instanceof StateLong)
        {
            StateLong longVal = (StateLong) stateVal;
            longVal.value = (Long) triple.getValue();
        }
        else
        {
            StateGeneric<Object> genVal = (StateGeneric<Object>) stateVal;
            genVal.value = triple.getValue();
        }
    }

    /**
     * Installs statevals for the given resource name into the provided holon.
     * 
     * @param resourceName
     *            Name of the resource
     * @param holon
     *            Holon to install statevals in
     * @param triple
     *            Triple containing the necessary information to create the
     *            statevals.
     */
    private void installStateValFromTriple(String resourceName, Holon holon, Triple<String, String, Object> triple)
    {
        PopulatorStrategy strategy = null;

        if (triple.getType().equalsIgnoreCase("Double"))
        {
            strategy = new DoublePopulatorStrategy();
        }
        else if (triple.getType().equalsIgnoreCase("Integer") || triple.getType().equalsIgnoreCase("Int"))
        {
            strategy = new IntegerPopulatorStrategy();
        }
        else if (triple.getType().equalsIgnoreCase("Long") || triple.getType().equalsIgnoreCase("bigint"))
        {
            strategy = new LongPopulatorStrategy();
        }
        else
        {
            strategy = new GenericPopulatorStrategy();
        }

        strategy.installDefaultStateVals(updatables, resourceName, triple, holon);
    }

    /**
     * Provides the logic to install StateVals and Dynams into the Holon
     * specified by the uniqueID.
     * 
     * @param name
     *            ID of the holon to install statevals and dynams into
     * @param manager
     *            The DatabaseManager which provides all necessary info to
     *            instal statevals and dynams
     * @param bdi
     * @return The List of IUpdatables installed into the Holon with the
     *         specified uniqueID
     * @throws IOException
     *             if there is a problem looking up the statevals to be
     *             installed
     * @throws SQLException
     *             if there is a problem connecting to or querying the database
     * @throws IllegalAccessException
     *             if there is a problem accessing dynam classes reflectively
     * @throws InstantiationException
     *             if there is a problem instatiating dynam classes reflectively
     */
    public abstract Set<IUpdatable> installStateValsAndDynams(String name, DbMediator manager,
            BehaviorDynamIdentifier bdi) throws IOException, SQLException, IllegalAccessException,
            InstantiationException;

    /**
     * Returns the list of dynams installed by this populator
     * 
     * @return List of dynam objects that have been installed by this Populator
     */
    public List<Dynam> getInstalledDynams()
    {
        return installedDynams;
    }

    /**
     * Installs NegDynams for a given behavior into the provided Holon. In order
     * to do this it determines if the requisite StateVal (with the same name as
     * the dynam) exists or if it needs to create it and then installs the
     * dynam. The list of dynams for the given behavior is provided by the
     * behaviorDynams HashMap.
     * 
     * @param behDynamMap
     *            HashMap<String, List<Class>> relating a behavior name to a
     *            list of Dynam Class objects for which are to be installed.
     * @param behavior
     *            Behavior to lookup in the behaviorDynams HashMap to install
     *            dynams into the provided holon
     * @param holon
     *            Holon object requiring Dynam installation (and possibly
     *            statevals) for the provided behavior
     * @throws IllegalAccessException
     *             Thrown if there is a problem reflectively accessing the dynam
     *             class
     * @throws InstantiationException
     *             Thrown if there is a problem reflectively instantiating a
     *             dynam that is to be installed
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected void installNegDynams(Map<Currency, Map<Behavior, Set<Class>>> behDynamMap, Currency currency,
            Behavior behavior, Holon holon) throws IllegalAccessException, InstantiationException
    {
        if (checkForMissingBehavior(behDynamMap, behavior))
        {
            return;
        }

        // Iterate through the inheritance hierarchy of Dynam until the correct
        // dynam type is found
        for (Class c : behDynamMap.get(currency).get(behavior))
        {
            // get the dynam with the same name from the holon under
            // question
            StateVal stateVal = null;
            if (FaceFluxDynam.class.isAssignableFrom(c))
            {
                try
                {
                    stateVal = holon.getStateVal(c.getSimpleName());
                    Dynam tempDynam = createProperNegDynam(stateVal, behavior);
                    attachDynamsAndStateVals(tempDynam, stateVal, behavior);
                    addStateValUpdaterToUpdatables(stateVal);
                }
                catch (StateValContainerException svce)
                {
                    // need to instantiate the correct type of stateval as well as
                    // correct type of dynam
                    createDynam(c, null, behavior, holon, true);
                }
            }
        }
    }

    /**
     * Creates the proper type of NegDynam based on the type of StateVal
     * provided.
     * 
     * @param stateVal
     *            The StateVal
     * @param behavior
     *            The Behavior to which the NegDynam will belong
     * @return the NegDynam that will be created, or null if no such Dynam can
     *         be generated.
     */
    protected Dynam createProperNegDynam(StateVal stateVal, Behavior behavior)
    {
        Dynam tempDynam = null;

        try
        {
            StateVal other = ((Face) stateVal.getHolon()).getEdge().getToFace().getStateVal(stateVal.getName());

            if (other.getDynam() != null)
            {
                if (stateVal instanceof StateInt)
                {
                    tempDynam = selectProperDynamType(other, FaceFluxDynamInt.class, AutoDynamInt.class,
                            new NegFaceFluxDynamInt(), new NegDynamInt());
                }
                else if (stateVal instanceof StateDouble)
                {
                    tempDynam = selectProperDynamType(other, FaceFluxDynamDouble.class, AutoDynamDouble.class,
                            new NegFaceFluxDynamDouble(), new NegDynamDouble());
                }
                else if (stateVal instanceof StateLong)
                {
                    tempDynam = selectProperDynamType(other, FaceFluxDynamLong.class, AutoDynamLong.class,
                            new NegFaceFluxDynamLong(), new NegDynamLong());
                }
                else
                {
                    tempDynam = selectProperDynamType(other, FaceFluxDynamGeneric.class, AutoDynamGeneric.class,
                            new NegFaceFluxDynamGeneric(), new NegDynamGeneric());
                }
            }
        }
        catch (StateValContainerException e)
        {
            NEOLogger.logStateVal(model, this.getClass().getSimpleName(), "createProperNegDynam()", stateVal.getName());
        }
        return tempDynam;
    }
}
