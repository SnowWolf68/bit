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

### BM39 序列化二叉树

这题虽然题目中提供的方法是用层序遍历的结果进行序列化以及反序列化，然而我想试一下用前序序列和中序序列去做，但是在字符串创建和字符串分割当中遇到了一些问题，于是我去问了一下GPT4：

和GPT4的聊天记录：https://poe.com/s/U4viFhJ0i6OnCmdhfiU2

感觉也没有什么好说的，遇到的一些问题在和GPT4交流的时候也都有体现，下面就直接放代码吧：

```java
	String Serialize(TreeNode root) {
        if(root == null){
            return "";
        }
        List<Integer> preorder = preorder(root);
        List<Integer> inorder = inorder(root);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < preorder.size(); i++) {
            if (i == preorder.size() - 1) {
                stringBuilder.append(preorder.get(i));
                break;
            }
            stringBuilder.append(preorder.get(i));
            stringBuilder.append(",");
        }
        stringBuilder.append(";");
        for (int i = 0; i < inorder.size(); i++) {
            if (i == inorder.size() - 1) {
                stringBuilder.append(inorder.get(i));
                break;
            }
            stringBuilder.append(inorder.get(i));
            stringBuilder.append(",");
        }
        return stringBuilder.toString();
    }
    TreeNode Deserialize(String str) {
        if(str.equals("")){
            return null;
        }
        String[] strs = str.split(";");
        String preorder = strs[0];
        String inorder = strs[1];
        //int[] Intpreorder = new int[preorder.length()];
        // int index = 0;
        // for(int i = 0;i < preorder.length();i++){
        //     if(preorder.charAt(i) == ','){
        //         continue;
        //     }
        //     Intpreorder[index++] = preorder.charAt(i);
        // }
        //int[] Intinorder = new int[inorder.length()];
        // index = 0;
        // for(int i = 0;i < inorder.length();i++){
        //     if(inorder.charAt(i) == ','){
        //         continue;
        //     }
        //     Intinorder[index++] = inorder.charAt(i);
        // }
        int[] Intpreorder = Arrays.stream(preorder.split(","))
            .mapToInt(Integer:: parseInt)
            .toArray();
        int[] Intinorder = Arrays.stream(inorder.split(","))
            .mapToInt(Integer::parseInt)
            .toArray();
        TreeNode root = buildTree(Intpreorder, Intinorder);
        return root;
    }
    public int preIndex;
    private TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTreeChild(preorder, inorder, 0, inorder.length - 1);
    }
    private TreeNode buildTreeChild(int[] preorder, int[] inorder, int start,int end) {
        if (start > end) {
            return null;
        }
        int fIndex = findIndex(preorder, inorder);
        TreeNode cur = new TreeNode(preorder[preIndex++]);
        cur.left = buildTreeChild(preorder, inorder, start, fIndex - 1);
        cur.right = buildTreeChild(preorder, inorder, fIndex + 1, end);
        return cur;
    }
    private int findIndex(int[] preorder, int[] inorder) {
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == preorder[preIndex]) {
                return i;
            }
        }
        return -1;
    }
    private List<Integer> preorder(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                ret.add(cur.val);
                cur = cur.left;
            }
            TreeNode top = stack.pop();
            cur = top.right;
        }
        return ret;
    }
    private List<Integer> inorder(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
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

但是我还是有一点问题，又问了一下GPT4，记录如下：

https://poe.com/s/4YiqCPlqNi0xeh3TiWSU

附几张截图：

<img src="E:\bit\notes\img\image-20230520101708206.png" alt="image-20230520101708206" style="zoom: 50%;" />

<img src="E:\bit\notes\img\image-20230520101750351.png" alt="image-20230520101750351" style="zoom:50%;" />

可以看出修改过后就能通过二叉树的节点值为一位正数的测试点了

### BM43 包含min函数的栈

这是一道很简单的题，之前博哥也带着写过，但是自己写的时候还是遇到了一点问题

主要的问题有两点：

1.`push()`方法中，如果当前的`node`值和`minStack`最小栈的栈顶元素相同时，`minStack`中也需要`push`进当前元素，所以不能用`<`比较，应该用`<=`进行比较

因为如果不往`minStack`中入栈的话，如果有多个相同的最小值，当`pop`出一个以后，这个最小值就没了，所以如果有多个相等的最小值时，要在`minStack`中添加相同个数的这个最小值

2.关于`Integer`对象的比较问题

当两个`Integer`对象在进行算数运算的时候确实会进行自动拆箱操作，但是我误以为两个`Integer`对象在进行`==`号比较的时候也会进行自动拆箱操作

其实后来想一想，`==`号对于引用数据类型（包括`Integer`这种的包装类来说），肯定是比较的两者的地址值，这是毫无疑问的

可能是因为之前写了一个`int`和`Integer`类型的比较`node <= minStack.peek()`会自动拆箱让我误以为这里的`==`号也会进行自动拆箱

这个地方本来不应该错的

看看我糊涂的时候和GPT4的聊天记录：https://poe.com/s/2D0BW3VdM2FjJ2ncIcTr

后来想想这个地方当时没想清楚真的很不应该

突然发现忘记贴AC代码了，那就贴一下：

```java
public class Solution {
    Stack<Integer> stack = new Stack<>();
    Stack<Integer> minStack = new Stack<>();
    
