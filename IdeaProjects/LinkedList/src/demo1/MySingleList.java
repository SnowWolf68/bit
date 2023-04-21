package demo1;

import java.util.List;
import java.util.PrimitiveIterator;

public class MySingleList {
    static class ListNode{//内部类
        public int val;
        public ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode head;//永远指向我的头节点

    public void creatList(){
        ListNode node1 = new ListNode(12);
        ListNode node2 = new ListNode(23);
        ListNode node3 = new ListNode(34);
        ListNode node4 = new ListNode(45);
        ListNode node5 = new ListNode(56);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;

        this.head = node1;
    }

    public void show(){
        ListNode p = this.head;
        while(p != null){
            System.out.print(p.val + " ");
            p = p.next;
        }
        System.out.println();
    }

    public void show(ListNode node){
        ListNode p = node;
        while(p != null){
            System.out.print(p.val + " ");
            p = p.next;
        }
        System.out.println();
    }

    //头插法
    public void addFirst(int data){
        ListNode node = new ListNode(data);
        node.next = head;
        head = node;
    }
    //尾插法
    public void addLast(int data){//规范
        ListNode node = new ListNode(data);
        if(head == null){
            head = node;
            return;
        }
        ListNode cur = head;
        while(cur.next != null){
            cur = cur.next;
        }
        cur.next = node;

        /*if(this.head == null){
            head = new ListNode(data);
        }else{
            ListNode cur = head;
            while(cur.next != null){
                cur = cur.next;
            }
            ListNode node = new ListNode(data);
            cur.next = node;
        }*/

    }
    //任意位置插入,第一个数据节点为0号下标
    public void addIndex(int index,int data){
        if(index < 0 || index > this.size()){
            throw new IndexOutOfBoundsException("index:" + index + "位置不合法");
        }
        if(index == 0){
            addFirst(data);
            return;
        }
        if(index == this.size()){//这个有必要吗？？？？
            addLast(data);
            return;
        }
        ListNode node = new ListNode(data);
        ListNode cur = findIndex(index);
        node.next = cur.next;
        cur.next = node;
    }

    private ListNode findIndex(int index){//找到index - 1位置的节点
        ListNode cur = this.head;
        int count = 0;
        for(int i = 0;i < index - 1;i++){
            cur = cur.next;
        }
        return cur;
    }

    //查找是否包含关键字key是否在单链表当中
    public boolean contains(int key){
        ListNode cur = this.head;
        while(cur != null){
            //这里如果是引用，需要用equals方法进行比较
            if(cur.val == key){
                return true;
            }
            cur = cur.next;
        }
        return false;
    }
    //删除第一次出现关键字为key的节点
    public void remove(int key){
        if(head.val == key){
            head = head.next;
            return;
        }
        ListNode prev = searchPrev(key);
        if(prev == null){
            System.out.println("没有要删除的数字！");
            return;
        }
        prev.next = prev.next.next;
    }

    private ListNode searchPrev(int key){
        ListNode prev = this.head;
        while(prev.next != null){
            if(prev.next.val == key){
                return prev;
            }else{
                prev = prev.next;
            }

        }
        return null;
    }

    //删除所有值为key的节点
    public void removeAllKey(int key){
        if(head == null){
            return;
        }
        while(head.val == key){//或者不用改while，把这段放在最后也可以
            head = head.next;
            if(head == null){//这个容易漏掉
                return;
            }
        }
        ListNode prev = this.head;
        while(prev.next != null){
            if(prev.next.val == key){
                prev.next = prev.next.next;
            }else{
                prev = prev.next;
            }
        }

        /*if(head.val == key){
            head = head.next;
            return;
        }
        ListNode cur = head;
        while(cur.next != null){
            if(cur.next.val == key){
                cur.next = cur.next.next;
                continue;
            }
            cur = cur.next;
        }*/
    }
    //得到单链表的长度
    public int size(){
        ListNode cur = this.head;
        int count = 0;
        while(cur != null){
            count++;
            cur = cur.next;
        }
        return count;
    }

    public void clear() {
        //this.head = null;
        while(this.head != null){
            ListNode headNext = head.next;
            head.next = null;
            head = headNext;
        }
    }

    public ListNode reverseList() {
        if(head == null){
            return null;
        }
        if(head.next == null){
            return head;
        }
        ListNode cur = head.next;
        head.next = null;
        while(cur != null){
            ListNode curNext = cur.next;
            cur.next = head;
            head = cur;
            cur = curNext;
        }
        return head;
    }

    public ListNode middleNode() {
        if(head == null){
            return null;
        }
        if(head.next == null){
            return head;
        }
        ListNode fast = head;
        ListNode slow = head;
        while(fast != null && fast.next != null){//这个判断还不能写反!!!!!
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    public ListNode FindKthToTail1(int k) {//牛客网没过
        if(head == null){
            return null;
        }
        ListNode cur = head.next;
        head.next = null;
        while(cur != null){
            ListNode curNext = cur.next;
            cur.next = head;
            head = cur;
            cur = curNext;
        }
        cur = head;
        if(k <= 0){
            return null;
        }
        for(int i = 0;i < k - 1;i++){
            cur = cur.next;
            if(cur == null){
                return null;
            }
        }
        return cur;
    }

    public ListNode FindKthToTail(int k) {
        if(k <= 0){
            return null;
        }
        if(head == null){
            return null;
        }
        ListNode fast = head;
        ListNode slow = head;
        for(int i = 0;i < k - 1;i++){
            fast = fast.next;
            if(fast == null){
                return null;
            }
        }
        while(fast.next != null){
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    public void display() {}

}
