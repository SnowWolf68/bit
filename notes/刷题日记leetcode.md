### 257.二叉树的所有路径

leetcode链接：[257. 二叉树的所有路径 - 力扣（Leetcode）](https://leetcode.cn/problems/binary-tree-paths/description/)

```java
	public List<String> binaryTreePaths(TreeNode root) {
        String string = new String();
        List<String> ret = new ArrayList<>();
        binaryTreePathsChild(root,string,ret);
        return ret;
    }

    private void binaryTreePathsChild(TreeNode root, String string, List<String> ret) {
        StringBuilder stringBuilder = new StringBuilder(string);
        stringBuilder.append(root.val);
        if(root.left == null && root.right == null){
            ret.add(stringBuilder.toString());
            return;
        }
        stringBuilder.append("->");
        if(root.left != null){
            binaryTreePathsChild(root.left, stringBuilder.toString(),ret);
        }
        if(root.right != null){
            binaryTreePathsChild(root.right,stringBuilder.toString(),ret);
        }
    }
```

**有一点要注意的是**，为了避免子函数递归完之后String Builder中的残留的字符对下一次递归产生影响（虽然StringBuilder提供了delete方法，但是StringBuilder没有提供查看当前对象中字符串长度以及是否为空的方法，所以导致如果我们递归结束时候删除本次递归新插入的字符的时候，当当前节点为根节点的时候，会多删一个"->"，从而会产生异常），可以把函数的参数从StringBuilder变成String，**每次都传入当前的**String，然后每次进入函数的时候再**用传进来的这个String重新创建一个StringBuilder对象**，然后再进行append操作，最后再调用toStirng把转成的String继续传给子递归，这样虽然每次依旧会添加字符，但是这些字符**是在String Builder中添加的**，**对原始的String并没有影响**，所以子递归return之后**不会有残留的字符在String中**，影响下一次递归

### 404.左叶子之和

leetcode链接：[404. 左叶子之和 - 力扣（Leetcode）](https://leetcode.cn/problems/sum-of-left-leaves/)

判断是否是左节点的方法是用单独的一个`boolean`型变量

对于当前节点：

如果当前节点就是叶子节点，判断`isLeft`是`true`或`false`，如果是左叶子节点，那么把当前节点的`val`加到`sum`中，然后返回上一层递归

​                                                                                                         如果不是，直接返回上一层递归

如果当前节点有左子树，把`isLeft`置为`true`，然后递归左子树

如果当前节点有右子树，把`isLeft`置为`false`，然后递归右子树

```java
	public int sumOfLeftLeaves(TreeNode root) {
        boolean isLeft = false;
        sumOfLeftLeavesChild(root,isLeft);
        return sum1;
    }

    private int sum1;
    private void sumOfLeftLeavesChild(TreeNode root, boolean isLeft) {
        if(root.left == null && root.right == null){
            if(isLeft){
                sum1 += root.val;
                return;
            }else{
                return;
            }
        }
        if(root.left != null){
            isLeft = true;
            sumOfLeftLeavesChild(root.left,isLeft);
        }
        if(root.right != null){
            isLeft = false;
            sumOfLeftLeavesChild(root.right,isLeft);
        }
    }
```

```java
		//其中的一段代码可以简化为下面的
		if(root.left == null && root.right == null){
            if(isLeft){
                sum += root.val;
            }
            return;
        }
```

注意，在`if(root.right != null)`中，不要忘了把`isLeft`再置为`false`，因为如果上面走了`if(root.left != null)`分支的话，会把`isLeft`置为`true`，而此时在`if(root.right != null)`中，`isLeft`应该是`false`

### 501.二叉搜索树中的众数

leetcode链接：

[501. 二叉搜索树中的众数 - 力扣（Leetcode）](https://leetcode.cn/problems/find-mode-in-binary-search-tree/description/)

看到“出现次数”，下意识想到用HashMap来记录，然后遍历找到出现次数最多的就行了，但是这样做无法达到空间复杂度为O(1)

```java
	public int[] findMode(TreeNode root) {
        if(root == null){
            return new int[0];
        }
        Map<Integer,Integer> map = new HashMap<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        int maxCount = 0;
        while(!stack.isEmpty()){
            TreeNode cur = stack.pop();
            int count = map.getOrDefault(cur.val,0);
            if(count + 1 > maxCount){
                maxCount = count + 1;
            }
            map.put(cur.val,count + 1);
            if(cur.right != null){
                stack.push(cur.right);
            }
            if(cur.left != null){
                stack.push(cur.left);
            }
        }
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        List<Integer> list = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry : entries){
            if(entry.getValue() == maxCount){
                list.add(entry.getKey());
            }
        }
        int[] ret = new int[list.size()];
        int index = 0;
        for (Integer integer : list) {
            ret[index++] = integer;
        }
        return ret;
    }
```

一开始的错误原因：`maxCount = count`，maxCount的更新错了

### 530.二叉搜索树的最小绝对差

leetcode链接：

[530. 二叉搜索树的最小绝对差 - 力扣（Leetcode）](https://leetcode.cn/problems/minimum-absolute-difference-in-bst/description/)

出现“二叉搜索树”，反手就是一个中序遍历，在得到的list中找相邻元素差的最小值就行了

**注意：**二叉树的前中后序遍历的**非递归**也要能熟练写出来

```java
public int getMinimumDifference(TreeNode root) {
        if(root == null){
            return 0;
        }
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while(cur != null || !stack.isEmpty()){
            while(cur != null){
                stack.push(cur);
                cur = cur.left;
            }
            TreeNode top = stack.pop();
            list.add(top.val);
            cur = top.right;
        }
        if(list.size() == 1){
            return 0;
        }
        int minDif = list.get(1) - list.get(0);
        for (int i = 0; i < list.size() - 1; i++) {
            if(list.get(i + 1) - list.get(i) < minDif){
                minDif = list.get(i + 1) - list.get(i);
            }
        }
        return minDif;
    }
```

#### 附：二叉树的中序遍历非递归

```java
 	public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if(root == null){
            return ret;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while(cur != null || !stack.isEmpty()){//注意这里是||而不是&&
            while(cur != null){
                stack.add(cur);
                cur = cur.left;
            }
            TreeNode top = stack.pop();
            ret.add(top.val);
            cur = top.right;//这里记得一定要是top.right
        }
        return ret;
    }
```

用两层循环来模拟递归左树和递归右树的情况，只要是`cur != null`或者`!stack.isEmpty()`就进入while循环，首先将当前cur加到stack当中（因为找完左树之后还要找这个节点的右树，所以要把这个节点记录一下）

内层循环首先循环去找cur的左子树，当内层循环结束的时候此时`cur == null`然后从stack中pop栈顶元素，然后让cur指向弹出的栈顶元素top的右子树，然后继续循环遍历`top.right`的左子树

#### 附：二叉树的前序遍历非递归

前序遍历非递归和中序遍历差不多，只不过`ret.add()`的位置有区别，下面是代码：

```java
	public int[] preorderTraversal (TreeNode root) {
        // write code here
        
        List<Integer> list = new ArrayList<>();
        if(root == null){
            return new int[0]; 
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while(cur != null || !stack.isEmpty()){
            while(cur != null){
                stack.add(cur);
                list.add(cur.val);
                cur = cur.left;
            }
            TreeNode top = stack.pop();
            cur = top.right;
        }
        int[] ret = new int[list.size()];
        int index = 0;
        for(Integer i : list){
            ret[index++] = i;
        }
        return ret;
    }
```

#### 附：二叉树的前序遍历非递归

后序遍历和之前两个有较大差别，因为后序遍历是要在左右子树都遍历完之后再打印根节点的值，然而在上面的代码中，外层循环不断找右子树的过程中，一旦遍历到最后右子树为空的时候，循环会直接退出，所以需要在外层循环上加一个if判断，当遍历到最后的右子树为空时，把子树的根节点加到list当中，否则就一直往右子树去找

还有一点需要注意的是：如果所有的右子树都已经遍历完了，往回添加的时候，除了最后一个节点的右子树为null之外，其余的节点的右子树都不一定为null，但是此时需要的是把子树的根节点添加到list集合当中，而不是继续遍历右子树，所以还需要一个prev指向上一个添加到list集合中的节点，如果当前节点的右子节点为prev的话，证明此时是所有的右子树都遍历完了，应该往回走并且把当前子树的根节点添加到list集合当中了

下面是代码：

```java
	public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if(root == null){
            return ret;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        TreeNode prev = null;
        while(cur != null || !stack.empty()){
            while(cur != null){
                stack.push(cur);
                //ret.add(cur.val);
                cur = cur.left;
            }
            //cur == null
            TreeNode top = stack.peek();
            if(top.right == null || top.right == prev){
                ret.add(top.val);
                prev = top;
                stack.pop();
            }else{
                cur = top.right;
            }
        }
        return ret;
    }
```



### 543.二叉树的直径

首先想到整棵树的最大直径可以拆分为左子树的最大直径和右子树的最大直径来求，所以想到用递归

这是第一版代码

```java
	public int diameterOfBinaryTree(TreeNode root) {
        if(root == null || (root.left == null && root.right == null)){
            return 0;
        }
        if((root.left != null && root.right == null)
        || (root.left == null && root.right != null)){
            return 1;
        }
        return diameterOfBinaryTreeChild(root.left) + diameterOfBinaryTreeChild(root.right);
    }
    private int diameterOfBinaryTreeChild(TreeNode root){
        if(root == null){
            return 0;
        }
        if(root.left == null && root.right == null){
            return 1;
        }
        int leftDepth = diameterOfBinaryTreeChild(root.left);
        int rightDepth = diameterOfBinaryTreeChild(root.right);
        return Math.max(leftDepth,rightDepth) + 1;
    }
```

发现单分支的时候答案错误

找到错误原因是在这里：

```java
	if((root.left != null && root.right == null)
        || (root.left == null && root.right != null)){
            return 1;
        }
```

这段代码当遇到单分支的情况时就直接返回1，而不是递归去找不为空的那一棵子树，于是进行修改

发现如果遇到单分支的情况，再写if判断递归左子树还是递归右子树的效果，和直接把这段代码去掉的效果是一样的，因为当一边为空时，由第一个if可知，此时为空的这一边返回的是0，与另一边取最大值，结果还是另一边的深度，所以可以直接去掉，于是有了第二版：

```java
	public int diameterOfBinaryTree(TreeNode root) {
        if(root == null || (root.left == null && root.right == null)){
            return 0;
        }
        return diameterOfBinaryTreeChild(root.left) + diameterOfBinaryTreeChild(root.right);
    }
    private int diameterOfBinaryTreeChild(TreeNode root){
        if(root == null){
            return 0;
        }
        if(root.left == null && root.right == null){
            return 1;
        }
        int leftDepth = diameterOfBinaryTreeChild(root.left);
        int rightDepth = diameterOfBinaryTreeChild(root.right);
        return Math.max(leftDepth,rightDepth) + 1;
    }
```

然后发现，`diameterOfBinaryTreeChild`中的

```java
	if(root.left == null && root.right == null){
            return 1;
        }
```

也可以进行优化，因为如果两边都为空的话，分别再去递归左边和右边得到的结果都是0（因为第一个if），然后返回值为左右两边的最大值（都是0）+ 1 = 1，所以上面那段代码也是可以直接删掉的

于是有了下面的代码：

```java
	public int diameterOfBinaryTree(TreeNode root) {
        if(root == null || (root.left == null && root.right == null)){
            return 0;
        }
        return diameterOfBinaryTreeChild(root.left) + diameterOfBinaryTreeChild(root.right);
    }
    private int diameterOfBinaryTreeChild(TreeNode root){
        if(root == null){
            return 0;
        }
        int leftDepth = diameterOfBinaryTreeChild(root.left);
        int rightDepth = diameterOfBinaryTreeChild(root.right);
        return Math.max(leftDepth,rightDepth) + 1;
    }
```

同样的道理，`if(root == null || (root.left == null && root.right == null)){`这段if的后半条件也可以直接删掉

于是有了：

```java
	public int diameterOfBinaryTree(TreeNode root) {
        if(root == null){
            return 0;
        }
        return diameterOfBinaryTreeChild(root.left) + diameterOfBinaryTreeChild(root.right);
    }
    private int diameterOfBinaryTreeChild(TreeNode root){
        if(root == null){
            return 0;
        }
        int leftDepth = diameterOfBinaryTreeChild(root.left);
        int rightDepth = diameterOfBinaryTreeChild(root.right);
        return Math.max(leftDepth,rightDepth) + 1;
    }
```

但是还是有数据过不了，问了一下gpt：

![image-20230518155403022](E:\bit\notes\img\image-20230518155403022.png)

发现这样的代码只考虑了过根节点的情况，还有可能**最大直径是不过根节点的**

所以需要单独拿一个**成员变量maxDiameter来记录最大直径**，而在maxDepth函数中，当算出来左子树的最大深度leftDepth以及右子树的最大深度rightDepth之后，需要判断一下当前的最大直径（leftDepth + rightDepth）是不是大于之前保存的maxDiameter，如果大于，就要**更新maxDiameter**，最后再返回左右子树的最大深度 + 1，完成正常maxDepth的正常功能

还有一点需要注意，就是为什么当前的最大直径是leftDepth + rightDepth，注意在求最大深度的时候，算的是**节点的个数**，而算最大直径的时候，计算的是**节点与节点之间的边的个数**，**所以对于任意一个节点，它当前的最大直径就是左右节点的最大深度 + 1**

所以最后能AC的代码为：

```java
	public int maxDiameter = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        if(root == null){
            return 0;
        }
        diameterOfBinaryTreeChild(root);
        return maxDiameter;
    }
    private int diameterOfBinaryTreeChild(TreeNode root){
        if(root == null){
            return 0;
        }
        int leftDepth = diameterOfBinaryTreeChild(root.left);
        int rightDepth = diameterOfBinaryTreeChild(root.right);
        maxDiameter = Math.max(maxDiameter,leftDepth + rightDepth);
        return Math.max(leftDepth,rightDepth) + 1;
    }
```

或者也可以把`diameterOfBinaryTreeChild`的名字改成`maxDepth`，这样更合适一点：

```java
	public int maxDiameter = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        if(root == null){
            return 0;
        }
        maxDepth(root);
        return maxDiameter;
    }
    private int maxDepth(TreeNode root){
        if(root == null){
            return 0;
        }
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        maxDiameter = Math.max(maxDiameter,leftDepth + rightDepth);
        return Math.max(leftDepth,rightDepth) + 1;
    }
```

### 105.从前序与中序遍历序列构造二叉树

链接：

[105. 从前序与中序遍历序列构造二叉树 - 力扣（Leetcode）](https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/)

前序遍历的特点是先打印根节点，所以遍历一次前序遍历的数组，那么每次拿到的都是当前的根节点，然后在中序遍历的数组中找到当前的这个节点，左边的就是当前节点的左子树，右边就是当前节点的右子树。

由于函数需要递归调用，所以把遍历前序遍历数组的`preIndex`放在成员变量位置进行保存

而且，由于每次找到当前的根节点在中序遍历数组当中的位置之后，需要将中序遍历数组分割成左右两部分，然后分别建立这两部分的两棵子树，然后再通过上一层递归接收两次子递归的返回值，从而建立起一整棵二叉树，所以这个递归的函数需要再另外接收两个`int`值`start`和`end`分别表示建立子树的节点对应在中序遍历的数组中的范围

具体来说，在递归的函数中：
首先判断`start`和`end`的大小关系，如果`start > end`那么应该返回一个`null`，这里要特别注意，停止的条件是`start > end`时返回一个`null`，而不是`start == end`时返回新建的当前节点，因为叶子节点的左右子节点都应该是`null`，而如果按照第二种错误的方式来写的话，那么最后建立完叶子节点就返回了

emmm上面我的解释有点问题，我又不明白了，于是又去找了GPT4聊聊，聊完就大概明白了

和GPT4又交流了好久：https://poe.com/s/O51wrnzpvC8peSHX8QqW

这样就没有啥问题了，下面贴一下AC的代码：

```java
	public int preIndex = 0;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTreeChild(preorder,inorder,0,preorder.length - 1);
    }
    private TreeNode buildTreeChild(int[] preorder,int[] inorder,int start,int end){
        if(start > end){
            return null;
        }
        int fIndex = findIndex(preorder,inorder);
        TreeNode cur = new TreeNode(preorder[preIndex++]);
        cur.left = buildTreeChild(preorder,inorder,start,fIndex - 1);
        cur.right = buildTreeChild(preorder,inorder,fIndex + 1,end);
        return cur;
    }
    private int findIndex(int[] preorder,int[] inorder){
        for(int i = 0;i < inorder.length;i++){
            if(preorder[preIndex] == inorder[i]){
                return i;
            }
        }
        return -1;
    }
```



### 106.从中序与后序遍历序列构造二叉树

这题跟上面那题差不多，但是还是没有一遍过，所以想着还是再整理一下思路

中序和后序建立二叉树和中序和前序建立二叉树的区别就在与找根节点的顺序

中序和前序建立二叉树时，前序序列从前往后遍历的每一个就是当前子树的根节点，因为前序遍历的顺序是（根，左，右）

而中序和后续建立二叉树时，每一个根节点应该是后序遍历序列从最后一个元素依次向前遍历，因为后续遍历的顺序是（左，右，根）

以上的差别在代码中表现为下面两点区别：

1.之前每次建立的根节点在`preorder`中的下标是用`preIndex`来记录的，并且`preIndex`是从`0`开始遍历，一直到`preorder.length - 1`结束，那么后序遍历的下标`postIndex`就应该从`postorder`的最后一个元素`postorder.length - 1`开始，并且向前遍历

2.这点很容易被忽略，就是当前根节点创建好了之后，左右子节点的创建顺序，在前序和中序创建二叉树的时候，顺序是先左子节点，再右子节点，这是因为前序遍历的顺序就是先根，再左子树，再右子树

而到了后序，由于后序遍历的顺序是先左子节点，再右子节点，最后才是根节点，并且`postIndex`是从后序序列的最后一个开始向前遍历的，所以当根创建好了之后，应该先递归创建右子树，然后才是递归创建左子树

但是这里可能会有一个疑问，就是根已经创建好了，那么创建左右子树的区间肯定是位于中序遍历中根节点位置的左右两侧，那么先创建左子树还是先创建右子树不是没有区别吗？

但实际上不是这样的，因为还有一个大的前提，就是`postIndex`是从`postorder`的最后一个开始向前遍历的，所以当`postIndex`走到当前的根节点之后，那么它下一个走到的应该是当前根节点的右子节点，而每次创建子树都是根据`postIndex`位置上的元素以及该元素在`inorder`中的位置进行创建的，所以如果还是先创建左子树，再创建右子树的话，那么就会出现，在当前节点的左子树中寻找当前节点的右子节点的情况，那么这样肯定是错误的

注意这两个问题以后，代码应该就没有什么问题了，下面是AC代码：

```java
	public int postIndex;
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        postIndex = inorder.length - 1;//不同点1
        return buildTreeChild(inorder,postorder,0,inorder.length - 1);
    }
    private TreeNode buildTreeChild(int[] inorder,int[] postorder,int start,int end){
        if(start > end){
            return null;
        }
        int fIndex = findIndex(inorder,postorder);
        TreeNode cur = new TreeNode(postorder[postIndex--]);
        cur.right = buildTreeChild(inorder,postorder,fIndex + 1,end);//不同点2
        cur.left = buildTreeChild(inorder,postorder,start,fIndex - 1);
        return cur;
    }
    private int findIndex(int[] inorder,int[] postorder){
        for(int i = 0;i < inorder.length;i++){
            if(inorder[i] == postorder[postIndex]){
                return i;
            }
        }
        return -1;
    }
```

### 面试题 17.14. 最小K个数

在做牛客上的题的时候，遇到了Top K问题，于是想着先再来复习一下最基本的Top K问题

上链接：[面试题 17.14. 最小K个数 - 力扣（Leetcode）](https://leetcode.cn/problems/smallest-k-lcci/description/)

具体思路就是先建立相反的堆（大根堆），然后遍历原来的数组，如果是在前k个之内，那么就直接添加到堆中

如果是前k个之外，那么就要首先`peek`一下堆顶元素，和当前数组中的元素进行比较，如果当前元素小于堆顶元素，那么就把堆顶元素`poll`出来，然后`offer`进数组当前的元素

否则的话就继续遍历当前数组中的下一个元素

最后把堆中的元素全部`poll`出来放在`int`数组中就行了，但是要注意此时顺序`poll`出来的元素的顺序是正好反过来的，也就是说此时`int`数组中的元素是一个**降序**的序列，如果我们想获得从小到大（升序）的序列的话，需要再手动逆序一下

这里注明一点，如果是`Collection`类的话，可以用`Collections`工具类中的`reverse`进行快速逆序，但是本题的返回值是`int[]`

不过本题并没有要求这k个元素的返回顺序，所以也不用管这一点

下面是AC代码：

```java
	public int[] smallestK(int[] arr, int k) {
        int[] ret = new int[k];
        if(k == 0){
            return ret;
        }
        // PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new Comparator<Integer>(){
        //     @Override
        //     public int compare(Integer o1,Integer o2){//匿名内部类
        //         return o2 - o1;
        //     }
        // });
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1,o2) -> o2 - o1);//lambda表达式
        for(int i = 0;i < k;i++){
            maxHeap.offer(arr[i]);
        }
        for(int i = k;i < arr.length;i++){
            int top = maxHeap.peek();
            if(arr[i] < top){
                maxHeap.poll();
                maxHeap.offer(arr[i]);
            }
        }
        for(int i = 0;i < k;i++){
            ret[i] = maxHeap.poll();
        }
        return ret;
    }
```

有一点需要注意的是，之前创建大根堆采用的方法是写一个`Imp`类实现`Comparator`接口，然后重写`Comparator`接口中的`Compare`函数

但是这一步可以简化，从源码中可以看到，`Comparator`接口是一个函数式接口

![image-20230520114544525](E:\bit\notes\img\image-20230520114544525.png)

所以可以使用匿名内部类来简化

除此之外，还可以使用lambda表达式进一步简化匿名内部类的写法

以上的两种方法在上面的代码中均有体现

### 95.不同的二叉搜索树 II

如何找到所有的情况？ -- 遍历当前数组，让每一个元素都当一次根节点

对于其中的某一个根节点，以这个节点为根的所有二叉树的情况可以转换为**左子树和右子树各自所有种类的任意组合**

也就是说对于任意节点，可以首先递归左右子树，返回两个分别包含左右子树所有情况的List集合，然后对于根节点，可以用两个循环分别遍历左右子树的所有情况，然后分别链接到当前的根节点上

注意：

这里对左右子树的递归调用（也相当于左右子树的建立）是在根绑定左右子树之前，原因也很好理解，因为首先要有左右子树，然后才能有左右子树的父节点



下面是AC代码：

```java
	public List<TreeNode> generateTrees(int n) {
        return generateTreesChild(1,n);
    }
    private List<TreeNode> generateTreesChild(int start,int end){
        List<TreeNode> ret = new ArrayList<>();
        if(start > end){
            ret.add(null);
            return ret;
        }
        for(int i = start;i <= end;i++){
            List<TreeNode> leftRoot = generateTreesChild(start,i - 1);
            List<TreeNode> rightRoot = generateTreesChild(i + 1,end);
            for(int j = 0;j < leftRoot.size();j++){
                for(int k = 0;k < rightRoot.size();k++){
                    TreeNode root = new TreeNode(i);//1
                    root.left = leftRoot.get(j);
                    root.right = rightRoot.get(k);
                    ret.add(root);
                }
            }
        }
        return ret;
    }
```

有一点需要注意：

一开始我以为**注释1处**的代码可以放在j，k两层循环外面，i循环里面，也就是：

```java
		for(int i = start;i <= end;i++){
            TreeNode root = new TreeNode(i);//1
            List<TreeNode> leftRoot = generateTreesChild(start,i - 1);
            List<TreeNode> rightRoot = generateTreesChild(i + 1,end);
            for(int j = 0;j < leftRoot.size();j++){
                for(int k = 0;k < rightRoot.size();k++){
                    root.left = leftRoot.get(j);
                    root.right = rightRoot.get(k);
                    ret.add(root);
                }
            }
        }
```

然而，这样是不可以的

因为虽然`root`的值不会有影响（因为都在i这层循环里面），但是由于每次都需要把添加完左右子树的`root`添加到`ret`集合中进行返回，并且每次在j，k循环中添加的左右子树都是不相同的，所以一旦把`root`的创建放在j，k循环外面，就会导致最后加到`ret`集合中的都是同一个`root`，无法达到添加所有种类的树的效果

### 99.恢复二叉搜索树

看到二叉搜索树，反手就先写一个中序遍历，虽然这个中序遍历我也写错了，这个后面再说

首先拿到中序序列，由于两个元素发生了交换，所以考虑两种情况：

1. 如果是两个相邻的元素发生了错误的的交换：

   那么在遍历整个序列的时候，会有一个位置的元素（设为`pos1`下标）大于其后面位置的元素，而发生交换的位置就应该是`pos1`下标和`pos1 + 1`下标，恢复的方法就是把这两个位置的元素再进行一次交换

2. 如果是两个不相邻的元素发生了错误的交换：

   那么在遍历整个序列的时候，假设有两个位置的元素（分别设为`pos1`和`pos2`下标）那么发生交换的两个位置就应该是`pos1`位置和`pos2 + 1`位置，恢复方法还是对这两个位置的元素进行交换即可

下面是AC代码：
```java
	public void recoverTree(TreeNode root) {
        List<TreeNode> inorderList = inorderTraversal(root);
        int pos1 = -1;
        int pos2 = -1;
        for(int i = 0;i < inorderList.size() - 1;i++){
            if(inorderList.get(i).val > inorderList.get(i + 1).val && pos1 == -1){
                pos1 = i;
                continue;
            }
            if(inorderList.get(i).val > inorderList.get(i + 1).val && pos1 != -1){
                pos2 = i;
            }
        }
        if(pos2 == -1){
            int t = inorderList.get(pos1).val;
            inorderList.get(pos1).val = inorderList.get(pos1 + 1).val;
            inorderList.get(pos1 + 1).val = t;
        }else{
            int t = inorderList.get(pos1).val;
            inorderList.get(pos1).val = inorderList.get(pos2 + 1).val;
            inorderList.get(pos2 + 1).val = t;
        }
    }
    private List<TreeNode> inorderTraversal(TreeNode root){
        List<TreeNode> ret = new ArrayList<>();
        if(root == null){
            return ret;
        }
        Stack<TreeNode> stack = new Stack<>();
        //stack.push(root);		//一开始我这里多写了这样一行代码，就导致中序遍历得到的结果就是错误的
        TreeNode cur = root;
        while(cur != null || !stack.empty()){
            while(cur != null){
                stack.push(cur);
                cur = cur.left;
            }
            TreeNode top = stack.pop();
            ret.add(top);
            cur = top.right;
        }
        return ret;
    }
```

一开始中序遍历的循环外面我多写了一个`stack.push(root)`的代码，就导致中序遍历得到的结果就是错误的了

### 113.路径总和 II

还是采用**子树**的思想，在当前节点找和为`targetSum`，就可以转化为在左右子树中找和为`targetSum - root.val`

所以可以定义一个`sum`，每到一个节点都让`sum += root.val`

当当前节点的左右节点都为`null`时，说明此时已经到了叶子节点，判断当前路径是否等于`targetSum`只需要判断当前`sum`是否等于`targetSum`即可

反之，如果当前节点不为叶子节点，那么就需要继续递归当前节点的左右子节点，同时让`sum += root.val`

但这里要注意：由于这个函数是进行递归调用，并且`sum`是基本数据类型，所以下一层的`sum += root.val`并不会影响上一层的`sum`的值，所以再进行下一次左右子树的寻找的时候不需要进行`sum -= root.val`

然而，对于每一次用来记录根节点到当前节点路径的`list`集合来说，不管是定义成成员变量也好，或者是说放进函数的形参每次都传进来也好，由于是引用类型，所以自始至终都是操作的同一个`list`集合，所以当当前节点的左右节点都遍历完成之后，要回退到上一个节点（父节点）继续尝试其他可能的时候，需要把当前的节点`root`在`list`集合中弹出来，也就是说每一次递归的函数结束的时候，都要加上一句`list.remove(list.size() - 1)`以弹出当前的节点

下面是AC代码：
```java
	public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> ret = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        int sum = 0;
        pathSumChild(root,targetSum,ret,list,sum);
        return ret;
    }

    private void pathSumChild(TreeNode root, int targetSum, List<List<Integer>> ret, List<Integer> list,int sum) {
        if(root == null){
            return;
        }
        sum += root.val;
        list.add(root.val);
        if(root.left == null && root.right == null){
            if(sum == targetSum){
                ret.add(new ArrayList(list));
            }
        }else{
            pathSumChild(root.left,targetSum,ret,list,sum);
            pathSumChild(root.right,targetSum,ret,list,sum);
        }
        list.remove(list.size() - 1);
    }
```

### 114.二叉树展开为链表

这题我本来想着是按照**前序遍历非递归打印二叉树的那种遍历方式**进行遍历，但是错了好多次之后发现那样遍历不大行，原因是：

因为这题目的是要把二叉树展开为链表，所以会牵扯到当前节点左右节点的指向的改变

然而前序遍历非递归打印二叉树的方法中，对于某一个节点，在找这个节点的左右子树的时候采用的方法是通过`cur = cur.left`或`cur = top.right`来找，也就是说这种方法中`cur`的左右指针是不能发生改变的

然而这题要求必然会改变`cur`左右指针的指向，所以不能使用那种遍历方式

但是可能会这样想：能不能在改变左右指针指向之前先记录一下左右指针的指向，然后再改变呢？

乍一看确实很有道理，然而也存在问题

因为是前序遍历，所以当走到其中某一个节点的时候，就需要改变这个节点的左右指针的指向，然而此时需要把这个节点`push`到`stack`中，以便于后续再拿到这个节点的右子节点，此时问题就出现了，哪怕你之前记录了这个节点的左右子节点，然而当这个节点左子树全部遍历完，该走到这个节点的右子树的时候，你会发现由于之前已经把这个节点的右子节点置为`null`所以此时根本找不到这个节点的右子节点，哪怕你之前存过也不行，因为当找右子节点的时候，是从`stack`中弹出栈顶元素，然后找栈顶元素的右子节点

所以这里选择的遍历方式应该是当遇到其中一个根节点时，首先把这个根节点的左右子树都放到栈里，然后再去更改这个节点的左右子节点的指向，这样就不会出现上面所说的问题了

但是有一点需要注意，由于需要先把当前节点的左右节点放在栈里存起来，而且还要求是前序遍历，所以需要先让当前节点的右子节点入栈，然后再让当前节点的左子节点入栈

因为先右边入栈，再左边入栈可以保证先出栈的是左子节点，这样再让左子节点的右子节点和左子节点分别入栈，就可以保证下次出栈的还是左子节点的左子节点（如果有的话），如果没有的话，那就出的是右子节点，正好符合前序遍历的要求

下面是AC代码：

```java
	public void flatten(TreeNode root) {
        if(root == null){
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode top = stack.pop();
            if(top.right != null){
                stack.push(top.right);
            }
            if(top.left != null){
                stack.push(top.left);
            }
            if(pre != null){
                pre.right = top;
            }
            top.left = null;
            pre = top;
        }
        pre.right = null;//虽然经过测试，这句话加不加都可以AC，但是感觉为了更严谨，还是加上吧
    }
```

除此之外，由于这题需要建立一个单链表，建立单链表需要把上一个节点的右指针指向下一个节点，所以在遍历的时候需要记录下上一个节点，所以需要定义一个`pre`引用，指向上一个节点，这一点别忘了



