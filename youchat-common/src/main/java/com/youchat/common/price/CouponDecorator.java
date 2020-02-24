package com.youchat.common.price;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/2/24 6:53 下午
 * @version: v1.0
 */

import java.math.BigDecimal;

/**
 * 计算使用优惠券后的金额
 * @author admin
 *
 */
public class CouponDecorator extends BaseCountDecorator{

    public CouponDecorator(IBaseCount count) {
        super(count);
    }

    @Override
    public BigDecimal countPayMoney(OrderDetail orderDetail) {
        BigDecimal payTotalMoney = new BigDecimal(0);
        payTotalMoney = super.countPayMoney(orderDetail);
        payTotalMoney = countCouponPayMoney(orderDetail);
        return payTotalMoney;
    }

    private BigDecimal countCouponPayMoney(OrderDetail orderDetail) {

        BigDecimal coupon =  orderDetail.getMerchandise()
                .getSupportPromotions()
                .get(PromotionType.COUPON)
                .getUserCoupon()
                .getCoupon();
        System.out.println("优惠券金额：" + coupon);

        orderDetail.setPayMoney(orderDetail.getPayMoney().subtract(coupon));
        return orderDetail.getPayMoney();
    }
}
