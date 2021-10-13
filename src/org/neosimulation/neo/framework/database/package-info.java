/**
 * Classes within this package represent the control and logic necessary to
 * extract information from a NEO Model Input Database in order to create a
 * Simulation Model. Classes that extend the TableProcessor class are used to
 * extract the information necessary to build the Simulation Model. The
 * NEODbController represents the control construct which utilizes these table
 * processors. The DbMediator provides mediation between the entire Database
 * Subsystem in this package and the remaining framework. That is DbMediator
 * acts as the means of directing communication between key framework components
 * and the NEO Database Subsystem. Finally, this package also contains the
 * necessary classes to provide easier access and generation of connections to
 * database sources via the JDBCConnectionFactory, DatabaseManager, and
 * DbConnectionPool classes.
 */
package org.neosimulation.neo.framework.database;