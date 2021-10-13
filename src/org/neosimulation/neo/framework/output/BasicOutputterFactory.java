/**
 * 
 */
package org.neosimulation.neo.framework.output;

import org.neosimulation.neo.framework.config.DbConfig;

/**
 * BasicOutputterFactory - Implementation of the OutputterFactory which creates
 * an Outputter on the same thread as the solver
 * 
 * @author Ryan Nix
 * @author Isaac Griffith
 */
public class BasicOutputterFactory implements OutputterFactory {

    /**
     * Singleton instance of this OutputterFactory
     */
    private static OutputterFactory outputterInstance = null;

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.OutputterFactory#createOutputter
     * (org.neosimulation.neo.framework.database.DbConfig)
     */
    public Outputter createOutputter(DbConfig config)
    {
        return new BasicOutputter(config);
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
            outputterInstance = new BasicOutputterFactory();
        }

        return outputterInstance;
    }

    /**
     * private constructor for singleton
     */
    private BasicOutputterFactory()
    {

    }
}
