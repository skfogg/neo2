/**
 * 
 */
package org.neosimulation.neo.framework.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.neosimulation.neo.framework.behcurr.Behavior;
import org.neosimulation.neo.framework.behcurr.Currency;
import org.neosimulation.neo.framework.behcurr.CurrencyManager;
import org.neosimulation.neo.framework.dynam.Dynam;
import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Edge;
import org.neosimulation.neo.framework.holon.Face;
import org.neosimulation.neo.framework.model.Matrix;
import org.neosimulation.neo.framework.model.Network;
import org.neosimulation.neo.framework.stateval.StateVal;

/**
 * RegExProcessor - A Singleton class used to process the output configurations
 * stored within the Output Configuration table of the NEO Input tables. Since
 * there are multiple regular expressions, this class provides methods to trim
 * the available information stored in the Matrix and CurrencyManager of a
 * SimulationModel in order to actively select the proper information to be sent
 * to the Output Database.
 * 
 * @author Isaac Griffith
 */
public class RegExProcessor {

    /**
     * The Singleton instance of this class
     */
    private static RegExProcessor instance;

    /**
     * The private constructor
     */
    private RegExProcessor()
    {
    }

    /**
     * @return The singleton instance of this class.
     */
    public static RegExProcessor getInstance()
    {
        if (instance == null)
        {
            instance = new RegExProcessor();
        }

        return instance;
    }

    /**
     * Method which actually handles processing the regular expression used to
     * trim the provided set of strings.
     * 
     * @param completeListing
     *            Set of strings which should be compared to the provided
     *            regular expression pattern.
     * @param regEx
     *            The compiled regular expression.
     * @return The trimmed down set of strings, from the provided complete set,
     *         representing all the strings which match the provided pattern.
     * @throws RegExProcessorException
     *             Thrown if the provided regular expression is correctly
     *             formatted as a Java regular expression.
     */
    private Set<String> processRegEx(Set<String> completeListing, Pattern regEx) throws RegExProcessorException
    {
        if (regEx == null)
        {
            throw new RegExProcessorException("regular expression is null.");
        }

        Set<String> processedList = new HashSet<>();

        for (String value : completeListing)
        {
            Matcher matcher = regEx.matcher(value);
            try
            {
                if (matcher.matches())
                {
                    processedList.add(value);
                }
            }
            catch (PatternSyntaxException pse)
            {
                throw new RegExProcessorException("regular expression: " + regEx.pattern()
                        + ", does not conform to the syntax of Java regular expressions.");
            }
        }

        return processedList;
    }

    /**
     * Processes the available set of all behaviors associated with holons in
     * the matrix by comparing them to a provided behavior limiting regular
     * expression. If the forEdge flag is set this method will compare edge
     * behaviors against the provided pattern, otherwise it compares cell
     * behaviors to the regular expression.
     * 
     * @param behRegEx
     *            The compiled behavior regular expression.
     * @param matrix
     *            The matrix object to search for behaviors in.
     * @param forEdge
     *            Flag determining whether to deal with cell or edge behaviors.
     * @return The set of all behavior names which match the provided pattern.
     * @throws RegExProcessorException
     *             thrwon if the provided behavior regular expression is not
     *             syntactically correct
     */
    public Set<String> trimForBehaviors(Pattern behRegEx, Matrix matrix, boolean forEdge)
            throws RegExProcessorException
    {
        Set<String> retVal = new HashSet<>();
        Set<String> toProcess = new HashSet<>();
        if (forEdge)
        {
            for (Behavior beh : matrix.getAllFromBehaviors())
            {
                toProcess.add(beh.getName());
            }
            for (Behavior beh : matrix.getAllToBehaviors())
            {
                toProcess.add(beh.getName());
            }
        }
        else
        {
            for (Behavior beh : matrix.getAllCellBehaviors())
            {
                toProcess.add(beh.getName());
            }
        }

        try
        {
            retVal = processRegEx(toProcess, behRegEx);
        }
        catch (RegExProcessorException e)
        {
            throw new RegExProcessorException("Behavior Name " + e.getMessage());
        }

        return retVal;
    }

    /**
     * Processes the available set of all currencies in the provided currency
     * manager by comparing them to a provided behavior limiting regular
     * expression.
     * 
     * @param currRegEx
     *            The compiled currency regular expression.
     * @param manager
     *            The currency manager to extract currency names from.
     * @return The set of currency names which match the provided regular
     *         expression.
     * @throws RegExProcessorException
     *             thrown if the currency name regex is not syntactically
     *             correct.
     */
    public Set<String> trimForCurrencies(Pattern currRegEx, CurrencyManager manager) throws RegExProcessorException
    {
        Set<String> retVal = new HashSet<>();
        List<Currency> currencies = manager.getCurrencies();
        Set<String> toProcess = new HashSet<>();
        for (Currency currency : currencies)
        {
            toProcess.add(currency.getName());
        }

        try
        {
            retVal = processRegEx(toProcess, currRegEx);
        }
        catch (RegExProcessorException e)
        {
            throw new RegExProcessorException("Currency Name " + e.getMessage());
        }

        return retVal;
    }

