package org.neosimulation.neo.framework.output;

import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SingleOutputProcessor - A single threaded implementation of the
 * OutputProcessor.
 * 
 * @author Ryan Nix
 * @author Isaac Griffith
 */
public class SingleOutputProcessor extends OutputProcessor {

    private Map<Long, StringBuilder> builders;

    /**
     * Creates a new output processor to process output from the
     * SingleOutputQueue
     * 
     * @param outputter
     *            The outputter has the link to the queue
     */
    public SingleOutputProcessor(SingleOutputter outputter)
    {
        super(outputter);
        builders = new ConcurrentHashMap<>();
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.OutputProcessor#processOutput()
     */
    @Override
    public void processOutput()
    {
        System.out.println("processing outputs");
        for (Long key : outputs.keySet())
        {
            if (builders.get(key) == null)
                continue;
            LinkedList<String> sqlLines = outputs.remove(key);
            OutputProcessStrategy database = outputter.getProcessStrategy(key);
            database.processOutput(builders.get(key).toString());
            builders.put(key, null);
        }
    }

    /**
     * The output processor will loop through the queue and attempt to remove a
     * message from the output queue. Once there is no more messages, it
     * finishes and returns with nothing
     */
    public void execute()
    {
        try
        {
            int i = 0;
            dead = false;
            while (!dead) // if there is an output available, then
            {
                OutputEvent event = outputter.getNextOutputEvent();
                System.out.println("event = " + event);
                if (event != null)
                {
                    long id = event.getModel().getId();
                    IOutputProvider outputProvider = event.getModel();
                    OutputType type = event.getType();
                    String tableName = event.getTableName();
                    StringTokenizer eventTokens = new StringTokenizer(event.getOutput());
                    i++;

                    handleOutputs(id, type, tableName, eventTokens);

                    if (i % 100 == 0)
                    {
                        processOutput();
                    }
                }
                else
                {
                    dead = true;
                    output = null;
                }
                System.out.println("Dead = " + dead);
            }
        }
        catch (Exception e)
        {
            // NEOLogger.logException(outputter,
            // "A problem occurred during output.", e);
            System.out.println(e);
        }
        finally
        {
            if (outputs.size() > 0)
            {
                processOutput();
            }
        }
    }

    /**
     * Method designed to handle output.
     * 
     * @param id
     *            id of the output
     * @param type
     *            type of output
     * @param tableName
     *            name of the table into which the output should go
     * @param eventTokens
     *            a StringTokenizer object containing the output event's entries
     */
    void handleOutputs(long id, OutputType type, String tableName, StringTokenizer eventTokens)
    {
        if (builders.get(id) == null)
        {
            StringBuilder builder = new StringBuilder();
            switch (type)
            {
            case Insert:
                builder.append(outputter.getProcessStrategy(id).formatInsertOutput(tableName));
                break;
            case Update:
                builder.append(outputter.getProcessStrategy(id).formatUpdateOutput(tableName));
                break;
            }
            builders.put(id, builder);
        }
        builders.get(id).append(outputter.getProcessStrategy(id));
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
