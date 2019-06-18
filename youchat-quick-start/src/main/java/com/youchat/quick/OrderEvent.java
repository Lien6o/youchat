package com.youchat.quick;

import javafx.event.Event;
import lombok.Data;

/**
 * @author: Lien6o
 * @description:
 * @date: 2019-06-17 21:16
 * @version: v1.0
 */
@Data
public class OrderEvent extends Event {
    private long value;

}
