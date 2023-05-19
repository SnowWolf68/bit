import java.util.Arrays;
import java.util.Stack;

public class SortPublished20230515_16 {
    /**
     * 时间复杂度：O(N ^ 2)
     * 空间复杂度：O(1)
     * 稳定性：稳定的排序
     * @param array
     */
    public static void bubbleSort(int[] array){
        for(int i = 0;i < array.length;i++){
            boolean flag = false;
            for(int j = 0;j < array.length - i - 1;j++){
                if(array[j] > array[j + 1]){
                    flag = true;
                    swap(array,j,j + 1);
                }
            }
            if(!flag){
                break;
            }
        }
    }

    private static void swap(int[] array,int index1,int index2){
        int t = array[index1];
        array[index1] = array[index2];
        array[index2] = t;
    }


    /**
     * 时间复杂度：O(N ^ 2)
     * 空间复杂度：O(1)
     * 稳定性：稳定的排序
     * @param array
     */
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

    /**
     * 时间复杂度：最好情况下：O(N * logN) -- 完全二叉树
     *          最坏情况下：O(N ^ 2) -- 数据有序或数据逆序，
     *          每次只能排一个元素，是一个等差数列求和，最后变成N ^ 2的时间复杂度
     * 空间复杂度：最好情况下：O(logN) -- 完全二叉树的深度
     *          最坏情况下：O(N) -- 变成单链表
     * 稳定性：不稳定的排序 -- 因为如果两个相同的元素在最左边，左边做key的话，
     *                      有可能会把前一个元素换到后面去
     * @param array
     */
    public static void quickSort(int[] array){
        quick(array,0,array.length - 1);
    }

    private static void quick(int[] array, int start, int end) {
        if(start >= end){//这里也不要忘记=号，如果start == end说明当前区间只有一个元素，
                         //那么肯定是排好序的
            return;
        }
        if(end - start <= 10){
            insertSort1(array,start,end);
            return;
        }
        int mid = threeNumber(array,start,end);
        swap(array,mid,start);
        int pivot = partition2(array,start,end);
        quick(array,start,pivot - 1);
        quick(array,pivot + 1,end);
    }

    private static int threeNumber(int[] array, int start, int end) {
        int mid = (array[start] + array[end]) / 2;
        if(array[start] < array[end]){
            if(array[mid] < array[start]){
                return start;
            }else if(array[mid] > array[start] && array[mid] < array[end]){
                return mid;
            }else{
                return end;
            }
        }else{
            if(array[mid] < array[end]){
                return end;
            }else if(array[mid] > array[end] && array[mid] < array[start]){
                return mid;
            }else{
                return start;
            }
        }
    }

    private static void insertSort1(int[] array, int start, int end) {
        for(int i = start + 1;i <= end;i++){
            int temp = array[i];
            int j = i - 1;
            for(;j >= start;j--){
                if(array[j] > temp){
                    array[j + 1] = array[j];
                }else{
                    break;//如果找到比temp小的别忘了break
                }
            }
            array[j + 1] = temp;
        }
    }

    /**
     * Hoare法
     * @param array
     * @param start
     * @param end
     * @return
     */
    private static int partition(int[] array, int start, int end) {
        int keyIndex = start;
        while(start < end){
            //array[end] >= array[keyIndex]这里不要忘了=号
            //如果不加，遇到相等的元素无法继续向前（后）走
            //start < end这个条件也不要忘记加上
            while(start < end && array[end] >= array[keyIndex]){
                end--;
            }
            while(start < end && array[start] <= array[keyIndex]){
                start++;
            }
            swap(array,start,end);
        }
        swap(array,keyIndex, start);
        return start;
    }

    /**
     * 挖坑法
     * @param array
     * @param start
     * @param end
     * @return
     */
    private static int partition1(int[] array,int start,int end){
        int keyIndex = start;
        int key = array[start];
        while(start < end){
            while(start < end && array[end] >= key){
                end--;
            }
            array[start] = array[end];
            while(start < end && array[start] <= key){
                start++;
            }
            array[end] = array[start];
        }
        array[start] = key;
        return start;
    }

    /**
     * 前后指针法
     * @param array
     * @param start
     * @param end
     * @return
     */
    private static int partition2(int[] array,int start,int end){
        int cur = start + 1;
        int prev = start;
        while(cur <= end){
            if(array[cur] < array[start] && array[++prev] != array[cur]){
                swap(array,prev,cur);
            }
            cur++;
        }
        swap(array,prev,start);
        return prev;
    }

