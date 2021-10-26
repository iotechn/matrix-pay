package com.dobbinsoft.fw.pay.model.request;

import lombok.Data;

@Data
public class MatrixPayUnifiedOrderRequestGoodsDetail {

    /**
     * 必填 32 商品的编号
     */
    private String goodsId;

    /**
     * 可选 32 支付定义的统一商品编号
     */
    private String payGoodsId;

    /**
     * 必填 256 商品名称
     */
    private String goodsName;

    /**
     * 必填 商品数量
     */
    private String goodsNum;

    /**
     * 必填 商品单价
     */
    private Integer price;

    /**
     * 可选 32 商品类目
     */
    private String goodsCategory;

    /**
     * 可选 商品描述信息
     */
    private String body;

}
