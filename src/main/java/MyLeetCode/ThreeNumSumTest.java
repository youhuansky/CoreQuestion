package MyLeetCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有满足条件且不重复的三元组。
 * <p>
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 示例：
 * <p>
 * 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 * <p>
 * 满足要求的三元组集合为：
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 */
public class ThreeNumSumTest {


    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> lists = new ArrayList<>();
        java.util.Arrays.sort(nums);

        if (nums.length < 3) {
            return lists;
        }
        for (int i = 0; i < nums.length; i++) {
            final int point = nums[i];
            int result = 0 - point;
            int j = 1;
            int k = 1;
            l1:
            for (; j + k < nums.length - i; ) {
                final int beginPoint = nums[j + i];
                final int endPoint = nums[nums.length - k];
                if (result == (beginPoint + endPoint)) {
                    List<Integer> integers = null;
                    if (lists.size() != 0) {
                        integers = lists.get(lists.size() - 1);
                    }
                    if (null == integers || (!(integers.get(0) == point && integers.get(1) == beginPoint && integers.get(2) == endPoint))) {
                        lists.add(new ArrayList<Integer>() {
                            {
                                add(point);
                                add(beginPoint);
                                add(endPoint);
                            }
                        });
                    }
                    k++;
                    j++;
                } else if (result > (beginPoint + endPoint)) {
                    j++;
                } else if (result < (beginPoint + endPoint)) {
                    k++;
                }
            }
        }

        return lists.stream().distinct().collect(Collectors.toList());
    }

    public static void main(String[] args) {
//        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
        int[] nums = new int[]{3, 0, -2, -1, 1, 2};
        java.util.Arrays.sort(nums);
        java.util.Arrays.stream(nums).forEach(System.out::print);
        List<List<Integer>> lists = threeSum(nums);
        System.out.println(lists);
    }
}
