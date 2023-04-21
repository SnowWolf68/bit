public class TestHeap {
    private int[] elem;
    private int usedSize;

    public TestHeap() {
        this.elem = new int[10];
    }

    //创建一个大根堆
    public void createHeap(int[] array){
        //把原始数据给到elem数组
        for (int i = 0; i < array.length; i++) {
            elem[i] = array[i];
            usedSize++;
        }
        //对elem数组进行调整 -- 向下调整
        for (int parent = (usedSize - 1 - 1) / 2; parent >= 0; parent--) {
            shiftDown(parent,)
        }


    }

    private void shiftDown(int parent,int x){
         
    }


}
