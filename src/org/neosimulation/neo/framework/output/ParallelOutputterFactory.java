package org.neosimulation.neo.framework.output;

import org.neosimulation.neo.framework.config.DbConfig;

/**
 * ParallelOutputterFactory - Implementation of the OutputterFactory which
 * creates a multi-threaded version of the Outputter
 * 
 * @author Ryan Nix
 * @author Isaac Griffith
 */
public class ParallelOutputterFactory implements OutputterFactory {

    /**
     * Singleton instance of the Outputter
     */
    private static OutputterFactory outputterInstance = null;

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.OutputterFactory#createOutputter
     * (org.neosimulation.neo.framework.database.DbConfig)
     */
    @Override
    public Outputter createOutputter(DbConfig config)
    {
        return new ParallelOutputter(config);
    }

    /**
     * Returns the singleton instance of this class
     * 
     * @return the single instance of this class
     */
    public static OutputterFactory getInstance()
    {
        if (outputterInstance == null)
        {
            outputterInstance = new ParallelOutputterFactory();
        }

        return outputterInstance;
    }

    /**
     * private constructor for singleton
     */
    private ParallelOutputterFactory()
    {
    }
}
