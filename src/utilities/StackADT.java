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
    void push(T element);

    /**
     * Removes and returns the element at the top of the stack.
     * 
     * @return the element removed from the top of the stack
     * @precondition The stack is not empty.
     * @postcondition The top element is removed from the stack.
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    T pop();

    /**
     * Returns, but does not remove, the element at the top of the stack.
     * 
     * @return the element at the top of the stack
     * @precondition The stack is not empty.
     * @postcondition The stack remains unchanged.
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    T peek();

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
}
