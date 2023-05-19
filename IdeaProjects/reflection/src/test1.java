import javax.print.DocFlavor;
import java.util.Comparator;
import java.util.PriorityQueue;

public class test1 {
    //有返回值多参数
    @FunctionalInterface
    interface MoreParameterReturn {
        int test(int a,int b);
    }
    public static void main(String[] args) {
        MoreParameterReturn moreParameterReturn = (a, b)->a + b;
        int ret = moreParameterReturn.test(1, 2);
        System.out.println(ret);

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2)->o2 - o1);

    }
}