    public void push(int node) {
        if(stack.empty()){
            stack.push(node);
            minStack.push(node);
        }else{
            if(node <= minStack.peek()){
                minStack.push(node);
            }
            stack.push(node);
        }
    }
    
    public void pop() {
        if(stack.peek().equals(minStack.peek())){
            minStack.pop();
        }
        stack.pop();
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int min() {
        return minStack.peek();
    }
}
```

### BM44 有效括号序列

同样是做过的题，但是在这次做的时候还是不能一次AC，难受

还是记录一下我的错误吧：

我一开始的想法是，在第一次遍历时如果遇到左括号，就添加对应的右括号

然后这次遍历完成之后，再重新遍历一次，这次是如果遇到和栈顶元素相同的右括号，就从栈中弹出栈顶元素

然而这样显然是错误的

错误原因感觉也不好解释，反正正确的做法就是在一次遍历中，入栈的同时判断是否需要出栈，如果需要，就出栈

下面放一下AC代码吧

这种代码我都不能AC，感觉好丢人

```java
	public boolean isValid (String s) {
        // write code here
        Stack<Character> stack = new Stack<>();
        for(int i = 0;i < s.length();i++){
            if(s.charAt(i) == '('){
                stack.push(')');
            }else if(s.charAt(i) == '['){
                stack.push(']');
            }else if(s.charAt(i) == '{'){
                stack.push('}');
            }
            if(stack.empty()){
                return false;
            }
            if(s.charAt(i) == stack.peek()){
                stack.pop();
            }
        }
        if(stack.empty()){
            return true;
        }else{
            return false;
        }
    }
```

### BM45 滑动窗口的最大值

这题感觉和之前最小栈那题很像，但是自己想了一下还是不会做，只能去看了一下题解

题解中有一段话是这样说的：

> 我们都知道，若是一个数字A进入窗口后，若是比窗口内其他数字都大，那么这个数字之前的数字都没用了，因为它们必定会比A早离开窗口，在A离开之前都争不过A，所以A在进入时依次从尾部排除掉之前的小值再进入，而每次窗口移动要弹出窗口最前面值，因此队首也需要弹出，所以我们选择双向队列。

也就是说，该双端队列的队尾元素永远都是当前窗口的最大值

当向前滑动一次的时候：

入队：

如果新的元素比当前队头元素大，那么从队头循环弹出所有比新元素小的元素，然后再让新的元素头进队列

如果新的元素比当前队头元素小，那么可以直接头进队列

出队：

判断当前滑动窗口中要滑出去的元素和队尾元素相不相等，如果相等，那么队尾元素也要出队

如果不相等，那么队尾元素无需出队

在入队和出队都完成之后，此时的队尾元素就是当前滑动窗口的最大值了，那么此时再把队尾元素`add`进`ret`结果列表就可以了

下面是AC代码：

```java
    /*
    用队列来模拟这个过程
    首先对于前size个元素：
        如果双端队列为空，直接头进队列
        如果不为空，从队头把所有比当前元素小的都出队，然后当前元素再头进队列
    每次滑动一格，都判断数组当前元素和双端队列队尾元素是否相等，如果相等，就出队
                                                           如果不等，那就继续向后滑动
    因为头尾都要进，出，所以选择双端队列Deque
    */
    public Deque<Integer> deque = new LinkedList<>();
    public ArrayList<Integer> maxInWindows(int [] num, int size) {
        if (size == 0 || size > num.length) {
            return new ArrayList<Integer>();
        }
        for (int i = 0; i < size; i++) {
            add(num[i]);
        }
        ArrayList<Integer> ret = new ArrayList<>();
        ret.add(deque.getLast());
        for (int i = size; i < num.length; i++) {
            if(num[i - size] == deque.getLast()){
                deque.pollLast();
            }
            add(num[i]);
            ret.add(deque.getLast());
        }
        return ret;
    }
    private void add(int val) {
        if (deque.isEmpty()) {
            deque.addFirst(val);
        } else {
            while (!deque.isEmpty() && deque.getFirst() < val) {
                deque.pollFirst();
            }
            deque.addFirst(val);
        }
    }
```

### NC5 二叉树根节点到叶子节点的所有路径和

这题本来想再用非递归的遍历方式做，但是发现`StringBuilder`对象中最后一个字符无法及时删除

因为如果当前节点是叶子节点，那么可以直接删除`StringBuilder`对象的最后一个字符，然而在回溯时的其他情况时，`StringBuilder`的最后一个字符何时该删除却不好判断

所以最后还是选择了用递归的方法进行遍历

递归处理这个问题的好处就是，递归可以很容易判断何时回溯到当前节点 -- 当前节点的左右子节点都递归完成之后，代码执行到这里的时候，也就意味着回溯到了当前节点，那么此时删除`StringBuilder`的最后一个字符，就可以轻松解决非递归很难解决的问题

下面是AC代码：
```java
	public int sum = 0;
    public StringBuilder stringBuilder = new StringBuilder();
    public int sumNumbers (TreeNode root) {
        // write code here
        if(root == null){
            return 0;
        }
        getSum(root);
        return sum;
    }
    private void getSum(TreeNode root){
        if(root == null){
            return;
        }
        stringBuilder.append(root.val);
        if(root.left == null && root.right == null){
            sum += Integer.parseInt(stringBuilder.toString());
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            return;
        }
        if(root.left != null){
            getSum(root.left);
        }
        if(root.right != null){
            getSum(root.right);
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    }
```

但是刚才我又问了一下GPT4，他给我了一种不用递归的方法：

GPT4原话：

> 在这个方法中，我们使用两个栈：`nodeStack`用于存储树节点，`sumStack`用于存储当前路径和。当我们从`nodeStack`弹出一个节点时，我们也从`sumStack`弹出它对应的路径和。然后，我们检查当前节点是否为叶子节点。如果是叶子节点，我们将当前路径和累加到总和中。如果不是叶子节点，我们将其子节点及其对应的路径和压入相应的栈。
>
> 这个方法能够以非递归的方式实现您所需的功能。

```java
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

public int sumNumbers(TreeNode root) {
    if (root == null) {
        return 0;
    }

    int sum = 0;
    Stack<TreeNode> nodeStack = new Stack<>();
    Stack<Integer> sumStack = new Stack<>();
    nodeStack.push(root);
    sumStack.push(root.val);

    while (!nodeStack.isEmpty()) {
        TreeNode currentNode = nodeStack.pop();
        int currentSum = sumStack.pop();

        if (currentNode.left == null && currentNode.right == null) {
            sum += currentSum;
        }

        if (currentNode.right != null) {
            nodeStack.push(currentNode.right);
            sumStack.push(currentSum * 10 + currentNode.right.val);
        }

        if (currentNode.left != null) {
            nodeStack.push(currentNode.left);
            sumStack.push(currentSum * 10 + currentNode.left.val);
        }
    }

    return sum;
}
```

下面是我按这种方式自己写的代码：

```java
	public int sumNumbers (TreeNode root) {
        // write code here
        if(root == null){
            return 0;
        }
        Stack<TreeNode> nodeStack = new Stack<>();
        Stack<Integer> sumStack = new Stack<>();
        nodeStack.push(root);
        sumStack.push(root.val);
        int sum = 0;
        while(!nodeStack.empty()){
            TreeNode cur = nodeStack.pop();
            int curSum = sumStack.pop();
            if(cur.left == null && cur.right == null){
                sum += curSum;
            }
            if(cur.right != null){
                nodeStack.push(cur.right);
                sumStack.push(curSum * 10 + cur.right.val);
            }
            if(cur.left != null){
                nodeStack.push(cur.left);
                sumStack.push(curSum * 10 + cur.left.val);
            }
        }
        return sum;
    }
```

虽然这个代码看起来很好懂，然而自己写起来还是有很多问题

首先关于`nodeStack`和`sumStack`的入栈和出栈时机的问题：

`nodeStack`很明显，就是用来遍历这个二叉树的，所以就是正常的出入栈

`sumStack`这里是用来记录当前为止，路径上的所有节点和，所以每次有节点入栈，`sumStack`就要进行更新，同样，如果有元素出栈，`sumStack`也要进行更新

这里还要注意，当有节点出栈的时候，`sumStack`肯定是要进行`pop`操作的，然而此时还不能直接把弹出来的这个`curSum`直接扔掉，因为下一个节点的当前路径和还是要基于这个`curSum`的，所以就先用一个临时变量记录一下

注意这两点写这段代码应该就没什么大问题了

还有一点不是很重要的，就是如果到了当前节点，那么先遍历（入栈）当前节点的左子节点还是先遍历（入栈）当前节点的右子节点其实都是可以的，因为这里并没有要求遍历顺序，所以只要能遍历，先左还是先右都无所谓

### NC8 二叉树中和为某一值的路径(二)

这题读完题感觉和上面那题的方法应该差不多，都是递归然后去搜索

但是真正写起来还是有一点小小的问题，不过问题不大

先放代码：

```java
	ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
    ArrayList<Integer> list = new ArrayList<>();
    int sum = 0;
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int expectNumber) {
        if(root == null){
            return ret;
        }
        find(root,expectNumber);
        return ret;
    }
    private void find(TreeNode root,int expectedNumber){
        if(root == null){
            return;
        }
        list.add(root.val);
        sum += root.val;
        if(root.left == null && root.right == null){
            if(sum == expectedNumber){
                ret.add(new ArrayList<>(list));
            }
            list.remove(list.size() - 1);
            sum -= root.val;
            return;
        }
        if(root.left != null){
            find(root.left,expectedNumber);
        }
        if(root.right != null){
            find(root.right,expectedNumber);
        }
        list.remove(list.size() - 1);
        sum -= root.val;
    }
```

需要注意一个地方：`ret.add(new ArrayList<>(list));`一开始的时候我直接`ret.add(list)`然后发现最后`ret`里面直接就是空的，把代码拿去调试了一下发现是因为`add`这里添加的是`list`的引用，和之后的`list`共用的一个对象，最后回溯完成之后`list`肯定是空的，那么`ret`中的那个`list`肯定也是空的

解决方法也很简单，就是在添加的时候用`list`再`new`一个新的对象进行添加就好了

还有一点不是很重要的，就是最后提交的时候发现这里路径的顺序有要求，从测试用例上来看，要求应该是先是左边的路径，然后才是右边的路径，所以递归中左右子节点的递归顺序就不能随便来了，而是应该先递归左子节点，然后递归右子节点

注意以上这两小点，这段代码就没什么问题了

做下一题的时候突然想起来，`list`被共用的这个问题也可以通过在函数里面每一次`new`一个新的`list`来解决

只不过我这里把`list`放到了成员位置，所以容易出现被共用的问题

### NC98 判断t1树中是否有与t2树完全相同的子树

这题不是一道难题，但是做的时候思路还是没有那么清晰，还是写一下吧

由于是要在`t1`中找是不是有`t2`的子树，所以应该拿着`t2`这棵树去和`t1`的每一个子树进行比较

这里选择用递归来进行遍历`t1`这棵树

对于`t1`中的某一个节点，先判断当前`t1`的这棵子树是不是和`t2`是相同的树，如果是，那么返回`true`，如果不是，继续递归当前节点的左右子节点，判断以左右子节点为根节点的子树是不是和`t2`是相同的树

这样大概一写思路就差不多知道应该怎么写代码了

**判断两棵树是否相同的`isSameTree`这个函数肯定是必不可少的**

下面是AC代码：
```java
	public boolean isContains (TreeNode root1, TreeNode root2) {
        // write code here
        if(root1 == null && root2 == null){
            return true;
        }else if(root1 == null && root2 != null 
        || root1 != null && root2 == null){
            return false;
        }else{
            if(isSameTree(root1,root2)){
                return true;
            }else{
                return isContains(root1.left,root2) || isContains(root1.right,root2);
            }
        }

    }
    private boolean isSameTree(TreeNode root1,TreeNode root2){
        if(root1 == null && root2 == null){
            return true;
        }else if(root1 != null && root2 != null){
            if(root1.val == root2.val){
                return isSameTree(root1.left,root2.left) 
                && isSameTree(root1.right,root2.right);
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
```

下面是GPT4提供的如果把`isSameTree`函数写在`isContains`函数里面，只用一个函数进行递归调用的方法

这样写很明显就不如把`isSameTree`拿出来，在`isContains`函数的递归过程中如果需要的话随时调用方便

```java
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;
    }
}

public class Solution {
    public boolean isContains(TreeNode root1, TreeNode root2) {
        return isContains(root1, root2, false);
    }

    private boolean isContains(TreeNode root1, TreeNode root2, boolean checkSameTree) {
        if (root1 == null && root2 == null) {
            return true;
        } else if (root1 == null || root2 == null) {
            return false;
        } else {
            if (checkSameTree) {
                // Check if the trees are the same
                return root1.val == root2.val
                        && isContains(root1.left, root2.left, true)
                        && isContains(root1.right, root2.right, true);
            } else {
                // Check if the current subtree is the same as root2
                if (isContains(root1, root2, true)) {
                    return true;
                }
                // Recursively check the left and right subtrees
                return isContains(root1.left, root2, false) || isContains(root1.right, root2, false);
            }
        }
    }
}
```

### NC273 树的子结构

注意这题和上一题的区别：

上一题要求是**子树**，而这一题要求是**子结构**

也就意味着：

1. 如果`root2`为`null`，不管`root1`是不是`null`，都需要返回`false`（这点我是从测试数据里面看出来的，如果不改就会有两组数据过不了）
2. 如果在判断两棵树是否相同时，`root2 == null`，此时意味着`root2`已经完全匹配完了，并且没有返回`false`，说明此时`root2`为根的树就是`root1`的子结构，所以也应该返回`true`

只有这两处不同，下面是代码：

先写一下判断是不是子树的代码：

```java
	public boolean isContains(TreeNode root1, TreeNode root2) {
        if(root1 == null && root2 == null){
            return true;
        }
        if (root1 == null && root2 != null) {
            return false;
        }
        if (isSameTree(root1, root2)) {
            return true;
        }
        return isContains(root1.left, root2)
               || isContains(root1.right, root2);
    }
    private boolean isSameTree(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1 == null && root2 != null
                || root1 != null && root2 == null) {
            return false;
        }
        if (root1.val == root2.val) {
            return isSameTree(root1.left, root2.left)
                   && isSameTree(root1.right, root2.right);
        } else {
            return false;
        }
    }
```

相较于上题的写法，这里做了一些优化：

在`isSameTree`函数中只是换了一种表达方式，写法还没有变

在`isContains`函数中，省略掉前两个`if`分支，因为这两种情况在下面的`isSameTree`函数中都会判断

直接开始判断`isSameTree(root1,root2)`

但是这样的修改又可能导致`NullPointerException`，原因是：如果`root1 == null`，那么`isSameTree`函数会返回一个`false`，那么就不会进`if`，会继续走到下面的`isContains(root1.left, root2) || isContains(root1.right, root2);`中，此时就会出现空指针异常

对应的修改方法就是在一开始加一个判断，即如果`root1 == null`，那么第一棵树中明显不可能有第二棵树的子树，需要返回`false`

但这样还有一个前提，就是`root2 != null`，因为一旦`root1`和`root2`都为空，很明显也应该返回一个`true`，所以还需要再加一个判断

所以最后就改成了上面这个样子

然后再把上面的代码修改之前提到的两处不同，于是就有了这题的代码：

```java
	public boolean HasSubtree(TreeNode root1, TreeNode root2) {
        if(root2 == null){
            return false;
        }
        if(root1 == null && root2 == null){
            return true;
        }
        if (root1 == null && root2 != null) {
            return false;
        }
        if (isSameTree(root1, root2)) {
            return true;
        }
        return HasSubtree(root1.left, root2)
               || HasSubtree(root1.right, root2);
    }
    private boolean isSameTree(TreeNode root1, TreeNode root2) {
        if(root2 == null){
            return true;
        }
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1 == null && root2 != null
                || root1 != null && root2 == null) {
            return false;
        }
        if (root1.val == root2.val) {
            return isSameTree(root1.left, root2.left)
                   && isSameTree(root1.right, root2.right);
        } else {
            return false;
        }
    }
