package com.youchat.common.price;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/2/24 6:47 下午
 * @version: v1.0
 */

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 主订单
 * @author admin
 *
 */
@Data
public class Order {

    private int id; //订单ID
    private String orderNo; //订单号
    private BigDecimal totalPayMoney; //总支付金额
    private List<OrderDetail> list; //详细订单列表
}
