package com.youchat.creative.factory.spring;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Lien6o
 * @description some desc
 * @email lienbo@meituan.com
 * @date 2021/4/8 2:37 下午
 */
public class Proxy {
    public static void main(String[] args) throws Exception {
        InterfaceMaker interfaceMaker = new InterfaceMaker();
        // 抽取某个类的方法生成接口方法
        interfaceMaker.add(AbstractDemo.class);
        Class<?> targetInterface = interfaceMaker.create();
        for (Method method : targetInterface.getMethods()) {
            System.out.println(method.getName());
        }
        // 接口代理并设置代理接口方法拦截
        Object object = Enhancer.create(Object.class, new Class[]{targetInterface}, (MethodInterceptor) (obj, method, args1, methodProxy) -> {
            if (method.getName().equals("execute")) {
                System.out.println("filter method1 ");
                return "mummer";
            }
            return "default";
        });

        Method targetMethod1 = object.getClass().getMethod("execute");

        System.out.println(targetMethod1.invoke(object));
    }
}
