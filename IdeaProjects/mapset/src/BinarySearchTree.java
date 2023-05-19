import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class BinarySearchTree {
    static class TreeNode{
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    private TreeNode root;

    /**
     * 找到val值，返回节点的地址
     * 递归写法
     * @param val
     * @return
     */
    public TreeNode search(int val){
        return search(root,val);
    }

    private TreeNode search(TreeNode cur,int val){
        if(cur == null){
            return null;
        }else if(cur.val == val){
            return cur;
        }else if(val < cur.val){
            search(cur.left,val);
        }else{
            search(cur.right,val);
        }
        return null;
    }

    /**
     * 查找的非递归写法
     * @param val
     * @return
     */
    public TreeNode search1(int val){
        TreeNode cur = root;
        while(cur != null){
            if(val < cur.val){
                cur = cur.left;
            }else if(val > cur.val){
                cur = cur.right;
            }else{
                return cur;
            }
        }
        return null;
    }

    public boolean insert(int val){
        TreeNode newTreeNode = new TreeNode(val);
        if(root == null){
            root = newTreeNode;
            return true;
        }
        TreeNode parent = null;
        TreeNode cur = root;
        while(cur != null){
            if(cur.val < val){
                parent = cur;
                cur = cur.right;
            }else if(cur.val == val){
                return false;
            }else{
                parent = cur;
                cur = cur.left;
            }
        }
        if(parent.val > val){
            parent.left = newTreeNode;
        }else{
            parent.right = newTreeNode;
        }
        return true;
    }

    public void removeNode(int key){
        TreeNode cur = root;
        TreeNode parent = null;
        while(cur != null){
            if(cur.val < key){
                parent = cur;
                cur = cur.right;
            }else if(cur.val > key){
                parent = cur;
                cur = cur.left;
            }else{
                remove(cur,parent);
                return;
            }
        }
    }

    /**
     * 删除cur这个节点
     * @param cur 要删除的节点
     * @param parent 要删除节点的父节点
     */
    private void remove(TreeNode cur, TreeNode parent) {
        if(cur.left == null){
            if(cur == root){
                root = cur.right;
            }else if(cur == parent.left){
                parent.left = cur.right;
            }else{
                parent.right = cur.right;
            }
        }else if(cur.right == null){
            if(cur == root){
                root = cur.left;
            }else if(cur == parent.left){
                parent.left = cur.left;
            }else{
                parent.right = cur.left;
            }
        }else{
            TreeNode targetParent = cur;
            TreeNode target = cur.right;
            while(target.left != null){
                targetParent = target;
                target = target.left;
            }
            cur.val = target.val;
            if(target == targetParent.left) {
                targetParent.left = target.right;
            }else{
                targetParent.right = target.right;
            }
        }
    }



}
