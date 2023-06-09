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

### 863.二叉树中所有距离为 K 的结点

这题的方法是从目标节点开始，分别向父节点，左子节点，右子节点三个方向进行寻找

为了记录下每个节点的父节点，可以使用`HashMap`，键为当前节点，值为键的父节点

所以首先需要遍历一遍二叉树，找到每个节点的父节点并添加到`parentMap`中

第二步就是从`target`节点开始，分别向三个方向进行搜索

但是要注意，如果直接进行递归搜索的话，有可能会出现走回去又走回来的情况，比如：

在三个搜索的方向中走到了左子节点，而在左子节点中进行搜索的时候，还要向父节点进行搜索，于是又走回了原来的节点

然而在左子节点中，还不能只向左子节点走，而是除了向父节点方向，其余两个方向（左子节点和右子节点）都要进行搜索

为了解决这个问题，可以再定义一个引用`from`，作为递归函数的形参，每次都要传进来

这个引用用来记录上一个走到的节点

初始值为`null`，每一次走到下一个节点时，都把这个引用指向当前节点

当向下一个节点走的时候，首先判断`from`这个引用是不是本来就指向下一个要走的节点，如果指向下一个要走的节点，说明本来就是从这个节点走过来的，那么肯定不能再走回去了

所以当下一个要走的节点不是`from`引用指向的节点时，就继续递归到下一个节点，同时更新`from`引用的指向，指向当前节点，意味着下一个节点时从当前节点走过来的

注意这点之后代码就比较好写了，下面是AC代码：

```java
	HashMap<TreeNode,TreeNode> parentMap = new HashMap<>();
    List<Integer> ret = new ArrayList<>();
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        if(root == null){
            return new ArrayList<Integer>();
        }
        parentMap.put(root,null);
        getMap(root);
        findNode(target,k,null);
        return ret;
    }
    private void getMap(TreeNode root){
        if(root == null){
            return;
        }
        if(root.left != null){
            parentMap.put(root.left,root);
            getMap(root.left);
        }
        if(root.right != null){
            parentMap.put(root.right,root);
            getMap(root.right);
        }
    }
    private void findNode(TreeNode cur,int k,TreeNode from){
        if(cur == null){
            return;
        }
        if(k == 0){
            ret.add(cur.val);
            return;
        }
        if(cur.left != null && cur.left != from){
            findNode(cur.left,k - 1,cur);
        }
        if(cur.right != null && cur.right != from){
            findNode(cur.right,k - 1,cur);
        }
        if(parentMap.get(cur) != null && parentMap.get(cur) != from){
            findNode(parentMap.get(cur),k - 1,cur);
        }
    }
```

### 508.出现次数最多的子树元素和

这题的方法也是用子树的思想，对于其中的某一个节点，求这个节点的子树元素和，可以转化为左右子树的元素和 + 当前节点的`val`

而要求出现次数最多的元素和，就可以先求出所有的元素和，利用`<元素和，出现次数>`的形式存在`HashMap`中，最后再遍历一次`HashMap`，就可以得到出现次数最多的元素和

在递归求子树的元素和的函数中，首先判断当前`root`是否为空，如果为空，直接返回一个0，如果不为空，递归左右子树

由于当前节点的递归结果和左右子节点的递归结果有关，所以这个递归函数是需要有返回值的

记录下左树的递归结果和右树的递归结果，然后加上当前节点的`val`，就是当前节点的子树元素和

然后更新`HashMap`，最后把这个`sum`返回就OK了

下面是AC代码（就改了一次返回值数组的问题，然后一次AC我也是没想到的）：

```java
	public HashMap<Integer,Integer> hashMap = new HashMap<>();
    public int[] findFrequentTreeSum(TreeNode root) {
        if(root == null){
            return new int[0];
        }
        findFrequentTreeSumChild(root);
        Set<Map.Entry<Integer,Integer>> entries = hashMap.entrySet();
        int maxCount = 0;
        for(Map.Entry<Integer,Integer> entry : entries){
            if(entry.getValue() > maxCount){
                maxCount = entry.getValue();
            }
        }
        List<Integer> retList = new ArrayList<>();
        for(Map.Entry<Integer,Integer> entry : entries){
            if(entry.getValue() == maxCount){
                retList.add(entry.getKey());
            }
        }
        Integer[] retInteger = retList.stream().toArray(new IntFunction<Integer[]>() {
            @Override
            public Integer[] apply(int value) {
                return new Integer[value];
            }
        });
        int[] ret = new int[retInteger.length];
        for(int i = 0;i < retInteger.length;i++){
            ret[i] = retInteger[i];
        }
        return ret;
    }
    private int findFrequentTreeSumChild(TreeNode root){
        if(root == null){
            return 0;
        }
        int left = findFrequentTreeSumChild(root.left);
        int right = findFrequentTreeSumChild(root.right);
        int sum = left + right + root.val;
        hashMap.put(sum,hashMap.getOrDefault(sum,0) + 1);
        return sum;
    }
```

### 538.把二叉搜索树转换为累加树

这题答案很简单，但是思路不那么好想

看到题根本没有思路，去看题解

累加树就是树中的每个节点都是所有大于这个节点值的节点值的和

所以可以通过反向的中序遍历，遍历的同时用`sum`记录节点值的和，遍历的同时更新`sum`，也更新当前节点的`val`值

倒序中序遍历完之后，二叉搜索树就已经转变成一棵累加树了

下面是代码：

```java
	public int sum;
    public TreeNode convertBST(TreeNode root) {
        if(root == null){
            return null;
        }
        convertBST(root.right);
        sum += root.val;
        root.val = sum;
        convertBST(root.left);
        return root;
    }
```

### 563.二叉树的坡度

这题基本上没怎么看题解，但是在改了两处小错误之后，一次AC，开心！

坡度和子树节点值的和有关，所以需要先求子树的节点值的和，这个可以用递归求

首先如果当前节点不为空，就递归去找左右子节点，直到为空，然后在回溯的过程中计算当前节点为根节点的子树的节点值的和

与此同时，我们还可以同时算出当前子树的坡度

因为二叉树的坡度等于所有子树（节点）的坡度和，所以还需要计算出所有节点的坡度，最后再求和

当前子树的坡度就可以用之前递归求出来的左右子树的节点值的和，再求差的绝对值，就得到了当前节点为根节点的子树的坡度

