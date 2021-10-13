package org.neosimulation.neo.framework.output;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * OutputProcessor - Provides the logic to control and process Output Events.
 * 
 * @author Ryan Nix
 * @author Isaac Griffith
 */
public abstract class OutputProcessor implements Runnable, Serializable {

    /**
     * boolean flag determining whether the processor has been stopped (killed).
     */
    protected boolean dead;
    /**
     * The list of output to be written.
     */
    protected Map<Long, LinkedList<String>> outputs;
    /**
     * The next output event string to be processed.
     */
    protected String output;
    /**
     * The associated Outputter
     */
    protected Outputter outputter;

    /**
     * Constructs a new output processor with an empty list of output locations.
     */
    public OutputProcessor(Outputter outputter)
    {
        this.outputter = outputter;
        this.outputs = new HashMap<>();
        dead = false;
    }

    /**
     * Kills the output processor, that this stops the output processor.
     */
    public void kill()
    {
        dead = true;
    }

    /**
     * Tests whether the output processor dead or not. That is whether the kill
     * method has been called.
     * 
     * @return true if the processor is dead, false otherwise.
     */
    public boolean isDead()
    {
        return dead;
    }

    /**
     * Process the outputs from the outputs hashmap.
     */
    public abstract void processOutput();

    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run()
    {
        execute();
    }

    /**
     * Starts the OutputProcessor. The output processor will loop through the
     * queue and attempt to remove a message from the output queue. Once there
     * is no more messages, then it will kill itself.
     */
    protected abstract void execute();

    /**
     * Retrieves the next output string to be processed.
     * 
     * @return current output string
     */
    public String getOutputString()
    {
        return output;
    }

    /**
     * 
     */
    public abstract void flush();
}