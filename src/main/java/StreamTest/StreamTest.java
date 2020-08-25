package StreamTest;

import com.sun.org.apache.xpath.internal.functions.FuncFalse;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class User{

    private  int id ;
    private  String name ;
    private  int age ;

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}


public class StreamTest {


    public static void main(String[] args) {
        User u1 = new User(11, "a", 23);
        User u2 = new User(12, "b", 24);
        User u3 = new User(13, "c", 25);
        User u4 = new User(14, "d", 26);
        User u5 = new User(15, "e", 27);

        List<User> list = Arrays.asList(u1, u2, u3, u4, u5);

        Stream<User> stream = list.stream();
//        stream.filter(user->{ return false ;});
        stream=stream.filter(user ->{return doTest(user);});
        stream.forEach(
            System.out::println
        );

    }


    public static boolean doTest(User user){

        return user.getAge()%2==0;
    }
}
