import sun.reflect.generics.tree.Tree;

import java.util.*;

public class SolutionPublished {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    //https://leetcode.cn/problems/binary-tree-level-order-traversal/
    public void levelOrder(TreeNode root){
        if(root == null){
            return;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode top = queue.poll();
            System.out.print(top.val + " ");
            if(top.left != null){
                queue.offer(top.left);
            }
            if(top.right != null){
                queue.offer(top.right);
            }
        }
    }

    public List<List<Integer>> levelOrder1(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        if(root == null){
            return ret;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            while(size != 0){
                TreeNode top = queue.poll();
                //System.out.print(top.val + " ");
                list.add(top.val);
                if(top.left != null){
                    queue.offer(top.left);
                }
                if(top.right != null){
                    queue.offer(top.right);
                }
                size--;
            }
            ret.add(list);
        }
        return ret;
    }

    /**
     * 判断一棵二叉树是不是完全二叉树
     * @param root
     * @return
     */
    public boolean isCompleteTree(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<>();
        if(root == null){
            return true;
        }
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode cur = queue.poll();
            if(cur != null){
                queue.offer(cur.left);
                queue.offer(cur.right);
            }else{
                break;
            }
        }
        while(!queue.isEmpty()){
            if(queue.poll() != null){
                return false;
            }
        }
        return true;
    }



    //https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/description/
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null){
            return null;
        }
        if(p == root || q == root){
            return root;
        }
        TreeNode leftRet = lowestCommonAncestor(root.left,p,q);
        TreeNode rightRet = lowestCommonAncestor(root.right,p,q);
        if(leftRet != null && rightRet != null){
            return root;
        }else if(leftRet != null){
            return leftRet;
        }else{
            return rightRet;
        }
    }

    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null){
            return null;
        }
        Stack<TreeNode> s1 = new Stack<>();
        getPath(root,p,s1);
        Stack<TreeNode> s2 = new Stack<>();
        getPath(root,q,s2);
        int size1 = s1.size();
        int size2 = s2.size();
        if(size1 > size2){
            int size = size1 - size2;
            while(size != 0){
                s1.pop();
                size--;
            }
        }else{
            int size = size2 - size1;
            while(size != 0){
                s2.pop();
                size--;
            }
        }
        while(!s1.empty() && !s2.empty()){
            TreeNode tmp1 = s1.pop();
            TreeNode tmp2 = s2.pop();
            if(tmp1 == tmp2){
                return tmp1;
            }
        }
        return null;
    }

    /**
     * 在root这棵树中找到node的位置
     * @param root
     * @param node
     * @return
     */
    public boolean getPath(TreeNode root, TreeNode node, Stack<TreeNode> stack){
        if(root == null){
            return false;
        }
        stack.push(root);
        if(root == node){
            return true;
        }
        boolean leftRet = getPath(root.left, node, stack);
        if(leftRet == true){
            return true;
        }
        boolean rightRet = getPath(root.right, node, stack);
        if(rightRet == true){
            return true;
        }
        stack.pop();
        return false;
    }
    //https://leetcode.cn/problems/binary-tree-preorder-traversal/
    public void preorderTraversalNor(TreeNode root) {
        if(root == null){
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while(cur != null || !stack.empty()){
            while(cur != null){
                stack.push(cur);
                System.out.print(cur.val + " ");
                cur = cur.left;
            }
            //cur == null
            TreeNode top = stack.pop();
            cur = top.right;
        }
    }

    public List<Integer> preorderTraversalNor1(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if(root == null){
            return ret;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while(cur != null || !stack.empty()){
            while(cur != null){
                stack.push(cur);
                ret.add(cur.val);
                cur = cur.left;
            }
            //cur == null
            TreeNode top = stack.pop();
            cur = top.right;
        }
        return ret;
    }

    //https://leetcode.cn/problems/binary-tree-inorder-traversal/
    public List<Integer> inorderTraversalNor(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        if(root == null){
            return ret;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while(cur != null || !stack.empty()){
            while(cur != null){
                stack.push(cur);
                //ret.add(cur.val);
                cur = cur.left;
            }
            //cur == null
            TreeNode top = stack.pop();
            ret.add(top.val);
            cur = top.right;
        }
        return ret;
    }


    //https://leetcode.cn/problems/binary-tree-postorder-traversal/
    public List<Integer> postorderTraversalNor(TreeNode root) {
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

    //https://leetcode.cn/problems/construct-string-from-binary-tree/
    public String tree2str(TreeNode root) {
        StringBuilder stringBuilder = new StringBuilder();
        tree2strChild(root,stringBuilder);
        return stringBuilder.toString();
    }

    public void tree2strChild(TreeNode root,StringBuilder stringBuilder){
        if(root == null){
            return;
        }
        stringBuilder.append(root.val);
        if(root.left != null){
            stringBuilder.append("(");
            tree2strChild(root.left,stringBuilder);
            stringBuilder.append(")");
        }else{
            if(root.right == null){
                return;
            }else{
                stringBuilder.append("()");
            }
        }
        if(root.right != null){
            stringBuilder.append("(");
            tree2strChild(root.right,stringBuilder);
            stringBuilder.append(")");
        }
    }

    //https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
    private int preIndex = 0;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return buildTreeChild(preorder,inorder,0, inorder.length - 1);
    }
    public TreeNode buildTreeChild(int[] preorder, int[] inorder,int inBegin,int inEnd){
        if(inBegin > inEnd){
            return null;
        }
        TreeNode root = new TreeNode(preorder[preIndex]);
        int fIndex = findIndex(preorder,inorder);
        preIndex++;
        root.left = buildTreeChild(preorder,inorder,inBegin,fIndex - 1);
        root.right = buildTreeChild(preorder,inorder,fIndex + 1,inEnd);
        return root;
    }
    public int findIndex(int[] preorder,int[] inorder){
        for (int i = 0; i < inorder.length; i++) {
            if(inorder[i] == preorder[preIndex]){
                return i;
            }
        }
        return -1;
    }

    //https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
    private int postIndex = 0;
    public TreeNode buildTree1(int[] inorder, int[] postorder) {
        postIndex = inorder.length - 1;
        return buildTreeChild1(postorder,inorder,0, inorder.length - 1);
    }
    public TreeNode buildTreeChild1(int[] postorder, int[] inorder,int inBegin,int inEnd){
        if(inBegin > inEnd){
            return null;
        }
        TreeNode root = new TreeNode(postorder[postIndex]);
        int fIndex = findIndex1(postorder,inorder);
        postIndex--;
        root.right = buildTreeChild1(postorder,inorder,fIndex + 1,inEnd);
        root.left = buildTreeChild1(postorder,inorder,inBegin,fIndex - 1);
        return root;
    }
    public int findIndex1(int[] postorder,int[] inorder){
        for (int i = 0; i < inorder.length; i++) {
            if(inorder[i] == postorder[postIndex]){
                return i;
            }
        }
        return -1;
    }

    //https://leetcode.cn/problems/balanced-binary-tree/description/

    /**
     * 时间复杂度：因为每次都需要从新的root开始向下遍历一边整棵树，所以时间复杂度为O(N ^ 2)
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        if(root == null){
            return true;
        }
        return Math.abs(maxDepth(root.left) - maxDepth(root.right)) < 2
                && isBalanced(root.left)
                && isBalanced(root.right);
    }

    private int maxDepth(TreeNode root){
        if(root == null){
            return 0;
        }
        return Math.max(maxDepth(root.left),maxDepth(root.right)) + 1;
    }

    /**
     * 时间复杂度：这次改进了maxDepth方法，使得向上回溯时从最下面的节点开始，每一次都判断是否为平衡二叉树
     * 从而实现之遍历一次就得出结果
     * 所以时间复杂度为O(N)
     * @param root
     * @return
     */
    public boolean isBalanced1(TreeNode root){
        return maxDepth1(root) >= 0;
    }

    private int maxDepth1(TreeNode root){
        if(root == null){
            return 0;
        }
        int leftDepth = maxDepth1(root.left);
        int rightDepth = maxDepth1(root.right);
        if(leftDepth >= 0 && rightDepth >= 0
        && Math.abs(leftDepth - rightDepth) < 2){
            return Math.max(leftDepth,rightDepth) + 1;
        }else{
            return -1;
        }
    }

    /**
     * minDepth和maxDepth有点区别，minDepth不能直接return left 和 right 的最小值
     * 因为如果root不是叶子节点（root的左或右有一个不为null），还需要返回minDepth(不为空的那个子节点)
     * 所以需要在最后的return之前判断root是否为叶子节点，即root一边为null，一边不为null时如何返回
     * @param root
     * @return
     */
    public int minDepth(Solution2.TreeNode root) {
        if(root == null){
            return 0;
        }
        if(root.left == null){
            return minDepth(root.right) + 1;
        }
        if(root.right == null){
            return minDepth(root.left) + 1;
        }
        return Math.min(minDepth(root.left),minDepth(root.right)) + 1;
    }

}
