package JVMTest;

import java.util.Random;

public class CMSTest
{


    public static void main(String[] args) {

        String str ="youhuan";
        try{
            while (true){
                str=str+ new Random().nextInt(77777777)+ new Random().nextInt(88888888);
                str.intern();
//                byte[] bytes = new byte[1000000000];
            }
        }catch(Exception e){
            e.printStackTrace();
        }





    }
}
