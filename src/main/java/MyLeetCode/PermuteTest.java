package MyLeetCode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName PermuteTest
 * @Description 给定一个数组，输出数组的全排序
 * @Author huan.you
 * @Date 2019/4/25 10:41
 */
public class PermuteTest {

    public static void main(String[] args) {
        PermuteTest test = new PermuteTest();
        System.out.println(test.permute(new int[] {1, 2, 3, 4}));
    }

    void swap(int[] nums, int A, int B) {
        int temp = nums[A];
        nums[A] = nums[B];
        nums[B] = temp;
    }

    public void recPer(int[] nums, Integer[] lastList, List<List<Integer>> result, int len, int place) {
        for (int temp = 0; temp < len; temp++) {
            lastList[place] = nums[temp];
            if (place + 1 != nums.length) {
                //把当前元素与数组尾交换
                swap(nums, temp, len - 1);
                recPer(nums, lastList, result, len - 1, place + 1);
                //还原数组
                swap(nums, temp, len - 1);

            } else {
                result.add(Arrays.asList(lastList.clone()));
            }
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new LinkedList<List<Integer>>();
        recPer(nums, new Integer[nums.length], result, nums.length, 0);
        return result;
    }
}
