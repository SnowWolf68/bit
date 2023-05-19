public class test {
    public static void main(String[] args) {
        int[] array = {27,15,19,18,28,34,65,49,25,37};
        TestHeapPublished testHeapPublished = new TestHeapPublished();
        testHeapPublished.createHeap(array);
        testHeapPublished.push(38);
        testHeapPublished.pop();
        System.out.println("AAAAA");
    }
    public static void main1(String[] args) {
        TestHeap testHeap = new TestHeap();
        int[] array = {27,15,19,18,28,34,65,49,25,37};
        testHeap.createHeap(array);

        //testHeap.push(80);
        //testHeap.pop();
        testHeap.heapSort();

    }
}
