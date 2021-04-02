package com.youchat.common.price;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/2/24 6:55 下午
 * @version: v1.0
 */

import java.math.BigDecimal;

/**
 * 计算使用红包后的金额
 * @author admin
 *
 */
public class RedPacketDecorator extends BaseCountDecorator{

    public RedPacketDecorator(IBaseCount count) {
        super(count);
    }

    public BigDecimal countPayMoney(OrderDetail orderDetail) {
        BigDecimal payTotalMoney = new BigDecimal(0);
        payTotalMoney = super.countPayMoney(orderDetail);
        payTotalMoney = countCouponPayMoney(orderDetail);
        return payTotalMoney;
    }

    private BigDecimal countCouponPayMoney(OrderDetail orderDetail) {

        BigDecimal redPacket = orderDetail.getMerchandise()
                .getSupportPromotions()
                .get(PromotionType.REDPACKED)
                .getUserRedPacket()
                .getRedPacket();

        System.out.println("红包优惠金额：" + redPacket);

        orderDetail.setPayMoney(orderDetail.getPayMoney().subtract(redPacket));
        return orderDetail.getPayMoney();
    }
}
