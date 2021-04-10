package com.youchat.creative.factory.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Lien6o
 * @description some desc
 * @email lienbo@meituan.com
 * @date 2021/4/8 10:17 上午
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DummyTransactional {

}
