/**
 * 
 */
package org.neosimulation.neo.user.interpolator;

/**
 * FileMathInterpolator - A file based Math Interpolator.
 * 
 * @author Isaac Griffith
 */
public class FileMathInterpolator extends MathInterpolator {

    // TODO Implement this Class using the NIO classes

    /**
     * Constructs a new FileMathInterpolator which uses the file located at name
     * to provide data, and which uses the provided strategy as the
     * interpolation method.
     * 
     * @param name
     *            location of file containing data.
     * @param strategy
     *            interpolation method.
     */
    protected FileMathInterpolator(String name, InterpolationStrategy strategy)
    {
        super(strategy, true);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.user.interpolator.MathInterpolator#canInterpolate()
     */
    @Override
    public boolean canInterpolate()
    {
        throw new RuntimeException("Method not yet implemented");
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.user.interpolator.MathInterpolator#initialize(java
     * .lang.String)
     */
    @Override
    protected void initialize()
    {
        throw new RuntimeException("Method not yet implemented");
    }

}
