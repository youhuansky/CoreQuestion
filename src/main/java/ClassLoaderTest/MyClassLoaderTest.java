package ClassLoaderTest;

import java.lang.reflect.Field;

public class MyClassLoaderTest {


//    static {
//
//
//        System.out.println("static");
//    }

    public static void main(String[] args) {
//        System.out.println("asd");
//        MyClassLoaderTest loaderTest = new MyClassLoaderTest();
//        ClassLoader classLoader1 = loaderTest.getClass().getClassLoader();
//        System.out.println(classLoader1);
//        System.out.println(classLoader1.getParent());
//         System.out.println(classLoader1.getParent().getParent());


        try {
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            Class<?> aClass = classLoader.loadClass("ClassLoaderTest.ClassLoaderTest");

//            Class<?> aClass = Class.forName("ClassLoaderTest.ClassLoaderTest");
            System.out.println("------------------------------------------------------");
            Field[] fields = aClass.getFields();
            System.out.println(fields.length);
            for (Field field : fields) {
                        System.out.println(field.getName()+"==================="+field.getInt(aClass));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
