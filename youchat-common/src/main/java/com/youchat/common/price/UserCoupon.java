package com.youchat.common.price;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/2/24 6:48 下午
 * @version: v1.0
 */

import java.math.BigDecimal;

import lombok.Data;

/**
 * 优惠券
 * @author admin
 *
 */
@Data
public class UserCoupon {

    private int id; //优惠券ID
    private int userId; //领取优惠券用户ID
    private String sku; //商品SKU
    private BigDecimal coupon; //优惠金额
}
