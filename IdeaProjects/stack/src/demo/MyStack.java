package demo;

import java.util.LinkedList;
import java.util.Queue;

public class MyStack {
    private Queue<Integer> queue1;
    private Queue<Integer> queue2;
    public MyStack() {
        queue1 = new LinkedList();
        queue2 = new LinkedList();
    }

    public void push(int x) {
        if(!queue1.isEmpty()){
            queue1.offer(x);
        }else if(!queue2.isEmpty()){
            queue2.offer(x);
        }else{
            queue1.offer(x);
        }
    }

    public int pop() {
        if(empty()){
            return -1;
        }
        if(!queue1.isEmpty()){
            int currentSize = queue1.size();
            for (int i = 0; i < currentSize - 1; i++) {
                int x = queue1.poll();
                queue2.offer(x);
            }
            return queue1.poll();
        }else if(!queue2.isEmpty()){
            int currentSize = queue2.size();
            for (int i = 0; i < currentSize - 1; i++) {
                int x = queue2.poll();
                queue1.offer(x);
            }
            return queue2.poll();
        }
        return -1;
    }

    public int top() {
        if(empty()){
            return -1;
        }
        if(!queue1.isEmpty()){
            int currentSize = queue1.size();
            int x = -1;
            for (int i = 0; i < currentSize; i++) {
                x = queue1.poll();
                queue2.offer(x);
            }
            return x;
        }else if(!queue2.isEmpty()){
            int currentSize = queue2.size();
            int x = -1;
            for (int i = 0; i < currentSize; i++) {
                x = queue2.poll();
                queue1.offer(x);
            }
            return x;
        }
        return -1;
    }

    public boolean empty() {
        return queue1.isEmpty() && queue2.isEmpty();
    }

}
