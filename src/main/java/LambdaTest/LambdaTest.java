package LambdaTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LambdaTest {


    public static void main(String[] args) {

//        String[] strs={"asdasd","asdasdadsasd","zxczxqawweq"};
//
//        List<String> strings = Arrays.asList(strs);
//        strings.forEach((String x)->{
//            System.out.println(x);
//        });

        Map<String,String> map=new HashMap(){{put("1","2");put("2","2");put("3","2");put("4","2");put("5","2");}};
        Set<String> strings = map.keySet();
//        strings.forEach(System.out::println);
//        strings.forEach(x->{System.out.println(map.get(x));});
        Stream<String> stream = strings.stream();
        stream.forEach(x->{System.out.println(map.get(x));});





    }
}
