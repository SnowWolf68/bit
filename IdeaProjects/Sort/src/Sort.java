import java.util.Stack;

public class Sort {
    /**
     * 稳定性：稳定
     * 一个本身就是稳定的排序，可以实现一个不稳定的排序
     * 一个本身就是不稳定的排序，不可能实现一个稳定的排序
     * 元素越接近有序，效率越高
     * @param array
     */
    public static void insertSort(int[] array){
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];
            int j = i - 1;
            for(;j >= 0;j--){
                if(array[j] >= temp){
                    //这里加一个等号，就不是一个稳定的排序了
                    array[j + 1] = array[j];
                }else{
                    break;
                }
            }
            array[j + 1] = temp;
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
        int gap = array.length;
        while(gap > 1){
            gap /= 2;
            shell(array,gap);
        }
    }

    /**
     * 对每组进行直接插入排序
     * @param array
     * @param gap
     */
    private static void shell(int[] array,int gap){
        for (int i = gap; i < array.length; i++) {
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
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i + 1;j < array.length;j++) {
                if(array[j] < array[minIndex]){
                    minIndex = j;
                }
            }
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
    }

    public static void selectSortPlus(int[] array){
        int left = 0;
        int right = array.length - 1;
        while(left < right){
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

    /**
     * 堆排序
     * 时间复杂度：O(n * logn)    对数据不敏感
     * 空间复杂度：O(1)
     * 稳定性：不稳定
     * @param array
     */
    public static void heapSort(int[] array){
        createBigHeap(array);
        int end = array.length - 1;
        while(end >= 0){
            swap(array,end,0);
            shiftDown(array,0,end);
            end--;
        }
    }

    private static void createBigHeap(int[] array){
        for (int i = (array.length - 1 - 1) / 2; i >= 0; i--) {
            shiftDown(array,i,array.length);
        }
    }

    private static void shiftDown(int[] array,int parent,int len){
        int child = 2 * parent + 1;
        while(child < len){
            if(child + 1 < len && array[child] < array[child + 1]){
                child = child + 1;
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

    private static void swap(int[] array,int index1,int index2){
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    /**
     * 冒泡排序
     * 时间复杂度：O(N ^ 2) 对数据不敏感
     * 空间复杂度：O(1)
     * 稳定性：稳定的排序
     *      在目前学的排序中，只有 插入排序，冒泡排序 是稳定的排序
     * 加了优化之后，时间复杂度有可能变成：O(N) -- 本来就是有序的
     * @param array
     */
    public static void bubbleSort(int[] array){
        for(int i = 0;i < array.length - 1;i++){
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

    /**
     * 时间复杂度：O(N * logN) -- 好的情况，是完全二叉树
     *      坏的情况：O(N ^ 2) -- 坏的情况，数据有序或逆序
     * 空间复杂度：O(logN) -- 好的情况
     *      不好的情况：O(N)
     * 稳定性：不稳定的排序
     * @param array
     */
    public static void quickSort(int[] array){
        quick(array,0,array.length - 1);
    }

    public static void insertSort1(int[] array, int left, int right){
        for (int i = left + 1; i <= right; i++) {
            int temp = array[i];
            int j = i - 1;
            for(;j >= left;j--){
                if(array[j] >= temp){
                    //这里加一个等号，就不是一个稳定的排序了
                    array[j + 1] = array[j];
                }else{
                    break;
                }
            }
            array[j + 1] = temp;
        }
    }

    private static void quick(int[] array,int start,int end){
        if(start >= end){
            return;
        }
        if (end - start + 1 <= 20){
            insertSort1(array,start,end);
            return;
        }
        int mid = threeNumber(array,start,end);
        swap(array,mid,start);
        int pivot = partition(array,start,end);
        quick(array,start,pivot - 1);
        quick(array,pivot + 1,end);
    }

    /**
     * 三数取中法
     * @param array
     * @param left
     * @param right
     * @return 中间数字的下标
     */
    private static int threeNumber(int[] array,int left,int right){
        int mid = (left + right) / 2;
        if(array[left] < array[right]){
            if(array[mid] < array[left]){
                return left;
            } else if (array[mid] > array[right]){
                return right;
            } else {
                return mid;
            }
        } else {
            if (array[mid] < array[right]){
                return right;
            } else if (array[mid] > array[left]){
                return left;
            } else {
                return mid;
            }
        }
    }

    /**
     * hoare法
     * @param array
     * @param left
     * @param right
     * @return
     */
    private static int partition(int[] array, int left, int right){
        int i = left;
        int temp = array[left];
        while(left < right){
            while(left < right && array[right] >= temp){
                right--;
            }
            //先判断左边为什么会有问题
            while(left < right && array[left] <= temp){
                left++;
            }
            swap(array,left,right);
        }
        swap(array,i,left);
        return left;
    }

    /**
     * 挖坑法
     * @param array
     * @param left
     * @param right
     * @return
     */
    private static int partition1(int[] array, int left, int right){
        int i = left;
        int temp = array[left];
        while(left < right){
            while(left < right && array[right] >= temp){
                right--;
            }
            array[left] = array[right];
            while(left < right && array[left] <= temp){
                left++;
            }
            array[right] = array[left];
        }
        array[left] = temp;
        return left;
    }

    /**
     * 非递归实现快速排序
     * @param array
     */
    public static void quickSort1(int[] array){
        Stack<Integer> stack = new Stack<>();
        int start = 0;
        int end = array.length - 1;
        int pivot = partition(array,start,end);
        if(pivot > start + 1){
            stack.push(start);
            stack.push(pivot - 1);
        }
        if(pivot < end - 1){
            stack.push(pivot + 1);
            stack.push(end);
        }
        while(!stack.empty()){
            end = stack.pop();
            start = stack.pop();
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
     * 时间复杂度：O(N * logN)
     * 空间复杂度：O(N) -- 最后一步申请的数组和原数组大小相同
     * 稳定性：稳定的排序 -- 但是和取不取等号有关系
     *      目前稳定的排序：冒泡 插入 归并
     * @param array
     */
    public static void mergeSort(int[] array){
        mergeSortFunc(array,0,array.length - 1);
    }

    private static void mergeSortFunc(int[] array,int left,int right){
        if(left >= right){
            return;
        }
        int mid = (left + right) / 2;
        mergeSortFunc(array,left,mid);
        mergeSortFunc(array,mid + 1,right);

        merge(array,left,mid,right);//合并
    }

    /**
     * 归并
     * @param array
     * @param left
     * @param mid
     * @param right
     */
    private static void merge(int[] array,int left,int mid,int right) {
        int s1 = left;
        int e1 = mid;
        int s2 = mid + 1;
        int e2 = right;
        int[] tempArr = new int[right - left + 1];
        int tempIndex = 0;
        while(s1 <= e1 && s2 <= e2){
            if(array[s1] <= array[s2]){
                tempArr[tempIndex++] = array[s1++];
            }else{
                tempArr[tempIndex++] = array[s2++];
            }
        }
        while(s1 <= e1){
            tempArr[tempIndex++] = array[s1++];
        }
        while(s2 <= e2){
            tempArr[tempIndex++] = array[s2++];
        }
        tempIndex = 0;
        for(int i = left;i <= right;i++){
            array[i] = tempArr[tempIndex++];
        }
    }

    /**
     * 非递归版
     * @param array
     */
    public static void mergeSort1(int[] array){
        int gap = 1;
        while(gap < array.length){
            for (int i = 0; i < array.length; i = i + gap * 2) {
                int left = i;
                int mid = left + gap - 1;
                int right = mid + gap;
                if(mid >= array.length){
                    mid = array.length - 1;
                }
                if(right >= array.length){
                    right = array.length - 1;
                }
                merge(array,left,mid,right);
            }
            gap *= 2;
        }
    }

    /**
     * 计数排序
     * 时间复杂度：O(范围 + n)
     * 空间复杂度：O(范围)
     * 稳定性：本身是一个稳定的排序，但下面的写法不一定是一个稳定的写法
     * @param array array to be sorted
     */
    public static void countSort(int[] array){
        int minValue = array[0];
        int maxValue = array[0];
        for (int i = 1; i < array.length; i++) {
            if(array[i] < minValue){
                minValue = array[i];
            }
            if(array[i] > maxValue){
                maxValue = array[i];
            }
        }
        int range = maxValue - minValue + 1;
        int[] count = new int[range];
        for (int i = 0; i < array.length; i++) {
            count[array[i] - minValue]++;
        }
        int index = 0;
        for (int i = 0; i < count.length; i++) {
            for (int j = 0; j < count[i]; j++) {
                array[index++] = i + minValue;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {5,4,3,2,6,7,8,1,2,0};
        countSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }

}