    /**
     * Processes the available set of all holon types associated with holons in
     * the matrix by comparing them to a provided holon type limiting regular
     * expression. If the forEdge flag is set this method will compare edge
     * types against the provided pattern, otherwise it compares cell types to
     * the regular expression.
     * 
     * @param typeRegEx
     *            The compile holon type regular expression
     * @param matrix
     *            The matrix object to search for holon types in.
     * @param forEdge
     *            Flag determining whether to deal with cell or edge types.
     * @return The set of all holon type names which match the provided pattern.
     * @throws RegExProcessorException
     *             thrown if the provided holon type regex is not syntactically
     *             correct
     */
    public Set<String> trimForHolonType(Pattern typeRegEx, Matrix matrix, boolean forEdge)
            throws RegExProcessorException
    {
        Set<String> retVal = new HashSet<>();
        Set<String> toProcess = new HashSet<>();
        if (forEdge)
        {
            toProcess.addAll(matrix.getAllEdgeTypes());
        }
        else
        {
            toProcess.addAll(matrix.getAllCellTypes());
        }

        try
        {
            retVal = processRegEx(toProcess, typeRegEx);
        }
        catch (RegExProcessorException e)
        {
            throw new RegExProcessorException("Holon Type " + e.getMessage());
        }

        return retVal;
    }

    /**
     * Processes the available set of all names associated with holons in the
     * matrix by comparing them to a provided holon name limiting regular
     * expression. Since holon names are unique this method compiles a list of
     * all edge and cell names that match the provided regular expression.
     * 
     * @param nameRegEx
     *            Compiled regular expression pattern for Holon Names
     * @param matrix
     *            The matrix object to search for holon names in.
     * @return The set of all holon names which match the provided pattern.
     * @throws RegExProcessorException
     *             thrown if the provided holon name regex is not syntactically
     *             correct
     */
    public Set<String> trimForHolonName(Pattern nameRegEx, Matrix matrix) throws RegExProcessorException
    {
        Set<String> retVal = new HashSet<>();
        Set<String> toProcess = new HashSet<>();

        toProcess.addAll(matrix.getCellIDs());
        for (String name : matrix.getEdgeIDs())
        {
            toProcess.add(name + "-to");
            toProcess.add(name + "-from");
        }

        try
        {
            retVal = processRegEx(toProcess, nameRegEx);
        }
        catch (RegExProcessorException e)
        {
            throw new RegExProcessorException("Holon Name " + e.getMessage());
        }

        return retVal;
    }

    /**
     * Processes the available set of all names associated with statevals
     * contained within holons of the matrix by comparing them to a provided
     * stateval name limiting regular expression. Since stateval names are
     * unique within a given holon this set will only contain the set of
     * available stateval names and must be used in conjunction with things like
     * limiting holons by name in order to be most effective.
     * 
     * @param stateValRegEx
     *            The stateval name limiting regular expression
     * @param matrix
     *            The matrix object to search for statevals in
     * @return The set of all stateval names from within the matrix which match
     *         the provided regular expression pattern
     * @throws RegExProcessorException
     *             thrown if the provided stateval name regex is not
     *             syntacticaly correct.
     */
    public Set<String> trimForStateValNames(Pattern stateValRegEx, Matrix matrix) throws RegExProcessorException
    {
        Set<String> retVal = new HashSet<>();
        Set<String> toProcess = new HashSet<>();

        for (String cellID : matrix.getCellIDs())
        {
            Cell cell = matrix.getCell(cellID);
            for (StateVal val : cell.getStateVals())
            {
                toProcess.add(val.getName());
            }
        }

        for (String edgeID : matrix.getEdgeIDs())
        {
            Edge edge = matrix.getEdge(edgeID);
            Face toFace = edge.getToFace();
            Face fromFace = edge.getFromFace();

            if (toFace != null)
            {
                for (StateVal val : toFace.getStateVals())
                {
                    toProcess.add(val.getName());
                }
            }

            if (fromFace != null)
            {
                for (StateVal val : fromFace.getStateVals())
                {
                    toProcess.add(val.getName());
                }
            }
        }

        try
        {
            retVal = processRegEx(toProcess, stateValRegEx);
        }
        catch (RegExProcessorException e)
        {
            throw new RegExProcessorException("StateVal Name " + e.getMessage());
        }

        return retVal;
    }

    /**
     * Processes the available set of all names associated with dynams contained
     * within the network by comparing them to a provided dynam name limiting
     * regular expression. Since dynam names are unique within a given holon
     * this set will only contain the set of available dynam names and must be
     * used in conjunction with things like limiting holons by name in order to
     * be most effective.
     * 
     * @param dynamRegEx
     *            the dynam name limiting regular expression
     * @param network
     *            the network containing the list of dynams
     * @return Set of viable dynam names as limited by the regular expression
     * @throws RegExProcessorException
     *             thrown if the provided dynam name regex is not syntacticaly
     *             correct.
     */
    public Set<String> trimForDynams(Pattern dynamRegEx, Network network) throws RegExProcessorException
    {
        Set<String> retVal = new HashSet<>();
        Set<String> toProcess = new HashSet<>();

        for (Dynam dynam : network.getDynams())
        {
            toProcess.add(dynam.getClass().getSimpleName());
        }

        try
        {
            retVal = processRegEx(toProcess, dynamRegEx);
        }
        catch (RegExProcessorException e)
        {
            throw new RegExProcessorException("Dynam Name " + e.getMessage());
        }

        return retVal;
    }
}
