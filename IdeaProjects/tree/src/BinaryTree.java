import java.util.*;

public class BinaryTree {
    public static class BTNode {
        public int val;
        public BTNode left;
        public BTNode right;

        public BTNode(int val) {
            this.val = val;
        }
    }
    private BTNode root;
    public void createBinaryTree(){
        BTNode node1 = new BTNode(1);
        BTNode node2 = new BTNode(2);
        BTNode node3 = new BTNode(3);
        BTNode node4 = new BTNode(4);
        BTNode node5 = new BTNode(5);
        BTNode node6 = new BTNode(6);
        root = node1;
        node1.left = node2;
        node2.left = node3;
        node1.right = node4;
        node4.left = node5;
        node5.right = node6;
    }
    // 前序遍历
    public void preOrder(BTNode root){
        if(root == null){
            return;
        }
        System.out.print(root.val + " ");
        preOrder(root.left);
        preOrder(root.right);
    }
    // 中序遍历
    public void inOrder(BTNode root){
        if(root == null){
            return;
        }
        inOrder(root.left);
        System.out.print(root.val + " ");
        inOrder(root.right);
    }
    // 后序遍历

    /**
     * 层序遍历-直接打印
     * @param root
     */
    public void levelOrder(BTNode root){
        Queue<BTNode> queue = new LinkedList<>();
        if(root != null){
            queue.offer(root);
        }
        while(!queue.isEmpty()){
            BTNode top = queue.poll();
            System.out.print(top.val + " ");
            if(top.left != null){
                queue.offer(top.left);
            }
            if(top.right != null){
                queue.offer(top.right);
            }
        }
    }

