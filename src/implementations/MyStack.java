package implementations;

import java.util.EmptyStackException;

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
        stackList.add(0, element);
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stackList.remove(0);
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stackList.get(0);
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
        return stackList.iterator();
    }

    @Override
    public int search(T toFind) {
        for (int i = 0; i < stackList.size(); i++) {
            if (stackList.get(i).equals(toFind)) {
                return i + 1;
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

    @Override
    public boolean equals(StackADT<T> that) {
        if (this.size() != that.size()) {
            return false;
        }
        Iterator<T> thisIter = this.iterator();
        Iterator<T> thatIter = that.iterator();
        while (thisIter.hasNext() && thatIter.hasNext()) {
            T thisElem = thisIter.next();
            T thatElem = thatIter.next();
            if (!thisElem.equals(thatElem)) {
                return false;
            }
        }
        return true;
    }

}
