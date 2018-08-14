package test;


import Main.ShutdownHookManager;
import Main.shutdown;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import sun.applet.Main;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.parseInt;
import static java.lang.Thread.sleep;
import static java.util.Arrays.*;

class Demo2 extends Thread
{
    public  void run()
    { try {
        sleep(10000);
    } catch (Exception e) {
        e.printStackTrace();
    }
        for (int x = 0; x < 5; ++x)
            System.out.println("启动了线程Demo1");
    }
}

public class shut implements Runnable{

    public String name;
    protected int age;
    char sex;
    private String phoneNum;

    @Override
    public String toString() {
        return "Student [name=" + name + ", age=" + age + ", sex=" + sex
                + ", phoneNum=" + phoneNum + "]";
    }

    public static final ExecutorService EXECUTOR =
            Executors.newSingleThreadExecutor(new ThreadFactoryBuilder()
                    .setDaemon(true).build());
    static {
   Runtime.getRuntime().addShutdownHook(new Thread());
    }
      public  void run(){
          try {
              sleep(10000);
          } catch (Exception e) {
              e.printStackTrace();
          }
          for (int x = 0; x < 5; ++x)
              System.out.println("启动了线程主");
}
    public  shut(){
        System.out.println("构造函数");
   }
     public shut(String str){
       System.out.println("有参构造函数");
   }
    public void show(){
        System.out.println("调用了：pro.txt");
    }

    public void show1(String s){
        System.out.println("调用了：公有的，String参数的show1(): s = " + s);
    }
    protected void show2(){
        System.out.println("调用了：受保护的，无参的show2()");
    }
    void show3(){
        System.out.println("调用了：默认的，无参的show3()");
    }
    private String show4(int age){
        System.out.println("调用了，私有的，并且有返回值的，int参数的show4(): age = " + age);
        return "abcd";
    }


    public static void main(String[] args) throws Exception {
       shut shut=new shut();
       /*Demo2 demo1 = new Demo2();
        demo1.start();
         ShutdownHookManager.get().addShutdownHook(shut, 10, 100, TimeUnit.SECONDS);
            sleep(10000);
        }*/
    /* FutureTask<?>futureTask=EXECUTOR.submit(ShutdownHookManager.HookEntry.class.)*/

       /* long deadline = true ? System.nanoTime() +1000000000: 0L;
        System.out.println(System.nanoTime());
        System.out.println(System.currentTimeMillis());
        System.out.println(deadline);*/
        //List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
      // List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
       // System.out.println(filtered);
        System.out.println("**********************所有公有构造方法*********************************");
        Class stuClass = Class.forName("test.shut");
        Constructor[] conArray = stuClass.getConstructors();
        for(Constructor c : conArray){
            System.out.println(c);
        }


        //2.获取字段
        System.out.println("************获取所有公有的字段********************");
        Field[] fieldArray = stuClass.getDeclaredFields();
        for(Field f : fieldArray){
            System.out.println(f);
        }
        System.out.println("************修改成员变量********************");
            Field f = null;
            stuClass = Class.forName("test.shut");
            f = stuClass.getField("name");
            System.out.println(f);

        Class[] classes = new Class[] { String.class, int.class };
          //shut obj  = shut.class.getConstructor(classes).newInstance();
            //shut solution3 = (shut) Class.forName("test.shut").getConstructor().newInstance(); // 无参也可用getConstructor()
           shut obj = (shut)stuClass.newInstance();
            f.set(obj, "刘德");//为Student对象中的name属性赋值--》stu.name = "刘德华"
            shut stu = (shut)obj;
           System.out.println("验证姓名：............." + stu);

        System.out.println("**************获取私有字段****并调用********************************");
        f = stuClass.getDeclaredField("phoneNum");
        System.out.println(f);
        f.setAccessible(true);//暴力反射，解除私有限定
        f.set(obj, "18888889999");
        System.out.println("验证电话：" + stu);

        System.out.println("***************获取所有的”公有“方法*******************");
        stuClass.getMethods();
        Method[] methodArray = stuClass.getMethods();
        for(Method m : methodArray){
            System.out.println(m);
        }

        System.out.println("***************获取所有的方法，包括私有的*******************");
        methodArray = stuClass.getDeclaredMethods();
        for(Method m : methodArray){
            System.out.println(m);
        }

        System.out.println("***************获取公有的show1()方法*******************");
        Method m = stuClass.getMethod("show1", String.class);
        System.out.println(m);
        //实例化一个Student对象
        Object ob = stuClass.getConstructor().newInstance();
        m.invoke(ob, "刘德华");

        System.out.println("***************获取私有的show4()方法******************");
        m = stuClass.getDeclaredMethod("show4", int.class);
        System.out.println(m);
        m.setAccessible(true);//解除私有限定
        Object result = m.invoke(ob, 20);//需要两个参数，一个是要调用的对象（获取有反射），一个是实参
        System.out.println("返回值：" + result);


          System.out.println("***************获取Main方法******************");
          Class clazz = Class.forName("CThreadDemo1");
        //Method methodMain = clazz.getMethod("main", String[].class);
        // methodMain.invoke(null, (Object)new String[]{"a","b","c"});

       }
}