这些坡度可以用`HashMap<TreeNode,Integer>`来进行存储

所有的都求完之后，再遍历一次哈希表，就可以得到整棵树的坡度

下面是AC代码：

```java
	public Map<TreeNode,Integer> hashMap = new HashMap<>();
    public int findTilt(TreeNode root) {
        getSum(root);
        Set<Map.Entry<TreeNode,Integer>> entries = hashMap.entrySet();
        int sumTilt = 0;
        for(Map.Entry<TreeNode,Integer> entry : entries){
            sumTilt += entry.getValue();
        }
        return sumTilt;
    }
    private int getSum(TreeNode root){
        if(root == null){
            return 0;
        }
        int leftRet = getSum(root.left);
        int rightRet = getSum(root.right);
        hashMap.put(root,Math.abs(leftRet - rightRet));
        return leftRet + rightRet + root.val;
    }
```

### 606.根据二叉树创建字符串

之前博哥讲过，但是现在做还是有点手生，再记一下

就是硬判断

但是要注意，如果当前节点既没有左子节点也没有右子节点，那么左右子树的括号都可以省掉

如果当前节点有左子节点，没有右子节点，右子节点的括号也可以省掉

但是如果当前节点没有左子节点，有右子节点，那么左子节点的括号不能省掉

所以就需要根据当前节点的左右子树的情况进行判断，详细的我就不说了，直接放代码吧，分析完发现这题其实并不难：

```java
	public String tree2str(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        subTree(root,sb);
        return sb.toString();
    }
    private void subTree(TreeNode root,StringBuilder sb){
        if(root == null){
            return;
        }
        sb.append(root.val);
        if(root.left != null){
            sb.append("(");
            subTree(root.left,sb);
            sb.append(")");
            if(root.right != null){
                sb.append("(");
                subTree(root.right,sb);
                sb.append(")");
            }
        }else{
            if(root.right != null){
                sb.append("()");
                sb.append("(");
                subTree(root.right,sb);
                sb.append(")");
            }
        }
    }
```

下面再放一下博哥当时讲的代码，想看的话可以看一看：

```java
	public String tree2str(TreeNode root) {
        if(root == null){
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        tree2strChild(root,stringBuilder);
        return stringBuilder.toString();
    }
    private void tree2strChild(TreeNode t,StringBuilder stringBuilder){
        if(t == null){
            return;
        }
        stringBuilder.append(t.val);
        if(t.left != null){
            stringBuilder.append("(");
            tree2strChild(t.left,stringBuilder);
            stringBuilder.append(")");
        }else{
            if(t.right == null){
                return;
            }else{
                stringBuilder.append("()");
            }
        }
        
        if(t.right != null){
            stringBuilder.append("(");
            tree2strChild(t.right,stringBuilder);
            stringBuilder.append(")");
        }else{
            return;
        }
    }
```

感觉博哥这样写比我那样写思路要更清晰一点

### 617.合并二叉树

这题在牛客做过，这里就单纯记一下这次写的代码吧：

```java
	public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if(root1 == null && root2 == null){
            return null;
        }
        if(root1 != null && root2 == null){
            return root1;
        }
        if(root1 == null && root2 != null){
            return root2;
        }
        root1.val += root2.val;
        root1.left = mergeTrees(root1.left,root2.left);
        root1.right = mergeTrees(root1.right,root2.right);
        return root1;
    }
```

### 623.在二叉树中增加一行

这题一开始想用层序遍历非递归，当走到`depth - 1`层时进行处理，但是发现这样不大好做，于是看了一下题解：

还是用子树的思想，如果当前没有走到`depth`的上一层，就递归处理左右子树

如果当前节点是`depth`的上一层，那就改变当前节点的左右指针的指向

注意题目中给的二叉树中提供了一种另外的构造方法，可以在不用额外变量记录当前节点左右指针的指向的同时在当前节点的下面添加节点

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     //注意下面的这个构造
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
```

如果当前`depth == 1`，特殊处理

下面是AC代码：

```java
	public TreeNode addOneRow(TreeNode root, int val, int depth) {
        if(root == null){
            return null;
        }
        if(depth == 1){
            return new TreeNode(val,root,null);
        }
        if(depth == 2){
            root.left = new TreeNode(val,root.left,null);
            root.right = new TreeNode(val,null,root.right);
            return root;//这个地方不return也不会有问题，那样的话就会一直遍历完整棵树，所以最好还是return一下
        }
        addOneRow(root.left,val,depth - 1);
        addOneRow(root.right,val,depth - 1);
        return root;
    }
```

我又想了一下，因为有那个重载的构造方法的存在，所以如果用层序遍历非递归好像也不是很难做

下面是用层序遍历非递归的写法：

```java
	public TreeNode addOneRow(TreeNode root, int val, int depth) {
        if(depth == 1){//特殊情况
            return new TreeNode(val,root,null);
        }
        if(root == null){
            return null;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            if(depth == 2){//当走到depth的上一层时，开始修改
                while(!queue.isEmpty()){//此时queue中记录的就是当前层的所有节点
                    TreeNode cur = queue.poll();
                    cur.left = new TreeNode(val,cur.left,null);
                    cur.right = new TreeNode(val,null,cur.right);
                }
                return root;
            }else{//如果没有走到depth的上一层，就继续正常的层序遍历非递归
                while(size != 0){
                    size--;//一开始我把这句话忘了，就会出现空指针异常，因为会一直poll，queue空了以后poll出来的就是null
                    	   //再进行cur.left就会出现空指针异常
                    TreeNode cur = queue.poll();
                    if(cur.left != null){
                        queue.offer(cur.left);
                    }
                    if(cur.right != null){
                        queue.offer(cur.right);
                    }
                }
            }
            depth--;
        }
        return root;
    }