```



### NC162 二叉树中和为某一值的路径(三)

因为路径不规定必须从根节点到叶子节点，而是从父节点到子节点的路径就算是有效的，所以要把路径从任意一个节点开始都考虑进去

直接的方法就是进行两次遍历，第一次遍历是遍历整棵树，其中的每一个节点都当作是当前路径的开始节点

第二次遍历就是从开始节点向下进行遍历，遍历的同时判断路径上的节点和是不是等于`sum`

但是这里有一个坑

就是当确定了当前路径的开始节点，遍历开始节点下面的每一个节点来找路径的时候，如果当前路径上的节点值的和等于`sum`时，还不能直接`return`回上一层递归，而是要继续遍历当前节点的左右子节点，继续进行递归

原因是，因为这棵二叉树的节点值并不是全部大于等于0的，也就意味着`1->2->3`满足要求，`1->2->3->0`也满足要求，甚至`1->2->3->0->(-1)->1`也是满足要求的

所以如果当前路径上的节点值之和等于`sum`的时候，不能直接`return`，而是应该继续遍历当前节点的左右子节点

```java
	public int count = 0;
    public int FindPath (TreeNode root, int sum) {
        // write code here
        if(root == null){
            return 0;//这里return什么都无所谓
        }
        find(root,sum);
        FindPath(root.left,sum);
        FindPath(root.right,sum);
        return count;
    }
    private void find(TreeNode root,int sum){
        if(root == null){
            return;
        }
        sum -= root.val;
        if(sum == 0){
            count++;
            //return;
        }
        find(root.left,sum);
        find(root.right,sum);
    }
