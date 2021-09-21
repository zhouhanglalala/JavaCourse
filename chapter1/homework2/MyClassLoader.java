package com.zhouhang.chapter1.lesson3;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @author zhouhang
 */
public class MyClassLoader extends ClassLoader {

    private static final String PATH = "/Users/zhouhang/IdeaProjects/java-course/build/resources/main/Hello";

    private static final String SUFFIX = ".xlass";

    /**
     * todo 如果重写的是loadclass为啥 defineClass的时候还要调用一次重写的这个loadclass 看一下jdk
     * @param path
     * @return
     */
    @Override
    public Class<?> findClass(String path) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(path + SUFFIX);
            int length = in.available();
            byte[] byteArray = new byte[length];
            int result = in.read(byteArray);
            byte[] resultArray = decode(byteArray);
            String name = path.substring(path.lastIndexOf("/") + 1);
            return defineClass(name, resultArray, 0, resultArray.length);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * 解码
     *
     * @param byteArray 源码
     *
     * @return 解码
     */
    private static byte[] decode(byte[] byteArray) {
        byte[] targetArray = new byte[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            targetArray[i] = (byte) (255 - byteArray[i]);
        }
        return targetArray;
    }

    public static void main(String[] args) throws Exception {

        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> clazz = myClassLoader.findClass(PATH);
        for (Method m : clazz.getDeclaredMethods()) {
            System.out.println(clazz.getSimpleName() + "." + m.getName());
            // 创建对象
            Object instance = clazz.getDeclaredConstructor().newInstance();
            // 调用实例方法
            Method method = clazz.getMethod(m.getName());
            method.invoke(instance);
        }

    }
}
