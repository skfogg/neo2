/**
 * 
 */
package org.neosimulation.neo.framework.logging;

import java.util.Calendar;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * NEOLogFormatter - Class which correctly formats the log files for NEOLogger
 * instances.
 * 
 * @author Isaac Griffith
 */
public class NEOLogFormatter extends Formatter {

    /**
     * String representing the termination of a line.
     */
    private String term;

    /**
     * Constructs a new NEOLogFormatter
     */
    public NEOLogFormatter()
    {
        String value = System.getProperty("os.name");
        if (value.toLowerCase().contains("windows"))
        {
            term = "\r\n";
        }
        else
        {
            term = "\n";
        }
    }

    /*
     * (non-Javadoc)
     * @see java.util.logging.Formatter#format(java.util.logging.LogRecord)
     */
    @Override
    public String format(LogRecord record)
    {
        StringBuffer buf = new StringBuffer(1000);
        buf.append(String.format("   %1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tM %1$Tp ", Calendar.getInstance()));
        buf.append(String.format(record.getSourceClassName() + " " + record.getSourceMethodName()));
        buf.append(term);
        buf.append("   " + record.getLevel());
        buf.append(": ");
        buf.append(record.getMessage());
        buf.append(term);
        return buf.toString();
    }

    /*
     * (non-Javadoc)
     * @see java.util.logging.Formatter#getHead(java.util.logging.Handler)
     */
    @Override
    public String getHead(Handler h)
    {
        return "Log initiated: " + String.format("%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tM %1$Tp ", Calendar.getInstance())
                + term;
    }

    /*
     * (non-Javadoc)
     * @see java.util.logging.Formatter#getTail(java.util.logging.Handler)
     */
    @Override
    public String getTail(Handler h)
    {
        return "Log terminated: "
                + String.format("%1$tb %1$td, %1$tY %1$tl:%1$tM:%1$tM %1$Tp ", Calendar.getInstance()) + term + term;
    }
}
