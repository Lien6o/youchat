package com.youchat.common.price;

/**
 * @author: Lien6o
 * @description:
 * @date: 2020/2/24 6:48 下午
 * @version: v1.0
 */

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;

/**
 * 商品
 * @author admin
 *
 */
@Data
public class Merchandise {

    private String sku;//商品SKU
    private String name; //商品名称
    private BigDecimal price; //商品单价
    private Map<PromotionType, SupportPromotions> supportPromotions; //支持促销类型
}
