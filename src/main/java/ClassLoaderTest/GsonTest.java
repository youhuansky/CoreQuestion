package ClassLoaderTest;

import java.util.List;

public class GsonTest {


    /**
     * name : 小强
     * age : 16
     * msg : ["a","b"]
     * regex : ^http://.*
     */

    private String name;
    private int age;
    private String regex;
    private List<String> msg;

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

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public List<String> getMsg() {
        return msg;
    }

    public void setMsg(List<String> msg) {
        this.msg = msg;
    }
}
