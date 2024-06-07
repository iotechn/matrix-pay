package com.dobbinsoft.fw.pay.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class MatrixPayRequestGoodsDetail implements Serializable {

    private static final long serialVersionUID = 5561218000553885190L;
    /**
     * 必填 32 商品的编号
     */
    @JsonProperty("goods_id")
    private String goodsId;

    /**
     * 可选 32 支付定义的统一商品编号
     */
    @JsonProperty("pay_goods_id")
    private String payGoodsId;

    /**
     * 必填 256 商品名称
     */
    @JsonProperty("goods_name")
    private String goodsName;

    /**
     * 必填 商品数量
     */
    @JsonProperty("goods_num")
    private String goodsNum;

    /**
     * 必填 商品单价
     */
    private Integer price;

    /**
     * 可选 32 商品类目
     */
    @JsonProperty("goods_category")
    private String goodsCategory;

    /**
     * 可选 商品描述信息
     */
    private String body;

}
