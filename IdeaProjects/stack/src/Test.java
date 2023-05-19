import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class Test {
    public static void main(String[] args) {
        Deque<Integer> deque = new LinkedList<>();
        deque.offer(1);
        Deque<Integer> stack = new LinkedList<>();
        stack.push(11);

    }
    public static void main2(String[] args) {
        MyStack myStack = new MyStack();
        int[] pushA = {1,2,3,4,5};
        int[] popA = {4,5,3,2,1};
        myStack.IsPopOrder(pushA,popA);
        System.out.println("sda");
    }
    public static void main1(String[] args){
        /*Stack<Integer> stack = new Stack<>();
        //底层是一个数组！！链表能不能实现栈？ -- 可以的
        stack.add(12);
        stack.add(23);
        stack.add(34);

        int x = stack.pop();
        System.out.println(x);
        //ArrayDequeue 可以代替Stack*/
        MyStack myStack = new MyStack();
        myStack.push(1);
        myStack.push(2);
        myStack.push(3);
        myStack.push(4);

        System.out.println(myStack.peek());
        System.out.println(myStack.peek());
        System.out.println(myStack.peek());
        System.out.println(myStack.peek());

    }
}
