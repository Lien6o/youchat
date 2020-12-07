package com.youchat.account.pool;

import lombok.Data;

@Data
public class ConfigProperty {

    private int initAccountPoolSize;

    private int maxAccountPoolSize;

    private int expansionQPS;

    private int shrinkQPS;

}
