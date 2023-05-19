import java.util.Arrays;
import java.util.Random;

public class Test20230516 {
    public static void orderedArray1(int[] array){
        for(int i = 0;i < array.length;i++){
            array[i] = i;
        }
    }
    public static void orderedArray2(int[] array){
        for(int i = 0;i < array.length;i++){
            array[i] = array.length - i;
        }
    }
    public static void notOrderedArray(int[] array){
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(1_0000);
        }
    }
    public static boolean isSorted(int[] array){
        for (int i = 0; i < array.length - 1; i++) {
            if(!(array[i] <= array[i + 1])){
                return false;
            }
        }
        return true;
    }
    public static void testBubbleSort(int[] orderedArray1,int[] orderedArray2,int[] notOrderedArray){
        orderedArray1 = Arrays.copyOf(orderedArray1,orderedArray1.length);
        orderedArray2 = Arrays.copyOf(orderedArray2,orderedArray2.length);
        notOrderedArray = Arrays.copyOf(notOrderedArray,notOrderedArray.length);

        long startTime = System.currentTimeMillis();
        SortPublished20230515_16.bubbleSort(orderedArray1);
        long endTime = System.currentTimeMillis();
        System.out.println("bubbleSortTime - orderedArray1:  " + (endTime - startTime) + " " + isSorted(orderedArray1));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.bubbleSort(orderedArray2);
        endTime = System.currentTimeMillis();
        System.out.println("bubbleSortTime - orderedArray2:  " + (endTime - startTime) + " " + isSorted(orderedArray2));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.bubbleSort(notOrderedArray);
        endTime = System.currentTimeMillis();
        System.out.println("bubbleSortTime - notOrderedArray:  " + (endTime - startTime) + " " + isSorted(notOrderedArray));
        System.out.println();
    }

    public static void testInsertSort(int[] orderedArray1,int[] orderedArray2,int[] notOrderedArray){
        orderedArray1 = Arrays.copyOf(orderedArray1,orderedArray1.length);
        orderedArray2 = Arrays.copyOf(orderedArray2,orderedArray2.length);
        notOrderedArray = Arrays.copyOf(notOrderedArray,notOrderedArray.length);

        long startTime = System.currentTimeMillis();
        SortPublished20230515_16.insertSort(orderedArray1);
        long endTime = System.currentTimeMillis();
        System.out.println("insertSortTime - orderedArray1:  " + (endTime - startTime) + " " + isSorted(orderedArray1));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.insertSort(orderedArray2);
        endTime = System.currentTimeMillis();
        System.out.println("insertSortTime - orderedArray2:  " + (endTime - startTime) + " " + isSorted(orderedArray2));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.insertSort(notOrderedArray);
        endTime = System.currentTimeMillis();
        System.out.println("insertSortTime - notOrderedArray:  " + (endTime - startTime) + " " + isSorted(notOrderedArray));
        System.out.println();
    }

    public static void testQuickSort(int[] orderedArray1,int[] orderedArray2,int[] notOrderedArray){
        orderedArray1 = Arrays.copyOf(orderedArray1,orderedArray1.length);
        orderedArray2 = Arrays.copyOf(orderedArray2,orderedArray2.length);
        notOrderedArray = Arrays.copyOf(notOrderedArray,notOrderedArray.length);

        long startTime = System.currentTimeMillis();
        SortPublished20230515_16.quickSort(orderedArray1);
        long endTime = System.currentTimeMillis();
        System.out.println("quickSortTime - orderedArray1:  " + (endTime - startTime) + " " + isSorted(orderedArray1));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.quickSort(orderedArray2);
        endTime = System.currentTimeMillis();
        System.out.println("quickSortTime - orderedArray2:  " + (endTime - startTime) + " " + isSorted(orderedArray2));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.quickSort(notOrderedArray);
        endTime = System.currentTimeMillis();
        System.out.println("quickSortTime - notOrderedArray:  " + (endTime - startTime) + " " + isSorted(notOrderedArray));
        System.out.println();
    }

    public static void testQuickSort1(int[] orderedArray1,int[] orderedArray2,int[] notOrderedArray){
        orderedArray1 = Arrays.copyOf(orderedArray1,orderedArray1.length);
        orderedArray2 = Arrays.copyOf(orderedArray2,orderedArray2.length);
        notOrderedArray = Arrays.copyOf(notOrderedArray,notOrderedArray.length);

        long startTime = System.currentTimeMillis();
        SortPublished20230515_16.quickSort1(orderedArray1);
        long endTime = System.currentTimeMillis();
        System.out.println("quickSort1Time - orderedArray1:  " + (endTime - startTime) + " " + isSorted(orderedArray1));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.quickSort1(orderedArray2);
        endTime = System.currentTimeMillis();
        System.out.println("quickSort1Time - orderedArray2:  " + (endTime - startTime) + " " + isSorted(orderedArray2));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.quickSort1(notOrderedArray);
        endTime = System.currentTimeMillis();
        System.out.println("quickSort1Time - notOrderedArray:  " + (endTime - startTime) + " " + isSorted(notOrderedArray));
        System.out.println();
    }

    public static void testShellSort(int[] orderedArray1,int[] orderedArray2,int[] notOrderedArray){
        orderedArray1 = Arrays.copyOf(orderedArray1,orderedArray1.length);
        orderedArray2 = Arrays.copyOf(orderedArray2,orderedArray2.length);
        notOrderedArray = Arrays.copyOf(notOrderedArray,notOrderedArray.length);

        long startTime = System.currentTimeMillis();
        SortPublished20230515_16.shellSort(orderedArray1);
        long endTime = System.currentTimeMillis();
        System.out.println("shellSortTime - orderedArray1:  " + (endTime - startTime) + " " + isSorted(orderedArray1));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.shellSort(orderedArray2);
        endTime = System.currentTimeMillis();
        System.out.println("shellSortTime - orderedArray2:  " + (endTime - startTime) + " " + isSorted(orderedArray2));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.shellSort(notOrderedArray);
        endTime = System.currentTimeMillis();
        System.out.println("shellSortTime - notOrderedArray:  " + (endTime - startTime) + " " + isSorted(notOrderedArray));
        System.out.println();
    }

    public static void testSelectSort(int[] orderedArray1,int[] orderedArray2,int[] notOrderedArray){
        orderedArray1 = Arrays.copyOf(orderedArray1,orderedArray1.length);
        orderedArray2 = Arrays.copyOf(orderedArray2,orderedArray2.length);
        notOrderedArray = Arrays.copyOf(notOrderedArray,notOrderedArray.length);

        long startTime = System.currentTimeMillis();
        SortPublished20230515_16.selectSort(orderedArray1);
        long endTime = System.currentTimeMillis();
        System.out.println("selectSortTime - orderedArray1:  " + (endTime - startTime) + " " + isSorted(orderedArray1));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.selectSort(orderedArray2);
        endTime = System.currentTimeMillis();
        System.out.println("selectSortTime - orderedArray2:  " + (endTime - startTime) + " " + isSorted(orderedArray2));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.selectSort(notOrderedArray);
        endTime = System.currentTimeMillis();
        System.out.println("selectSortTime - notOrderedArray:  " + (endTime - startTime) + " " + isSorted(notOrderedArray));
        System.out.println();
    }

    public static void testSelectSort1(int[] orderedArray1,int[] orderedArray2,int[] notOrderedArray){
        orderedArray1 = Arrays.copyOf(orderedArray1,orderedArray1.length);
        orderedArray2 = Arrays.copyOf(orderedArray2,orderedArray2.length);
        notOrderedArray = Arrays.copyOf(notOrderedArray,notOrderedArray.length);

        long startTime = System.currentTimeMillis();
        SortPublished20230515_16.selectSort1(orderedArray1);
        long endTime = System.currentTimeMillis();
        System.out.println("selectSort1Time - orderedArray1:  " + (endTime - startTime) + " " + isSorted(orderedArray1));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.selectSort1(orderedArray2);
        endTime = System.currentTimeMillis();
        System.out.println("selectSort1Time - orderedArray2:  " + (endTime - startTime) + " " + isSorted(orderedArray2));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.selectSort1(notOrderedArray);
        endTime = System.currentTimeMillis();
        System.out.println("selectSort1Time - notOrderedArray:  " + (endTime - startTime) + " " + isSorted(notOrderedArray));
        System.out.println();
    }

    public static void testHeapSort(int[] orderedArray1,int[] orderedArray2,int[] notOrderedArray){
        orderedArray1 = Arrays.copyOf(orderedArray1,orderedArray1.length);
        orderedArray2 = Arrays.copyOf(orderedArray2,orderedArray2.length);
        notOrderedArray = Arrays.copyOf(notOrderedArray,notOrderedArray.length);

        long startTime = System.currentTimeMillis();
        SortPublished20230515_16.heapSort(orderedArray1);
        long endTime = System.currentTimeMillis();
        System.out.println("heapSortTime - orderedArray1:  " + (endTime - startTime) + " " + isSorted(orderedArray1));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.heapSort(orderedArray2);
        endTime = System.currentTimeMillis();
        System.out.println("heapSortTime - orderedArray2:  " + (endTime - startTime) + " " + isSorted(orderedArray2));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.heapSort(notOrderedArray);
        endTime = System.currentTimeMillis();
        System.out.println("heapSortTime - notOrderedArray:  " + (endTime - startTime) + " " + isSorted(notOrderedArray));
        System.out.println();
    }

    public static void testMergeSort(int[] orderedArray1,int[] orderedArray2,int[] notOrderedArray){
        orderedArray1 = Arrays.copyOf(orderedArray1,orderedArray1.length);
        orderedArray2 = Arrays.copyOf(orderedArray2,orderedArray2.length);
        notOrderedArray = Arrays.copyOf(notOrderedArray,notOrderedArray.length);

        long startTime = System.currentTimeMillis();
        SortPublished20230515_16.mergeSort(orderedArray1);
        long endTime = System.currentTimeMillis();
        System.out.println("mergeSortTime - orderedArray1:  " + (endTime - startTime) + " " + isSorted(orderedArray1));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.mergeSort(orderedArray2);
        endTime = System.currentTimeMillis();
        System.out.println("mergeSortTime - orderedArray2:  " + (endTime - startTime) + " " + isSorted(orderedArray2));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.mergeSort(notOrderedArray);
        endTime = System.currentTimeMillis();
        System.out.println("mergeSortTime - notOrderedArray:  " + (endTime - startTime) + " " + isSorted(notOrderedArray));
        System.out.println();
    }

    public static void testMergeSort1(int[] orderedArray1,int[] orderedArray2,int[] notOrderedArray){
        orderedArray1 = Arrays.copyOf(orderedArray1,orderedArray1.length);
        orderedArray2 = Arrays.copyOf(orderedArray2,orderedArray2.length);
        notOrderedArray = Arrays.copyOf(notOrderedArray,notOrderedArray.length);

        long startTime = System.currentTimeMillis();
        SortPublished20230515_16.mergeSort1(orderedArray1);
        long endTime = System.currentTimeMillis();
        System.out.println("mergeSort1Time - orderedArray1:  " + (endTime - startTime) + " " + isSorted(orderedArray1));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.mergeSort1(orderedArray2);
        endTime = System.currentTimeMillis();
        System.out.println("mergeSort1Time - orderedArray2:  " + (endTime - startTime) + " " + isSorted(orderedArray2));

        startTime = System.currentTimeMillis();
        SortPublished20230515_16.mergeSort1(notOrderedArray);
        endTime = System.currentTimeMillis();
        System.out.println("mergeSort1Time - notOrderedArray:  " + (endTime - startTime) + " " + isSorted(notOrderedArray));
        System.out.println();
    }

    public static void main(String[] args) {
        int[] orderedArray1 = new int[1_0000];
        int[] orderedArray2 = new int[1_0000];
        int[] notOrderedArray = new int[1_0000];
        orderedArray1(orderedArray1);
        orderedArray2(orderedArray2);
        notOrderedArray(notOrderedArray);

        testBubbleSort(orderedArray1,orderedArray2,notOrderedArray);
        testInsertSort(orderedArray1,orderedArray2,notOrderedArray);
        testQuickSort(orderedArray1,orderedArray2,notOrderedArray);
        testQuickSort1(orderedArray1,orderedArray2,notOrderedArray);
        testShellSort(orderedArray1,orderedArray2,notOrderedArray);
        testSelectSort(orderedArray1,orderedArray2,notOrderedArray);
        testSelectSort1(orderedArray1,orderedArray2,notOrderedArray);
        testHeapSort(orderedArray1,orderedArray2,notOrderedArray);
        testMergeSort(orderedArray1,orderedArray2,notOrderedArray);
        testMergeSort1(orderedArray1,orderedArray2,notOrderedArray);


    }
}
