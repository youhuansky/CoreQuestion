package suanfaTest;

public class BinarySearchTest {


    public static void main(String[] args) {

        System.out.println(search(new int[]{1, 2, 3, 4, 5, 6, 7, 8}, 8));


    }



    //二分查找，输入有序不重复的数组nums，输入查找的目标值target，如果在数组中找到改值，则返回该值得下标，找不到则返回-1
    public static  int search(int[] nums, int target) {
        //定义左边界为数组的第一个数字，也是最小值（因为是有序数组）
        int left=0;
        //定义右边界为数组的最后一个数字，也是最大值（因为是有序数组）
        int right=nums.length-1;
        //中间值为首+末除以2
        int mid=(left+right)/2;
        //如果左边比右边小，则继续比较，否则退出循环，说明数组中没有target值
        while(left<=right) {
            //比较mid的数字和target的大小
            if(nums[mid]<target) {
                //如果target大的话，则将左边界设置为mid右边第一位（就是将比target小的数全部舍去）
                left = mid+1;
            }else if(nums[mid]>target) {
                //如果target小的话，则将右边界设置为mid左边第一位（就是将比target大的数全部舍去）
                right = mid-1;
            }else {
                //在数组中找到了和target一样大的数字，返回下标
                return mid;
            }
            mid = (left+right)/2;
        }
        return -1;
    }
}
