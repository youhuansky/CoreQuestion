package MyLeetCode;


/**
 * @ClassName ReverseIntegerTest
 * @Description TODO
 * @Author huan.you
 * @Date 2019/4/24 17:36
 */
public class ReverseIntegerTest {
    public static void main(String[] args) {


        System.out.println(reverse(1199999999));
    }


    public static int reverse(int x) {
        StringBuilder sb = new StringBuilder(String.valueOf(x));
        if(x<0){
            sb=new StringBuilder(sb.substring(1,sb.length()));
        }
        sb = sb.reverse();
        int endZeroCount=0;
        String tmp;
        while(endZeroCount<sb.length()){
            tmp=sb.substring(sb.length()-endZeroCount-1,sb.length()-endZeroCount);
            if(!"0".equals(tmp)){
                break;
            }
            endZeroCount++;
        }
        sb=new StringBuilder(sb.substring(0,sb.length()-endZeroCount));
        tmp=sb.toString();
        if(x<0){
            tmp="-"+tmp;
        }
        int res;
        try{
            res= Integer.parseInt(tmp);
        }catch(Exception e){
            res=0;
        }
        return  res ;
    }

}
