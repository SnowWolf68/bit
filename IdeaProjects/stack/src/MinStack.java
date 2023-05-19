import java.util.Stack;

public class MinStack {
    private Stack<Integer> stack;
    private Stack<Integer> minStack;
    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {
        if(stack.empty()){
            stack.push(val);
            minStack.push(val);
        }else{
            stack.push(val);
            if(val <= minStack.peek()){ //if there are two minimum, you should push twice
                minStack.push(val);
            }
        }
    }

    public void pop() {
        if(stack.empty()){
            return;
        }
        int val = stack.pop();
        if(minStack.peek() == val){
            minStack.pop();
        }
    }

    public int top() {
        if(stack.empty()){
            return -1;
        }
        return stack.peek();
    }

    public int getMin() {
        if(minStack.empty()){
            return -1;
        }
        return minStack.peek();
    }
}
