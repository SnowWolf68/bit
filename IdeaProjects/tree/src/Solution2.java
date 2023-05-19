public class Solution2 {

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode() {}
     *     TreeNode(int val) { this.val = val; }
     *     TreeNode(int val, TreeNode left, TreeNode right) {
     *         this.val = val;
     *         this.left = left;
     *         this.right = right;
     *     }
     * }
     */

    public class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode() {}
         TreeNode(int val) { this.val = val; }
         TreeNode(int val, TreeNode left, TreeNode right) {
             this.val = val;
             this.left = left;
             this.right = right;
         }
    }

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

    /**
     * minDepth和maxDepth有点区别，minDepth不能直接return left 和 right 的最小值
     * 因为如果root不是叶子节点（root的左或右有一个不为null），还需要返回minDepth(不为空的那个子节点)
     * 所以需要在最后的return之前判断root是否为叶子节点，即root一边为null，一边不为null时如何返回
     * @param root
     * @return
     */
    public int minDepth(TreeNode root) {
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
