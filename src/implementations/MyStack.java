package implementations;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

import utilities.Iterator;
import utilities.ListADT;
import utilities.StackADT;

public class MyStack<T> implements StackADT<T> {
    private ListADT<T> stackList;

    public MyStack() {
        stackList = new MyArrayList<T>();
    }

    @Override
    public void push(T element) {
        stackList.add(element);
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stackList.remove(size() - 1);
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stackList.get(size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return stackList.size();
    }

    @Override
    public void clear() {
        stackList.clear();
    }

    @Override
    public boolean contains(T toFind) {
        return stackList.contains(toFind);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = size() - 1;

            @Override
            public boolean hasNext() {
                return currentIndex >= 0;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements in stack");
                }
                T element = stackList.get(currentIndex);
                currentIndex--;
                return element;
            }
        };
    }

    @Override
    public int search(T toFind) {
        for (int i = size() - 1; i >= 0; i--) {
            if (stackList.get(i).equals(toFind)) {
                return size() - i;
            }
        }
        return -1;
    }

    @Override
    public Object[] toArray() {
        return stackList.toArray();
    }

    @Override
    public T[] toArray(T[] toHold) throws NullPointerException {
        return stackList.toArray(toHold);
    }

    @Override
    public boolean stackOverflow() {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MyStack<?>)) {
            return false;
        }
        MyStack<?> otherStack = (MyStack<?>) other;
        if (this.size() != otherStack.size()) {
            return false;
        }
        Iterator<T> thisIter = this.iterator();
        Iterator<?> otherIter = otherStack.iterator();
        while (thisIter.hasNext() && otherIter.hasNext()) {
            T thisElem = thisIter.next();
            Object otherElem = otherIter.next();
            if (!thisElem.equals(otherElem)) {
                return false;
            }
        }
        return true;
    }

}
