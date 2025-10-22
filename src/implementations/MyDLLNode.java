package implementations;

public class MyDLLNode<T> {
    private T data;
    private MyDLLNode<T> next;
    private MyDLLNode<T> prev;

    public MyDLLNode(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setNext(MyDLLNode<T> next) {
        this.next = next;
    }

    public MyDLLNode<T> getNext() {
        return next;
    }

    public MyDLLNode<T> getPrev() {
        return prev;
    }

    public void setPrev(MyDLLNode<T> prev) {
        this.prev = prev;
    }

}
