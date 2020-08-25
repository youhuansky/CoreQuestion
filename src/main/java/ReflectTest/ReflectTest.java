package ReflectTest;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class ReflectResource{

        private String name="youhuan";
        private int age=11;
        private static  int age2=12;
        private List lists=new ArrayList();

    {

        lists.add("asd");
        lists.add("asdasd");
        lists.add("1123");



    }






}

public class ReflectTest {
    public static Object getValue(Object target, String fieldName) throws  Exception{

        Class<?> aClass = target.getClass();


        Field field = aClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        Object o = field.get(target);

        return o;
    }

    public static void main(String[] args) {

       ReflectResource resource = new ReflectResource();

        try {
            Object lalala = getValue(resource, "age2");
            System.out.println(lalala);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
