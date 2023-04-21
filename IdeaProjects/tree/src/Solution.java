public class Solution {
    private int preIndex = 0;
    public BinaryTree.BTNode buildTree(int[] preorder, int[] inorder) {
        return buildTreeChild(preorder,inorder,0,inorder.length - 1);
    }
    private BinaryTree.BTNode buildTreeChild(int[] preorder, int[] inorder,int inbegin,int inend){
        if(inbegin > inend){
            return null;
        }

        BinaryTree.BTNode root = new BinaryTree.BTNode(preorder[preIndex]);

        int rootIndex = findIndex(inorder,inbegin,inend,preorder[preIndex]);
        preIndex++;

        root.left = buildTreeChild(preorder,inorder,inbegin,rootIndex - 1);
        root.right = buildTreeChild(preorder,inorder,rootIndex + 1,inend);
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
