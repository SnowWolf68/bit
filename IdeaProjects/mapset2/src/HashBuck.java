import java.util.Arrays;
import java.util.Objects;

public class HashBuck<K,V> {
    static class Node<K,V>{
        private K key;
        private V val;
        private Node<K,V> next;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    public Node<K,V>[] array = (Node<K,V>[])new Node[10];
    public int usedSize;
    private static final double DEFAULT_LOAD_FACTOR = 0.75f;

    public HashBuck() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashBuck<?, ?> hashBuck = (HashBuck<?, ?>) o;
        return usedSize == hashBuck.usedSize && Arrays.equals(array, hashBuck.array);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(usedSize);
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    public void put(K key, V val){
        Node<K,V> node = new Node<>(key,val);
        int hash = key.hashCode();
        int index = hash % array.length;
        Node<K,V> cur = array[index];
        while(cur != null){
            if(cur.key.equals(key)){
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

    private void resize() {
        Node<K,V>[] tempArray = new Node[2 * array.length];
        for (int i = 0; i < array.length; i++) {
            Node<K,V> cur = array[i];
            while(cur != null){
                Node<K,V> curNext = cur.next;
                int hash = cur.key.hashCode();
                int index = hash % tempArray.length;
                cur.next = tempArray[index];
                tempArray[index] = cur;
                cur = curNext;
            }
        }
        array = tempArray;
    }

    private float loadFactor() {
        return usedSize * 1.0f / array.length;
    }

    public V get(K key){
        int hash = key.hashCode();
        int index = hash % array.length;
        Node<K,V> cur = array[index];
        while(cur != null){
            if(cur.key.equals(key)){
                return cur.val;
            }
            cur = cur.next;
        }
        return null;
    }
    public int getArrayLength(){
        return array.length;
    }
}
