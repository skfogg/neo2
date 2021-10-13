/**
 * This package contains the classes necessary to build and control a simulation
 * model run. Classes responsible for building and populating the SimulatonModel
 * class is the ModelInitiator and NetworkBuilder. The ModelInitiator creates
 * and sets the properties of the SimulationModel. The NetworkBuilder, on the
 * other hand, creates the underlying topology of the Matrix and Network for the
 * SimulationModel. The Matrix is the class which represents the structural
 * topology of a model and is composed of holons which are the basic static
 * elements of the framework. The Network provides the underlying topology of
 * the flux network. The Network is populated with both StateVals and Dynams
 * using the various Populators and their strategy classes. These flux networks
 * represent the means by which Currencies change are are represented by the
 * remaining three major components of the system. These components are:
 * StateVals, which store some type of value within a holon; Dynams, which are
 * attached to and modify a StateVal; and IUpdatables, which control the
 * interaction between StateVals and Dynams. In order to correctly initialize
 * and execute the model a DependencyGraph is built through the use of a DAG
 * (Directed Acyclic Graph) which is then sorted and divided accordingly. The
 * sorted nodes of this graph are then used by Solvers attached to the
 * SimulationModel to either initialize all the necessary StatVals (through
 * StateVal updaters) and then to execute (update the statevals) until some
 * StoppingCondition is met. This is in essence the operation of the
 * NEOFramework.
 */
package org.neosimulation.neo.framework;