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

![image-20230518155403022](C:\Users\zhang\AppData\Roaming\Typora\typora-user-images\image-20230518155403022.png)

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
        if(start == end){
            return new TreeNode(preorder[preIndex++]);
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





