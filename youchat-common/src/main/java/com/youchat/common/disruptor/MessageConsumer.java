package com.youchat.common.disruptor;

import com.lmax.disruptor.WorkHandler;
import com.youchat.common.entity.TranslatorDataWrapper;

/**
 * @author: Lien6o
 * @description:
 * @date: 2019-06-17 19:17
 * @version: v1.0
 */
public abstract class MessageConsumer implements WorkHandler<TranslatorDataWrapper> {

    protected String consumerId;

    public MessageConsumer(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

}
