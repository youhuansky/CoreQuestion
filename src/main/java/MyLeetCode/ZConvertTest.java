package MyLeetCode;

/**
 * @ClassName ZConvertTest
 * @Description z字形变换
 * @Author huan.you
 * @Date 2019/4/24 15:53
 */
public class ZConvertTest {
    public static void main(String[] args) {

//        int i=7;
//        int j=8;
//        String string =  "str";
//        System.out.println(string.substring(2,2));
        System.out.println(convert("abcdefghijklm", 5));


    }


    public static String convert(String s, int numRows) {
        if(numRows ==1){
            return s;
        }
        StringBuilder sb = new StringBuilder();
        String tmp="";
        int len=s.length();
        //循环次数
        int times=len/numRows+1;
        int rowEnd=0;
        for (int i=1;i<=numRows;i++){
            for(int j=0;j<times;j++){
                if(i==1||i==numRows){
                    if((j*numRows+i+j*(numRows-2))<=len){
                        tmp=s.substring(j*numRows+i+j*(numRows-2)-1,j*numRows+i+j*(numRows-2));
                        sb.append(tmp);
                    }
                    System.out.println(sb.toString());
                }else{
                    rowEnd=(j+1)*numRows+j*(numRows-2);
                    if((rowEnd-(numRows-i))<=len) {
                        tmp = s.substring(rowEnd-(numRows-i)-1, rowEnd-(numRows-i));
                        sb.append(tmp);
                    }
                    if((2*rowEnd-(rowEnd-(numRows-i)))<=len) {
                        tmp=s.substring(2*rowEnd-(rowEnd-(numRows-i))-1,2*rowEnd-(rowEnd-(numRows-i)));
                        sb.append(tmp);
                    }
                    System.out.println(sb.toString());
                }

            }

        }


        return sb.toString();
    }
}
