/**
 * This package contains classes which are used by Package Developers to develop
 * the Dynams necessary to create a model. The classes within this package that
 * end in Dynam are classes which execute and modify StateVals within a model's
 * network topology. <br>
 * <br>
 * The NEOApp class provides an abstraction which when implemented allows the
 * user to provide extra functionality not already existing within the NEO
 * Framework. These NEO Apps allow the developer to provide new tools which can
 * interact with both the underlying Model Management API or the Simulation
 * Model API and provide runtime feedback based on events occurring during a
 * simulation model run. The NEORuntime class acts as the front end execution
 * point for a Model and provides the user the ability to connect a model to the
 * model manager. <br>
 * <br>
 * Finally, the NEOObject class provides an interface for the development of new
 * Objects that can be fluxed through a model. These objects are designed to
 * work with the generic portions of the NEO Framework. In order to use these
 * objects an object package must be added directly from the base package of the
 * model package in which they are used. It also must be noted that the
 * developer must create their own user defined type in their DBMS with the same
 * name (case sensitive) and use that as the column type in both init tables and
 * default tables in the NEO input tables for their model.<br>
 * <br>
 * Note: the NEOObject functionality is still in development and should not yet
 * be used.
 */
package org.neosimulation.neo.user;