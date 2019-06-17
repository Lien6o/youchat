package com.youchat.common.entity;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

/**
 * @author: Lien6o
 * @description:
 * @date: 2019-06-17 19:13
 * @version: v1.0
 */
@Data
public class TranslatorDataWrapper {
    private TranslatorData data;

    private ChannelHandlerContext ctx;
}
