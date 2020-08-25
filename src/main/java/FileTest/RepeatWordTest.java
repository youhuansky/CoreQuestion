package FileTest;

import java.io.*;
import java.util.Timer;

public class RepeatWordTest {


    public static void main(String[] args) {
        String word = "y";
        char[] words = word.toCharArray();
        File file = new File("C:\\Users\\HP\\Desktop\\file.txt");
        FileReader fileReader = null;
        BufferedReader br = null;
        int times = 0;
        boolean flag=true;
        try {
            fileReader = new FileReader(file);
            br = new BufferedReader(fileReader);
            String s = br.readLine();

            while (null!=s) {
                System.out.println(s);
                char[] chars = s.toCharArray();
                if (chars.length >= words.length) {


                    for (int i = 0; i <= chars.length - words.length; i++) {
                        l1:
                        for (int j = 0; j < words.length;j++) {
                            if (words[j] != chars[i+j]) {
                                flag=false;
                                break l1;
                            }

                        }
                        if(flag){
                            times = times + 1;
                        }
                        flag=true;
                    }
                }
                s = br.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(times);
    }
}
