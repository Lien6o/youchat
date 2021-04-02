package com.youchat.common.pipeline;

import lombok.Data;

@Data
public class PowerQuery {


    private String type;

    private Integer userType;
    /**
     * 如果不是ALL角度查看 * 外部用户一次只能查看一个权限
     */
    private String powerName;

}
