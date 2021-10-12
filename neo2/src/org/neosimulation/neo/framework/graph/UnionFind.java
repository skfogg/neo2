/**
 * 
 */
package org.neosimulation.neo.framework.graph;


/**
 * TODO Finish this comment
 * @author Isaac Griffith
 */
public class UnionFind {

    /**
     * TODO Finish this comment
     */
    private int[] id, sz;

    /**
     * TODO Finish this comment
     * 
     * @param x
     * @return index of the node
     */
    public int find(int x)
    {
        while (x != id[x])
        {
            x = id[x];
        }
        
        return x;
    }

    /**
     * TODO Finish this comment
     * 
     * @param N
     */
    public UnionFind(int N)
    {
        id = new int[N];
        sz = new int[N];

        for (int i = 0; i < N; i++)
        {
            id[i] = i;
            sz[i] = 1;
        }
    }

    /**
     * TODO Finish this comment
     * 
     * @param p
     * @param q
     * @return found
     */
    public boolean find(int p, int q)
    {
        return (find(p) == find(q));
    }

    /**
     * TODO Finish this comment
     * 
     * @param p
     * @param q
     */
    public void unite(int p, int q)
    {
        int i = find(p);
        int j = find(q);

        if (i == j)
            return;

        if (sz[i] < sz[j])
        {
            id[i] = j;
            sz[j] += sz[i];
        }
        else
        {
            id[j] = i;
            sz[i] += sz[j];
        }
    }
}
