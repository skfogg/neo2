/**
 * 
 */
package org.neosimulation.neo.framework.graph;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * TODO Finish this comment
 * 
 * @author Isaac
 */
public class SCCResult {
    /**
     * TODO Finish this comment
     */
    private Set nodeIDsOfSCC = null;
    /**
     * TODO Finish this comment
     */
    private Vector[] adjList = null;
    /**
     * TODO Finish this comment
     */
    private int lowestNodeId = -1;

    /**
     * TODO Finish this comment
     * 
     * @param adjList
     * @param lowestNodeId
     */
    public SCCResult(Vector[] adjList, int lowestNodeId)
    {
        this.adjList = adjList;
        this.lowestNodeId = lowestNodeId;
        this.nodeIDsOfSCC = new HashSet();
        if (this.adjList != null)
        {
            for (int i = this.lowestNodeId; i < this.adjList.length; i++)
            {
                if (this.adjList[i].size() > 0)
                {
                    this.nodeIDsOfSCC.add(new Integer(i));
                }
            }
        }
    }

    /**
     * TODO Finish this comment
     * 
     * @return list of adjacencies
     */
    public Vector[] getAdjList()
    {
        return adjList;
    }

    /**
     * TODO Finish this comment
     * 
     * @return lowest node id
     */
    public int getLowestNodeId()
    {
        return lowestNodeId;
    }
}