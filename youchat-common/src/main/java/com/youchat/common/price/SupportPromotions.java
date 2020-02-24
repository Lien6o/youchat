package com.youchat.common.price;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/2/24 6:48 下午
 * @version: v1.0
 */

import lombok.Data;

/**
 * 促销类型
 * @author admin
 *
 */
@Data
public class SupportPromotions implements Cloneable{

    /**
     * 该商品促销的ID
     */
    private int id;
    /**
     * 促销类型 1\优惠券 2\红包
     */
    private PromotionType promotionType;
    /**
     *  优先级
     */
    private int priority;
    /**
     *  用户领取该商品的优惠券
     */
    private UserCoupon userCoupon;
    /**
     *  用户领取该商品的红包
     */
    private UserRedPacket userRedPacket;

    /**
     * 重写clone方法
     */
    public SupportPromotions clone(){
        SupportPromotions supportPromotions = null;
        try{
            supportPromotions = (SupportPromotions)super.clone();
        }catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
        return supportPromotions;
    }
}
