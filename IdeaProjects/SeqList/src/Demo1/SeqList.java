package Demo1;

import java.util.Arrays;

public class SeqList {
    private int[] elem;
    private int usedSize;
    private static final int DEFAULT_CAPACITY = 5;
    public SeqList(){
        this.elem = new int[DEFAULT_CAPACITY];
    }

    // 打印顺序表，注意：该方法并不是顺序表中的方法，为了方便看测试结果给出的
    public void display() {
        for (int i = 0; i < this.usedSize; i++) {
            System.out.print(this.elem[i] + " ");
        }
        System.out.println();
    }

    // 新增元素,默认在数据最后新增
    public void add(int data) {
        if(isFull()){
            resize();
        }
        this.elem[usedSize] = data;
        this.usedSize++;
    }

    public boolean isFull(){
        return this.usedSize == this.elem.length;
    }
    // 判定是否包含某个元素
    public boolean contains(int toFind) {
        for (int i = 0; i < usedSize; i++) {
            if(this.elem[i] == toFind){//如果elem中存储的是引用类型，应该调用equals方法
                return true;
            }
        }
        return false;
    }
    // 查找某个元素对应的位置 下标
    public int indexOf(int toFind) {
        for (int i = 0; i < usedSize; i++) {
            if(this.elem[i] == toFind){
                return i;
            }
        }
        return -1;
    }
    // 获取 pos 位置的元素
    public int get(int pos) {
        if(!checkPos(pos)){
            throw new PosOutBoundsException("get 获取数据时，位置不合法");
        }
        return this.elem[pos];
    }

    // 获取顺序表长度
    public int size() { return this.usedSize; }

    private  boolean checkPos(int pos){
        if(pos < 0 || pos > this.usedSize - 1){
            return false;
        }
        return true;
    }

    // 给 pos 位置的元素设为 value
    public void set(int pos, int value) {
        if(!checkPos(pos)){
            throw new PosOutBoundsException("set 数据时，位置不合法");
        }
        this.elem[pos] = value;
    }

    // 在 pos 位置新增元素
    public void add(int pos, int data) {
        if(pos < 0 || pos > usedSize){
            throw new PosOutBoundsException("add 元素时，pos位置不合法！");
        }
        if(isFull()){
            resize();
        }
        for (int i = usedSize; i > pos; i--) {
            elem[i] = elem[i - 1];
        }
        elem[pos] = data;
        usedSize++;
    }

    private void resize(){//扩容
        elem = Arrays.copyOf(elem,2 * elem.length);
    }

    //删除第一次出现的关键字key
    public void remove(int toRemove) {
        if(isEmpty()){//这段别忘了
            return;
        }
        int index = indexOf(toRemove);//学会复用之前写过的代码
        if(index == -1){
            throw new PosOutBoundsException("key 不存在！");
        }
        for (int i = index; i < this.usedSize - 1; i++) {
            this.elem[i] = this.elem[i + 1];
        }
        //如果是引用类型，也要把最后一个数据元素置空
        //this.elem[this.usedSize] = null;
        this.usedSize--;
    }

    public boolean isEmpty(){
        return this.usedSize == 0;
    }

    // 清空顺序表
    public void clear() {//当存的数据不是基本数据类型时，要把里面每一个下标对应的数据元素都置空(null)
        /*for (int i = 0; i < this.usedSize; i++) {
            this.elem[i] = null;
        }*/
        this.usedSize = 0;
    }



}