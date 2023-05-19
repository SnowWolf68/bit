import java.util.Arrays;

public class TestHeap {
    private int[] elem;
    private int usedSize;

    public TestHeap() {
        this.elem = new int[10];
    }

    //创建一个大根堆
    //时间复杂度：O(N)
    public void createHeap(int[] array){
        //把原始数据给到elem数组
        for (int i = 0; i < array.length; i++) {
            elem[i] = array[i];
            usedSize++;
        }
        //对elem数组进行调整 -- 向下调整
        for (int parent = (usedSize - 1 - 1) / 2; parent >= 0; parent--) {
            shiftDown(parent,usedSize);
        }


    }

    /**
     *
     * @param parent 代表每棵子树的根节点
     * @param len 代表每棵子树的结束位置
     */
    private void shiftDown(int parent,int len){
         int child = 2 * parent + 1;
         while(child < len){
             if(child + 1 < len && elem[child] < elem[child + 1]){
                 child = child + 1;
             }
             if(elem[child] > elem[parent]){
                 int tmp = elem[child];
                 elem[child] = elem[parent];
                 elem[parent] = tmp;
             }
             parent = child;
             child = 2 * parent + 1;
         }
    }

    public void push(int val){
        if(isFull()){
            elem = Arrays.copyOf(elem,2 * elem.length);
        }

        elem[usedSize] = val;
        usedSize++;

        shiftUp(usedSize - 1);

    }
    private boolean isFull(){
        return usedSize == elem.length;
    }

    private void shiftUp(int child){
        int parent = (child - 1) / 2;
        while(child > 0){
            if(elem[child] > elem[parent]){ //因为原来就是大根堆或小根堆，所以只让新插入的数据和parent比较就可以了
                int tmp = elem[child];
                elem[child] = elem[parent];
                elem[parent] = tmp;
                child = parent;
                parent = (child - 1) / 2;
            }else{
                break;
            }
        }
    }

    public void pop(){
        if(isEmpty()){
            throw new HeapEmptyException("PriorityQueue is Empty!");
        }
        int tmp = elem[0];
        elem[0] = elem[usedSize - 1];
        elem[usedSize - 1] = tmp;
        usedSize--;
        shiftDown(0,usedSize);
    }

    public boolean isEmpty(){
        return usedSize == 0;
    }

    public int peek(){
        if(isEmpty()){
            throw new HeapEmptyException("PriorityQueue is Empty!");
        }
        return elem[0];
    }

    public void heapSort(){
        int end = usedSize - 1;
        while(end > 0){
            int temp = elem[0];
            elem[0] = elem[end];
            elem[end] = temp;
            shiftDown(0,end);//shiftDown中调整的时候，不会调整len这个节点，所以这里直接写end就行，不用end - 1
            end--;
        }

    }

}
