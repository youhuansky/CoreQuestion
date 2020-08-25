package SortTest;

import java.util.ArrayList;

public class BooleanTest {


    public static void main(String[] args) {
//        ArrayList<Integer> ints = new ArrayList<>();
//        ints.add(65);
//        ints.add(1);
//        ints.add(6);
//        ints.add(99);
//        ints.add(9);
//        ints.add(0);
//        ints.add(2);
//        ints.add(-3);
       Integer[] ints=new Integer[]{65,1,6,99,9,0,2,-3};
        Integer tem=0;
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

        for (Integer  num:ints) {
            System.out.println(num);
        }






    }
}
