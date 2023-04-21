package demo1;

public class test1 {
    public MySingleList.ListNode mergeTwoLists(MySingleList.ListNode list1, MySingleList.ListNode list2) {
        MySingleList.ListNode p1 = list1;
        MySingleList.ListNode p2 = list2;
        if(p1 == null){
            return p2;
        }
        if(p2 == null){
            return p1;
        }
        MySingleList.ListNode head = null;
        if(p1.val < p2.val){
            head = p1;
            p1 = p1.next;
        }else{
            head = p2;
            p2 = p2.next;
        }
        MySingleList.ListNode cur = head;
        while(p1 != null && p2 != null){
            if(p1.val < p2.val){
                cur.next = p1;
                cur = cur.next;
                p1 = p1.next;
            }else{
                cur.next = p2;
                cur = cur.next;
                p2 = p2.next;
            }
        }
        if(p1 != null){
            cur.next = p1;
        }
        if(p2 != null){
            cur.next = p2;
        }
        return head;
    }
    
    public static void main(String[] args) {
        MySingleList mySingleList = new MySingleList();

//        mySingleList.addIndex(0,1);
        mySingleList.addLast(1);
        mySingleList.addLast(2);
        mySingleList.addLast(3);
        mySingleList.addLast(4);
        mySingleList.addLast(5);
        /*mySingleList.addIndex(2,2);
        mySingleList.addIndex(mySingleList.size(),999);
        mySingleList.addIndex(0,199);*//*
        *//*try {
            mySingleList.addIndex(mySingleList.size() + 1,999);
        }catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }*//*
        mySingleList.show();
//        mySingleList.removeAllKey(34);

        mySingleList.clear();
        *//*System.out.println(mySingleList.size());
        System.out.println(mySingleList.contains(2));
        mySingleList.addFirst(11);*//*
        System.out.println("=======");*/
        mySingleList.show();
        /*MySingleList.ListNode listNode = mySingleList.reverseList();
        mySingleList.show(listNode);*/
        System.out.println(mySingleList.FindKthToTail(1).val);


    }
}
