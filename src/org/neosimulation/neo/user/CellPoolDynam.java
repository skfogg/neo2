/**
 * 
 */
package org.neosimulation.neo.user;

import org.neosimulation.neo.framework.holon.Cell;
import org.neosimulation.neo.framework.holon.Face;

/**
 * CellPoolDynam - The base class for all cell pool dynams. This class provides
 * a common inheritance hierarchy for cell pool dynams. It extends the base
 * class for AutoDynams. This class provides the basic non-type-specific
 * functionality for cell pool dynams.
 * 
 * @author Isaac Griffith
 */
public abstract class CellPoolDynam extends AutoDynam {

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.user.CalcDynamGeneric#setCalcDeps()
     */
    @Override
    public synchronized void setCalcDeps()
    {
        Face[] faces = ((Cell) holon).getFacesArray(this.stateVal.getName());
        
        if (faces.length > 0)
        {
            for (int i = 0; i < faces.length; i++)
            {
                this.createDependency(faces[i], this.stateVal.getName());
            }
        }
    }
}
