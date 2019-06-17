package com.youchat.common.entity;

import java.io.Serializable;

import lombok.Data;

/**
 * @author: Lien6o
 * @description:
 * @date: 2019-06-17 19:12
 * @version: v1.0
 */
@Data
public class TranslatorData implements Serializable {

    private String id;
    private String name;
    // 传输消息体内容
    private String message;

}
