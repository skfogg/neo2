package org.neosimulation.neo.framework.output;

/**
 * QueueStatus - The different status' that the OutputQueue can be in. This is
 * based on the amount of messages in the queue.
 * 
 * @author Michael Paulson
 * @author Isaac Griffith
 */
public enum QueueStatus {

    /**
     * The queue is currently empty. There are no outputs available.
     */
    Empty(0),
    /**
     * The amount of messages coming into the queue is slower than the amount of
     * processors removing messages.
     */
    Emptying(1),
    /**
     * The filling and emptying are staying roughly equal.
     */
    Normal(2),
    /**
     * The amount of input is exceeding the amount of output being processed.
     */
    Filling(4),
    /**
     * the amount of input is now reaching a dangerous level.
     */
    Critical(8),
    /**
     * The current output queue will not be receiving any more messages.
     */
    Full(16);
    /**
     * the value of the output type
     */
    private int value;

    /**
     * The type of output that is available.
     * 
     * @param value
     *            the value from the enumeration.
     */
    private QueueStatus(int value)
    {
        this.value = value;
    }

    /**
     * the value of the queue status.
     * 
     * @return the value of the QueueStatus.
     */
    public int getValue()
    {
        return value;
    }
}
