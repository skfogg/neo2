/**
 * 
 */
package org.neosimulation.neo.user.interpolator;

/**
 * FileInputInterpolator - A time-series input interpolator which uses a file as
 * its backend for the dataset.
 * 
 * @author Isaac Griffith
 */
public class FileInputInterpolator extends InputInterpolator {

    // TODO Implement this Class using the NIO classes

    /**
     * Constructs a new FileInputInterpolator using the given
     * InterpolationStrategy as the interpolation method and using the provided
     * name as the location of the file from which data is read.
     * 
     * @param strategy
     *            Interpolation method.
     * @param name
     *            File name of the file to be read.
     */
    protected FileInputInterpolator(InterpolationStrategy strategy, String name)
    {
        super(strategy, true);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.user.interpolator.InputInterpolator#canInterpolate
     * ()
     */
    @Override
    public boolean canInterpolate()
    {
        throw new RuntimeException("Method not yet implemented.");
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.user.interpolator.InputInterpolator#initialize(
     * java.lang.String)
     */
    @Override
    protected void initialize()
    {
        throw new RuntimeException("Method not yet implemented.");
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.user.interpolator.InputInterpolator#scrollRecord
     * (double, boolean)
     */
    @Override
    protected void scrollRecord(double key)
    {
        throw new RuntimeException("Method not yet implemented.");
    }

}