```

下面是用哈希表的写法：

```java
	public int FindPath(TreeNode root, int sum) {
        // 创建一个哈希表来存储累加和及其出现的次数
        Map<Integer, Integer> prefixSumCount = new HashMap<>();
        // 初始化哈希表，将和为 0 的路径的数量设置为 1
        prefixSumCount.put(0, 1);
        // 调用递归函数进行遍历
        return findPathWithHash(root, prefixSumCount, 0, sum);
    }

    private int findPathWithHash(TreeNode root,
                                 Map<Integer, Integer> prefixSumCount, int currentSum, int target) {
        // 如果当前节点为 null，返回 0
        if (root == null) {
            return 0;
        }

        // 计算从根节点到当前节点的累加和
        currentSum += root.val;
        // 检查 currentSum - target 是否在哈希表中，如果在，将对应的值赋给 count，否则赋值为 0
        int count = prefixSumCount.getOrDefault(currentSum - target, 0);
        // 更新哈希表，将当前累加和的路径数量加 1
        prefixSumCount.put(currentSum, prefixSumCount.getOrDefault(currentSum, 0) + 1);

        // 递归遍历左右子树，并将找到的满足条件的路径数量加到 count 中
        count += findPathWithHash(root.left, prefixSumCount, currentSum, target) +
                 findPathWithHash(root.right, prefixSumCount, currentSum, target);

        // 回溯：在返回上一层之前，将当前累加和的路径数量减 1
        prefixSumCount.put(currentSum, prefixSumCount.get(currentSum) - 1);

        // 返回找到的满足条件的路径数量
        return count;
    }
