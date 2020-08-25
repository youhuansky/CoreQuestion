package AOPTest;


import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
class CgStu {


    public void sayHi() {

        System.out.println("CGHI");

    }


}
class CglibProxy implements MethodInterceptor {
    Enhancer enhancer = new Enhancer();

    public Object getObj(Class clazz) {
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {


        return methodProxy.invokeSuper(o,objects);
    }
}

public class CglibTest {


    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        CgStu obj = (CgStu) cglibProxy.getObj(CgStu.class);

        obj.sayHi();

//        System.out.println(102%5);
    }


}
