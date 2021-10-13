package org.neosimulation.neo.framework.output;

import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ParallelOutputProcessor - An OutputProcessor is spawned by the Outputter
 * which will remove messages from the OutputQueue associated with the Outputter
 * and place them into specific lists to be processed. Once a certain amount of
 * outputs has been processed OR the processor is finished, it will add them to
 * the database.
 * 
 * @author Michael Paulson
 * @author Isaac Griffith
 */
public class ParallelOutputProcessor extends OutputProcessor {

    /**
     * Designed to kill the output processor if needed.
     */
    private volatile boolean dead;
    /**
     * 
     */
    private volatile Map<Long, StringBuilder> builders;

    /**
     * Creates a new output processor to process output from the OutputQueue.
     * 
     * @param outputter
     *            The outputter has the link to the queue.
     */
    public ParallelOutputProcessor(ParallelOutputter outputter)
    {
        super(outputter);
        builders = new ConcurrentHashMap<>();
        this.dead = false;
    }

    /*
     * (non-Javadoc)
     * @see org.neosimulation.neo.framework.output.OutputProcessor#execute()
     */
    @Override
    public void execute()
    {
        // FIXME Requires Refactoring
        dead = false;
        try
        {
            long id;
            IOutputProvider outputProvider;
            OutputType type;
            StringTokenizer st;
            String tableName;
            int i = 0;

            while (!dead) // if there is an output available, then
            {
                OutputEvent event = outputter.getNextOutputEvent();
                if (event != null)
                {
                    id = event.getModel().getId();
                    outputProvider = event.getModel();
                    type = event.getType();
                    tableName = event.getTableName();
                    st = new StringTokenizer(event.getOutput());
                    i++;

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

                    if (i % 100 == 0)
                    {
                        processOutput();
                    }
                }
                else
                {
                    dead = true;
                }
            }
        }
        catch (Exception e)
        {
            // i do not see anything that could happen to cause an error, but
            // just in case.
            // outputter.getLogger().logSevereException("Output Processor: ",
            // e);
        }
        finally
        {
            if (outputs.size() > 0)
            {
                processOutput();
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * org.neosimulation.neo.framework.output.OutputProcessor#processOutput()
     */
    @Override
    public void processOutput()
    {
        for (Long key : outputs.keySet())
        {
            LinkedList<String> sqlLines = outputs.remove(key);
            OutputProcessStrategy database = outputter.getProcessStrategy(key);
            for (String line : sqlLines)
            {
                database.processOutput(line);
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
