import java.util.Arrays;

public class MyStack{
    private int[] elem;
    private int usedSize;

    public MyStack() {
        this.elem = new int[5];
    }

    public void push(int val){
        if(isFull()){
            elem = Arrays.copyOf(elem,2 * elem.length);
        }
        elem[usedSize] = val;
        usedSize++;
    }
    public boolean isFull(){
        return usedSize == elem.length;
    }
    public int pop(){
        if(empty()){
            throw new StackEmptyException("Stack is null!");
        }
        return elem[--usedSize];
    }
    public boolean empty(){
        return usedSize == 0;
    }
    public int peek(){
        if(empty()){
            throw new StackEmptyException("Stack is null!");
        }
        return elem[usedSize - 1];
    }
}
