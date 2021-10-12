/**
 * 
 */
package org.neosimulation.neo.framework.solver;

import org.neosimulation.neo.framework.model.SimulationModel;

/**
 * BasicSolverFactory - Implementation of the SolverFactory which creates
 * singled threaded BasicSolvers.
 * 
 * @author Isaac Griffith
 */
public class BasicSolverFactory implements SolverFactory {

    /**
     * The singleton instance of this factory
     */
    private static SolverFactory solverInstance = null;

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.SolverFactory#createInitSolver(org.
     * neosimulation.neo.framework.SimulationModel)
     */
    @Override
    public Solver createInitSolver(SimulationModel model)
    {
        return new BasicSolver(true, model);
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.SolverFactory#createExecSolver(org.
     * neosimulation.neo.framework.SimulationModel)
     */
    @Override
    public Solver createExecSolver(SimulationModel model)
    {
        return new BasicSolver(false, model);
    }

    /**
     * Returns the singleton instance of this class
     * 
     * @return the single instance of this class
     */
    public static SolverFactory getInstance()
    {
        if (solverInstance == null)
        {
            solverInstance = new BasicSolverFactory();
        }

        return solverInstance;
    }

    /**
     * private constructor for this singleton
     */
    private BasicSolverFactory()
    {
    }
}
