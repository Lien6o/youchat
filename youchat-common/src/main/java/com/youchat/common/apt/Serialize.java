package com.youchat.common.apt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/5/19 9:05 下午
 * @version: v1.0
 */

@Target({ ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.CLASS)
public @interface Serialize {
}
