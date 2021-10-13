/**
 * 
 */
package org.neosimulation.neo.framework.populator;

import java.io.File;
import java.util.List;

import org.neosimulation.neo.framework.behcurr.Currency;
import org.neosimulation.neo.framework.model.SimulationModel;

/**
 * DynamLoaderParams - A parameter object which encapsulates all the information
 * necessary to control the operation of a DynamLoadingStrategy implementation.
 * 
 * @author Isaac Griffith
 */
public class DynamLoaderParams {

    /**
     * The currency containing the information necessary to find the dynam
     * classes to be loaded.
     */
    private Currency currency;
    /**
     * The file that is the base location where the class files are stored.
     */
    private File filename;
    /**
     * The name of the package for the behavior from which classes are to be
     * loaded.
     */
    private String behaviorPkg;
    /**
     * The name of the deviant package from which classes are to be loaded.
     */
    private String deviantPkg;
    /**
     * Name of the package
     */
    private String pkgName;
    /**
     * Flag indicating whether a deviant behavior is to be used or not.
     */
    private boolean deviant;
    /**
     * The associated simulation model to be populated
     */
    private SimulationModel model;
    private String baseBehaviorPkg;

    /**
     * Constructs a new DynamLoaderParams instance.
     * 
     * @param model
     *            The SimulationModel to be populated
     * @param dynams
     *            the dynam classes that are loaded by a DynamLoadingStrategy
     *            using this parameter object.
     * @param currency
     *            The currency containing the information necessary to find the
     *            dynam classes to be loaded.
     * @param filename
     *            The file that is the base location where the class files are
     *            stored.
     * @param behaviorPkg
     *            The name of the package for the behavior from which classes
     *            are to be loaded.
     * @param deviantPkg
     *            The name of the deviant package from which classes are to be
     *            loaded.
     * @param pkgName
     *            Name of the package
     * @param deviant
     *            Flag indicating whether a deviant behavior is to be used or
     *            not.
     */
    public DynamLoaderParams(SimulationModel model, Currency currency, File filename, String baseBehaviorPkg,
            String behaviorPkg, String deviantPkg, String pkgName, boolean deviant)
    {
        this.model = model;
        this.currency = currency;
        this.filename = filename;
        this.behaviorPkg = behaviorPkg;
        this.deviantPkg = deviantPkg;
        this.pkgName = pkgName;
        this.deviant = deviant;
        this.baseBehaviorPkg = baseBehaviorPkg;
    }

    /**
     * Retrieves the Currency
     * 
     * @return the currency associated with dynam classes to be loaded.
     */
    public Currency getCurrency()
    {
        return currency;
    }

    /**
     * Sets the currency for which dynam classes are to be loaded
     * 
     * @param currency
     *            the currency
     */
    public void setCurrency(Currency currency)
    {
        this.currency = currency;
    }

    /**
     * Retrieves the file name of the base location for dynam classes
     * 
     * @return the filename of the base location where the classes are stored
     */
    public File getFilename()
    {
        return filename;
    }

    /**
     * Sets the filename of the base location where classes are stored
     * 
     * @param filename
     *            the filename to set
     */
    public void setFilename(File filename)
    {
        this.filename = filename;
    }

    /**
     * Retrieves the name of the package for the behavior comprising the dynams
     * to be loaded
     * 
     * @return the behavior package name from which dynam classes will be
     *         loaded.
     */
    public String getBehaviorPkg()
    {
        return behaviorPkg;
    }

    /**
     * Sets the package name for the behavior from which dynam class files will
     * be loaded.
     * 
     * @param behaviorPkg
     *            the name of the package for the behavior from which dynams
     *            will be loaded.
     */
    public void setBehaviorPkg(String behaviorPkg)
    {
        this.behaviorPkg = behaviorPkg;
    }

    /**
     * Retrieves the Deviant package name that will be search when the deviant
     * flag is set
     * 
     * @return the name of the deviant package that will be searched if the
     *         deviant flag is set.
     */
    public String getDeviantPkg()
    {
        return deviantPkg;
    }

    /**
     * Sets the name of a deviant package that will be search. This is only used
     * if the deviant flag has been set.
     * 
     * @param deviantPkg
     *            the name of the deviant package to use
     */
    public void setDeviantPkg(String deviantPkg)
    {
        this.deviantPkg = deviantPkg;
    }

    /**
     * Retrieves the package name that will be searched
     * 
     * @return the package name that will be search by a DynamLoadingStrategy
     */
    public String getPkgName()
    {
        return pkgName;
    }

    /**
     * Sets the package name in which to look for dynams to be loaded.
     * 
     * @param pkgName
     *            the package name to use for the search
     */
    public void setPkgName(String pkgName)
    {
        this.pkgName = pkgName;
    }

    /**
     * Tests whether the dynams to be loaded are from a deviant behavior.
     * 
     * @return true if loading deviant dynam classes
     */
    public boolean isDeviant()
    {
        return deviant;
    }

    /**
     * Sets the flag to determine if the loader is to load a deviant behavior's
     * classes
     * 
     * @param deviant
     *            true to load for a deviant, false otherwise.
     */
    public void setDeviant(boolean deviant)
    {
        this.deviant = deviant;
    }

    /**
     * Retrieves the SimulationModel to be used by this DynamLoadingStrategy
     * which uses this paremeter object.
     * 
     * @return the SimulationModel
     */
    public SimulationModel getModel()
    {
        return model;
    }

    /**
     * Sets the associated SimulationModel to be used by the
     * DynamLoadingStrategy using this Parameter Object.
     * 
     * @param model
     *            the SimulationModel
     */
    public void setModel(SimulationModel model)
    {
        this.model = model;
    }

    /**
     * @return
     */
    public String getBaseBehaviorPkg()
    {
        return baseBehaviorPkg;
    }
}
