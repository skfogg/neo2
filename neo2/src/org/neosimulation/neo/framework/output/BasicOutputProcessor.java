/**
 * 
 */
package org.neosimulation.neo.framework.output;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.neosimulation.neo.framework.logging.NEOLogger;

/**
 * BasicOutputProcessor - An implementation of OutputProcessor designed to work
 * within the main thread of execution.
 * 
 * @author Ryan Nix
 * @author Isaac Griffith
 */
public class BasicOutputProcessor extends OutputProcessor {

    /**
     * The OutputEvent
     */
    private OutputEvent event;
    /**
     * the database processing strategy used by this output processor
     */
    private OutputProcessStrategy strategy;
    /**
     * 
     */
    private Map<String, StringBuilder> builders;

    /**
     * creates a new outputProcessor to process output from the BasicOutputQueue
     * 
     * @param outputter
     *            The outputter has the link to the queue
     */
    public BasicOutputProcessor(BasicOutputter outputter)
    {
        super(outputter);
        builders = new HashMap<>();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.OutputProcessor#processOutput()
     */
    @Override
    public void processOutput()
    {
        for (String key : builders.keySet())
        {
            StringBuilder builder = builders.get(key);
            if (builder != null)
            {
                builder.append(";");
                strategy.processOutput(builder.toString());
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.OutputProcessor#execute()
     */
    @Override
    protected void execute()
    {
        event = outputter.getNextOutputEvent();
        if (event != null)
        {
            try
            {
                Long id = event.getModel().getId();
                StringTokenizer st = new StringTokenizer(event.getOutput());
                String tableName = event.getTableName();
                strategy = outputter.getProcessStrategy(id);

                StringBuilder builder = builders.get(tableName);
                boolean first = false;
                if (builder == null)
                {
                    builder = new StringBuilder();
                    switch (event.getType())
                    {
                    case Insert:
                        builder.append(strategy.formatInsertOutput(tableName));
                        break;
                    case Update:
                        builder.append(strategy.formatUpdateOutput(tableName));
                        break;
                    default:
                        throw new Exception("Incorrect OutputEventType in BasicOutputProcessor");
                    }
                    builders.put(tableName, builder);
                    first = true;
                }

                if (!first)
                    builder.append(", ");
                builder.append(strategy.formatValueString(st));
            }
            catch (Exception e)
            {
                NEOLogger.logException(outputter, "Problem occurred during output.", e);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.OutputProcessor#flush()
     */
    @Override
    public void flush()
    {
        processOutput();
        builders.clear();
    }
}
