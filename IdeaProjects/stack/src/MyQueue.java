public class MyQueue {
    static class ListNode{
        public int val;
        public ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }
    public ListNode head;
    public ListNode last;
    public int usedSize;

    public void offer(int val){
        ListNode node = new ListNode(val);
        if(head == null){
            head = node;
            last = node;
        }else{
            last.next = node;
            last = node;
        }
        usedSize++;
    }
    public int getUsedSize(){
        return usedSize;
    }
    public int poll(){
        if(head == null){
            return -1;
        }
        int val = -1;
        if(head.next == null){
            val = head.val;
            head = null;
            last = null;
            return val;
        }
        val = head.val;
        head = head.next;
        usedSize--;
        return val;
    }

    public int peek(){
        if(head == null){
            return -1;
        }
        return head.val;
    }

    public boolean isEmpty(){
        return usedSize == 0;
    }

    public static void main(String[] args) {
        MyCircularQueue myCircularQueue = new MyCircularQueue(3);
        System.out.println(myCircularQueue.enQueue(1));
        System.out.println(myCircularQueue.enQueue(2));
        System.out.println(myCircularQueue.enQueue(3));
        System.out.println(myCircularQueue.enQueue(4));
        System.out.println(myCircularQueue.Rear());
        System.out.println(myCircularQueue.isFull());
        System.out.println(myCircularQueue.deQueue());
        System.out.println(myCircularQueue.enQueue(4));
        System.out.println(myCircularQueue.Rear());
    }
}