    /**
     * 快排非递归
     * @param array
     */
    public static void quickSort1(int[] array){
        Stack<Integer> stack = new Stack<>();
        int start = 0;
        int end = array.length - 1;
        int mid = threeNumber(array,start,end);
        swap(array,mid,start);
        int pivot = partition(array,start,end);
        if(pivot > start + 1){
            stack.push(start);
            stack.push(pivot - 1);
        }
        if(pivot < end - 1){
            stack.push(pivot + 1);
            stack.push(end);
        }
        while(!stack.isEmpty()){
            end = stack.pop();
            start = stack.pop();
            mid = threeNumber(array,start,end);
            swap(array,mid,start);
            pivot = partition(array,start,end);
            if(pivot > start + 1){
                stack.push(start);
                stack.push(pivot - 1);
            }
            if(pivot < end - 1){
                stack.push(pivot + 1);
                stack.push(end);
            }
        }
    }

    /**
     * 希尔排序
     * 时间复杂度：O(n ^1.25 ~ n ^ 1.5) -> n ^ 1.3
     * 空间复杂度：O(1)
     * 稳定性：不稳定的排序
     * @param array
     */
    public static void shellSort(int[] array){
        int gap = array.length - 1;
        gap /= 2;
        while(gap != 0){
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
                }else{
                    break;
                }
            }
            array[j + gap] = temp;
        }
    }

    /**
     * 选择排序
     * 时间复杂度：O(N ^ 2)
     * 空间复杂度：O(1)
     * 稳定性：不稳定
     * @param array
     */
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

    public static void selectSort1(int[] array){
        int left = 0;
        int right = array.length - 1;
        while(left <= right){
            int minIndex = left;
            int maxIndex = left;
            for(int i = left + 1;i <= right;i++){
                if(array[i] < array[minIndex]){
                    minIndex = i;
                }
                if(array[i] > array[maxIndex]){
                    maxIndex = i;
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

    public static void heapSort(int[] array){
        createBigHeap(array);
        int end = array.length - 1;
        while(end > 0){
            swap(array,0,end);
            end--;
            shiftDown(array,0,end);
        }
        /*createBigHeap(array);
        int end = array.length - 1;
        swap(array,0,end);
        end--;
        while(end != 0){
            shiftDown(array,0,end);
            swap(array,0,end);
            end--;
        }*/
    }

    private static void shiftDown(int[] array, int start, int end) {
        int parent = start;
        int child = 2 * parent + 1;
        while(child <= end){
            if(2 * parent + 2 <= end && array[child] < array[2 * parent + 2]){
                child++;
            }
            if(array[child] > array[parent]){
                swap(array,child,parent);
                parent = child;
                child = 2 * parent + 1;
            }else{
                break;
            }
        }
    }

    private static void createBigHeap(int[] array) {
        for(int i = (array.length - 1 - 1) / 2;i >= 0;i--){
            shiftDown(array,i,array.length - 1);
        }
    }


    /**
     * 时间复杂度：O(N * logN)
     * 空间复杂度：O(N)
     * 稳定性：稳定的排序
     * @param array
     */
    public static void mergeSort(int[] array){
        mergeFunc(array,0,array.length - 1);
    }

    private static void mergeFunc(int[] array, int start, int end) {
        if(start >= end){
            return;
        }
        int mid = (start + end) / 2;
        mergeFunc(array,start,mid);
        mergeFunc(array,mid + 1,end);
        merge(array,start,mid,end);
    }

    private static void merge(int[] array, int start, int mid, int end) {
        int s1 = start;
        int e1 = mid;
        int s2 = mid + 1;
        int e2 = end;
        int[] temp = new int[end - start + 1];
        int index = 0;
        while(s1 <= e1 && s2 <= e2){
            if(array[s1] <= array[s2]){
                temp[index++] = array[s1];
                s1++;
            }else{
                temp[index++] = array[s2];
                s2++;
            }
        }
        while(s1 <= e1){
            temp[index++] = array[s1];
            s1++;
        }
        while(s2 <= e2){
            temp[index++] = array[s2];
            s2++;
        }
        for(int i = start;i <= end;i++){
            array[i] = temp[i - start];
        }
    }

    public static void mergeSort1(int[] array){
        int gap = 1;
        while(gap < array.length){
            for(int i = 0;i < array.length - 1;i += gap * 2){
                int left = i;
                int mid = left + gap - 1;
                int right = mid + gap;
                if(mid > array.length - 1){
                    mid = array.length - 1;
                }
                if(right > array.length - 1){
                    right = array.length - 1;
                }
                merge(array,left,mid,right);
            }
            gap *= 2;
        }
    }


    public static void main(String[] args) {
        int[] array = {5,2,3,4,6,7,2,1};
        mergeSort1(array);
        System.out.println(Arrays.toString(array));
    }
}
