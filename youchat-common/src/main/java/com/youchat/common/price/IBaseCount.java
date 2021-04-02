package com.youchat.common.price;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/2/24 6:51 下午
 * @version: v1.0
 */

import java.math.BigDecimal;

/**
 * 计算支付金额接口类
 * @author admin
 *
 */
public interface IBaseCount {

    BigDecimal countPayMoney(OrderDetail orderDetail);

}