```

### 剑指 Offer 37. 序列化二叉树

这题在牛客上做过，但是当时用的是前序序列和中序序列进行的序列化，这次我想用层序遍历做一下

序列化就是正常的层序遍历，只不过这次当遍历到当前节点的时候不是把当前节点添加到列表中，而是把当前节点的值拼接到`StringBuilder`中

还有和正常层序遍历不一样的一点是，序列化出来的字符串应该是要包括`null`节点的，这样才能保证树的结构不发生变化，所以在层序遍历的过程中，即使遇到了`null`节点，也需要加到`StringBuilder`当中

但是要注意，如果仅仅按上面那样写的话，最后会多出来一层`null`，这是因为遍历到原来二叉树的最后一层时，由于遇到了`null`节点也要正常添加，所以会导致最后一层的下一层也会被遍历到

解决办法就是首先求一下二叉树的最大深度，然后用`depth`变量记录一下当前是到了哪一层，如果到了最后一层，那么就不再往`queue`中添加节点

难点是反序列化

反序列化我也不会写，去问了一下chatGPT：

>您的序列化函数看起来是正确的，使用层序遍历将二叉树转化为字符串形式。现在让我们来讨论如何实现反序列化函数。
>
>反序列化函数的思路是根据序列化的字符串重建二叉树。我们可以按照以下步骤来实现反序列化函数：
>
>1. 首先，将序列化的字符串拆分为节点值的数组，使用逗号作为分隔符。例如，将字符串"1,2,3,null,null,4,5"拆分为["1", "2", "3", "null", "null", "4", "5"]。
>
>2. 创建一个队列，用于按顺序存储每个节点的引用。
>
>3. 根据数组中的第一个值创建根节点，并将其入队。
>
>4. 使用一个循环，直到队列为空。在每次迭代中，从队列中取出一个节点作为当前节点。
>
>5. 从数组中取下一个值作为当前节点的左子节点的值。如果该值为"null"，则左子节点为空；否则，创建一个新节点并将其作为当前节点的左子节点，并将新节点入队。
>
>6. 从数组中取下一个值作为当前节点的右子节点的值。如果该值为"null"，则右子节点为空；否则，创建一个新节点并将其作为当前节点的右子节点，并将新节点入队。
>
>7. 重复步骤4-6，直到遍历完数组中的所有值或队列为空。
>
>8. 返回根节点，即重建后的二叉树。
>
>下面是相应的代码实现：
>
>```java
>// Decodes your encoded data to tree.
>public TreeNode deserialize(String data) {
>    if (data.isEmpty()) {
>        return null;
>    }
>
>    // 拆分序列化的字符串为节点值数组
>    String[] values = data.split(",");
>
>    Queue<TreeNode> queue = new LinkedList<>();
>    TreeNode root = new TreeNode(Integer.parseInt(values[0]));
>    queue.offer(root);
>    int index = 1; // 用于遍历节点值数组的索引
>
>    while (!queue.isEmpty()) {
>        TreeNode current = queue.poll();
>
>        // 处理左子节点
>        if (!values[index].equals("null")) {
>            TreeNode left = new TreeNode(Integer.parseInt(values[index]));
>            current.left = left;
>            queue.offer(left);
>        }
>        index++;
>
>        // 处理右子节点
>        if (!values[index].equals("null")) {
>            TreeNode right = new TreeNode(Integer.parseInt(values[index]));
>            current.right = right;
>            queue.offer(right);
>        }
>        index++;
>    }
>
>    return root;
>}
>```
>
>这样，您现在有了一个完整的二叉树序列化和反序列化的实现。请注意，上述代码是基于假设输入的序列化字符串是有效的，并且没有重复的节点值。如果您需要处理无效输入或重复值的情况，可能需要进行额外的检查和处理。

也就是说，层序遍历的反序列化过程也和层序遍历类似

先从字符串数组中拿一个字符串（此时就是根节点），转换成相应的节点（如果取出来的是`null`，就说明根节点就是空节点，直接`return null`就行了），然后把这个节点放进队列`queue`中

如果队列不为空，并且字符串数组还没有遍历完：

每次从队列中取出一个节点，此时字符串数组的下面两个字符串就分别是当前节点的左右子节点转换成的字符串，把这两个字符串转换成对应的节点，放进队列`queue`中（如果是空节点就不要放了，因为空节点本身就为空了，更不可能有左右子节点，所以就不需要放在队列中继续建立它的左右子节点了）

这样队列中每次存的还是当前层的所有节点（不为空的），每次出队，并且建好左右子节点，让左右子节点入队，这样当前层都出队之后，队列中就存好了下一层的所有非空节点

以此类推，当队列为空或字符串数组遍历完之后，这棵二叉树就还原好了

下面是AC代码：

```java
	// Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if(root == null){
            return "null";
        }
        int maxDepth = maxDepth(root);
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        StringBuilder stringBuilder = new StringBuilder();
        int depth = 1;
        while(!queue.isEmpty()){
            int size = queue.size();
            while(size != 0){
                size--;
                TreeNode cur = queue.poll();
                if(cur == null){
                    stringBuilder.append(",null");
                    continue;
                }
                if(stringBuilder.length() == 0){
                    stringBuilder.append(cur.val);
                }else{
                    stringBuilder.append("," + cur.val);
                }
                if(depth != maxDepth){
                    queue.offer(cur.left);
                    queue.offer(cur.right);
                }

            }
            depth++;
        }
        return stringBuilder.toString();
    }

    private int maxDepth(TreeNode root){
        if(root == null){
            return 0;
        }
        return Math.max(maxDepth((root.left)),maxDepth((root.right))) + 1;
    }

    public TreeNode deserialize(String data) {
        if(data.length() == 0){
            return null;
        }
        String[] node = data.split(",");
        Queue<TreeNode> queue = new LinkedList<>();
        int index = 0;
        TreeNode root = null;
        if(node[index].equals("null")){
            return null;
        }else{
            root = new TreeNode(Integer.parseInt(node[index++]));
        }
        queue.offer(root);
        while(!queue.isEmpty() && index < node.length){
            TreeNode cur = queue.poll();
            if(node[index].equals("null")){
                cur.left = null;
                index++;
            }else{
                cur.left = new TreeNode(Integer.parseInt(node[index++]));
                queue.offer(cur.left);
            }
            if(node[index].equals("null")){
                cur.right = null;
                index++;
            }else{
                cur.right = new TreeNode(Integer.parseInt(node[index++]));
                queue.offer(cur.right);
            }
        }
        return root;
    }
