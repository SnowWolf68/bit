public class test {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(6);
        root.right = new TreeNode(16);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(8);
        root.right.left = new TreeNode(12);
        root.right.right = new TreeNode(14);

        Solution solution = new Solution();
        //TreeNode ret = solution.Convert(root);
        System.out.println(solution.isValidBST(root));

        System.out.println("sdfd");
    }


}
