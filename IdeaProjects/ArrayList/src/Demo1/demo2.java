package Demo1;

import java.util.ArrayList;

public class demo2 {
    public static void func(String str1,String str2){//CVTE面试题：删除第一个字符串中的第二个字符串
        ArrayList<Character> arrayList = new ArrayList<>();
        for(int i = 0;i < str1.length();i++){
            if(!str2.contains(str1.charAt(i) + "")){
                arrayList.add(str1.charAt(i));
            }
        }
    }
    public static void main(String[] args) {

    }
}
