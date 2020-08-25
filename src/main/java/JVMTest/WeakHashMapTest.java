package JVMTest;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapTest
{
    public static void main(String[] args) {
        Map<Integer, Byte[]> map = null;

//        map = new WeakHashMap<Integer, Byte[]>();
        map = new HashMap<Integer, Byte[]>(10);
        for (int i = 0; i < 10000; i++) {
            Integer integer = new Integer(i);
            map.put(integer, new Byte[i]);
            System.out.println(map.size());
        }

    }


}
