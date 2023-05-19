import java.util.ArrayList;
import java.util.List;

public class Solution {
    /**
     * 95. 不同的二叉搜索树 II
     * 给你一个整数 n ，请你生成并返回所有由 n 个节点组成且节点值从 1 到 n 互不相同的不同 二叉搜索树 。
     * 可以按 任意顺序 返回答案。
     * @param n
     * @return
     */
    public List<TreeNode> generateTrees(int n) {
        return generateChildTree(1,n);
    }

    private List<TreeNode> generateChildTree(int start, int end) {
        List<TreeNode> ret = new ArrayList<>();
        if(start > end){
            ret.add(null);
            return ret;
        }
        for(int i = start;i <= end;i++){
            List<TreeNode> leftTrees = generateChildTree(start,i - 1);
            List<TreeNode> rightTrees = generateChildTree(i + 1,end);
            TreeNode root = new TreeNode(i);
            for (TreeNode leftTree : leftTrees) {
                for (TreeNode rightTree : rightTrees) {
                    root.left = leftTree;
                    root.right = rightTree;
                    ret.add(root);
                }
            }
        }
        return ret;
    }

    

}