```

### 652.寻找重复的子树

这题能过，太不容易了

判断子树相同，也就相当于判断子树的序列化后的字符串相同

所以可以把每一棵子树的序列化后的字符串和其对应的出现次数放进哈希表中

最后遍历哈希表，只要出现次数超过1的，都是重复的子树

注意：这里要求返回的是重复子树的根节点，而我们哈希表中存的是重复子树的序列化值，所以可以再通过反序列化，把序列化的字符串转化成一棵新的子树，然后把这棵子树的根节点添加到`ret`集合中，最后返回这个`ret`集合，就可以了

下面是AC代码：

```java
	public HashMap<String,Integer> hashMap1 = new HashMap<>();
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        if(root == null){
            return new ArrayList<TreeNode>();
        }
        getHashMap(root);
        Set<Map.Entry<String,Integer>> entries = hashMap1.entrySet();
        List<TreeNode> ret = new ArrayList<>();
        for(Map.Entry<String,Integer> entry : entries){
            if(entry.getValue() > 1){
                ret.add(deserialize(entry.getKey()));
            }
        }
        return ret;
    }
    private void getHashMap(TreeNode root){
        if(root == null){
            return;
        }
        String serialize = Serialize(root);
        hashMap1.put(serialize,hashMap1.getOrDefault(serialize,0) + 1);
        if(root.left != null){
            getHashMap(root.left);
        }
        if(root.right != null){
            getHashMap(root.right);
        }
    }
    public String Serialize(TreeNode root){
        if(root == null){
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int maxDepth = maxDepth(root);
        int depth = 1;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            while(size != 0){
                size--;
                TreeNode cur = queue.poll();
                if(cur == null){
                    stringBuilder.append(",null");
                    continue;
                }
                if(stringBuilder.length() == 0){
                    stringBuilder.append(cur.val);
                }else{
                    stringBuilder.append("," + cur.val);
                }
                if(depth != maxDepth){
                    queue.offer(cur.left);
                    queue.offer(cur.right);
                }
            }
            depth++;
        }
        return stringBuilder.toString();
    }
    private int maxDepth(TreeNode root){
        if(root == null){
            return 0;
        }
        return Math.max(maxDepth(root.left),maxDepth(root.right)) + 1;
    }
    public TreeNode deserialize(String data) {
        if(data.length() == 0){
            return null;
        }
        String[] node = data.split(",");
        Queue<TreeNode> queue = new LinkedList<>();
        int index = 0;
        TreeNode root = null;
        if(node[index].equals("null")){
            return null;
        }else{
            root = new TreeNode(Integer.parseInt(node[index++]));
        }
        queue.offer(root);
        while(!queue.isEmpty() && index < node.length){
            TreeNode cur = queue.poll();
            if(node[index].equals("null")){
                cur.left = null;
                index++;
            }else{
                cur.left = new TreeNode(Integer.parseInt(node[index++]));
                queue.offer(cur.left);
            }
            if(index < node.length){
                if(node[index].equals("null")){
                    cur.right = null;
                    index++;
                }else{
                    cur.right = new TreeNode(Integer.parseInt(node[index++]));
                    queue.offer(cur.right);
                }
            }

        }
        return root;
    }
```

其实这题总的来看并不难，前提是要熟练写二叉树的序列化和反序列化

然后再加上哈希表的记录重复出现的次数，最后注意一下小细节，就可以了

这题题解上用的是另外的一种序列化方式，但是我不是很喜欢那种序列化方式，就没按题解的方法来做

### 653.两数之和 IV - 输入二叉搜索树

不难，简单记录一下

第一种是直接写的

第二种是看了题解之后按题解的思路写的

```java
	public boolean findTarget(TreeNode root, int k) {
        List<Integer> inorderList = inorderTraversal(root);
        int left = 0;
        int right = inorderList.size() - 1;
        while(left < right){
            if(inorderList.get(left) + inorderList.get(right) == k){
                return true;
            }
            if(inorderList.get(left) + inorderList.get(right) < k){
                left++;
            }
            if(inorderList.get(left) + inorderList.get(right) > k){
                right--;
            }
        }
        return false;
    }
    private List<Integer> inorderTraversal(TreeNode root){
        if(root == null){
            return new ArrayList<Integer>();
        }
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> ret = new ArrayList<>();
        TreeNode cur = root;
        while(cur != null || !stack.empty()){
            while(cur != null){
                stack.push(cur);
                cur = cur.left;
            }
            TreeNode top = stack.pop();
            ret.add(top.val);
            cur = top.right;
        }
        return ret;
    }
```

第二种：

```java
	public HashSet<Integer> hashSet = new HashSet<>();
    public boolean findTarget(TreeNode root, int k) {
        if(root == null){
            return false;
        }
        if(hashSet.contains(k - root.val)){
            return true;
        }else{
            hashSet.add(root.val);
            return findTarget(root.left,k) || findTarget(root.right,k);
        }
    }
```

### 654.最大二叉树

简单的递归，直接放代码：

```java
	public TreeNode constructMaximumBinaryTree(int[] nums) {
        if(nums.length == 0){
            return null;
        }
        return constructMaximumBinaryTreeChild(nums,0,nums.length - 1);
    }
    private TreeNode constructMaximumBinaryTreeChild(int[] nums,int start,int end){
        if(start > end){
            return null;
        }
        int maxNum = nums[start];
        int maxIndex = start;
        for(int i = start;i <= end;i++){
            if(nums[i] > maxNum){
                maxNum = nums[i];
                maxIndex = i;
            }
        }
        TreeNode cur = new TreeNode(maxNum);
        cur.left = constructMaximumBinaryTreeChild(nums,start,maxIndex - 1);
        cur.right = constructMaximumBinaryTreeChild(nums,maxIndex + 1,end);
        return cur;
    }
```

### 669.修剪二叉搜索树

这题也是一看到题一点思路都没有，看了题解才明白这题原来很简单

这题还是用子树的思想

对于当前节点，如果当前节点的值小于左边界，返回递归处理当前节点右子节点的结果

如果大于右边界，返回递归处理当前节点左子节点的结果

如果在两者之间，当前节点的左子节点就是递归处理左子节点之后的结果

右子节点同理

最后返回当前节点

下面是AC代码：

```java
	public TreeNode trimBST(TreeNode root, int low, int high) {
        if(root == null){
            return null;
        }
        if(root.val < low){
            return trimBST(root.right,low,high);
        }else if(root.val > high){
            return trimBST(root.left,low,high);
        }else{
            root.left = trimBST(root.left,low,high);
            root.right = trimBST(root.right,low,high);
            return root;
        }
    }
