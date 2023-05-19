import java.util.Arrays;
import java.util.Random;

public class TestSort {
    public static void orderedArray(int[] array){
        for(int i = 0;i < array.length;i++){
            array[i] = array.length - i;
        }
    }
    public static void notOrderedArray(int[] array){
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(10_0000);
        }
    }
    public static void testInsertSort(int[] array){
        array = Arrays.copyOf(array,array.length);
        long startTime = System.currentTimeMillis();
        Sort.insertSort(array);
        long endTime = System.currentTimeMillis();
        System.out.println("insertSortTime:" + (endTime - startTime) + " " + isSorted(array));
    }
    public static void testShellSort(int[] array){
        array = Arrays.copyOf(array,array.length);
        long startTime = System.currentTimeMillis();
        Sort.shellSort(array);
        long endTime = System.currentTimeMillis();
        System.out.println("shellSortTime:" + (endTime - startTime) + " " + isSorted(array));
    }
    public static void testSelectSort(int[] array){
        array = Arrays.copyOf(array,array.length);
        long startTime = System.currentTimeMillis();
        Sort.selectSort(array);
        long endTime = System.currentTimeMillis();
        System.out.println("selectSortTime:" + (endTime - startTime) + " " + isSorted(array));
    }

    public static void testSelectSortPlus(int[] array){
        array = Arrays.copyOf(array,array.length);
        long startTime = System.currentTimeMillis();
        Sort.selectSortPlus(array);
        long endTime = System.currentTimeMillis();
        System.out.println("selectSortPlusTime:" + (endTime - startTime) + " " + isSorted(array));
    }

    public static void testQuickSort(int[] array){
        array = Arrays.copyOf(array,array.length);
        long startTime = System.currentTimeMillis();
        Sort.quickSort(array);
        long endTime = System.currentTimeMillis();
        System.out.println("quickSortTime:" + (endTime - startTime) + " " + isSorted(array));
    }

    public static void testMergeSort(int[] array){
        array = Arrays.copyOf(array,array.length);
        long startTime = System.currentTimeMillis();
        Sort.mergeSort(array);
        long endTime = System.currentTimeMillis();
        System.out.println("mergeSortTime:" + (endTime - startTime) + " " + isSorted(array));
    }

    public static boolean isSorted(int[] array){
        for (int i = 0; i < array.length - 1; i++) {
            if(!(array[i] < array[i + 1])){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] array = new int[100_0000];
        orderedArray(array);
//        testInsertSort(array);
//        testSelectSort(array);
//        testShellSort(array);
//        testSelectSortPlus(array);
        testQuickSort(array);
//        testMergeSort(array);
        System.out.println("ads");
    }
}
