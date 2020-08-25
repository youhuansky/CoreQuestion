package LeCoTest;


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}

public class LecoTest {
//        [2,4,3]
//        [5,6,4]
    public static void main(String[] args) {
        int[] nums1=new int []{1,2};
        int[] nums2=new int []{3,4};
        double medianSortedArrays = findMedianSortedArrays(nums1,nums2);
        System.out.println(medianSortedArrays);
        System.out.println(5/ 2);

    }


    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {

        int[] ints = new int[nums1.length + nums2.length];
        System.arraycopy(nums1,0,ints,0,nums1.length);
        System.arraycopy(nums2,0,ints,nums1.length,nums2.length);


        int tem;

        for(int i=0;i<ints.length;i++){
            for(int j=0;j<ints.length-1-i;j++){
                Integer left=ints[j];
                Integer right=ints[j+1];
                if(left>right){
                    tem=left;
                    left=right;
                    right=tem;
                    ints[j]=left;
                    ints[j+1]=right;
                }
            }
        }

        for(int i=0;i<ints.length;i++){
            System.out.print(ints[i]);
        }
        System.out.println();
        double res=0;
        if(ints.length==2){
            int anInt = ints[0];
            int anInt2 = ints[1];
            Double aDouble = Double.valueOf(anInt);
            Double aDouble1 = Double.valueOf(anInt2);
            res=(aDouble+aDouble1)/2;
            return  res;
        }
        if(ints.length%2==0){
            int anInt = ints[ints.length / 2-1];
            int anInt2 = ints[ints.length / 2];
            Double aDouble = Double.valueOf(anInt);
            Double aDouble1 = Double.valueOf(anInt2);
            res=(aDouble+aDouble1)/2;
        }else {
            res=Double.valueOf(ints[ints.length / 2]);
        }
        return res;
    }
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        BigDecimal num = getNum(l1);
        BigDecimal num1 = getNum(l2);
        BigDecimal add = num.add(num1);


        return    getNode(add);
    }
    public static ListNode getNode(BigDecimal sum){
        String reverse = new StringBuilder().append(sum).reverse().toString();
        ListNode l1=new ListNode(Integer.valueOf(reverse.substring(0,1)));
        ListNode l2=l1;
        for (int i=1;i<reverse.length();i++){
            l2.next= new ListNode(Integer.valueOf(reverse.substring(i,i+1)));
            l2=l2.next;
        }

        return l1;
    }
    public static BigDecimal getNum(ListNode l1){
        StringBuilder sb=new StringBuilder();
        sb.append(l1.val);
        ListNode l2=l1;
        while (null!=l2.next){
            l2=l2.next;
            sb.append(l2.val);
        }
        String s = sb.reverse().toString();
        return new BigDecimal(s);
    }



    //1873755326
    //2046416005
    //1802630646
    //170525812189
    //2200655
    //2395979
    public static int lengthOfLongestSubstring(String s) {
        //asdasdqwegrag
        int max = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int[] ints = new int[128];
            int times = 0;
            for (int j = i; j < chars.length; j++) {

                int word = chars[j];

                if (ints[word] == 0) {
                    ints[word] = 1;
                    times++;

                    max = Math.max(times, max);
                } else {
                    int size = 0;
                    for (int num : ints) {
                        size = size + num;
                    }
                    ints = new int[300];
                    times = 0;
                    max = Math.max(size, max);
                }

            }
        }


        return max;
    }


    public static int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        int length = nums.length;
        l1:
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                int a = nums[i];
                int b = nums[j];
                if ((a + b) == target) {
                    res[0] = i;
                    res[1] = j;
                    break l1;
                }

            }
        }

        return res;
    }
}
