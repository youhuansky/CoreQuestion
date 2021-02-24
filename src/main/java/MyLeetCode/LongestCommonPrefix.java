package MyLeetCode;

import java.util.StringJoiner;

/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * <p>
 * 如果不存在公共前缀，返回空字符串 ""。
 * 示例 1：
 * <p>
 * 输入：strs = ["flower","flow","flight"]
 * 输出："fl"
 * 示例 2：
 * <p>
 * 输入：strs = ["dog","racecar","car"]
 * 输出：""
 * 解释：输入不存在公共前缀。
 *  
 * 提示：
 * <p>
 * 0 <= strs.length <= 200
 * 0 <= strs[i].length <= 200
 * strs[i] 仅由小写英文字母组成
 */
public class LongestCommonPrefix {

    public static void main(String[] args) {
        System.out.println(longestCommonPrefix(new String[]{"flower", "flow", "flight"}));
//        System.out.println(longestCommonPrefix(new String[]{"ab", "a"}));
    }


    public static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }

        if (strs.length == 1) {
            return strs[0];
        }

        boolean flag = true;
        int cusor = 0;
        StringJoiner stringJoiner = new StringJoiner("");
        l1: while (flag) {
            String tmp = null;
            for (int i = 0; i < strs.length; i++) {
                String str = strs[i];
                if (null == tmp && (str.length() - cusor) > 0) {
                    tmp = str.substring(cusor, cusor + 1);
                } else if (null == tmp) {
                    break l1;
                } else {
                    if (str.length() <= cusor || !tmp.equals(str.substring(cusor, cusor + 1))) {
                        break l1;
                    }
                    if (i == strs.length - 1) {
                        stringJoiner.add(tmp);
                        cusor++;
                    }
                }
            }
        }
        return stringJoiner.toString();
    }
}
