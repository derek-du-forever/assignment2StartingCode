package implementations;

import utilities.Iterator;
import utilities.ListADT;

public class MyDLL<T> implements ListADT<T> {
    private MyDLLNode<T> head;
    private MyDLLNode<T> tail;
    private int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean add(int index, T toAdd) throws NullPointerException, IndexOutOfBoundsException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null element");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        size++;
        MyDLLNode<T> newNode = new MyDLLNode<>(toAdd);
        if (head == null) {
            head = newNode;
            tail = newNode;
            return true;
        }
        if (index == 0) {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
            return true;
        }
        if (index == size) {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
            return true;
        }
        MyDLLNode<T> current = head;
        for (int i = 0; i <= index - 1; i++) {
            current = current.getNext();
        }
        MyDLLNode<T> previous = current.getPrev();
        previous.setNext(newNode);
        newNode.setPrev(previous);
        newNode.setNext(current);
        current.setPrev(newNode);
        return true;
    }

    @Override
    public boolean add(T toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null element");
        }
        return add(size, toAdd);
    }

    @Override
    public boolean addAll(ListADT<? extends T> toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add from null list");
        }
        // Iterate by calling iterator with while loop
        Iterator<? extends T> iter = toAdd.iterator();
        while (iter.hasNext()) {
            T element = iter.next();
            add(element);
        }
        return true;
    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        MyDLLNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getData();
    }

    @Override
    public T remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }

        MyDLLNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return removeNode(current);
    }

    private T removeNode(MyDLLNode<T> node) {
        MyDLLNode<T> previous = node.getPrev();
        MyDLLNode<T> next = node.getNext();
        if (previous != null) {
            previous.setNext(next);
        } else {
            head = next;
        }

        if (next != null) {
            next.setPrev(previous);
        } else {
            tail = previous;
        }
        size--;
        return node.getData();
    }

    @Override
    public T remove(T toRemove) throws NullPointerException {
        if (toRemove == null) {
            throw new NullPointerException("Cannot remove null element");
        }
        MyDLLNode<T> current = head;
        while (current != null) {
            if (current.getData().equals(toRemove)) {
                return removeNode(current);
            }
            current = current.getNext();
        }
        return null;
    }

    @Override
    public T set(int index, T toChange) throws NullPointerException, IndexOutOfBoundsException {
        if (toChange == null) {
            throw new NullPointerException("Cannot set null element");
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        MyDLLNode<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        T oldData = current.getData();
        current.setData(toChange);
        return oldData;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null element");
        }
        MyDLLNode<T> current = head;
        while (current != null) {
            if (current.getData().equals(toFind)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    @Override
    public T[] toArray(T[] toHold) throws NullPointerException {
        if (toHold == null) {
            throw new NullPointerException("Array cannot be null");
        }
        if (toHold.length < size) {
            toHold = (T[]) java.lang.reflect.Array.newInstance(toHold.getClass().getComponentType(), size);
        }
        MyDLLNode<T> current = head;
        for (int i = 0; i < size; i++) {
            toHold[i] = current.getData();
            current = current.getNext();
        }
        return toHold;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        MyDLLNode<T> current = head;
        for (int i = 0; i < size; i++) {
            array[i] = current.getData();
            current = current.getNext();
        }
        return array;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private MyDLLNode<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new IndexOutOfBoundsException("No more elements to iterate");
                }
                T data = current.getData();
                current = current.getNext();
                return data;
            }
        };

    }
}
