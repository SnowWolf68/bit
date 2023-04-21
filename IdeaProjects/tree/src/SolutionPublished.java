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
    /*public boolean isCompleteTree(TreeNode root){

    }*/



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

}
