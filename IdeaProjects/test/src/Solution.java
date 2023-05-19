import java.util.ArrayList;

/** */


public class Solution {

    TreeNode parent = null;
    TreeNode head = null;

    /**
     * JZ36 二叉搜索树与双向链表
     * @param pRootOfTree
     * @return
     */
    public TreeNode Convert(TreeNode pRootOfTree) {
        if(pRootOfTree == null){
            return null;
        }
        Convert(pRootOfTree.left);
        //此时pRootOfTree就是最小的节点
        //还需要记录一下此时的parent节点
        if(parent == null){
            parent = pRootOfTree;
            head = pRootOfTree;
        }else{
            //当前节点是pRootOfTree
            parent.right = pRootOfTree;
            pRootOfTree.left = parent;
            parent =  pRootOfTree;
        }
        Convert(pRootOfTree.right);
        return head;
    }


    /**
     * 上一题的暴力写法
     * @param pRootOfTree
     * @return
     */
    public TreeNode Convert1(TreeNode pRootOfTree) {
        if(pRootOfTree == null){
            return null;
        }
        ArrayList<TreeNode> arrayList = new ArrayList<>();
        inorder(pRootOfTree,arrayList);
        for (int i = 0; i < arrayList.size(); i++) {
            if(i == 0){
                if(i + 1 >= arrayList.size()){
                    arrayList.get(i).right = null;
                }else{
                    arrayList.get(i).right = arrayList.get(i + 1);
                }
                continue;
            }
            if(i == arrayList.size() - 1){
                arrayList.get(i).left = arrayList.get(i - 1);
                continue;
            }
            arrayList.get(i).left = arrayList.get(i - 1);
            arrayList.get(i).right = arrayList.get(i + 1);
        }
        return arrayList.get(0);
    }

    private void inorder(TreeNode pRootOfTree, ArrayList<TreeNode> arrayList) {
        if(pRootOfTree == null){
            return;
        }
        inorder(pRootOfTree.left,arrayList);
        arrayList.add(pRootOfTree);
        inorder(pRootOfTree.right,arrayList);
    }

    TreeNode parent1 = null;

    /**
     * BM34 判断是不是二叉搜索树
     * 其实直接中序遍历存到数组里再判断也没问题，只不过这里没用那种写法
     * @param root
     * @return
     */
    public boolean isValidBST (TreeNode root) {
        // write code here
        if(root == null){
            return true;
        }
        //下面的代码是关键
        if (!isValidBST(root.left)) {
            return false;
        }
        //isValidBST(root.left);
        //这里需要对isValidBST(root.left)的返回值进行判断，而不能简单递归下去
        //原因是如果子递归返回了一个false，如果这里不判断的话，也会继续向下执行
        //所以结果就是无论如何都会返回true

        //此时递归到最小的节点 -- root
        if(parent1 == null){
            parent1 = root;
        }else{
            if(parent1.val > root.val){
                return false;
            }
            parent1 = root;
        }
        isValidBST(root.right);
        return true;
    }

}