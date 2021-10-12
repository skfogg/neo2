/**
 * 
 */
package org.neosimulation.neo.framework.config;

import org.neosimulation.neo.framework.output.BasicOutputterFactory;
import org.neosimulation.neo.framework.output.Outputter;
import org.neosimulation.neo.framework.output.OutputterFactory;
import org.neosimulation.neo.framework.output.ParallelOutputterFactory;
import org.neosimulation.neo.framework.output.SingleOutputterFactory;

/**
 * ModelConfig - An Object encapsulating information required to create
 * simulation models and their associated outputters.
 * 
 * @author Isaac Griffith
 */
public class ModelConfig {

    /**
     * OutputterType - An enumeration defining a set of choices for the
     * outputter to be associated with the model described in the ModelConfig
     * object.
     * 
     * @author Isaac Griffith
     */
    private enum OutputterType {
        /**
         * A Single threaded outputter running in the main thread
         */
        basic,

        /**
         * A Single threaded outputter running in its own thread
         */
        single,

        /**
         * A multi-threaded outputter running in separate threads
         */
        parallel;
    }

    /**
     * Flag to determine if console output is required.
     */
    private boolean useLocal;
    /**
     * Flag to determine if this model config is to be used.
     */
    private boolean active;
    /**
     * The unique prefix of the model run name (forms the first third of the
     * model name)
     */
    private String modelPrefix;
    /**
     * The factory used to create the outputter for the moel.
     */
    private OutputterFactory factory;
    /**
     * The actual outputter of the model.
     */
    private Outputter outputter;
    /**
     * The DbConfig describing the means to open a connection to the output
     * database.
     */
    private DbConfig outputConfig;
    /**
     * Name of the output DbConfig to be used with this ModelConfig
     */
    private String outputConfigName;
    /**
     * flag indicating whether output tables associated with SimulationModel
     * object created using this ModelConfig should provide a time_date stamp as
     * a part of the name to ensure the uniqueness of the name, and that no data
     * loss will occur. If this flag is false then if tables with the same names
     * already exist within the database, then they will be dropped and
     * recreated.
     */
    private boolean uniqueTables;

    /**
     * Constructs a new ModelConfig
     * 
     * @param modelPrefix
     *            Unique prefix identifying models created using this model
     *            config
     * @param outputterType
     *            Type of outputter to be generated.
     * @param useLocal
     *            Flag that determines whether console output is to be used or
     *            not
     * @param active
     *            Flag that determines whether this model config is to be used
     * @param uniqueTables
     * @throws ModelConfigException
     *             Thrown if the provided outputterType is not a valid name of
     *             an outputter type as defined in the OutputterType enum. @link
     *             org.neosimulation.neo.framework.config.ModelConfig.
     *             OutputterType} or if outputterType is null.
     */
    public ModelConfig(String modelPrefix, String outputterType, boolean useLocal, boolean active, boolean uniqueTables)
            throws ModelConfigException
    {
        this.modelPrefix = modelPrefix;
        this.active = active;
        this.useLocal = useLocal;
        this.outputConfig = null;
        this.outputConfigName = null;
        this.uniqueTables = uniqueTables;
        setFactory(outputterType);
    }

    /**
     * Sets the factory to be used based on the provided outputter type.
     * 
     * @param outputterType
     *            name of the outputter type as extracted from the model
     *            configuration file. Each type conforms to a type specified in
     *            the OutputterType enumeration.
     * @throws ModelConfigException
     *             Thrown if the outputterType provided is not a valid name from
     *             the enum constant(@link
     *             org.neosimulation.neo.framework.config
     *             .ModelConfig.OutputterType}) or if outputterType is null.
     */
    private void setFactory(String outputterType) throws ModelConfigException
    {
        if (outputterType == null)
            throw new ModelConfigException(
                    "The outputter-type cannot be null, it must have a value specified in the OutputterType enumeration is currently accepted.");
        try
        {
            OutputterType type = OutputterType.valueOf(outputterType);

            switch (type)
            {
            case basic:
                factory = BasicOutputterFactory.getInstance();
                break;
            case single:
                factory = SingleOutputterFactory.getInstance();
                break;
            case parallel:
                factory = ParallelOutputterFactory.getInstance();
                break;
            }
        }
        catch (IllegalArgumentException e)
        {
            throw new ModelConfigException("Invalid name, " + outputterType
                    + ", for outputter-type in model config for model: " + modelPrefix
                    + ". Only a value specified in the OutputterType enumeration is currently accepted.");
        }
    }

    /**
     * Tests whether or not this ModelConfig is to be used to define model runs.
     * 
     * @return if true then this model config is to be used.
     */
    public boolean isActive()
    {
        return active;
    }

    /**
     * Retrieves the OutputterFactory used by SimulationModels built using this
     * ModelConfig object.
     * 
     * @return The OutputterFactory used to generate an Outputter associated
     *         with this ModelConfig.
     */
    public OutputterFactory getOutputterFactory()
    {
        return factory;
    }

    /**
     * The model name prefix to be used when associating information with this
     * ModelConfig.
     * 
     * @return Returns the model prefix associated with this ModelConfig.
     */
    public String getPrefix()
    {
        return modelPrefix;
    }

    /**
     * Sets the outputter to be used for models based on this ModelConfig.
     * 
     * @param outputter
     *            The outputter to associate SimulationModels, created based on
     *            this ModelConfig, to.
     */
    public void setOutputter(Outputter outputter)
    {
        this.outputter = outputter;
    }

    /**
     * The current Outputter to be associated with models generated using this
     * ModelConfig.
     * 
     * @return The outputter used to send output to the output database
     */
    public Outputter getOutputter()
    {
        return outputter;
    }

    /**
     * Sets the DbConfig object used to access the output database.
     * 
     * @param config
     *            the new output DbConfig object.
     */
    public void setOutputDbConfig(DbConfig config)
    {
        this.outputConfigName = config.getName();
        this.outputConfig = config;
    }

    /**
     * Retrieves the current OutputDbConfig to be used by this ModelConfig.
     * 
     * @return The DbConfig object describing the necessary information used to
     *         open a connection to the database where the model output is to be
     *         stored.
     */
    public DbConfig getOutputDbConfig()
    {
        return outputConfig;
    }

    /**
     * Tests whether or not local output is used.
     * 
     * @return if true then local output to the console is turned on.
     */
    public boolean useLocalOutput()
    {
        return useLocal;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "ModelConfig [useLocal=" + useLocal + ", active=" + active + ", modelPrefix=" + modelPrefix
                + ", factory=" + factory + ", outputter=" + outputter + ", outputConfig=" + outputConfig + "]";
    }

    /**
     * Sets the name of the output DbConfig to be used with this ModelConfig.
     * 
     * @param outputConfigName
     *            name of the Output DbConfig
     */
    public void setOutputDbConfigName(String outputConfigName)
    {
        this.outputConfigName = outputConfigName;
    }

    /**
     * Returns the name of the output DbConfig to be used for instance of
     * SimulationModel built using this ModelConfig
     * 
     * @return output DbConfig name
     */
    public String getOutputDbConfigName()
    {
        return outputConfigName;
    }

    /**
     * Test whether output config table names should be unique. Where uniqueness
     * is provided via an included time_date stamp as a part of the table name.
     * 
     * @return true if unique tables are set, false otherwise
     */
    public boolean isUniqueTables()
    {
        return uniqueTables;
    }
}