```

### 671.二叉树中第二小的节点

自己写的方法就是用层序遍历，然后硬写

代码如下：

```java
	public int findSecondMinimumValue(TreeNode root) {
        if(root == null){
            return -1;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean flag = false;
        int ans = Integer.MAX_VALUE;
        while(!queue.isEmpty()){
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            while(size != 0){
                size--;
                TreeNode cur = queue.poll();
                list.add(cur.val);
                if(cur.left != null){
                    queue.offer(cur.left);
                }
                if(cur.right != null){
                    queue.offer(cur.right);
                }
            }
            list.sort((o1,o2) -> o1 - o2);
            if(list.get(0) > root.val){
                if(list.get(0) <= ans){
                    ans = list.get(0);
                    flag = true;
                }
            }else{
                for(int i = 1;i < list.size();i++){
                    if(list.get(i) > root.val){
                        if(list.get(i) <= ans){
                            ans = list.get(i);
                            flag = true;
                        }
                    }
                }
            }
        }
        if(flag){
            return ans;
        }else{
            return -1;
        }
    }
```

题解的方法是用先序遍历，代码如下：

```java
	public int ans = -1;
    public int findSecondMinimumValue(TreeNode root) {
        int rootVal = root.val;
        findSecondMinimumValueChild(root,rootVal);
        return ans;
    }
    private void findSecondMinimumValueChild(TreeNode root,int rootVal){
        if(root == null){
            return;
        }
        if(ans != -1 && ans < root.val){
            return;
        }
        if(root.val > rootVal){
            ans = root.val;
        }
        findSecondMinimumValueChild(root.left,rootVal);
        findSecondMinimumValueChild(root.right,rootVal);
    }
```

麻了，不想解释

### 124.二叉树中的最大路径和

这题看题解感觉多难多难，其实也就那样

还是先递归到叶子节点，然后在回溯的时候同时更新当前节点的路径最大值`maxSum`，并且返回包括当前节点的**单只**路径和的最大值

可能说不明白，但是读一遍代码就明白了

下面是代码；
```java
	public int maxSum = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        maxPathSumChild(root);
        return maxSum;
    }
    private int maxPathSumChild(TreeNode root){
        if(root == null){
            return 0;
        }
        int leftRet = Math.max(maxPathSumChild(root.left),0);
        int rightRet = Math.max(maxPathSumChild(root.right),0);
        int curSum = leftRet + rightRet + root.val;
        if(curSum > maxSum){
            maxSum = curSum;
        }
        return Math.max(leftRet,rightRet) + root.val;
    }
```

### 687.最长同值路径

这题跟上一题一样，都是在回溯的时候进行处理，更新最大值的同时计算返回值

只不过要判断的条件有点复杂，要分别讨论当前节点，左子节点，右子节点的存在情况以及节点的值的情况

感觉也不是很难理解，直接放代码吧

下面是AC代码：

```java
	public int maxSum = 0;
    public int longestUnivaluePath(TreeNode root) {
        if(root == null){
            return 0;
        }
        longestUnivaluePathChild(root);
        return maxSum;
    }
    private int longestUnivaluePathChild(TreeNode root){
        if(root == null || root.left == null && root.right == null){
            return 0;
        }
        int leftRet = longestUnivaluePathChild(root.left);
        int rightRet = longestUnivaluePathChild(root.right);
        int curLen = 0;
        int ret = 0;
        if(root.left == null && root.right != null){
            if(root.val == root.right.val){
                curLen = rightRet + 1;
                ret = curLen;
            }else{
                curLen = 0;
                ret = 0;
            }
        }else if(root.left != null && root.right == null){
            if(root.val == root.left.val){
                curLen = leftRet + 1;
                ret = curLen;
            }else{
                curLen = 0;
                ret = 0;
            }
        }else{
            if(root.left.val == root.right.val && root.left.val == root.val){
                curLen = leftRet + rightRet + 2;
                ret = Math.max(leftRet,rightRet) + 1;
            }else if(root.left.val == root.val){
                curLen = leftRet + 1;
                ret = curLen;
            }else if(root.right.val == root.val){
                curLen = rightRet + 1;
                ret = curLen;
            }else{
                curLen = 0;
                ret = 0;
            }
        }
        maxSum = Math.max(maxSum,curLen);
        return ret;
    }
```

### 814.二叉树剪枝

参见“宫水三叶”大佬题解：

>递归
>根据题意，我们将原函数 pruneTree 作为递归函数，递归函数的含义为「将入参 root 中的所有不包含 111 的子树移除，并返回新树头结点」。
>
>不失一般性的考虑任意节点作为入参该如何处理：我们可以递归处理左右子树，并将新左右子树重新赋值给 root。由于当前节点 root 的左右子树可能为空树，因此我们要增加递归函数入参为空的边界处理。
>
>当递归操作完成后，若左右节点任一值不为空（说明当前节点 root 不为叶子节点），我们可以直接返回 root，否则根据 root 的值是否为 000 来决定返回空树还是 root 本身。
>
>代码：
>
>Java
>TypeScript
>class Solution {
>    public TreeNode pruneTree(TreeNode root) {
>        if (root == null) return null;
>        root.left = pruneTree(root.left);
>        root.right = pruneTree(root.right);
>        if (root.left != null || root.right != null) return root;
>        return root.val == 0 ? null : root;
>    }
>}
>时间复杂度：O(n)O(n)O(n)
>空间复杂度：忽略递归带来的额外空间开销，复杂度为 O(1)O(1)O(1)
>
>作者：宫水三叶
>链接：https://leetcode.cn/problems/binary-tree-pruning/solutions/1686077/by-ac_oier-7me9/
>来源：力扣（LeetCode）
>著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

首先要读懂这题的意思，什么样的子树要删除，什么样的子树无需删除

如果一棵子树的所有的节点都是0，那么这棵子树就需要删除

而如果一棵子树的节点不全为0，那么这棵子树就不能被删除

具体可以看样例

所以还是递归，但是在回溯的过程中处理当前节点时需要注意：

因为回溯是从叶子节点开始回溯，所以先考虑叶子节点的情况：

如果当前节点的左右子树都为`null`，即当前节点是叶子节点，那么判断当前节点的值是0还是1，如果是0，那么直接返回`null`，如果不是0，那么就返回当前节点

而当走到非叶子节点的时候，由于此时我们不能删除节点值不全为0的节点，那么什么样的节点为根的子树是不全为0的呢

由于我们是从叶子节点开始回溯的，并且当遇到节点值为0的叶子节点时会把这个节点删除（置为`null`），所以对于当前节点，如果其左右节点有一个不为空，就说明这棵子树的左右子树包含不为0的节点，也就说明这棵子树不能被删除

而如果当前子树的左右节点都为`null`，那么就说明当前节点又成了叶子节点，所以判断当前节点的值是不是0，如果是0，那么删除，如果不是0，那么返回当前节点

以下是AC代码：

```java
	public TreeNode pruneTree(TreeNode root) {
        if(root == null){
            return null;
        }
        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);
        if(root.left != null || root.right != null){
            return root;
        }
        if(root.val == 0){
            return null;
        }else{
            return root;
        }
    }