```

实在是看不懂ing

1. `prefixSumCount.put(0, 1);`这行代码的目的是正确处理从根节点开始的路径

   考虑从根节点开始的路径，该路径和等于`sum`，对于这种情况，`current - target == 0`如果没有这行代码，`map`中就很可能没有和为`0`的路径，那么这时候就会返回错误的答案

2. 有关下面两行代码的顺序问题：

   ```java
   		// 检查 currentSum - target 是否在哈希表中，如果在，将对应的值赋给 count，否则赋值为 0
           int count = prefixSumCount.getOrDefault(currentSum - target, 0);
           // 更新哈希表，将当前累加和的路径数量加 1
           prefixSumCount.put(currentSum, prefixSumCount.getOrDefault(currentSum, 0) + 1);
   ```

   这两行代码乍一看没有顺序问题，先执行那一个都可以

   然而，在根节点为`0`的时候，交换过来就会出现问题：

   如果根节点为0，当前的`currentSum == 0`，如果先更新哈希表，那么此时会把哈希表中累加和为`0`的路径的数量再`+ 1`，由于初始化就让累加和为`0`的节点的数量为`1`，所以这时累加和为`0`的路径数量就变成了`2`

   如果此时`sum`也等于`0`，那么这时候统计出来的数量就是错误的了

   而如果按照先判断是否存在，再更新哈希表的顺序，就可以解决这种问题，因为如果根节点为`0`，此时由于初始化就让累加和为`0`的路径数为`1`，在这种情况下也正好符合要求，因此也就不会出现上面的那种错误情况

### NC204 二叉树的最大宽度

读完题目首先想到的就是用层序遍历，但是中间会有空的节点，如果把空的节点也添加到层序遍历的`queue`中的话，比较难搞，于是问了一下GPT4：

> 我理解你的需求，你想使用层序遍历（BFS）来求解二叉树的最大宽度。下面是一个基于层序遍历的解决方案，我在代码中添加了详细的注释来解释每一步。
>
> ```java
> import java.util.LinkedList;
> import java.util.Queue;
> 
> class TreeNode {
>     int val;
>     TreeNode left;
>     TreeNode right;
>     TreeNode(int x) { val = x; }
> }
> 
> public class Solution {
>     public int widthOfBinaryTree(TreeNode root) {
>         // 如果根节点为 null，则宽度为 0
>         if (root == null) {
>             return 0;
>         }
> 
>         int maxWidth = 0; // 记录最大宽度
>         Queue<TreeNode> nodeQueue = new LinkedList<>(); // 存储节点的队列
>         Queue<Integer> indexQueue = new LinkedList<>(); // 存储节点位置的队列
> 
>         // 将根节点和索引（1）添加到队列中
>         nodeQueue.offer(root);
>         indexQueue.offer(1);
> 
>         // 当队列不为空时，执行循环
>         while (!nodeQueue.isEmpty()) {
>             int levelSize = nodeQueue.size(); // 当前层的节点数量
>             int leftMostIndex = indexQueue.peek(); // 当前层最左节点的索引
>             int rightMostIndex = 0; // 当前层最右节点的索引
> 
>             // 遍历当前层的节点
>             for (int i = 0; i < levelSize; i++) {
>                 TreeNode currentNode = nodeQueue.poll(); // 弹出队列头部的节点
>                 int currentIndex = indexQueue.poll(); // 弹出队列头部的索引
> 
>                 // 如果有左子节点，将左子节点及其索引加入队列
>                 if (currentNode.left != null) {
>                     nodeQueue.offer(currentNode.left);
>                     indexQueue.offer(currentIndex * 2);
>                 }
> 
>                 // 如果有右子节点，将右子节点及其索引加入队列
>                 if (currentNode.right != null) {
>                     nodeQueue.offer(currentNode.right);
>                     indexQueue.offer(currentIndex * 2 + 1);
>                 }
> 
>                 rightMostIndex = currentIndex; // 更新最右节点的索引
>             }
> 
>             // 更新最大宽度（当前层的最右节点索引 - 最左节点索引 + 1）
>             maxWidth = Math.max(maxWidth, rightMostIndex - leftMostIndex + 1);
>         }
> 
>         return maxWidth;
>     }
> }
> ```
>
> 这个解决方案的核心是使用两个队列，一个用于存储节点，另一个用于存储节点对应的索引。在层序遍历的过程中，我们可以根据节点的索引计算每一层的宽度，从而找到树的最大宽度。

GPT4选择用两个`queue`来分别记录每一层的每个节点，以及每个节点对应的`index`，由于每一层的父节点和其左右子节点之间的关系是`2 * i`和`2 * i + 1`的关系，所以在添加下一层的节点的时候，就可以根据父节点的`index`，从而求得其左右子节点的`index`

**注意：**这里的`index`并不是每个节点在当前二叉树中的`index`，而是当前节点在完全二叉树中的对应的`index`

这样做就无需考虑中间的空节点，因为我们考虑的`index`是在完全二叉树中的`index`，所以中间即使有空节点，那么空节点也会被计算在宽度之内

当我们求每一层的宽度的时候，只需要`rightIndex - leftIndex + 1`就可以得到当前层的宽度

然后单独定义一个`maxWeight`，每一层遍历完之后更新`maxWeight`，就可以在最后得到整个二叉树的最大宽度

下面是我按照GPT4的思路自己写了一遍：

```java
	public int widthOfBinaryTree (TreeNode root) {
        // write code here
        if(root == null){
            return 0;
        }
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> indexQueue = new LinkedList<>();
        int maxWeight = 0;
        nodeQueue.offer(root);
        indexQueue.offer(0);
        while(!nodeQueue.isEmpty()){
            int levelSize = nodeQueue.size();
            int leftIndex = indexQueue.peek();
            int rightIndex = 0;
            for(int i = 0;i < levelSize;i++){
                TreeNode cur = nodeQueue.poll();
                int curIndex = indexQueue.poll();
                rightIndex = curIndex;
                if(cur.left != null){
                    nodeQueue.offer(cur.left);
                    indexQueue.offer(curIndex * 2);
                }
                if(cur.right != null){
                    nodeQueue.offer(cur.right);
                    indexQueue.offer(curIndex * 2 + 1);
                }
            }
            maxWeight = Math.max(maxWeight,rightIndex - leftIndex + 1);
        }
        return maxWeight;
    }
