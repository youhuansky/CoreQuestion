package MyLeetCode;

/**
 * 请从字符串中找出一个最长的不包含重复字符的子字符串，计算该最长子字符串的长度。
 * 示例 1:
 *
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 *
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 *
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 * 提示：
 *
 * s.length <= 40000
 */
public class LongestUnrepeatStr {

    public static void main(String[] args) {
//        String str ="abcabcbb";
        String str ="bcdef";
        System.out.println(lengthOfLongestSubstring(str));
    }

    public static int lengthOfLongestSubstring(String s) {

        if (null ==s || s.length() == 0) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }
        int indexStart = 0;
        int indexEnd = 1;
        String sub = null;
        String tmp = null;
        int result = 0;
        for (;indexStart < s.length() -1 && indexEnd <s.length();) {
            sub = s.substring(indexStart, indexEnd);
            tmp = s.substring(indexEnd, indexEnd + 1);
            if (sub.contains(tmp)) {
                indexStart ++;
                indexEnd = indexStart + 1;
                result = Math.max(sub.length(), result);
            } else {
                indexEnd ++;
                if (indexEnd == s.length()) {
                    result = Math.max(sub.length() + 1, result);
                }
            }
        }

        return result;
    }
}