```

### 987.二叉树的垂序遍历

#### 1.DFS + 哈希表 + 排序

看完题解想死的心都有了，硬着头皮分析完，还是挺有成就感的

题解来自大佬“宫水三叶”

以下是链接：[【综合笔试题】难度 3.5/5，多解法热门二叉树笔试题 (qq.com)](https://mp.weixin.qq.com/s?__biz=MzU4NDE3MTEyMA==&mid=2247492468&idx=1&sn=0fee2f94b1cbbb4d63bafbdfaf6116de)

主要思路就是先把整棵树遍历一遍，把每个节点的位置以及节点值放在`HashMap`中，然后把哈希表中的数据获取出来存在`List`中，并对`List`进行排序，最后把这个集合按照题目的要求整理成`List<List<Integer>>`就可以了

下面是代码：

```java
	public Map<TreeNode,int[]> map = new HashMap<>();// col,row,val
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        map.put(root,new int[]{0,0,root.val});
        getMap(root);
        List<int[]> list = new ArrayList<>(map.values());
        Collections.sort(list,(o1,o2)->{
            if(o1[0] != o2[0]) return o1[0] - o2[0];
            if(o1[1] != o2[1]) return o1[1] - o2[1];
            return o1[2] - o2[2];
        });
        List<List<Integer>> ret = new ArrayList<>();
        int n = list.size();
        for(int i = 0;i < n;){
            int j = i;
            List<Integer> temp = new ArrayList<>();
            while(j < n && list.get(j)[0] == list.get(i)[0]) temp.add(list.get(j++)[2]);
            i = j;
            ret.add(temp);
        }
        return ret;
    }
    private void getMap(TreeNode root){
        if(root == null){
            return;
        }
        int[] arr = map.get(root);
        int col = arr[0];
        int row = arr[1];
        if(root.left != null){
            map.put(root.left,new int[]{col - 1,row + 1,root.left.val});
            getMap(root.left);
        }
        if(root.right != null){
            map.put(root.right,new int[]{col + 1,row + 1,root.right.val});
            getMap(root.right);
        }
    }
```

其中有一段循环比较难理解，放一下和GPT4的聊天记录：https://poe.com/s/dqnx03l96ZoZF0tY26dy

#### 2.DFS + 优先队列（堆）

> 显然，最终要让所有节点的相应信息有序，可以使用「优先队列（堆）」边存储边维护有序性。 -- 引用自宫水三叶大佬

下面是用堆来进行元素的排序

AC代码如下：

```java
	public PriorityQueue<int[]> heap = new PriorityQueue<>((o1,o2)->{
        if(o1[0] != o2[0]) return o1[0] - o2[0];
        if(o1[1] != o2[1]) return o1[1] - o2[1];
        return o1[2] - o2[2];
    });
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        heap.add(new int[]{0,0,root.val});
        getHeap(root,new int[]{0,0,root.val});
        List<List<Integer>> ret = new ArrayList<>();
        while(!heap.isEmpty()){
            List<Integer> temp = new ArrayList<>();
            int[] top = heap.peek();
            while(!heap.isEmpty() && top[0] == heap.peek()[0]) temp.add(heap.poll()[2]);
            ret.add(temp);
        }
        return ret;
    }
    private void getHeap(TreeNode root,int[] arr){
        if(root == null){
            return;
        }
        if(root.left != null){
            int[] arrNext = new int[]{arr[0] - 1,arr[1] + 1,root.left.val};
            heap.add(arrNext);
            getHeap(root.left,arrNext);
        }
        if(root.right != null){
            int[] arrNext = new int[]{arr[0] + 1,arr[1] + 1,root.right.val};
            heap.add(arrNext);
            getHeap(root.right,arrNext);
        }
    }
```

### 240.搜索二维矩阵 II

看成以右上角元素为根节点的二叉搜索树，从右上角的这个元素开始搜索

如果当前元素等于`target`，返回`true`

如果当前元素小于`target`，移到下一行

如果当前元素大于`target`，移到左边的那一列

就跟在二叉搜索树中查找元素一样

下面是AC代码：

```java
	public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int startR = 0;
        int startC = n - 1;
        while(startR < m && startC >= 0){
            if(matrix[startR][startC] == target){
                return true;
            }else if(target > matrix[startR][startC]){
                startR++;
            }else{
                startC--;
            }
        }
        return false;
    }
```

### 965.单值二叉树

很简单，直接放代码吧：

```java
	public boolean isUnivalTree(TreeNode root) {
        if(root == null){
            return true;
        }
        int val = root.val;
        return check(root.left,val) && check(root.right,val);
    }
    private boolean check(TreeNode root,int val){
        if(root == null){
            return true;
        }
        if(root.val != val){
            return false;
        }else{
            return check(root.left,val) && check(root.right,val);
        }
    }
```

### 面试题 04.06. 后继者

代码不长，但是难理解

摘自“宫水三叶”大佬的题解：

>1.若有 root.val <= p.val : 根据 BST 特性可知当前节点 root 及其左子树子节点均满足「值小于等于 p.val」，因此不可能是 p 点的后继，我们直接到 root 的右子树搜索 p 的后继（递归处理）；
>2.若有 root.val > p.val : 当第一次搜索到满足此条件的节点时，在以 root 为根节点的子树中「位于最左下方」的值为 p 的后继，但也有可能 root 没有左子树，因此 p 的后继要么在 root 的左子树中（若有），要么是 root 本身，此时我们可以直接到 root 的左子树搜索，若搜索结果为空返回 root，否则返回搜索结果。

给我的感觉就是

首先一直向`root`的右边找，找到第一个比`p.val`大的`root`

此时根据二叉搜索树的性质，`p`的下一个节点就应该是`root`这棵子树的最左边的那个节点（如果有的话）

所以再继续往`root`的左边找，如果没有左子树，那么直接返回`root`，如果有左子树，那么就一直向左边找，直到最左下的节点

下面是AC代码：

```java
	public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if(root == null){
            return null;
        }
        if(p.val >= root.val){
             return inorderSuccessor(root.right,p);
        }
        TreeNode leftRet = inorderSuccessor(root.left,p);
        return leftRet == null ? root : leftRet;
    }
