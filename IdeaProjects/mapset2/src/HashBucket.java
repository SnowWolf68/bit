public class HashBucket {
    static class Node{
        private int key;
        private int val;
        private Node next;

        public Node(int key, int value) {
            this.key = key;
            this.val = value;
        }
    }

    public Node[] array;
    public int usedSize;
    private static final double DEFAULT_LOAD_FACTOR = 0.75f;
    public HashBucket(){
        this.array = new Node[10];
    }

    public void put(int key,int val){
        Node node = new Node(key,val);
        int index = key % array.length;
        Node cur = array[index];
        while(cur != null){
            if(cur.key == key){
                cur.val = val;
                return;
            }
            cur = cur.next;
        }
        node.next = array[index];
        array[index] = node;
        usedSize++;
        if(loadFactor() >= DEFAULT_LOAD_FACTOR){
            resize();
        }
    }

    private void resize(){
        Node[] tempArray = new Node[array.length * 2];
        for (int i = 0; i < array.length; i++) {
            Node cur = array[i];
            while(cur != null){
                Node curNext = cur.next;
                int index = cur.key % tempArray.length;
                cur.next = tempArray[index];
                tempArray[index] = cur;
                cur = curNext;
            }
        }
        array = tempArray;
    }

    private float loadFactor(){
        return usedSize * 1.0f / array.length;
    }

    /**
     * 通过key值返回value
     * @param key
     * @return value
     */
    public int get(int key){
        int index = key % array.length;
        Node cur = array[index];
        while(cur != null){
            if(cur.key == key){
                return cur.val;
            }
        }
        return -1;
    }


}
