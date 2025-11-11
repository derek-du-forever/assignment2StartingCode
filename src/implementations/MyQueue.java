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
    public T dequeue() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException("Queue is empty");
        }
        return queueList.remove(0);
    }

    @Override
    public T peek() throws EmptyQueueException {
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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MyQueue)) {
            return false;
        }
        MyQueue<?> otherQueue = (MyQueue<?>) other;
        if (this.size() != otherQueue.size()) {
            return false;
        }

        utilities.Iterator<T> thisIter = this.iterator();
        utilities.Iterator<?> otherIter = otherQueue.iterator();

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
    public boolean equals(QueueADT<T> that) {
        if (this.size() != that.size()) {
            return false;
        }
        utilities.Iterator<T> thisIter = this.iterator();
        utilities.Iterator<T> thatIter = that.iterator();
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