```

附：（和GPT4的聊天记录）

https://poe.com/s/tLoKU8qccsDxS1Qm4zHZ

### 1022.从根到叶的二进制数之和

本来想着继续按老方法，用`StringBuilder`每次拼接一个，但是发现太麻烦了，于是又去看题解了

下面是大佬“宫水三叶”的AC代码：

```java
class Solution {
    public int sumRootToLeaf(TreeNode root) {
        return dfs(root, 0);
    }
    int dfs(TreeNode root, int cur) {
        int ans = 0, ncur = (cur << 1) + root.val;
        if (root.left != null) ans += dfs(root.left, ncur);
        if (root.right != null) ans += dfs(root.right, ncur);
        return root.left == null && root.right == null ? ncur : ans;
    }
}

/*作者：宫水三叶
链接：https://leetcode.cn/problems/sum-of-root-to-leaf-binary-numbers/solutions/1526069/by-ac_oier-1905/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。*/
```

有点小难理解，于是让GPT4解释了一下：

>代码的整体思路：这是一个二叉树的深度优先搜索（DFS）问题。我们遍历整棵树，并在遍历的过程中计算从根节点到当前节点所表示的二进制数。当我们到达叶节点时，将叶节点所表示的二进制数累加到结果中。最后返回累加的和。
>
>下面是详细的代码解释：
>
>java
>
>Copy
>
>```
>// 定义一个 Solution 类
>class Solution {
>    // 定义求和的公共函数，作为入口
>    public int sumRootToLeaf(TreeNode root) {
>        // 调用 dfs 函数，从根节点开始遍历，并初始化当前值为 0
>        return dfs(root, 0);
>    }
>
>    // 定义一个递归的 dfs 函数，参数是当前节点 root 和从根节点到当前节点所表示的二进制数 cur
>    int dfs(TreeNode root, int cur) {
>        // 初始化当前子树的结果为 0
>        int ans = 0;
>        
>        // 计算从根节点到当前节点的二进制数，将 cur 左移一位（相当于乘以 2），然后加上当前节点的值
>        int ncur = (cur << 1) + root.val;
>
>        // 如果当前节点有左子节点，将左子节点的结果累加到 ans 上
>        if (root.left != null) ans += dfs(root.left, ncur);
>        
>        // 如果当前节点有右子节点，将右子节点的结果累加到 ans 上
>        if (root.right != null) ans += dfs(root.right, ncur);
>
>        // 如果当前节点是叶节点（即没有左子节点和右子节点），返回从根节点到当前节点所表示的二进制数
>        // 否则返回当前子树的结果
>        return root.left == null && root.right == null ? ncur : ans;
>    }
>}
>```
>
>这段代码定义了一个名为 `Solution` 的类，其中包含了两个方法：`sumRootToLeaf` 和 `dfs`。`sumRootToLeaf` 是主方法，接收一个 `TreeNode` 类型的参数 `root`，表示二叉树的根节点。在这个方法中，调用 `dfs` 方法，并将当前值 `cur` 初始化为 0。
>
>`dfs` 方法是一个递归方法，它接收两个参数：当前节点 `root` 和从根节点到当前节点所表示的二进制数 `cur`。在 `dfs` 方法中，首先初始化当前子树的结果 `ans` 为 0。然后计算从根节点到当前节点的二进制数 `ncur`，将 `cur` 左移一位（相当于乘以 2），然后加上当前节点的值。接下来，如果当前节点有左子节点，那么将左子节点的结果累加到 `ans` 上；同理，如果当前节点有右子节点，那么将右子节点的结果累加到 `ans` 上。最后，如果当前节点是叶节点（即没有左子节点和右子节点），那么返回从根节点到当前节点所表示的二进制数；否则返回当前子树的结果。

每次通过左移运算符`<<`来模拟二进制转十进制的过程，从而在遍历的同时就完成了进制转换，接下来只需要根据当前节点是不是叶子节点来确定返回值就可以了

在**判断当前节点是不是叶子节点从而确定返回值**，这一步，大佬的代码也写的很简洁，然鹅我写的就很麻烦，（哭

下面是我的代码：

```java
	public int sumRootToLeaf(TreeNode root) {
        return getSum(root,0);
    }
    private int getSum(TreeNode root,int preSum){
        if(root == null){
            return preSum;
        }
        int curSum = (preSum << 1) + root.val;
        int leftRet = getSum(root.left,curSum);
        int rightRet = getSum(root.right,curSum);
        if(root.left == null && root.right == null){
            return curSum;
        }else if(root.left == null && root.right != null){
            return rightRet;
        }else if(root.left != null && root.right == null){
            return leftRet;
        }else{
            return leftRet + rightRet;
        }
    }
