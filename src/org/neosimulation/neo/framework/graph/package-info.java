/**
 * This package contains operations which are applicable to instances of the
 * Graph class in package org.neosimulation.neo.framework.graph. Currently this
 * package is structured into a set of classes that extend GraphSearch, a set of
 * classes that extend GraphSort, a set of classes that extend
 * MinimumSpanningTree, a set of classes that provide a means to detect all
 * elementary cycles in a graph, a set of classes that extend NetworkFlow, a
 * utility class which can find all topological sorts of a graph, a set of
 * classes which provide algorithms to correctly display a graph in either 2D or
 * 3D space, and the class GraphUtilities. <br>
 * <br>
 * GraphSearch provides a Template Pattern that provides the basic Graph search
 * algorithm.<br>
 * <br>
 * GraphSort, GraphDisplay, MinimumSpanningTree, and NetworkFlow on the other
 * hand are implemented using the Strategy Pattern. <br>
 * <br>
 * The GraphUtilties class provides the context for these operations and other
 * helper methods, while simultaneously providing the Graph structure in which
 * these operations run. The GraphUtilities class also provides the logic which
 * analyzes the environment in which the algorithm is ran and decides which
 * specific implementation will be used.
 */
package org.neosimulation.neo.framework.graph;