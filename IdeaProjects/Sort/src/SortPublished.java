public class SortPublished {
    public static void insertSort(int[] array){
        for(int i = 1;i < array.length;i++){
            int temp = array[i];
            int j = i - 1;
            for(;j >= 0;j--){
                if(array[j] > temp){
                    array[j + 1] = array[j];
                }else{
                    break;
                }
            }
            array[j + 1] = temp;
        }
    }

    public static void shellSort(int[] array){
        int gap = array.length;
        gap /= 2;
        while(gap >= 1){
            shell(array,gap);
            gap /= 2;
        }
    }

    private static void shell(int[] array, int gap) {
        for(int i = gap;i < array.length;i++){
            int temp = array[i];
            int j = i - gap;
            for(;j >= 0;j -= gap){
                if(array[j] > temp){
                    array[j + gap] = array[j];
                } else {
                    break;
                }
            }
            array[j + gap] = temp;
        }
    }

    public static void selectSort(int[] array){
        for(int i = 0;i < array.length;i++){
            int minIndex = i;
            for(int j = i + 1;j < array.length;j++){
                if(array[j] < array[minIndex]){
                    minIndex = j;
                }
            }
            swap(array,minIndex,i);
        }
    }

    public static void selectSortPlus(int[] array){
        int left = 0;
        int right = array.length - 1;
        while(left <= right){
            int minIndex = left;
            int maxIndex = left;
            for(int j = left + 1;j <= right;j++){
                if(array[j] < array[minIndex]){
                    minIndex = j;
                }
                if(array[j] > array[maxIndex]){
                    maxIndex = j;
                }
            }
            swap(array,minIndex,left);
            if(maxIndex == left){
                maxIndex = minIndex;
            }
            swap(array,maxIndex,right);
            left++;
            right--;
        }
    }

    private static void swap(int[] array,int index1,int index2){
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    public static void heapSort(int[] array){
        createHeap(array);
    }

    private static void createHeap(int[] array) {
        int len = array.length;
        while(len > 0){
            for(int i = (len - 1 - 1) / 2;i >= 0;i--){
                shiftDown(array,i,len);
            }
            len--;
            swap(array,0,len);
        }

    }

    private static void shiftDown(int[] array, int parent, int len) {
        int child = 2 * parent + 1;
        while(child < len){
            if(child + 1 < len && array[child + 1] > array[child]){
                child += 1;
            }
            if(array[child] > array[parent]){
                swap(array,child,parent);
            }
            parent = child;
            child = 2 * parent + 1;
        }
    }

    public static void bubbleSort(int[] array){
        for(int i = 0;i < array.length;i++){

        }
    }

    public static void main(String[] args) {
        int[] array = {3,2,4,6,5,1,9,8,10,7};
        heapSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }
}
