import java.util.*;

public class Test {
    public static void main(String[] args) {

    }
    public int singleNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if(set.contains(nums[i])){
                set.add(nums[i]);
            }else{
                set.remove(nums[i]);
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if(set.contains(nums[i])){
                return nums[i];
            }
        }
        return -1;
    }
    public List<String> topKFrequent(String[] words, int k) {
        Map<String,Integer> map = new HashMap<>();
        for(int i = 0;i < words.length;i++){
            if(map.get(words[i]) == null){
                map.put(words[i],1);
            }else{
                int val = map.get(words[i]);
                map.put(words[i],val + 1);
            }
        }
        PriorityQueue<Map.Entry<String,Integer>> minHeap = new PriorityQueue<>(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if(o1.getValue().compareTo(o2.getValue()) == 0){
                    return o2.getKey().compareTo(o1.getKey());
                }
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        for(Map.Entry<String,Integer> entry : map.entrySet()){
            if(minHeap.size() < k){
                minHeap.offer(entry);
            }else{
                Map.Entry<String,Integer> top = minHeap.peek();
                if(top.getValue().compareTo(entry.getValue()) == 0){
                    if(top.getKey().compareTo(entry.getKey()) > 0){
                        minHeap.poll();
                        minHeap.offer(entry);
                    }
                }else{
                    if(top.getValue().compareTo(entry.getValue()) < 0){
                        minHeap.poll();
                        minHeap.offer(entry);
                    }
                }
            }
        }
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            String s = minHeap.poll().getKey();
            ret.add(s);
        }
        Collections.reverse(ret);
        return ret;
    }
}
