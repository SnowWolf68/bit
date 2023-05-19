import java.util.Arrays;

public class TestHeapPublished {
    private int[] elem;
    private int usedSize;
    public TestHeapPublished() {
        this.elem = new int[4];
    }
    public void createHeap(int[] array){
        for (int i = 0; i < array.length; i++) {
            if(isFull()){
                grow();
            }
            this.elem[i] = array[i];
            usedSize++;
        }
        for (int i = (usedSize - 1 - 1) / 2; i >= 0; i--) {
            shiftDown(i,usedSize);
        }
    }

    /**
     * len是每棵子树的结束位置，注意是尾后位置，不是最后一个
     * 所以下面判断child是< len，而不是<= len
     * @param parent
     * @param len
     */
    private void shiftDown(int parent,int len){
        int child = 2 * parent + 1;
        while(child < len){
            if(child + 1 < len && elem[child + 1] > elem[child]){
                child += 1;
            }
            if(elem[child] > elem[parent]){
                int temp = elem[child];
                elem[child] = elem[parent];
                elem[parent] = temp;
            }
            parent = child;
            child = parent * 2 + 1;
        }
    }
    private void grow(){
        int newLength = usedSize * 2;
        this.elem = Arrays.copyOf(this.elem,newLength);
    }
    public void push(int val){
        while(isFull()){
            grow();
        }
        this.elem[usedSize] = val;
        usedSize++;
        /*for (int i = (usedSize - 1 - 1) / 2; i >= 0; i--) {
            shiftDown(i,usedSize);
        }*/
        shiftUp(usedSize - 1);
    }
    private boolean isFull(){
        return usedSize == elem.length;
    }
    private void shiftUp(int child){
        int parent = (child - 1) / 2;
        while(parent >= 0){
            if(elem[child] > elem[parent]){
                int temp = elem[child];
                elem[child] = elem[parent];
                elem[parent] = temp;
                child = parent;
                parent = (child - 1) / 2;
            }else{
                break;
            }
        }
    }
    public void pop(){
        int temp = elem[0];
        elem[0] = elem[usedSize - 1];
        elem[usedSize - 1] = temp;
        usedSize--;
        shiftDown(0,usedSize);
    }
    public boolean isEmpty(){
        return usedSize == 0;
    }
    public int peek(){
        return elem[0];
    }
    public void heapSort(){
        int end = usedSize - 1;
        while(end > 0){
            int temp = elem[0];
            elem[0] = elem[end];
            elem[end] = temp;
            shiftDown(0,end);
            end--;
        }
    }

}