    /**
     * 层序遍历,返回值是一个二维数组
     * 这个是我自己写的，也可以AC
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder2(BTNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        int size = 0;
        Queue<BTNode> queue = new LinkedList<>();
        if(root != null){
            queue.offer(root);
        }
        ArrayList<Integer> arrayList = null;
        while(!queue.isEmpty()){
            if(size == 0){
                size = queue.size();
                arrayList = new ArrayList<>();
            }
            BTNode top = queue.poll();
            size--;
            arrayList.add(top.val);
            if(top.left != null){
                queue.offer(top.left);
            }
            if(top.right != null){
                queue.offer(top.right);
            }
            if(size == 0){
                ret.add(arrayList);
            }
        }
        return ret;
    }

    /**
     *  层序遍历-返回二维数组-AC
     * @param root
     * @return: List<List<Integer>>
     */
    public List<List<Integer>> levelOrder3(BTNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        if(root == null){
            return ret;
        }
        Queue<BTNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            while(size != 0){
                BTNode top = queue.poll();
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
    public void postOrder(BTNode root){
        if(root == null){
            return;
        }
        postOrder(root.left);
        postOrder(root.right);
        System.out.print(root.val + " ");
    }
    // 获取树中节点的个数
    public static int usedSize = 0;
    int size1(BTNode root){
        if(root == null){
            return 0;
        }
        usedSize++;
        size1(root.left);
        size1(root.right);
        return usedSize;
    }
    int size2(BTNode root){
        if(root == null){
            return 0;
        }
        return size2(root.left) + size2(root.right) + 1;
    }
    // 获取叶子节点的个数
    public static int LeafNodeCount = 0;
    int getLeafNodeCount1(BTNode root){
        if(root == null){
            return 0;
        }
        if(root.left == null && root.right == null){
            LeafNodeCount++;
        }
        getLeafNodeCount1(root.left);
        getLeafNodeCount1(root.right);
        return LeafNodeCount;
    }
    int getLeafNodeCount2(BTNode root){
        if(root == null){
            return 0;
        }
        if(root.left == null && root.right == null){
            return 1;
        }
        return getLeafNodeCount2(root.left) + getLeafNodeCount2(root.right);
    }
    // 子问题思路-求叶子结点个数
    // 获取第K层节点的个数
    int getKLevelNodeCount(BTNode root,int k){
        if(root == null){
            return 0;
        }
        if(k == 1){
            return 1;
        }
        return getKLevelNodeCount(root.left,k - 1) + getKLevelNodeCount(root.right,k - 1);
    }
    // 获取二叉树的高度
    int getHeight(BTNode root){
        if(root == null){
            return 0;
        }
        return Math.max(getHeight(root.left),getHeight(root.right)) + 1;
    }
    // 检测值为value的元素是否存在
    public BTNode find(BTNode root, int val){
        if(root == null){
            return null;
        }
        if(root.val == val){
            return root;
        }
        BTNode left = find(root.left,val);
        BTNode right = find(root.right, val);
        return left == null ? right : left;//这样写相当简洁
    }
    //这样写貌似也可以
    public BTNode find1(BTNode root, int val){
        if(root == null){
            return null;
        }
        if(root.val == val){
            return root;
        }
        find1(root.left,val);
        find1(root.right, val);
        return null;
    }

    // 判断一棵树是不是完全二叉树
    public boolean isCompleteTree(BTNode root){
        Queue<BTNode> queue = new LinkedList<>();
        if(root != null){
            queue.offer(root);
        }
        while(!queue.isEmpty()){
            BTNode cur = queue.poll();
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

    /*public List<Integer> preorderTraversal(BTNode root) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        if(root == null){
            return arrayList;
        }
        arrayList.add(root.val);
        preorderTraversal(root.left);
        preorderTraversal(root.right);
        return arrayList;
    }*/

    public List<Integer> preorderTraversal(BTNode root) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        if(root == null){
            return arrayList;
        }
        arrayList.add(root.val);
        List<Integer> leftTree = preorderTraversal(root.left);
        arrayList.addAll(leftTree);
        List<Integer> rightTree = preorderTraversal(root.right);
        arrayList.addAll(rightTree);
        return arrayList;
    }
    public List<Integer> inorderTraversal(BTNode root) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        if(root == null){
            return arrayList;
        }
        List<Integer> leftTree = inorderTraversal(root.left);
        arrayList.addAll(leftTree);
        arrayList.add(root.val);
        List<Integer> rightTree = inorderTraversal(root.right);
        arrayList.addAll(rightTree);
        return arrayList;
    }
    public List<Integer> postorderTraversal(BTNode root) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        if(root == null){
            return arrayList;
        }
        List<Integer> leftTree = postorderTraversal(root.left);
        arrayList.addAll(leftTree);
        List<Integer> rightTree = postorderTraversal(root.right);
        arrayList.addAll(rightTree);
        arrayList.add(root.val);
        return arrayList;
    }
    public List<Integer> preorderTraversalNor(BTNode root) {
        List<Integer> ret = new ArrayList<>();
        if(root == null){
            return null;
        }
        Stack<BTNode> stack = new Stack<>();
        BTNode cur = root;
        while(cur != null || !stack.empty()){
            while(cur != null){
                stack.push(cur);
                //System.out.print(cur.val + " ");
                ret.add(cur.val);
                cur = cur.left;
            }
            BTNode top = stack.pop();
            cur = top.right;
        }
        return ret;
    }

    //时间复杂度O(n ^ 2)
    public boolean isBalanced(BTNode root) {
        if(root == null){
            return true;
        }
        return Math.abs(maxDepth(root.left) - maxDepth(root.right)) < 2
                && isBalanced(root.left)
                && isBalanced(root.right);
    }
    public int maxDepth(BTNode root) {
        if(root == null){
            return 0;
        }
        return Math.max(maxDepth(root.left),maxDepth(root.right)) + 1;
    }
    //时间复杂度O(n)
    public boolean isBalanced1(BTNode root) {
        if(root == null){
            return true;
        }
        return maxDepth1(root) >= 0;
    }
    public int maxDepth1(BTNode root) {
        if(root == null){
            return 0;
        }
        int leftH = maxDepth1(root.left);
        int rightH = maxDepth1(root.right);
        if(leftH >= 0 && rightH >= 0
                && Math.abs(leftH - rightH) < 2){
            return Math.max(leftH,rightH) + 1;
        }else{
            return -1;
        }
    }

    public BTNode lowestCommonAncestor(BTNode root, BTNode p, BTNode q) {
        if(root == null){
            return null;
        }
        if(p == root || q == root){
            return root;
        }
        BTNode leftRet = lowestCommonAncestor(root.left,p,q);
        BTNode rightRet = lowestCommonAncestor(root.right,p,q);
        if(leftRet != null && rightRet != null){
            return root;
        }else if(leftRet != null){
            return leftRet;
        }else{
            return rightRet;
        }
    }

    public BTNode lowestCommonAncestor2(BTNode root, BTNode p, BTNode q) {
        if(root == null){
            return null;
        }
        Stack<BTNode> s1 = new Stack<>();
        getPath(root,p,s1);
        Stack<BTNode> s2 = new Stack<>();
        getPath(root,q,s2);
        int size1 = s1.size();
        int size2 = s2.size();
        if(size1 > size2){
            int size = size1 - size2;
            while(size !=  0){
                s1.pop();
                size--;
            }
        }else{
            int size = size2 - size1;
            while(size !=  0){
                s2.pop();
                size--;
            }
        }
        while(!s1.empty() && !s2.empty()){
            BTNode tmp1 = s1.pop();
            BTNode tmp2 = s2.pop();
            if(tmp1 == tmp2){
                return tmp1;
            }
        }
        return null;
    }
    private boolean getPath(BTNode root, BTNode node, Stack<BTNode> stack){
        if(root == null){
            return false;
        }
        stack.push(root);
        if(root == node){
            return true;
        }
        boolean ret = getPath(root.left, node, stack);
        if(ret == true){
            return true;
        }
        boolean ret2 = getPath(root.right, node, stack);
        if(ret2 == true){
            return true;
        }
        stack.pop();
        return false;
    }
}