```

这一题本想问问有没有其他方法，梯子g了，等梯子好了我再问问

### NC234 二叉树的最小深度

这题不难，但是有一个小坑，简单一说就行

首先第一想法还是直接`return Math.max(run(root.left),run(root.right)) + 1`，然而，当遇到单分支的情况，这行代码会导致直接返回一个1，但是正确的答案应该是不为空的那一棵子树的最小深度

所以如果当前节点的其中一棵子树为空，应该特殊处理这种情况，返回不为空的那一棵子树的最小深度+1

下面是代码：

```java
	public int run (TreeNode root) {
        // write code here
        if(root == null){
            return 0;
        }
        if(root.left == null){
            return run(root.right) + 1;
        }
        if(root.right == null){
            return run(root.left) + 1;
        }
        return Math.min(run(root.left),run(root.right)) + 1;
    }
```

### NC279 二叉树的下一个结点

这题刚开始看感觉很蒙

首先分析一下这题二叉树的结构：在基本二叉树的基础上，每个节点都多了一个指向它父节点的指针`next`

再分析形参：这题的形参很巧妙，由于这棵二叉树的每一个节点都有一个指向其父节点的指针`next`，所以对于每一个节点，都可以根据左右节点指针以及父节点指针，找到一整棵树，所以这也是为什么这题的传参只有一个节点，也就是当前节点

题目的要求就是找到在中序遍历中当前节点的下一个节点

暴力的方法就是中序遍历，然后找到当前节点的位置，然后再找到当前节点的下一个节点，但是这样做就没意思了

正确的做法是判断当前节点在整个二叉树当中的位置

由于中序遍历的顺序是：左，中，右

所以对于当前节点，无需考虑其左子节点的情况

1、如果当前节点存在右子节点，那么中序遍历的下一个节点直接就是当前节点的右子节点

2、如果当前节点不存在右子节点：

​			 如果当前节点是父节点的左子节点，那么中序遍历的下一个节点就是当前节点的父节点

​             如果当前节点是父节点的右子节点，那么需要一直向上寻找，直到找到一个节点，使得这个节点是其父节点的左子节点，如果找到根节点也没有找到这样的一个节点，说明整棵树的中序遍历已经完成了，直接返回`null`即可

但是这样写还不够，有几个地方会出现空指针异常，这几个地方我会在代码中标出来

下面是代码：

```java
	public TreeLinkNode GetNext(TreeLinkNode pNode) {
        if(pNode == null){//根节点这里其实不判断也可以，因为题目要求二叉树的节点个数至少为1个
            return null;
        }
        if(pNode.right != null){
            TreeLinkNode cur = pNode.right;
            while(cur.left != null){
                cur = cur.left;
            }
            return cur;
        }else if(pNode.right == null){
            if(pNode.next == null){
                return null;
            }
            //关键的是这里
            //因为下面会找pNode.next，而如果这里的pNode为null，就会出现空指针异常
            //所以这里要加一个判断，如果当前节点没有父节点 -- 也就是说如果当前节点就是根节点
            //并且由上面的判断条件可知，当前节点没有右子节点
            //此时整棵二叉树已经全部遍历完了，直接返回null就可以
            if(pNode.next.left == pNode){
                return pNode.next;
            }else{
                TreeLinkNode cur = pNode.next;
                while(cur.next != null && cur.next.left != cur){
                    cur = cur.next;
                }
                if(cur.next == null){
                    return null;
                }else{
                    return cur.next;
                }
            }
        }
        return null;
    }
