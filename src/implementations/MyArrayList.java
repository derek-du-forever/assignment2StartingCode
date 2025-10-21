package implementations;

import java.util.Arrays;

import utilities.Iterator;
import utilities.ListADT;

public class MyArrayList<T> implements ListADT<T> {
    private static final int DEFAULT_CAPACITY = 10;

    private Object[] elements;
    private int size;

    public MyArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean add(int index, T toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null element to the list.");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        ensureCapacity(size + 1);
        copyArray(elements, index, elements, index + 1, size - index);

        elements[index] = toAdd;
        size++;
        return true;
    }

    @Override
    public boolean add(T toAdd) throws NullPointerException {
        return add(size, toAdd);
    }

    @Override
    public boolean addAll(ListADT<? extends T> toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null collection to the list.");
        }
        int addSize = toAdd.size();
        ensureCapacity(size + addSize);
        for (int i = 0; i < addSize; i++) {
            this.add(toAdd.get(i));
        }
        return true;
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elements[index];
    }

    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T removedElement = (T) elements[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            copyArray(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null;
        return removedElement;
    }

    @Override
    public T remove(T toRemove) throws NullPointerException {
        if (toRemove == null) {
            throw new NullPointerException("Cannot remove null element from the list.");
        }
        for (int i = 0; i < size; i++) {
            if (toRemove.equals(elements[i])) {
                return remove(i);
            }
        }
        return null;
    }

    @Override
    public T set(int index, T toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null) {
            throw new NullPointerException("Cannot set null element in the list.");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        T oldElement = (T) elements[index];
        elements[index] = toChange;
        return oldElement;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null element in the list.");
        }
        for (int i = 0; i < size; i++) {
            if (toFind.equals(elements[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public T[] toArray(T[] toHold) throws NullPointerException {
        if (toHold == null) {
            throw new NullPointerException("The provided array cannot be null.");
        }
        if (toHold.length < size) {
            // Create a new array of the same type and size
            Object[] newArray = new Object[size];
            copyArray(elements, 0, newArray, 0, size);
            return (T[]) newArray;
        }
        copyArray(elements, 0, toHold, 0, size);
        return toHold;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        copyArray(elements, 0, array, 0, size);
        return array;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException("No more elements in the iterator.");
                }
                return (T) elements[currentIndex++];
            }
        };
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity <= elements.length) {
            return;
        }
        elements = Arrays.copyOf(elements, Math.max(elements.length * 2, minCapacity));
    }

    private void copyArray(Object[] src, int srcPos, Object[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = src[srcPos + i];
        }
    }

}
