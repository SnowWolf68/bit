import java.util.*;

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
        return isValidBST(root.right);//这里同样
    }

    /**
     * 95. 不同的二叉搜索树 II
     * @param n
     * @return
     */
    public List<TreeNode> generateTrees(int n) {
        return generateChildTree(1,n);
    }

    private List<TreeNode> generateChildTree(int start, int end) {
        List<TreeNode> ret = new ArrayList<>();
        if(start > end){
            ret.add(null);//不要忘记把null添加进去
            return ret;
        }
        for(int i = start;i <= end;i++){
            List<TreeNode> leftList = generateChildTree(start,i - 1);
            List<TreeNode> rightList = generateChildTree(i + 1,end);
            for (int j = 0; j < leftList.size(); j++) {
                for (int k = 0; k < rightList.size(); k++) {
                    TreeNode root = new TreeNode(i);
                    root.left = leftList.get(j);
                    root.right = rightList.get(k);
                    ret.add(root);
                }
            }
        }
        return ret;
    }

    public int numTrees(int n) {
        return numTreesChild(1,n);
    }
    public int count = 0;
    /*
    递归次数：
    >= && n == 14 -- 2657205
    >  && n == 14 -- 4782969
     */
    private int numTreesChild(int start,int end){
        count++;
        if(start > end){ //因为下面循环里面可能会出现end到start左边的情况
            return 1;     //这种情况实际上表示有一边没有数了，因此也算是一种情况，因此返回1
                          //而且如果这里少了等于号，会多两次递归，从而超时
        }
        int ret = 0;
        for(int i = start;i <= end;i++){
            ret += numTreesChild(start,i - 1) * numTreesChild(i + 1,end);
        }
        return ret;
    }

    public void recoverTree(TreeNode root) {
        ArrayList<TreeNode> inorder = inorderTraversal(root);
        int pos1 = -1;
        int pos2 = -1;
        for (int i = 0; i < inorder.size() - 1; i++) {
            if(inorder.get(i).val > inorder.get(i + 1).val && pos1 == -1){
                pos1 = i;
                continue;
            }
            if(inorder.get(i).val > inorder.get(i + 1).val && pos1 != -1){
                pos2 = i;
            }
        }
        if(pos2 == -1){
            int temp = inorder.get(pos1).val;
            inorder.get(pos1).val = inorder.get(pos1 + 1).val;
            inorder.get(pos1 + 1).val = temp;
        }else{
            int temp = inorder.get(pos1).val;
            inorder.get(pos1).val = inorder.get(pos2 + 1).val;
            inorder.get(pos2 + 1).val = temp;
        }
    }
    public ArrayList<TreeNode> inorderTraversal(TreeNode root){
        ArrayList<TreeNode> ret = new ArrayList<>();
        if(root == null){
            return ret;
        }
        ArrayList<TreeNode> leftRet = inorderTraversal(root.left);
        ret.addAll(leftRet);
        ret.add(root);
        ArrayList<TreeNode> rightRet = inorderTraversal(root.right);
        ret.addAll(rightRet);
        return ret;
    }


    public boolean isSymmetric(TreeNode root) {
        if(root == null){
            return true;
        }
        return isSymmetricChildTree(root.left,root.right);
    }
    private boolean isSymmetricChildTree(TreeNode left,TreeNode right){
        if(left == null && right == null){
            return true;
        }
        if(left != null && right == null ||
                left == null && right != null){
            return false;
        }
        if(left.val != right.val){
            return false;
        }
        return isSymmetricChildTree(left.left,right.right)
                && isSymmetricChildTree(left.right,right.left);
    }

    public List<TreeNode> layerOrder(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<>();
        List<TreeNode> list = new ArrayList<>();
        if(root == null){
            return list;
        }
        queue.offer(root);
        while(!queue.isEmpty()){
            TreeNode cur = queue.poll();
            list.add(cur);
            if(cur.left != null){
                queue.offer(cur.left);
            }
            if(cur.right != null){
                queue.offer(cur.right);
            }
        }
        return list;
    }

    public List<List<TreeNode>> layerOrder1(TreeNode root){
        Queue<TreeNode> queue = new LinkedList<>();
        List<List<TreeNode>> list = new ArrayList<>();
        if(root == null){
            return list;
        }
        queue.offer(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            List<TreeNode> arrayList = new ArrayList<>();
            while(size != 0) {
                TreeNode cur = queue.poll();
                arrayList.add(cur);
                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
                size--;
            }
            list.add(arrayList);
        }
        return list;
    }

    public List<List<Integer>> levelOrder(TreeNode root){
        List<List<Integer>> ret = new ArrayList<>();
        Deque<TreeNode> deque = new LinkedList<>();
        if(root == null){
            return ret;
        }
        deque.addFirst(root);
        boolean isLeftFirst = true;
        while(!deque.isEmpty()){
            int size = deque.size();
            ArrayList<Integer> arrayList = new ArrayList<>();
            if(isLeftFirst){
                isLeftFirst = false;
                while(size != 0){
                    TreeNode cur = deque.pollLast();
                    if(cur.left != null) {
                        deque.addFirst(cur.left);
                    }
                    if(cur.right != null){
                        deque.addFirst(cur.right);
                    }
                    arrayList.add(cur.val);
                    size--;
                }
            }else{
                isLeftFirst = true;
                while(size != 0){
                    TreeNode cur = deque.pollFirst();
                    if(cur.right != null) {
                        deque.addLast(cur.right);
                    }
                    if(cur.left != null){
                        deque.addLast(cur.left);
                    }
                    arrayList.add(cur.val);
                    size--;
                }
            }
            ret.add(arrayList);
        }
        return ret;
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> ret = new ArrayList<>();
        Deque<TreeNode> deque = new LinkedList<>();
        if(root == null){
            return ret;
        }
        deque.addFirst(root);
        boolean isLeftFirst = true;
        while(!deque.isEmpty()){
            int size = deque.size();
            ArrayList<Integer> arrayList = new ArrayList<>();
            while(size != 0){
                size--;
                if(isLeftFirst){
                    TreeNode cur = deque.pollLast();
                    if(cur.left != null){
                        deque.addFirst(cur.left);
                    }
                    if(cur.right != null){
                        deque.addFirst(cur.right);
                    }
                    arrayList.add(cur.val);
                }else{
                    TreeNode cur = deque.pollFirst();
                    if(cur.right != null){
                        deque.addLast(cur.right);
                    }
                    if(cur.left != null){
                        deque.addLast(cur.left);
                    }
                    arrayList.add(cur.val);
                }
            }
            isLeftFirst = !isLeftFirst;
            ret.add(arrayList);
        }
        return ret;
    }

    private int preIndex = 0;
    public TreeNode buildTree(int[] preOrder,int[] inOrder){
        return buildTreeChild(preOrder,inOrder,0, preOrder.length - 1);
    }

    private TreeNode buildTreeChild(int[] preOrder, int[] inOrder, int start, int end) {
        /*if(start == end){
            return new TreeNode(inOrder[start]);
        }*/
        if(start > end){
            return null;
        }
        int fIndex = findIndex(preOrder,inOrder,preIndex);
        preIndex++;
        TreeNode node = new TreeNode(inOrder[fIndex]);
        node.left = buildTreeChild(preOrder,inOrder,start,fIndex - 1);
        node.right = buildTreeChild(preOrder,inOrder,fIndex + 1,end);
        return node;
    }

    private int findIndex(int[] preOrder,int[] inOrder,int i){
        for (int j = 0; j < inOrder.length; j++) {
            if(preOrder[i] == inOrder[j]){
                return j;
            }
        }
        return -1;
    }

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> ret = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if(root == null){
            return ret;
        }
        queue.offer(root);

        while(!queue.isEmpty()){
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            while(size != 0) {
                size--;
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                list.add(node.val);
            }
            ret.add(0,list);
        }
        return ret;
    }

    public int sum;
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if(root == null){
            return false;
        }
        int temp = sum;
        sum += root.val;
        if(root.left == null && root.right == null){
            if(sum == targetSum){
                sum = temp;
                return true;
            }else{
                sum = temp;
                return false;
            }
        }
        if(root.left != null && root.right == null){
            if(hasPathSum(root.left,targetSum)){
                sum = temp;
                return true;
            }else{
                sum = temp;
                return false;
            }
        }else if(root.left == null && root.right != null){
            if(hasPathSum(root.right,targetSum)){
                sum = temp;
                return true;
            }else{
                sum = temp;
                return false;
            }
        }else{
            if(hasPathSum(root.left,targetSum) || hasPathSum(root.right,targetSum)){
                sum = temp;
                return true;
            }else{
                sum = temp;
                return false;
            }
        }
    }


    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> ret = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        int sum = 0;
        pathSumChild(root,targetSum,ret,list,sum);
        return ret;
    }

    private void pathSumChild(TreeNode root, int targetSum, List<List<Integer>> ret, List<Integer> list,int sum) {
        if(root == null){
            return;
        }
        sum += root.val;
        list.add(root.val);
        if(root.left == null && root.right == null){
            if(sum == targetSum){
                ret.add(new ArrayList(list));
            }
        }else{
            pathSumChild(root.left,targetSum,ret,list,sum);
            pathSumChild(root.right,targetSum,ret,list,sum);
        }
        list.remove(list.size() - 1);
    }


    public void flatten(TreeNode root){
        if(root == null){
            return;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        TreeNode pre = null;
        while(!stack.isEmpty()){
            TreeNode top = stack.pop();
            stack.push(top.right);
            stack.push(top.left);
            top.left = null;
            if(pre != null){
                pre.right = top;
            }
            pre = top;
        }
        pre.right = null;
    }

    public Node connect(Node root) {
        List<List<Node>> levelList = levelOrder(root);
        for (int i = 0; i < levelList.size(); i++) {
            List<Node> list = levelList.get(i);
            for (int j = 0; j < list.size(); j++) {
                if(j == list.size() - 1){
                    list.get(j).next = null;
                    break;
                }
                list.get(j).next = list.get(j + 1);
            }
        }
        return root;
    }
    private List<List<Node>> levelOrder(Node root){
        List<List<Node>> ret = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        if(root == null){
            return ret;
        }
        queue.offer(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            List<Node> list = new ArrayList<>();
            while(size != 0){
                size--;
                Node cur = queue.poll();
                if(cur.left != null){
                    queue.offer(cur.left);
                }
                if(cur.right != null){
                    queue.offer(cur.right);
                }
                list.add(cur);
            }
            ret.add(list);
        }
        return ret;
    }

    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> ret = new ArrayList<>();
        List<List<Integer>> level = levelOrder1(root);
        for(List<Integer> list:level){
            ret.add(list.get(list.size() - 1));
        }
        return ret;
    }
    public List<List<Integer>> levelOrder1(TreeNode root){
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
                size--;
                TreeNode cur = queue.poll();
                list.add(cur.val);
                if(cur.left != null){
                    queue.offer(cur.left);
                }
                if(cur.right != null){
                    queue.offer(cur.right);
                }
            }
            ret.add(list);
        }
        return ret;
    }

    public List<String> topKFrequent(String[] words, int k) {
        Map<String,Integer> map = new HashMap<>();
        for (String word : words) {
            if(!map.containsKey(word)){
                map.put(word,1);
            }else{
                int count = map.get(word);
                map.put(word,count + 1);
            }
        }
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if(o1.getValue().compareTo(o2.getValue()) == 0){
                    return o2.getKey().compareTo(o1.getKey());
                }
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        for (Map.Entry<String, Integer> entry : entries) {
            if(minHeap.size() < k){
                minHeap.offer(entry);
            }else{
                Map.Entry<String, Integer> top = minHeap.peek();
                if(entry.getValue().compareTo(top.getValue()) == 0){
                    if(entry.getKey().compareTo(top.getKey()) < 0){
                        minHeap.poll();
                        minHeap.add(entry);
                    }
                }else if(entry.getValue().compareTo(top.getValue()) > 0){
                    minHeap.poll();
                    minHeap.add(entry);
                }
            }
        }
        List<String> ret = new ArrayList<>();
        for(int i = 0;i < k;i++){
            ret.add(minHeap.poll().getKey());
        }
        Collections.reverse(ret);
        return ret;
    }

    public int lengthOfLongestSubstring(String s) {
        if(s.length() == 0){
            return 0;
        }
        int j = 0;
        Set<Character> set = new HashSet<>();
        set.add(s.charAt(0));
        int maxLength = 0;
        int length = 1;
        for(int i = 0;i < s.length();i++){
            if(j < i){
                set.add(s.charAt(i));
                j = i;
                length = 1;
            }
            while(j + 1 < s.length() && !set.contains(s.charAt(j + 1))){
                set.add(s.charAt(j + 1));
                length++;
                j++;
            }
            if(length > maxLength){
                maxLength = length;
            }
            set.remove(s.charAt(i));
            length--;
        }
        return maxLength;
    }

    public List<String> letterCombinations(String digits) {
        List<String> ret = new ArrayList<>();
        if(digits.length() == 0){
            return ret;
        }
        Map<Character,String> map = new HashMap<>();
        map.put('2',"abc");
        map.put('3',"def");
        map.put('4',"ghi");
        map.put('5',"jkl");
        map.put('6',"mno");
        map.put('7',"pqrs");
        map.put('8',"tuv");
        map.put('9',"wxyz");

        StringBuilder stringBuilder = new StringBuilder();
        letterCombinationsChild(ret,map,digits,stringBuilder,0,digits.length() - 1);
        return ret;
    }

    private void letterCombinationsChild(List<String> ret, Map<Character, String> map, String digits,
                                         StringBuilder stringBuilder, int start, int end) {
        if(start > end){
            ret.add(stringBuilder.toString());
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            return;
        }
        String str = map.get(digits.charAt(start));

        for(int i = 0;i < str.length();i++){
            stringBuilder.append(str.charAt(i));
            letterCombinationsChild(ret,map,digits,stringBuilder,start + 1,end);
        }
        if(stringBuilder.length() > 0){
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

    }

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String,List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] array = str.toCharArray();
            Arrays.sort(array);
            String key = new String(array);
            List<String> list = map.getOrDefault(key,new ArrayList<>());
            list.add(str);
            map.put(key,list);
        }
        return new ArrayList<>(map.values());
    }

    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        set.add(n);
        while(true){
            int sum = 0;
            while(n != 0){
                sum += (n % 10) * (n % 10);
                n /= 10;
            }
            if(sum == 1){
                return true;
            }else{
                if(set.contains(sum)){
                    return false;
                }else{
                    set.add(sum);
                    n = sum;
                }
            }
        }
    }

    public boolean isIsomorphic(String s, String t) {
        Map<Character,Character> map1 = new HashMap<>();
        Map<Character,Character> map2 = new HashMap<>();
        for(int i = 0;i < s.length();i++){
            if(map1.containsKey(s.charAt(i)) && map1.get(s.charAt(i)) != t.charAt(i)
            || map2.containsKey(t.charAt(i)) && map2.get(t.charAt(i)) != s.charAt(i)){
                return false;
            }else{
                map1.put(s.charAt(i),t.charAt(i));
                map2.put(t.charAt(i),s.charAt(i));
            }
        }
        return true;
    }

    public boolean isAnagram(String s, String t) {
        char[] array1 = s.toCharArray();
        char[] array2 = t.toCharArray();
        Arrays.sort(array1);
        Arrays.sort(array2);
        s = new String(array1);
        t = new String(array2);
        if(s.equals(t)){
            return true;
        }else{
            return false;
        }
    }

    public int missingNumber(int[] nums) {
        int n = nums.length;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
        }
        for(int i = 0;i <= n;i++){
            if(!set.contains(i)){
                return i;
            }
        }
        return -1;
    }

    public boolean wordPattern(String pattern, String s) {
        String[] str = s.split(" ");
        Map<Character,String> map1 = new HashMap<>();
        Map<String,Character> map2 = new HashMap<>();
        for(int i = 0;i < pattern.length();i++){
            if(map1.containsKey(pattern.charAt(i)) && !map1.get(pattern.charAt(i)).equals(str[i])
            || map2.containsKey(str[i]) && map2.get(str[i]) != pattern.charAt(i)){
                return false;
            }else{
                map1.put(pattern.charAt(i),str[i]);
                map2.put(str[i],pattern.charAt(i));
            }
        }
        return true;
    }

    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<>();
        for(int i = 0;i < nums1.length;i++){
            set.add(nums1[i]);
        }
        Set<Integer> ret = new HashSet<>();
        for(int i = 0;i < nums2.length;i++){
            if(set.contains(nums2[i])){
                ret.add(nums2[i]);
            }
        }
        int[] array = ret.stream().mapToInt(Integer::intValue).toArray();
        return array;
    }

    public int majorityElement(int[] nums) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if(!map.containsKey(nums[i])){
                map.put(nums[i],1);
            }else{
                int count = map.get(nums[i]);
                map.put(nums[i],count + 1);
            }
        }
        Map.Entry<Integer,Integer> maxEntry = null;
        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
            if(maxEntry == null || entry.getValue() > maxEntry.getValue()){
                maxEntry = entry;
            }
        }
        return maxEntry.getKey();
    }


    public int[] intersect(int[] nums1, int[] nums2) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums1.length; i++) {
            int count = map.getOrDefault(nums1[i],0);
            map.put(nums1[i],count + 1);
        }
        int[] ret = new int[Math.max(nums1.length, nums2.length)];
        int index = 0;
        for (int i = 0; i < nums2.length; i++) {
            int count = map.getOrDefault(nums2[i],0);
            if(count > 0){
                ret[index++] = nums2[i];
                count--;
                if(count == 0){
                    map.remove(nums2[i]);
                }else{
                    map.put(nums2[i],count);
                }
            }
        }
        return Arrays.copyOfRange(ret,0,index);
    }

    public char findTheDifference(String s, String t) {
        char[] s1 = s.toCharArray();
        char[] t1 = t.toCharArray();
        Arrays.sort(s1);
        Arrays.sort(t1);
        for(int i = 0;i < t1.length;i++){
            if(i >= s1.length){
                return t1[i];
            }
            if(s1[i] != t1[i]){
                return t1[i];
            }
        }
        return '1';
    }

    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for(int i = 0;i < k;i++){
            minHeap.add(nums[i]);
        }
        for(int i = k;i < nums.length;i++){
            int top = minHeap.peek();
            if(nums[i] > top){
                minHeap.poll();
                minHeap.add(nums[i]);
            }
        }
        return minHeap.peek();
    }

    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new GreaterIntComparator());
        for (int i = 0; i < stones.length; i++) {
            maxHeap.add(stones[i]);
        }
        while(!maxHeap.isEmpty()){
            if(maxHeap.size() == 1){
                return maxHeap.peek();
            }
            int stone1 = maxHeap.poll();
            int stone2 = maxHeap.poll();
            maxHeap.add(Math.abs(stone1 - stone2));
        }
        return 0;
    }

    public List<String> binaryTreePaths(TreeNode root) {
        String string = new String();
        List<String> ret = new ArrayList<>();
        binaryTreePathsChild(root,string,ret);
        return ret;
    }

    private void binaryTreePathsChild(TreeNode root, String string, List<String> ret) {
        StringBuilder stringBuilder = new StringBuilder(string);
        stringBuilder.append(root.val);
        if(root.left == null && root.right == null){
            ret.add(stringBuilder.toString());
            return;
        }
        stringBuilder.append("->");
        if(root.left != null){
            binaryTreePathsChild(root.left, stringBuilder.toString(),ret);
        }
        if(root.right != null){
            binaryTreePathsChild(root.right,stringBuilder.toString(),ret);
        }
    }

    public int sumOfLeftLeaves(TreeNode root) {
        boolean isLeft = false;
        sumOfLeftLeavesChild(root,isLeft);
        return sum1;
    }

    private int sum1;
    private void sumOfLeftLeavesChild(TreeNode root, boolean isLeft) {
        if(root.left == null && root.right == null){
            if(isLeft){
                sum1 += root.val;
                return;
            }else{
                return;
            }
        }
        if(root.left != null){
            isLeft = true;
            sumOfLeftLeavesChild(root.left,isLeft);
        }
        if(root.right != null){
            isLeft = false;
            sumOfLeftLeavesChild(root.right,isLeft);
        }
    }

    public int[] findMode(TreeNode root) {
        if(root == null){
            return new int[0];
        }
        Map<Integer,Integer> map = new HashMap<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        int maxCount = 0;
        while(!stack.isEmpty()){
            TreeNode cur = stack.pop();
            int count = map.getOrDefault(cur.val,0);
            if(count + 1 > maxCount){
                maxCount = count + 1;
            }
            map.put(cur.val,count + 1);
            if(cur.right != null){
                stack.push(cur.right);
            }
            if(cur.left != null){
                stack.push(cur.left);
            }
        }
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        List<Integer> list = new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry : entries){
            if(entry.getValue() == maxCount){
                list.add(entry.getKey());
            }
        }
        int[] ret = new int[list.size()];
        int index = 0;
        for (Integer integer : list) {
            ret[index++] = integer;
        }
        return ret;
    }

    public int getMinimumDifference(TreeNode root) {
        if(root == null){
            return 0;
        }
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while(cur != null || !stack.isEmpty()){
            while(cur != null){
                stack.push(cur);
                cur = cur.left;
            }
            TreeNode top = stack.pop();
            list.add(top.val);
            cur = top.right;
        }
        if(list.size() == 1){
            return 0;
        }
        int minDif = list.get(1) - list.get(0);
        for (int i = 0; i < list.size() - 1; i++) {
            if(list.get(i + 1) - list.get(i) < minDif){
                minDif = list.get(i + 1) - list.get(i);
            }
        }
        return minDif;
    }

    public static List<Integer> inorderTraversalNor(TreeNode root){
        List<Integer> ret = new ArrayList<>();
        if(root == null){
            return ret;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while(cur != null || !stack.isEmpty()){
            while(cur != null){
                stack.add(cur);
                cur = cur.left;
            }
            TreeNode top = stack.pop();
            ret.add(top.val);
            cur = top.right;
        }
        return ret;
    }


    public int diameterOfBinaryTree(TreeNode root) {
        if(root == null || (root.left == null && root.right == null)){
            return 0;
        }

        return diameterOfBinaryTreeChild(root.left) + diameterOfBinaryTreeChild(root.right);
    }
    private int diameterOfBinaryTreeChild(TreeNode root){
        if(root == null){
            return 0;
        }
        if(root.left == null && root.right == null){
            return 1;
        }
        int leftDepth = diameterOfBinaryTreeChild(root.left);
        int rightDepth = diameterOfBinaryTreeChild(root.right);
        return Math.max(leftDepth,rightDepth) + 1;
    }

    public TreeNode mergeTrees (TreeNode t1, TreeNode t2) {
        // write code here
        if(t1 == null && t2 == null){
            return null;
        }
        else if(t1 != null && t2 != null){
            t1.val += t2.val;
        }
        else if(t1 == null && t2 != null){
            t1 = t2;
        }
        else if(t1 != null && t2 == null){
            t2 = t1;
        }
        t1.left = mergeTrees(t1.left,t2.left);
        t1.right = mergeTrees(t1.right,t2.right);
        return t1;
    }




}