```

### NC54 三数之和

#### 1、非哈希表

看了一下题解，自己写的时候有很多想法，但是写出来的代码有很多bug，改来改去最后还是改成了题解的模样（哭

大概方法就是先确定一个（用一次遍历）

在这个确定的元素后面的区间中，用双指针来找符合条件的另外两个元素

但是在去重以及双指针的更新中，还是存在很多的坑

中间遇到的坑太多了，就不细说了，直接放AC代码吧：

```java
	public ArrayList<ArrayList<Integer>> threeSum(int[] num) {
        ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
        if (num.length < 3) {
            return ret;
        }
        Arrays.sort(num);
        for (int i = 0; i < num.length; i++) {//这里题解用的是i < num.length - 2，题解更严谨，虽然我这样写也可以过
            if (i > 0 && num[i] == num[i - 1]) {//去重
                continue;
            }
            int left = i + 1;//双指针
            int right = num.length - 1;
            int target = -num[i];
            while (left < right) {
                if (num[left] + num[right] == target) {
                    //这里也是要注意的地方
                    //一开始我想用Arrays.asList()，然后在前面用(ArrayList<Integer>)强转为ArrayList
                    //但是这样的强转会抛异常
                    //这里有一个坑，之前我也没有注意到，问了GPT又看了源码才发现，我在下面会详细说
                    ArrayList<Integer> list = new ArrayList<>(Arrays.asList(num[i], num[left],
                            num[right]));
                    ret.add(list);
                    //下面这两段也都是去重操作
                    //即如果遇到相同的元素，因为之前已经判断过了，所以可以直接跳过
                    while (left + 1 < right && num[left] == num[left + 1]) {
                        left++;
                    }
                    while (left < right - 1 && num[right] == num[right - 1]) {
                        right--;
                    }
                    //当前满足要求的这一组处理完了，再让两个指针同时向中间走，继续寻找
                    left++;
                    right--;
                }else if (num[left] + num[right] > target) {
                    //但是如果当前的一组元素不满足要求
                    //此时就不能同时更新两个指针，而是判断当前的和与目标的大小关系
                    //然后再移动相应的指针
                    //移动一个指针就可以，此时如果移动两个指针，可能会遗漏一些答案
                    right--;
                } else {
                    left++;
                }
            }
        }
        return ret;
    }
