public class MyCircularQueue {
    private int[] elem;
    private int front;
    private int rear;
    private int usedSize;
    public MyCircularQueue(int k) {
        this.elem = new int[k + 1];
    }

    public boolean enQueue(int value) {
        if(isFull()){
            return false;
        }
        elem[rear] = value;
        rear = (rear + 1) % elem.length;
        return true;
    }

    public boolean deQueue() {
        if(isEmpty()){
            return false;
        }
        front = (front + 1) % elem.length;
        return true;
    }

    public int Front() {
        if(isEmpty()){
            return -1;
        }
        return elem[front];
    }

    public int Rear() {
        if(isEmpty()){
            return -1;
        }
        int index = (rear == 0) ? elem.length - 1 : rear - 1;
        return elem[index];
    }

    public boolean isEmpty() {
        return rear == front;
    }

    public boolean isFull() {
        return (rear + 1) % elem.length == front;
    }
}
