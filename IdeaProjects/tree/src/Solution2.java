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
}
