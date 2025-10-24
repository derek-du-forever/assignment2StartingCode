package utilities;

public interface QueueADT<T> {
    /**
     * Adds an element to the end of the queue.
     * 
     * @param element the element to be added to the queue
     * @precondition element != null
     * @postcondition The queue contains one additional element at the end.
     * @throws IllegalArgumentException if the element is null
     */
    void enqueue(T element);

    /**
     * Removes and returns the element at the front of the queue.
     * 
     * @return the element removed from the front of the queue
     * @precondition The queue is not empty.
     * @postcondition The front element is removed from the queue.
     * @throws exceptions.EmptyQueueException if the queue is empty
     */
    T dequeue() throws exceptions.EmptyQueueException;

    /**
     * Returns, but does not remove, the element at the front of the queue.
     * 
     * @return the element at the front of the queue
     * @precondition The queue is not empty.
     * @postcondition The queue remains unchanged.
     * @throws exceptions.EmptyQueueException if the queue is empty
     */
    T peek() throws exceptions.EmptyQueueException;

    /**
     * Checks whether the queue is empty.
     * 
     * @return true if the queue has no elements, false otherwise
     * @postcondition The queue remains unchanged.
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the queue.
     * 
     * @return the size of the queue
     * @postcondition The queue remains unchanged.
     */
    int size();

    /**
     * Removes all elements from the queue.
     * 
     * @postcondition The queue is empty.
     */
    void dequeueAll();

    /**
     * Returns an iterator over the elements in this list, in proper sequence.
     * 
     * @return An iterator over the elements in this list, in proper sequence. NB:
     *         The return is of type <code>linearUtilities.Iterator<E></code>, not
     *         <code>java.util.Iterator</code>.
     */
    Iterator<T> iterator();

    Object[] toArray();

    T[] toArray(T[] toHold) throws NullPointerException;

    boolean isFull();

    boolean contains(T toFind);

    /**
     * Searches for the position of an element in the queue.
     * 
     * @param toFind the element to search for
     * @return the 1-based position of the element in the queue, or -1 if not found
     */
    int search(T toFind);

}
