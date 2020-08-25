package JVMTest;

class MyResource1{



}
class MyResource2{



}

class GCRootTest{

    public  static MyResource1 myResource1=new MyResource1();
    public MyResource2 myResource2=new MyResource2();


}



public class JVMTest {


    public static void main(String[] args) {


        GCRootTest gCRootTest=new GCRootTest();
        gCRootTest=null;
        System.gc();
        String str ="asdfghjklaaaaaaaaaaaaaaxzcxzcsadfawegvartsebtacercawbsvrtactca";
        System.out.println(str.getBytes().length);
//        str=null;
        while(true){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }




    }
}
