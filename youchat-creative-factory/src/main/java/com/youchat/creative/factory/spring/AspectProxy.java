package com.youchat.creative.factory.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @author Lien6o
 * @description some desc
 * @email lienbo@meituan.com
 * @date 2021/4/8 10:16 上午
 */
@Aspect
@Component
public class AspectProxy {


    @Around("@annotation(com.youchat.creative.factory.spring.DummyTransactional)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("AspectProxy: around " + joinPoint.getSignature().getName());
        return joinPoint.proceed();
    }

}