```

**注意：**`Arrays.asList()`的返回值不是`java.util.List`，而是`Arrays.ArrayList`，即`Arrays`中的一个内部类，具体如图：

![image-20230525233639457](E:\bit\notes\img\image-20230525233639457.png)

注意这里虽然函数原型上返回的是`List`，然而在函数体里面返回的是一`ArrayList`

然而这个`ArrayList`就是我们常用的那个`ArrayList`吗，继续跟进

<img src="E:\bit\notes\img\image-20230525233800302.png" alt="image-20230525233800302" style="zoom:80%;" />

跟进之后发现到了这里，这张图看的不是很清楚

再看一张：

![image-20230525233918657](E:\bit\notes\img\image-20230525233918657.png)

![image-20230525233958473](E:\bit\notes\img\image-20230525233958473.png)

这张图就很清楚了，我们可以发现这里返回的`ArrayList`其实是`Arrays`的内部类，不是我们常用的`java.util.ArrayList`

这两者之间没有继承关系，所以进行强转自然会抛出类型转换异常

解决方法就是利用`ArrayList`的重载的构造函数：

![image-20230526103746007](E:\bit\notes\img\image-20230526103746007.png)

这个构造函数接收一个`Collection`对象，所以可以把`asList`的返回值传过来，这样就可以创建一个`ArrayList`类的对象了

#### 2、哈希表

**关键的一点：**

**通过外层的`i`的这个循环，把原来的三数之和问题转化为内层循环中的两数之和的问题**

```java
	public ArrayList<ArrayList<Integer>> threeSum(int[] num) {
        ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
        if(num.length < 3){
            return ret;
        }
        Arrays.sort(num);
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i < num.length;i++){
            map.put(num[i],i);
        }
        for(int i = 0;i < num.length;i++){//一会再判断这里要不要- 2
            if(i > 0 && num[i] == num[i - 1]){
                continue;
            }
            int target = -num[i];
            for(int j = i + 1;j < num.length;j++){
                if(j > i + 1 && num[j] == num[j - 1]){
                    continue;
                }
                if(map.containsKey(target - num[j]) && map.get(target - num[j]) > j){
                    ArrayList<Integer> list = new ArrayList<>
                            (Arrays.asList(num[i],num[j],target - num[j]));


                    ret.add(list);

                }
            }
        }
        return ret;
    }
```

或者也可以利用`HashSet`来进行最后结果的去重

但是要注意，如果直接用`HashSet`并不能保证取出和放入的顺序相同，所以需要用`LinkedHashSet`来保证取出的顺序

下面是用这种方法进行结果去重的代码：

```java
	public ArrayList<ArrayList<Integer>> threeSum(int[] num) {
        HashSet<ArrayList<Integer>> resultSet = new LinkedHashSet<>();
        //这里用LinkedHashSet来保证取出的顺序和放入时的顺序相同
        if(num.length < 3){
            return new ArrayList<ArrayList<Integer>>();
        }
        Arrays.sort(num);
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i < num.length;i++){
            map.put(num[i],i);
        }
        for(int i = 0;i < num.length;i++){//一会再判断这里要不要- 2
            // if(i > 0 && num[i] == num[i - 1]){
            //     continue;
            // }
            int target = -num[i];
            for(int j = i + 1;j < num.length;j++){
                // if(j > i + 1 && num[j] == num[j - 1]){
                //     continue;
                // }
                if(map.containsKey(target - num[j]) && map.get(target - num[j]) > j){
                    ArrayList<Integer> list = new ArrayList<>
                            (Arrays.asList(num[i],num[j],target - num[j]));


                    resultSet.add(list);

                }
            }
        }
        ArrayList<ArrayList<Integer>> ret = new ArrayList<>(resultSet);
        return ret;
    }
```

还有就是`i`这层循环中，要不要到`num.length - 2`就停止的问题

当然这样写更好，但是如果不这样写，也不会有问题，因为如果最后剩余的元素不足，下面的`for(int j = i + 1;j < num.length;j++)`和`if(map.containsKey(target - num[j]) && map.get(target - num[j]) > j)`都不会进去，所以也无所谓

