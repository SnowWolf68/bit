package demo1;

public class test1 {
    public static void main(String[] args) {
        try {
            System.out.println(10 / 0);
            System.out.println("test0");
        } catch (NullPointerException e){
            System.out.println();
        } catch (Exception e){
            System.out.println("捕获到ArithmeticException异常");
        }
        System.out.println("test1");
    }
}
