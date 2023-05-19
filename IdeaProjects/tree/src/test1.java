public class test1 {
    public static void main(String[] args) {
        int[] postorder = {9,15,7,20,3};
        int[] inorder = {9,3,15,20,7};
        SolutionPublished solutionPublished = new SolutionPublished();

        SolutionPublished.TreeNode root = solutionPublished.buildTree1(inorder,postorder);

    }
}
