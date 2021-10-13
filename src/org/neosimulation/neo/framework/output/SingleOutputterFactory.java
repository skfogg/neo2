package org.neosimulation.neo.framework.output;

import org.neosimulation.neo.framework.config.DbConfig;

/**
 * SingleOutputterFactory - Implementation of the OutputterFactory which creates
 * a single threaded Outputter.
 * 
 * @author Ryan Nix
 * @author Isaac Griffith
 */
public class SingleOutputterFactory implements OutputterFactory {

    /**
     * Singleton instance of this OutputterFactory
     */
    private static OutputterFactory outputterInstance = null;

    /**
     * Returns the singleton instance of this class
     * 
     * @return the single instance of this class
     */
    public static OutputterFactory getInstance()
    {
        if (outputterInstance == null)
        {
            outputterInstance = new SingleOutputterFactory();
        }

        return outputterInstance;
    }

    /**
     * Private constructor for singleton
     */
    private SingleOutputterFactory()
    {
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.OutputterFactory#createOutputter
     * (org.neosimulation.neo.framework.database.DbConfig)
     */
    @Override
    public Outputter createOutputter(DbConfig config)
    {
        return new SingleOutputter(config);
    }
}
