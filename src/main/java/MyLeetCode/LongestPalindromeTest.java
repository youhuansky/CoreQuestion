package MyLeetCode;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @ClassName LongestPalindromeTest
 * @Description 最长回文字符串
 * @Author huan.you
 * @Date 2019/4/22 17:35
 */
public class LongestPalindromeTest {


    public static void main(String[] args) {
//        System.out.println(longestPalindrome("civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth"));
//        System.out.println(longestPalindrome("ab"));


        HashMap<String, String> map = new HashMap<>();


        map.put("asdf","qeqqwe");
        map.put("asdf2","qeqqwe");
        map.put("asdf3","qeqqwe");
        map.put("asd4f","qeqqwe");
        map.put("asdf5","qeqqwe");
    }




    public static String longestPalindrome2(String s) {

        HashSet<String> strings = new HashSet<>();
        String tmp=null;
        String result="";
        for (int i = 0; i <=s.length(); i++) {
            for (int j = i; j <=s.length() ; j++) {
                tmp=s.substring(i,j);
                strings.add(tmp);
            }
        }
        StringBuilder stringBuilder;
        for (String str:strings) {
            stringBuilder = new StringBuilder(str);
            stringBuilder = stringBuilder.reverse();
            tmp=stringBuilder.toString();
            if(tmp.equals(str)){
                if(tmp.length()>result.length()){
                    result=tmp;
                }
            }
        }
        return result;
    }


    public static String longestPalindrome(String s) {
        if(null==s){
            return "";
        }
        if(s.length()<=1){
            return s;
        }
        if(s.length()<=4){
            StringBuilder  stringBuilder = new StringBuilder(s);
            stringBuilder = stringBuilder.reverse();
            String tmp=stringBuilder.toString();
            if(tmp.equals(s)){
                return s;
            };
        }
        String result="";
        String tmp="";
        int midIndex=0;
        int afterIndex=0;
        int beforeIndex=0;
        char[] chars = s.toCharArray();
        for (int i = 0; i <chars.length ; i++) {


            midIndex=i;
            afterIndex=i;
            beforeIndex=i;
            char mid = chars[midIndex];
            char after = chars[afterIndex];
            char before = chars[beforeIndex];

            while( mid==after){

                if(afterIndex>=chars.length-1){
//                    afterIndex--;
                    break;
                }
                if(i!=afterIndex){
                    i++;
                }

                afterIndex++;
                after = chars[afterIndex];
            }
            if(afterIndex<s.length()-1){
                afterIndex--;
            }else{
                after = chars[afterIndex];

                if(mid!=after){
                    afterIndex--;
                }
            }

            while(beforeIndex-1>0&&afterIndex+1<chars.length){

                beforeIndex--;
                afterIndex++;
                after = chars[afterIndex];
                before = chars[beforeIndex];
                if(after!=before){
                    beforeIndex++;
                    afterIndex--;
                    break;
                }
            }

            tmp = new String(chars, beforeIndex, afterIndex - beforeIndex+1);
            if(tmp.length()>result.length()){
                result=tmp;
            }
        }

        return result;
    }
}
