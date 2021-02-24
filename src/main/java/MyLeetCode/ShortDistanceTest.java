package MyLeetCode;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
 * <p>
 * 说明：每次只能向下或者向右移动一步。
 * <p>
 * 输入:
 * [
 * [1,3,1],
 * [1,5,1],
 * [4,2,1]
 * ]
 * 输出: 7
 * 解释: 因为路径 1→3→1→1→1 的总和最小。
 */
public class ShortDistanceTest {

    public static int minPathSum(int[][] grid) {

        // 创建一个栈，记录走过的路程
        LinkedBlockingDeque<String> queue = new LinkedBlockingDeque<>();

        Integer tmpDistance = 0;
        Integer resultDistance =Integer.MAX_VALUE;
        // 标识是否为回溯的
        boolean endFlag =false;


        for (int i = 0; i < grid.length;) {
            for (int j = 0; j < grid[0].length;) {
                queue.push(i + "" + j);
                tmpDistance += grid[i][j];
                j++;
                if (j >= grid[0].length) {
                    i++;
                }
            }
        }

        return 0;
    }

    public static void main(String[] args) {

//        minPathSum(
//                new int[][]{
//                          new int[]{1, 3, 1}
//                        , new int[]{1, 5, 1}
//                        , new int[]{4, 2, 1}
//                });
//        BigDecimal bigDecimal = new BigDecimal("0");
//        System.out.println(BigDecimal.ZERO.compareTo(bigDecimal) == 0);

        Integer i1 =null;
        if (1== i1) {
            System.out.println("222");
        }
    }
}