```

### 993.二叉树的堂兄弟节点

看似很简单的题，实际上没那么简单

由于判断是不是堂兄弟节点需要判断两个节点是不是在同一层，还要判断两个节点的父节点是不是同一个，所以单纯一个返回值已经不够用了，这里的dfs函数需要返回两个值，分别是父节点的值`parent.val`，用来判断这两个节点是不是有共同的父节点（因为题目中说明该二叉树当中所有节点的值都不相同），以及两个节点分别位于的深度`depth`

然后在dfs函数中分别更新这两个值，并继续搜索

下面是“宫水三叶”大佬的题解：

https://leetcode-cn.com/problems/cousins-in-binary-tree/solution/gong-shui-san-xie-shu-de-sou-suo-dfs-bfs-b200/

下面是GPT4的解法：

>整体思路：
>
>1. 使用深度优先搜索（DFS）遍历二叉树。
>2. 在遍历过程中，记录每个节点的父节点和深度信息。
>3. 比较x和y对应的节点的父节点和深度信息，判断它们是否为堂兄弟节点。
>
>Java代码实现：
>
>java
>
>Copy
>
>```
>// 树的节点定义
>class TreeNode {
>    int val;
>    TreeNode left;
>    TreeNode right;
>    
>    TreeNode() {}
>
>    TreeNode(int val) {
>        this.val = val;
>    }
>
>    TreeNode(int val, TreeNode left, TreeNode right) {
>        this.val = val;
>        this.left = left;
>        this.right = right;
>    }
>}
>
>public class Solution {
>    // 使用两个数组分别存储节点深度和父节点信息
>    int[] depth = new int[101];
>    int[] parent = new int[101];
>
>    public boolean isCousins(TreeNode root, int x, int y) {
>        // 使用深度优先搜索遍历二叉树，记录节点深度和父节点信息
>        dfs(root, 0, -1);
>        
>        // 判断x和y对应的节点是否为堂兄弟节点
>        return depth[x] == depth[y] && parent[x] != parent[y];
>    }
>
>    // 深度优先搜索遍历二叉树
>    private void dfs(TreeNode node, int d, int p) {
>        if (node == null) return;
>        
>        // 记录当前节点的深度和父节点
>        depth[node.val] = d;
>        parent[node.val] = p;
>        
>        // 遍历左子树和右子树
>        dfs(node.left, d + 1, node.val);
>        dfs(node.right, d + 1, node.val);
>    }
>}
>```
>
>代码实现中，我们使用深度优先搜索遍历二叉树，记录每个节点的深度和父节点信息。然后比较x和y对应的节点的深度和父节点信息，判断它们是否为堂兄弟节点。这样我们可以在O(N)的时间复杂度内解决这个问题，其中N为二叉树的节点个数。

看得出来，GPT4也是用的返回一个数组的深度优先搜索

最后是我写的：

```java
	public boolean isCousins(TreeNode root, int x, int y) {
        if(root.val == x || root.val == y){
            return false;
        }
        int[] xRet = dfs(root,null,x,1);
        int[] yRet = dfs(root,null,y,1);
        if(xRet[1] == yRet[1]){
            return xRet[0] != yRet[0];
        }else{
            return false;
        }
    }
    private int[] dfs(TreeNode root,TreeNode parent,int t,int depth){
        if(root == null){
            return null;
        }
        if(root.val == t){
            return new int[]{parent.val,depth};
        }else{
            int[] leftRet = dfs(root.left,root,t,depth + 1);
            int[] rightRet = dfs(root.right,root,t,depth + 1);
            return leftRet == null ? rightRet : leftRet;
        }
    }
```

### 3.无重复字符的最长子串

题不难，但是我也没AC，反思……

先放代码，再说哪里需要注意：

```java
	public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int index = 0;
        int last = 0;
        int len = 0;
        while(index < s.length()){
            if(!set.contains(s.charAt(index))){
                set.add(s.charAt(index));
                len = Math.max(len,index - last + 1);//3
                index++;//1
            }else{
                set.remove(s.charAt(last));//2
                last++;
            }
        }
        return len;
    }
```

注释1处：这里的`index++`不能放在`set.add(s.charAt(index));`里面，因为此时只能确定`index`位置（不++之前）的元素是不和之前的元素重复的，但是无法保证++之后`index`位置的元素仍然和之前的元素不重复，所以必须在`index++`之前更新`len`，或者说`maxLen`

注释2处：此处无需`while`循环前移`last`，因为如果前移之后还是存在重复的，那么还是进不去第一个`if`，还是会到`else`分支中继续前移`last`，并移除`set`中相应的元素

注释3处：`len`的更新只放在`index++`这里面就行，原因是只有`index++`的时候，才会让`len`变长，在`else`语句中，每次都是把这个滑动窗口末尾的元素去掉，那么`len`的长度肯定是变小的，所以在`else`这个分支中，无需进行`len`的更新，只在第一个`if`分支中（即`index++`的地方）进行`len`的更新即可

### 36.有效的数独

这题好难，人麻了

https://poe.com/s/wRS95y6zyQSQC8UWBfOL

先放代码，再解释吧：

```java
	public boolean isValidSudoku(char[][] board) {
        for(int i = 0;i < 9;i++){
            Set<Character> row = new HashSet<>();
            Set<Character> column = new HashSet<>();
            Set<Character> square = new HashSet<>();
            for(int j = 0;j < 9;j++){
                if(board[i][j] != '.' && !row.add(board[i][j])){
                    return false;
                }
                if(board[j][i] != '.' && !column.add(board[j][i])){
                    return false;
                }
                int rowIndex = 3 * (i / 3) + j / 3;
                int columnIndex = 3 * (i % 3) + j % 3;
                if(board[rowIndex][columnIndex] != '.' && !square.add(board[rowIndex][columnIndex])){
                    return false;
                }
            }
        }
        return true;
    }
```

首先需要注意的是，外层循环的`i`并不是特指的行或者列，而是动态变化的

当内层循环判断的是行的时候，那么外层循环的`i`就遍历的是每一行

当内层循环判断的是列的时候，那么外层循环的`i`就是遍历每一列

当内层循环判断的是九宫格的时候，那么外层循环的`i`就是遍历的每一个九宫格

这样就可以在一个嵌套循环中搞定这三种情况，而不需要三个嵌套的循环

但是判断九宫格的时候的`rowIndex`和`columnIndex`是怎么根据`i`和`j`算出来的我实在是看不懂

相当痛苦

### 138.复制带随机指针的链表

这题博哥讲过，讲完感觉还可以，但其实这题还是挺不好做的

需要进行两次遍历

第一次遍历，按照原来链表的`val`创建出等量的新节点值，作为返回的链表的节点

同时用`Map`建立起原来节点和对应的新节点之间的对应关系

然后进行第二次遍历，这次遍历的目的是重建`next`指针以及`random`指针

重建的方法很巧妙，

假设`cur`是遍历到的每一个节点

`map.get(cur.next)`获取到的是新的`cur`节点的`next`节点，而`map.get(cur).next`获取到的是当前`cur`节点对应新节点的`next`指针的指向（默认初始化为`null`），需要我们把这个指针指向`map.get(cur.next)`，即新链表当前节点的下一个节点

即`map.get(cur).next = map.get(cur.next)`

`random`指针同理

下面是代码：

```java
	public Node copyRandomList(Node head) {
        Map<Node,Node> map = new HashMap<>();
        Node cur = head;
        while(cur != null){
            Node node = new Node(cur.val);
            map.put(cur,node);
            cur = cur.next;
        }
        cur = head;
        while(cur != null){
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
            cur = cur.next;
        }
        return map.get(head);
    }
```

