import java.util.Stack;

public class MinStack {
    private Stack<Integer> stack;
    private Stack<Integer> minStack;
    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {
        stack.push(val);
        if(minStack.empty()){
            minStack.push(val);
        }else{

        }
    }

    public void pop() {

    }

    public int top() {

    }

    public int getMin() {

    }
}
