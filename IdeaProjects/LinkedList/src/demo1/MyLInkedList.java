package demo1;

public class MyLInkedList {
    public static void main(String[] args) {
        MyLInkedList myLInkedList = new MyLInkedList();
        myLInkedList.addLast(10);
        myLInkedList.addLast(20);
        myLInkedList.addLast(10);
//        myLInkedList.addLast(20);
//        myLInkedList.addLast(30);
//        myLInkedList.addLast(40);
//        myLInkedList.addIndex(3,99);
        //myLInkedList.removeAllKey(10);

        myLInkedList.display();
        myLInkedList.clear();

        System.out.println("+++++++++++");


    }





    static class ListNode{
        public int val;
        public ListNode prev;
        public ListNode next;

        public ListNode() {
        }

        public ListNode(int val) {
            this.val = val;
        }
    }
    public ListNode head;
    public ListNode last;
    //头插法
    public void addFirst(int data){
        if(this.head == null){
            ListNode cur = new ListNode(data);
            this.head = cur;
            this.last = cur;
            return;
        }
        ListNode cur = new ListNode(data);
        cur.next = this.head;
        this.head.prev = cur;
        this.head = cur;
    }
    //尾插法
    public void addLast(int data){
        ListNode cur = new ListNode(data);
        if(head == null){
            head = cur;
            last = cur;
            return;
        }
        cur.prev = last;
        last.next = cur;
        last = cur;
    }
    //任意位置插入,第一个数据节点为0号下标
    public void addIndex(int index,int data){
        int size = size();
        if(index == 0){
            addFirst(data);
            return;
        }
        if(index == size){
            addLast(data);
            return;
        }
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("index 不合法！");
        }
        ListNode cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        ListNode node = new ListNode(data);
        node.next = cur;
        node.prev = cur.prev;
        node.prev.next = node;
        cur.prev = node;
    }
    //查找是否包含关键字key是否在单链表当中
    public boolean contains(int key){
        ListNode cur = this.head;
        while(cur != null){
            if(cur.val == key){
                return true;
            }
            cur = cur.next;
        }
        return false;
    }
    //删除第一次出现关键字为key的节点
    public void remove(int key){
        ListNode cur = this.head;
        while(cur != null){
            if(cur.val == key){
                if(cur == this.head){
                    if(head == last){
                        head = null;
                        last = null;
                        return;
                    }
                    head = head.next;
                    head.prev = null;
                }
                else if(cur == this.last){
                    /*if(head == last){
                        head = null;
                        last = null;
                    }*/
                    last.prev.next = null;
                    last = last.prev;
                }
                else{
                    cur.next.prev = cur.prev;
                    cur.prev.next = cur.next;
                }
                return;
            }
            cur = cur.next;
        }
    }
    //删除所有值为key的节点
    public void removeAllKey(int key){
        ListNode cur = this.head;
        while(cur != null){
            if(cur.val == key){
                if(cur == this.head){
                    if(head == last){
                        head = null;
                        last = null;
                        return;
                    }
                    head = head.next;
                    head.prev = null;
                }
                else if(cur == this.last){
                    /*if(head == last){
                        head = null;
                        last = null;
                    }*/
                    last.prev.next = null;
                    last = last.prev;
                }
                else{
                    cur.next.prev = cur.prev;
                    cur.prev.next = cur.next;
                }
            }
            cur = cur.next;
        }
    }
    //得到单链表的长度
    public int size(){
        int size = 0;
        ListNode p = this.head;
        while(p != null){
            size++;
            p = p.next;
        }
        return size;
    }
    public void display(){
        ListNode cur = this.head;
        while(cur != null){
            System.out.print(cur.val + " ");
            cur = cur.next;
        }
        System.out.println();
    }
    public void clear(){
        ListNode cur = head;
        while(cur != null){
            ListNode curNext = cur.next;
            cur.next = null;
            cur.prev = null;
            //如果存的是引用类型，还需要cur.val = null;
            cur = curNext;
        }
        head = null;//这两行不加会少释放两个节点
        last = null;
    }
}
