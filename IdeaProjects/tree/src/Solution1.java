public class Solution1 {
    private int postIndex = 0;
    public BinaryTree.BTNode buildTree(int[] inorder, int[] postorder) {
        postIndex = inorder.length - 1;
        return buildTreeChild(postorder,inorder,0,inorder.length - 1);
    }
    private BinaryTree.BTNode buildTreeChild(int[] postorder, int[] inorder,int inbegin,int inend){
        if(inbegin > inend){
            return null;
        }

        BinaryTree.BTNode root = new BinaryTree.BTNode(postorder[postIndex]);

        int rootIndex = findIndex(inorder,inbegin,inend,postorder[postIndex]);
        postIndex--;

        root.right = buildTreeChild(postorder,inorder,rootIndex + 1,inend);
        root.left = buildTreeChild(postorder,inorder,inbegin,rootIndex - 1);

        return root;
    }
    private int findIndex(int[] inorder,int inbegin,int inend,int key){
        for(int i = inbegin;i <= inend;i++){
            if(inorder[i] == key){
                return i;
            }
        }
        return -1;
    }

}
