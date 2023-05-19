import java.util.*;

class Student implements Comparable<Student>{

    public String name;
    public int age;
    @Override
    public int compareTo(Student o) {
        return o.age - this.age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && Objects.equals(name, student.name);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
class AgeComparator implements Comparator<Student>{

    @Override
    public int compare(Student o1, Student o2) {
        return o1.age - o2.age;
    }
}
class NameComparator implements Comparator<Student>{

    @Override
    public int compare(Student o1, Student o2) {
        return o1.name.compareTo(o2.name);
    }
}
class Imp implements Comparator<Integer>{


    @Override
    public int compare(Integer o1, Integer o2) {
        return o2 - o1;
    }
}
public class Test {

    /**
     * 时间复杂度：
     * 最坏情况下：O(N ^ 2) -- 逆序情况下
     * 最好情况下：O(N) -- 本身就是有序的，只让i走一遍就行 --> 插入排序，当数据有序的时候，越快!!!
     * 空间复杂度：O(1)
     * 稳定性：稳定 TODO :
     * @param array
     */
    public static void insertSort(int[] array){
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if(array[j] > temp){
                    array[j + 1] = array[j];
                }else{
                    break;
                }
            }
            array[j + 1] = temp;
        }
    }

    public static void main(String[] args) {
        int[] array = {1,5,2,9,3,6};
        insertSort(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * Top K 问题的正确解法
     * @param array
     * @param k
     * @return
     */
    public static int[] smallestK1(int[] array,int k){
        int[] ret = new int[k];
        if(k == 0){
            return ret;
        }
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new Imp());
        for (int i = 0; i < k; i++) {
            maxHeap.offer(array[i]);
        }
        for (int i = k; i < array.length; i++) {
            int top = maxHeap.peek();
            if(top > array[i]){
                maxHeap.poll();
                maxHeap.offer(array[i]);
            }
        }

        for (int i = 0; i < k; i++) {
            ret[i] = maxHeap.poll();
        }
        return ret;
    }
    /**
     * Top K问题，但不是真正的解决方法
     * @param array
     * @param k
     * @return
     */
    public static int[] smallestK(int[] array,int k){
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int x : array){
            minHeap.offer(x);
        }
        int[] ret = new int[k];
        for (int i = 0; i < k; i++) {
            ret[i] = minHeap.poll();
        }
        return ret;
    }
    public static void main3(String[] args) {
        Student s1 = new Student();
        s1.name = "zhangsan";
        s1.age = 10;
        Student s2 = new Student();
        s2.name = "zhangsan";
        s2.age = 9;

        PriorityQueue<Student> priorityQueue = new PriorityQueue<>();

        priorityQueue.offer(s1);
        priorityQueue.offer(s2);

        System.out.println(priorityQueue.poll());
        System.out.println(priorityQueue.poll());

    }
    public static void main2(String[] args) {
        Student s1 = new Student();
        s1.name = "zhangsan";
        s1.age = 10;
        Student s2 = new Student();
        s2.name = "zhangsan";
        s2.age = 9;
        /*System.out.println(s1.equals(s2));
        System.out.println(s1.compareTo(s2));*/
        AgeComparator ageComparator = new AgeComparator();
        System.out.println(ageComparator.compare(s1, s2));

        NameComparator nameComparator = new NameComparator();
        System.out.println(nameComparator.compare(s1, s2));
    }
    public static void main1(String[] args) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

        Queue<Integer> priorityQueue1 = new PriorityQueue<>();

        priorityQueue1.offer(1);
        priorityQueue1.offer(2);
        priorityQueue1.offer(3);

        System.out.println(priorityQueue1.poll());
        System.out.println(priorityQueue1.poll());
        System.out.println(priorityQueue1.poll());

        Queue<Student> priorityQueue2 = new PriorityQueue<>();
        priorityQueue2.offer(new Student());

    }
}
