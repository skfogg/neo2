/**
 * 
 */
package org.neosimulation.neo.framework.logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import org.neosimulation.neo.user.ManualDynam;

/**
 * NEOLogger - Class designed to encapsulate the functionalities of the inherent
 * Java(tm) Logging facility but with an easy to use interface designed for both
 * framework functions and for the Package Developer.
 * 
 * @author Isaac Griffith
 */
public class NEOLogger implements Serializable {

    /**
     * The underlying Java Logging system logger used
     */
    private Logger neoLog;
    /**
     * Number of severe problems logged by this NEOLogger
     */
    private long numSevereLogged;
    /**
     * Number of warnings logged by this NEOLogger
     */
    private long numWarningLogged;
    /**
     * Number of informative comments logged by this NEOLogger
     */
    private long numInfoLogged;

    /**
     * Constructs a new NEOLogger with the specified verbosity and uses the
     * default log name ("NEO") and log location ("./neo.log")
     * 
     * @param verbose
     *            true for verbose logs false otherwise
     */
    public NEOLogger(boolean verbose)
    {
        this("NEO", "neo.log", true);
    }

    /**
     * Constructs a new NEOLogger with the specified name, file, and verbosity.
     * TODO Requires refactoring
     * 
     * @param name
     *            Name for this logger
     * @param fileName
     *            file path location to save the log to
     * @param verbose
     *            vebosity flag (true for verbose logs, false otherwise)
     */
    public NEOLogger(String name, String fileName, boolean verbose)
    {
        this.numInfoLogged = 0;
        this.numSevereLogged = 0;
        this.numWarningLogged = 0;

        if (fileName.contains(File.separator))
        {
            File logDir = new File(fileName.substring(0, fileName.indexOf(File.separator)));
            logDir.mkdirs();
        }
        if (!fileName.endsWith(".txt"))
            fileName = fileName + ".txt";

        neoLog = Logger.getLogger(name);

        /*
         * TODO Configure the FileHandler to use a SimpleFormatter (for plain
         * text, or an XML formatter for XML Output. Probably should create a
         * hierarchy of formatters for the logger.
         */
        try
        {
            File file = new File(fileName);
            Handler handler = null;
            if (file.exists())
            {
                FileOutputStream fos = new FileOutputStream(file, true);
                handler = new StreamHandler(fos, new NEOLogFormatter());
            }
            else
            {
                handler = new FileHandler(fileName);
                handler.setFormatter(new NEOLogFormatter());
            }
            neoLog.addHandler(handler);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        LogManager.getLogManager().addLogger(neoLog);
    }

    /**
     * Construct a new NEOLogger with the given name and verbosity
     * 
     * @param name
     *            name of the logger
     * @param verbose
     *            true for verbose logs, false otherwise
     */
    public NEOLogger(String name, boolean verbose)
    {
        this(name, "logs" + File.separator + name, verbose);
    }

    /**
     * Construct a new NEOLogger with the given name
     * 
     * @param name
     *            name of the logger
     */
    public NEOLogger(String name)
    {
        neoLog = Logger.getLogger(name);
    }

    /**
     * Log a warning condition
     * 
     * @param msg
     *            message to log
     */
    public void logWarning(String msg)
    {
        neoLog.log(Level.WARNING, msg);
        numWarningLogged++;
    }

    /**
     * Log a severe condition
     * 
     * @param msg
     *            message to log
     */
    public void logSevere(String msg)
    {
        neoLog.log(Level.SEVERE, msg);
        numSevereLogged++;
    }

    /**
     * Log an informative message
     * 
     * @param msg
     *            message to log
     */
    public void logInfo(String msg)
    {
        neoLog.log(Level.INFO, msg);
        numInfoLogged++;
    }

    /**
     * Log a severe stateval condition
     * 
     * @param msg
     *            message to log
     * @param sourceClass
     *            class where from which this log event is initiated
     * @param sourceMethod
     *            method where this log event is initiated
     * @param sv
     *            StateVal name to log
     */
    public void logStateValSevere(String msg, String sourceClass, String sourceMethod, String sv)
    {
        String temp = String
                .format("%s In class %s in method %s with StateVal: %s", msg, sourceClass, sourceMethod, sv);
        neoLog.log(Level.SEVERE, temp);
        numSevereLogged++;
    }

    /**
     * Log a warning stateval condition
     * 
     * @param msg
     *            message to log
     * @param sourceClass
     *            class where from which this log event is initiated
     * @param sourceMethod
     *            method where this log event is initiated
     * @param sv
     *            StateVal name to log
     */
    public void logStateValWarning(String msg, String sourceClass, String sourceMethod, String sv)
    {
        String temp = String
                .format(msg + ": in class %s in method %s with StateVal: %s", sourceClass, sourceMethod, sv);
        neoLog.log(Level.WARNING, temp);
        numWarningLogged++;
    }

    /**
     * Log an informative message for a StateVal
     * 
     * @param msg
     *            message to log
     * @param sourceClass
     *            class where from which this log event is initiated
     * @param sourceMethod
     *            method where this log event is initiated
     * @param sv
     *            StateVal name to log
     */
    public void logStateValInfo(String msg, String sourceClass, String sourceMethod, String sv)
    {
        String temp = String
                .format(msg + ": in class %s in method %s with StateVal: %s", sourceClass, sourceMethod, sv);
        neoLog.log(Level.INFO, temp);
        numInfoLogged++;
    }

    /**
     * Log a severe message about an exception
     * 
     * @param msg
     *            message to log
     * @param thrown
     *            exception thrown
     */
    public void logSevereException(String msg, Throwable thrown)
    {
        neoLog.log(Level.SEVERE, msg, thrown);
        numSevereLogged++;
    }

    /**
     * Log a warning message and an exception
     * 
     * @param msg
     *            message to log
     * @param thrown
     *            exception that was thrown
     */
    public void logWarningException(String msg, Throwable thrown)
    {
        neoLog.log(Level.WARNING, msg, thrown);
        numWarningLogged++;
    }

    /**
     * Log an information about an exception
     * 
     * @param msg
     *            message to log
     * @param thrown
     *            exception that was thrown
     */
    public void logInfoException(String msg, Throwable thrown)
    {
        neoLog.log(Level.INFO, msg, thrown);
        numInfoLogged++;
    }

    /**
     * Retrieves the number of Severe Problems that have been logged with this
     * logger.
     * 
     * @return number of severe problems logged.
     */
    public long getNumberOfSevereProblemsLogged()
    {
        return numSevereLogged;
    }

    /**
     * Retrieves the number of warnings that this logger has logged.
     * 
     * @return number of warnings logged
     */
    public long getNumberOfWarningsLogged()
    {
        return numWarningLogged;
    }

    /**
     * Retrieves the number of informative statements that this logger has
     * logged
     * 
     * @return number of informative statements logged.
     */
    public long getNumberOfInfoStmtsLogged()
    {
        return numInfoLogged;
    }

    /**
     * Clears the counts of logged events.
     */
    public void clearCounts()
    {
        this.numInfoLogged = 0;
        this.numSevereLogged = 0;
        this.numWarningLogged = 0;
    }

    /**
     * A convenience method to log severe exceptions on the provided loggagle
     * (if logging is availalbe) or to system.err (if logging is disabled for
     * the given loggable).
     * 
     * @param loggable
     *            Instance which contains the logger to be used.
     * @param msg
     *            Message to be logged
     * @param e
     *            Throwable causing the problem
     */
    public static void logException(Loggable loggable, String msg, Throwable e)
    {
        if (loggable.isLoggingEnabled())
        {
            loggable.getLogger().logSevereException(msg, e);
        }
        else
        {
            System.err.println(msg);
        }
    }

    /**
     * Logs an exception as a warning on the provided Loggable's logger.
     * 
     * @param loggable
     *            Loggable whose logger is to be used.
     * @param msg
     *            Message describing the warning
     * @param e
     *            Exception creating the necessity to log.
     */
    public static void logWarningException(Loggable loggable, String msg, Throwable e)
    {
        if (loggable.isLoggingEnabled())
        {
            loggable.getLogger().logWarningException(msg, e);
        }
        else
        {
            System.err.println(msg);
        }
    }

    /**
     * Log's an informative comment and the exception which generated the
     * problem to the provided Loggable's logger.
     * 
     * @param loggable
     *            Loggable whose logger is to be used
     * @param msg
     *            Informative Comment
     * @param e
     *            Exception propagating the need to log the comment.
     */
    public static void logInfoException(Loggable loggable, String msg, Throwable e)
    {
        if (loggable.isLoggingEnabled())
        {
            loggable.getLogger().logInfoException(msg, e);
        }
        else
        {
            System.err.println(msg);
        }
    }

    /**
     * Logs an informative comment to the provided loggable's logger.
     * 
     * @param loggable
     *            Loggable whose logger is to be used.
     * @param msg
     *            Informative Comment
     */
    public static void logInfo(Loggable loggable, String msg)
    {
        if (loggable.isLoggingEnabled())
        {
            loggable.getLogger().logInfo(msg);
        }
        else
        {
            System.out.println(msg);
        }
    }

    /**
     * Logs that a stateval with the provided stateval name could not be found.
     * It includes the location in code (class name and method name) where this
     * problem occurred for future debugging.
     * 
     * @param loggable
     *            Loggable whose logger should be used
     * @param sourceClass
     *            Name of the class where the problem occurred
     * @param sourceMethod
     *            Name of the method in the class where the problem occurred
     * @param sv
     *            Name of the stateval that could not be found.
     */
    public static void logStateVal(Loggable loggable, String sourceClass, String sourceMethod, String sv)
    {
        if (loggable.isLoggingEnabled())
        {
            loggable.getLogger().logStateValSevere("Required StateVal " + sv + " could not be found.", sourceClass,
                    sourceMethod, sv);
        }
        else
        {
            System.err.println("Required StateVal: " + sv + ", could not be found inside: " + sourceClass + "#"
                    + sourceMethod + "()");
        }
    }

    /**
     * Static Method to log a warning on the provided loggable's logger.
     * 
     * @param loggable
     *            Loggable whose logger will be used for logging
     * @param msg
     *            Message to be logged as a warning.
     */
    public static void logWarning(Loggable loggable, String msg)
    {
        if (loggable.isLoggingEnabled())
        {
            loggable.getLogger().logWarning(msg);
        }
        else
        {
            System.err.println(msg);
        }
    }

    /**
     * Static Method to log a warning that the provided ManualDynam was unable
     * to be registered.
     * 
     * @param loggable
     *            Loggable whose logger will be used for logging
     * @param sourceClass
     *            The Class containing the method where registration failed
     * @param sourceMethod
     *            The Method in which the registration failed
     * @param md
     *            The ManualDynam which was unable to be registered
     */
    public static void logManualDynamNotRegistered(Loggable loggable, String sourceClass, String sourceMethod,
            ManualDynam md)
    {
        if (loggable.isLoggingEnabled())
        {
            loggable.getLogger().logWarning(
                    "In " + sourceClass + "." + sourceMethod + "\n\tManual Dynam: " + md.getClass().getName()
                            + " has not been properly registred to an autodynam.");
        }
        else
        {
            System.err.println("In " + sourceClass + "." + sourceMethod + "\n\tManual Dynam: "
                    + md.getClass().getName() + " has not been properly registred to an autodynam.");
        }
    }
}
