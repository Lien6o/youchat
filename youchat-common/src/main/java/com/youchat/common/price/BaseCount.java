package com.youchat.common.price;

import java.math.BigDecimal;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/2/24 6:51 下午
 * @version: v1.0
 */

/**
 * 支付基本类
 * @author admin
 *
 */
public class BaseCount implements IBaseCount{

    public BigDecimal countPayMoney(OrderDetail orderDetail) {
        orderDetail.setPayMoney(orderDetail.getMerchandise().getPrice());
        System.out.println("商品原单价金额为：" +  orderDetail.getPayMoney());

        return orderDetail.getPayMoney();
    }

}
