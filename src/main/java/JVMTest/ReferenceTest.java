package JVMTest;


import java.lang.ref.WeakReference;


public class ReferenceTest {


    public static void main(String[] args) {
        JVMTest myResource3=new JVMTest();
        WeakReference<JVMTest> weakReference =new WeakReference(myResource3);
        myResource3=null;
        while(null!=weakReference.get()){
            System.out.println("对象被销毁还存活");
        }
        System.out.println("对象被销毁");



    }
}
