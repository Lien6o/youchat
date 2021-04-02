package com.youchat.creative.factory.spring;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemoteClient {

    private String ipAddr;

    private String type;
}
