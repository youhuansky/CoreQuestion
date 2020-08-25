package MyLeetCode;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * @ClassName Solution
 * @Description 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。解集不能包含重复的子集。
 * @Author youhuan
 * @Date 2019/8/9 19:20
 * 输入: nums = [1,2,3]
 * 输出:
 * [
 *   [3],
 *   [1],
 *   [2],
 *   [1,2,3],
 *   [1,3],
 *   [2,3],
 *   [1,2],
 *   []
 * ]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/subsets
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 **/
public class Solution {

    public static void main(String[] args) {

        int[] nums = new int[] {1, 2, 3};

        List<List<Integer>> subsets = subsets(nums);

        System.out.println(JSON.toJSONString(subsets));

    }

    public static List<List<Integer>> subsets(int[] nums) {

        List<List<Integer>> resultLists = new ArrayList<>();




        return resultLists;
    }
}
