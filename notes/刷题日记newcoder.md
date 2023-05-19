### BM27 按之字形顺序打印二叉树

链接：[按之字形顺序打印二叉树_牛客题霸_牛客网 (nowcoder.com)](https://www.nowcoder.com/practice/91b69814117f4e8097390d107d2efbe0?tpId=295&tqId=23454&ru=/exam/oj&qru=/ta/format-top101/question-ranking&sourceUrl=%2Fexam%2Foj%3Fpage%3D1%26tab%3D%E7%AE%97%E6%B3%95%E7%AF%87%26topicId%3D295)

**分析：**

先按正常的层序遍历走一下试试：

**第一层：**让根节点入队，然后让根节点出队，然后从左到右添加根节点的左子节点和右子节点

**第二层：**然后发现为了让这一层能够从右向左打印，此时需要先出队右子节点，再出队左子节点，所以出队就不能从队尾出，应该从头出，那么相应的进队就应该是尾进

同时，为了让下一层能够从左往右打印，所以需要先添加右子节点，再添加左子节点

**第三层：**类似，这一层如果想要从左往右打印，就需要从队尾出队，队头入队，并且先添加左子节点，再添加右子节点

**第四层：**……

所以可以总结一下规律：

| 层数            | 出入队方式 | 从哪边开始添加子节点 |
| --------------- | ---------- | -------------------- |
| 第0层（最开始） | 头进       |                      |
| 第一层          | 尾出头进   | 左边                 |
| 第二层          | 头出尾进   | 右边                 |
| 第三层          | 尾出头进   | 左边                 |
| 第四层          | ……         | ……                   |

由此可以看出，如果从左边开始添加子节点，对应的出入队方式就是尾出头进，反之就是头出尾进，所以可以用`boolean`型变量`isLeft`

来记录从那边开始添加子节点，通过判断`isLeft`的`true`或`false`来确定出入队方式

除此之外，最开始的那一次（第0层）和其他层都不一样，所以可以单独考虑

即：如果根节点为`null`，直接返回

否则，头进根节点，并且`isLeft`初始值为`true`

下面是AC代码：

```java
public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
    ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
    if(pRoot == null){
        return ret;
    }
    Deque<TreeNode> deque = new LinkedList<>();
    deque.offer(pRoot);
    boolean isLeft = true;
    while(!deque.isEmpty()){
        int size = deque.size();
        ArrayList<Integer> list = new ArrayList<>();
        while(size != 0){
            size--;
            if(isLeft){
                TreeNode cur = deque.pollLast();
                list.add(cur.val);
                if(cur.left != null){
                    deque.addFirst(cur.left);
                }
                if(cur.right != null){
                    deque.addFirst(cur.right);
                }
            }else{
                TreeNode cur = deque.pollFirst();
                list.add(cur.val);
                if(cur.right != null){
                    deque.addLast(cur.right);
                }
                if(cur.left != null){
                    deque.addLast(cur.left);
                }
            }
        }
        ret.add(list);
        isLeft = !isLeft;//注意这里应该是在一层都添加完之后再改变isLeft的值
    }
    return ret;
}
```

只要把一开始的那张表搞清楚，代码还是很好写的

### BM29 二叉树中和为某一值的路径(一)

链接：[二叉树中和为某一值的路径(一)_牛客题霸_牛客网 (nowcoder.com)](https://www.nowcoder.com/practice/508378c0823c423baa723ce448cbfd0c?tpId=295&tags=&title=&difficulty=0&judgeStatus=0&rp=0&sourceUrl=%2Fexam%2Foj)

**分析：**判断整棵树中有没有和为`sum`的路径，就相当于判断左右子树中有没有和为`sum - root.val`的路径，最后对左右子树返回的结果取`||`就可以

然而，这题有一些情况需要特殊处理：

1.根节点为`null`，`sum`为0，返回的是`false`，说明**如果根为`null`，无论`sum`为多少，返回都是`false`**

2.二叉树为`{1,2}`，`sum = 1`返回的是`false`，说明**如果根的左右子树不为空**，**路径要从根节点到叶子节点的路径中找** --- 即如果**根节点的左右子树有一个不为空**，**那么只有根节点的路径是无效的，必须要找能到叶子节点的路径**

3.二叉树为`{1}`，`sum = 1`，返回的是`true`，说明如果**根节点的左右节点都为`null`**，那么此时路径可以为**只有根节点的这个路径**

所以解决方法就是不用原来的函数进行递归，而是再新写一个`Child`函数进行递归，在原函数中处理`root`相关的特殊情况

下面是AC代码：

```java
	public boolean hasPathSum (TreeNode root, int sum){
        if(root == null){
            return false;
        }
        if(root.left == null && root.right == null){
            return root.val == sum;
        }
        if(root.left != null && root.right == null){
            return hasPathSumChild(root.left,sum - root.val);
        }
        if(root.left == null && root.right != null){
            return hasPathSumChild(root.right,sum - root.val);
        }
        return hasPathSumChild(root,sum);
    }
    public boolean hasPathSumChild (TreeNode root, int sum) {
        // write code here
        if(root == null){
            if(sum == 0){
                return true;
            }else{
                return false;
            }
        }
        return hasPathSumChild(root.left,sum - root.val) 
        || hasPathSumChild(root.right,sum - root.val);
    }
```

### BM32 合并二叉树

链接：[合并二叉树_牛客题霸_牛客网 (nowcoder.com)](https://www.nowcoder.com/practice/7298353c24cc42e3bd5f0e0bd3d1d759?tpId=295&tqId=1025038&ru=/exam/oj&qru=/ta/format-top101/question-ranking&sourceUrl=%2Fexam%2Foj)

**题目分析及改错历程：**

这题的想法还是用子树的思想去进行合并，通过递归，每次都合并当前节点，然后再递归合并当前节点的左右子节点

按照题目的合并要求，于是有了第一版的错误代码：

```java
	public TreeNode mergeTrees (TreeNode t1, TreeNode t2) {
    // write code here
        if (t1 == null && t2 == null) {
            return null;
        } else if (t1 != null && t2 != null) {
            t1.val += t2.val;
        } else if (t1 == null && t2 != null) {
            t1 = t2;
        } else if (t1 != null && t2 == null) {
            t2 = t1;
        }
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);
        return t1;
    }
```

但是这段代码交上去是不能AC的，因为赋值的这个地方我也不大明白，于是问了一下GPT4：

链接：https://poe.com/s/rYx95sGkxDi4zROjXp17

这个是在上一条的基础上又问了一些问题，终于搞明白了：https://poe.com/s/S9iFvCVB8m6HXI86aLqI

**分析错误原因：**上面的代码错在了如果遇到了一个为空，另一个不为空的情况，拿`t1 == null && t2 != null`来举例，此时如果按上面的代码来执行的话，会让`t1 = t2`，此时虽然在当前的递归中实现了把t2的剩余部分拼接到`t1`上的结果，但是在上一层递归中，`t1`的父节点仍然指向的是`null`（就相当于子递归把原本指向`null`的引用指向了`t2`，但是父节点的引用的指向仍然指向的是`null`），所以相当于没有起到合并的效果

正确的做法是，当遇到一个为`null`，而另一个不为null的情况时，应该直接返回不为`null`的那个节点

这样做正确的原因是，当子递归中直接返回不为`null`的节点时，在上一层递归中，可以通过`t1.left = mergeTrees(t1.left, t2.left);`或者是`t1.right = mergeTrees(t1.right, t2.right);`来接收子递归修改（合并）之后的结果，可以成功修改父节点左右节点的指向，也就可以正确的实现合并

下面是可以AC的代码：

```java
	public TreeNode mergeTrees (TreeNode t1, TreeNode t2) {
        // write code here
        if (t1 == null && t2 == null) {
            return null;
        } else if (t1 != null && t2 != null) {
            t1.val += t2.val;
        } else if (t1 == null && t2 != null) {
            return t2;
        } else if (t1 != null && t2 == null) {
            return t1;
        }
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);
        return t1;
    }
```

然后再看GPT4给出的代码，GPT4给出的代码更加简洁，也更符合递归的规范：

```java
public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
    // If both trees are null, return null
    if (t1 == null && t2 == null) {
        return null;
    }
    // If one of the trees is null, return the other tree
    if (t1 == null) {
        return t2;
    }
    if (t2 == null) {
        return t1;
    }
    
    // Both trees are not null, merge them
    t1.val += t2.val;
    t1.left = mergeTrees(t1.left, t2.left);
    t1.right = mergeTrees(t1.right, t2.right);
    
    return t1;
}
```

### BM33 二叉树的镜像

**题目分析及改错历程：**这题的原理和对称二叉树差不多，但是在写的过程中还是遇到了和上一题一样的问题，即在子递归中做的更改无法应用到上一层递归中，这次我还是不大会改正，于是又问了一下GPT4

和GPT4交流的记录：https://poe.com/s/B5xnB5B0C4guyZAvekvV

然后发现这题的改正方法还是比较简单的，就是在递归中不要想着传入两个节点，然后交换这两个节点，而是只传入一个节点，在递归的函数里面去改变这一个节点的左右子树的指向，这样就可以实现让子递归做出的修改能应用到上一层递归中的效果。

下面是AC代码：

```java
	public TreeNode Mirror (TreeNode pRoot) {
        // write code here
        if(pRoot == null){
            return pRoot;
        }
        MirrorChild(pRoot);
        return pRoot;
    }
    private void MirrorChild(TreeNode root){
        if(root == null){
            return;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        MirrorChild(root.left);
        MirrorChild(root.right);
    }
```

在上面的和GPT4的聊天记录中，我还问了一下能不能利用返回值来达到同样的效果，就像上一题一样，然后GPT4也给出了利用返回值的版本，但是感觉这个版本利用返回值有点牵强，还是用上面的代码（不带返回值的）看着比较好理解

### BM34 判断是不是二叉搜索树

看到二叉搜索树，反手就是一个中序遍历，这样确实可以，下面是AC代码：

```java
	public boolean isValidBST (TreeNode root) {
        // write code here
        if(root == null){
            return true;
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
        //此时list中存的就是二叉树的中序遍历结果
        for(int i = 0;i < list.size() - 1;i++){
            if(list.get(i) > list.get(i + 1)){
                return false;
            }
        }
        return true;
    }
```

但是这题这样做就有点太简单了，能不能在**遍历二叉树的同时**完成对这个二叉树是不是二叉搜索树的检查呢？

这题之前写过，需要注意的地方也在原代码中标注出来了：

```java
	TreeNode parent1 = null;
 
    /**
     * BM34 判断是不是二叉搜索树
     * 其实直接中序遍历存到数组里再判断也没问题，只不过这里没用那种写法
     * @param root
     * @return
     */
    public boolean isValidBST (TreeNode root) {
        // write code here
        if(root == null){
            return true;
        }
        //下面的代码是关键
        if (!isValidBST(root.left)) {
            return false;
        }
        //isValidBST(root.left);
        //这里需要对isValidBST(root.left)的返回值进行判断，而不能简单递归下去
        //原因是如果子递归返回了一个false，如果这里不判断的话，也会继续向下执行
        //所以结果就是无论如何都会返回true
 
        //此时递归到最小的节点 -- root
        if(parent1 == null){
            parent1 = root;
        }else{
            if(parent1.val > root.val){
                return false;
            }
            parent1 = root;
        }
        return isValidBST(root.right);
    }
```

这样做可以在进行中序遍历的同时判断是否符合二叉搜索树的要求

这题中中序遍历的方法采用了递归的方法，由于需要比较前后节点的`val`的大小，所以需要额外的一个成员变量`parent`来记录上一次遍历的节点

然后进行中序遍历，中序遍历采用的方法是递归，首先一直递归左子树，然后第一次比较的时候`parent`为空，那么就更新`parent`的值，如果`parent`不为空，那么就比较当前节点的`val`和`parent`的`val`是否符合要求，然后更新`parent`，然后接着递归右树就可以了

但是这里要注意一点，在递归左子树的时候，不能直接`isValidBST(root.left)`，而是要考虑该函数的返回值，如果返回值是`false`，那么就直接`return false`就可以了，因为虽然第一次寻找最左边的叶子节点的时候确实不需要考虑返回值，只要一直往左边找就行，但是当往回走的时候，当前节点如果是父节点的左子节点的时候，那么此时`isValidBST`函数的返回值就起作用了，所以还是需要考虑`isValidBST`的返回值的

同理，递归右边的时候该函数（`isValidBST`）的返回值也是需要考虑的

### BM35 判断是不是完全二叉树

这题一开始没有思路，看完题解之后感觉之前好像见过类似的题

这题的主要方法还是遍历，但是在遍历的过程中就不需要把当前节点添加到`List`集合中了，而是每次都判断`queue`中弹出的节点，如果当前弹出的节点为`null`时，说明已经到了叶子节点的下一层了，然后继续遍历（层序），如果接下来存在不为空的节点，那么说明就不是完全二叉树，如果接下来这一层的所有节点都是`null`，那么说明这棵二叉树就是完全二叉树

```java
	public boolean isCompleteTree (TreeNode root) {
        // write code here
        if(root == null){
            return true;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode cur = queue.poll();
            if(cur == null){
                while(!queue.isEmpty()){
                    if(queue.poll() != null){
                        return false;
                    }
                }
                return true;
            }else{
                queue.offer(cur.left);
                queue.offer(cur.right);
            }
        }
        return true;
    }
```

### BM36 判断是不是平衡二叉树

这题也是之前做过但是现在又不会做的题

这题的方法还是将问题转化成两个子树的问题

直接的做法就是先写一个maxDepth(TreeNode root)函数，用来计算最大深度，然后递归遍历每一个节点，对于每个节点，如果为空，直接返回`true`，如果不为空，判断左右子树的高度差是否大于1，如果大于1，那么说明不是平衡二叉树，`return false`，如果小于等于1，那么递归继续判断左右子树是不是平衡二叉树

直接去做的代码如下：

```java
	public boolean IsBalanced_Solution(TreeNode root) {
        if(root == null){
            return true;
        }
        if(Math.abs(maxDepth(root.left) - maxDepth(root.right)) > 1){
            return false;
        }
        return IsBalanced_Solution(root.left) && IsBalanced_Solution(root.right);
    }
    private int maxDepth(TreeNode root){
        if(root == null){
            return 0;
        }
        return Math.max(maxDepth(root.left),maxDepth(root.right)) + 1;
    }
```

这段代码还可以优化，优化的思路就是在求`maxDepth`的同时判断左右子树的高度差，也就是说判断以当前节点为根节点的子树是不是平衡二叉树，然后通过`maxDepth`的返回值来反映子树是不是平衡二叉树，如果子树都不是平衡二叉树，那么这棵树的父节点对应的子树也肯定不是平衡二叉树

具体的话可以让`maxDepth`函数在分别求完左右子树的高度的时候，加一个判断，如果当前节点的左右子树的高度差大于1，那么返回一个负值（-1），如果左右子树的高度差小于等于1，那么就正常返回当前节点的最大深度

相应的，在判断当前子树是不是平衡二叉树的时候，就不能只能判断左右子树的高度差是不是小于等于1，还要加上**左右高度差都大于等于0**这个条件，因为如果有一个子树的最大深度小于0，也就说明这棵树已经不是平衡二叉树了，那么它父节点的那棵树也肯定不是平衡二叉树了

下面是这种方法对应的AC代码：

```java
	public boolean IsBalanced_Solution(TreeNode root) {
        return maxDepth1(root) >= 0;
    }
    private int maxDepth1(TreeNode root){
        if(root == null){
            return 0;
        }
        int leftDepth = maxDepth1(root.left);
        int rightDepth = maxDepth1(root.right);
        if(leftDepth >= 0 && rightDepth >= 0 
        && Math.abs(leftDepth - rightDepth) <= 1){//注意这里的判断条件
            return Math.max(leftDepth,rightDepth) + 1;
        }else{
            return -1;//如果这棵子树不是平衡二叉树，可以直接返回一个-1来表明
        }
    }
```

### BM38 在二叉树中找到两个节点的最近公共祖先

下面这道题也是一道之前博哥讲过但是我现在还是不会做的题，唉

首先对于二叉树当中的某一个节点，分析一下p,q（这里是o1,o2）的位置和公共祖先节点位置的关系：

1.如果当前节点为p或q的其中一个的话，那么当前节点一定是公共祖先

2.如果p和q在当前节点的左右两边，那么当前节点也是公共祖先

3.如果p和q都在当前节点的左边（或右边），那么需要在当前节点的左边（或右边）继续找

于是就有了下面的AC代码：

```java
	public int lowestCommonAncestor (TreeNode root, int o1, int o2) {
        // write code here
        if(root == null){
            return -1;
        }
        if(root.val == o1 || root.val == o2){
            return root.val;
        }
        int leftRet = lowestCommonAncestor(root.left,o1,o2);
        int rightRet = lowestCommonAncestor(root.right,o1,o2);
        if(leftRet >= 0 && rightRet >= 0){
            return root.val;
        }else if(leftRet >= 0){
            return leftRet;
        }else{
            return rightRet;
        }
    }
```

但是现在我还是不大理解这段代码的意思，并且还有几个小问题，于是我去和GPT4交流了一下

这是和GPT4交流的记录：https://poe.com/s/m3XJDlJVChIbyeWqUcCI

首先是对前半段代码的理解，也就是下面的这段代码：

```java
		if(root == null){
            return -1;
        }
        if(root.val == o1 || root.val == o2){
            return root.val;
        }
```

这段代码的意思其实并不是去寻找公共祖先，而是去寻找这两个目标节点的位置，找到了目标节点，那么就把这个节点进行一个返回，反之，如果没有找到目标节点，那么就返回一个不存在的值（这题所有节点的值都`>=0`）

用这段代码来找到两个目标节点的位置，从而为下面回溯的时候确定最近公共祖先的位置做铺垫

```java
        int leftRet = lowestCommonAncestor(root.left,o1,o2);
        int rightRet = lowestCommonAncestor(root.right,o1,o2);
```

然后这两行代码分别往左右递归，去寻找两个目标节点的位置

然后在回溯的阶段确定最近公共祖先的位置，即下面的代码：

```java
		if(leftRet >= 0 && rightRet >= 0){
            return root.val;
        }else if(leftRet >= 0){
            return leftRet;
        }else{
            return rightRet;
        }
```

在回溯的过程中，如果两个目标节点分别在当前节点的左右两边时，说明当前节点就是最近公共祖先

如果两个目标节点都在当前节点的一边时，说明在这之前已经找到了最近公共祖先，那么只需要把那一边返回的那个最近公共祖先继续向上传递就可以了

又和GPT4聊了几句：https://poe.com/s/ktqSRU6mhekq9poNHqs2

之前还有一个问题，就是如何确定当前找到的公共祖先就是最近的公共祖先，通过上面的一系列分析，也可以解决这个问题：在回溯的过程中最先找到的公共祖先一定是最近的公共祖先，那么在这之后的回溯中，之前找到的那个最近的公共祖先会一层层向上传递，从而确定了最后的返回值一定是那个最近的公共祖先

这一点在和GPT4的交流中也提到过

