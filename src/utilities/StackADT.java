package utilities;

/**
 * StackADT defines the abstract data type for a generic stack structure.
 * 
 * @param <T> the type of elements stored in the stack
 */
public interface StackADT<T> {

    /**
     * Adds an element to the top of the stack.
     * 
     * @param element the element to be pushed onto the stack
     * @precondition element != null
     * @postcondition The stack contains one additional element at the top.
     * @throws IllegalArgumentException if the element is null
     */
    void push(T element) throws IllegalArgumentException;

    /**
     * Removes and returns the element at the top of the stack.
     * 
     * @return the element removed from the top of the stack
     * @precondition The stack is not empty.
     * @postcondition The top element is removed from the stack.
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    T pop() throws java.util.NoSuchElementException;

    /**
     * Returns, but does not remove, the element at the top of the stack.
     * 
     * @return the element at the top of the stack
     * @precondition The stack is not empty.
     * @postcondition The stack remains unchanged.
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    T peek() throws java.util.NoSuchElementException;

    /**
     * Checks whether the stack is empty.
     * 
     * @return true if the stack has no elements, false otherwise
     * @postcondition The stack remains unchanged.
     */
    boolean isEmpty();

    /**
     * Returns the number of elements in the stack.
     * 
     * @return the size of the stack
     * @postcondition The stack remains unchanged.
     */
    int size();

    /**
     * Removes all elements from the stack.
     * 
     * @postcondition The stack is empty.
     */
    void clear();

    boolean contains(T toFind);

    /**
     * Returns an iterator over the elements in this list, in proper sequence.
     * 
     * @return An iterator over the elements in this list, in proper sequence. NB:
     *         The return is of type <code>linearUtilities.Iterator<E></code>, not
     *         <code>java.util.Iterator</code>.
     */
    Iterator<T> iterator();

    /**
     * Searches for the position of an element in the stack.
     * 
     * @param toFind the element to search for
     * @return the 1-based position of the element in the stack, or -1 if not found
     */
    int search(T toFind);

    Object[] toArray();

    T[] toArray(T[] toHold) throws NullPointerException;

    boolean stackOverflow();

}
