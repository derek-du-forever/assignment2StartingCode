package implementations;

import exceptions.EmptyQueueException;
import utilities.ListADT;
import utilities.QueueADT;

public class MyQueue<T> implements QueueADT<T> {
    private ListADT<T> queueList;

    public MyQueue() {
        queueList = new MyDLL<T>();
    }

    @Override
    public void enqueue(T element) {
        queueList.add(element);
    }

    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty");
        }
        return queueList.remove(0);
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty");
        }
        return queueList.get(0);
    }

    @Override
    public boolean isEmpty() {
        return queueList.size() == 0;
    }

    @Override
    public int size() {
        return queueList.size();
    }

    @Override
    public void dequeueAll() {
        queueList.clear();
    }

    @Override
    public utilities.Iterator<T> iterator() {
        return queueList.iterator();
    }

    @Override
    public Object[] toArray() {
        return queueList.toArray();
    }

    @Override
    public T[] toArray(T[] toHold) throws NullPointerException {
        return queueList.toArray(toHold);
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public boolean contains(T toFind) {
        return queueList.contains(toFind);
    }

    @Override
    public int search(T toFind) {
        utilities.Iterator<T> iter = queueList.iterator();
        int index = 0;
        while (iter.hasNext()) {
            if (iter.next().equals(toFind)) {
                return index + 1;
            }
            index++;
        }
        return -1;
    }

}
