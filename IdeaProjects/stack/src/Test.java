public class Test {
    public static void main(String[] args){
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
