//package com.dobbinsoft.fw.pay.service.pay.ali.model;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
//@Getter
//@Setter
//public class AliPayUnifiedOrderRequest extends AliPayBaseRequest {
//
//    @JsonProperty("out_trade_no")
//    private String outTradeNo;
//
//    @JsonProperty("total_amount")
//    private String totalAmount;
//
//    @JsonProperty("subject")
//    private String subject;
//
//    @JsonProperty("product_code")
//    private String productCode;
//
//    @JsonProperty("goods_detail")
//    private List<GoodsDetail> goodsDetail;
//
//    @JsonProperty("time_expire")
//    private String timeExpire;
//
//    @JsonProperty("extend_params")
//    private ExtendParams extendParams;
//
//    @JsonProperty("passback_params")
//    private String passbackParams;
//
//    @JsonProperty("merchant_order_no")
//    private String merchantOrderNo;
//
//    @JsonProperty("ext_user_info")
//    private ExtUserInfo extUserInfo;
//
//    @Getter
//    @Setter
//    public static class GoodsDetail {
//
//        @JsonProperty("goods_id")
//        private String goodsId;
//
//        @JsonProperty("goods_name")
//        private String goodsName;
//
//        @JsonProperty("quantity")
//        private Integer quantity;
//
//        @JsonProperty("price")
//        private String price;
//
//        @JsonProperty("alipay_goods_id")
//        private String alipayGoodsId;
//
//        @JsonProperty("goods_category")
//        private String goodsCategory;
//
//        @JsonProperty("categories_tree")
//        private String categoriesTree;
//
//        @JsonProperty("show_url")
//        private String showUrl;
//
//    }
//
//    @Getter
//    @Setter
//    public static class ExtendParams {
//
//        @JsonProperty("sys_service_provider_id")
//        private String sysServiceProviderId;
//
//        @JsonProperty("hb_fq_num")
//        private String hbFqNum;
//
//        @JsonProperty("hb_fq_seller_percent")
//        private String hbFqSellerPercent;
//
//        @JsonProperty("industry_reflux_info")
//        private String industryRefluxInfo;
//
//        @JsonProperty("specified_seller_name")
//        private String specifiedSellerName;
//
//        @JsonProperty("card_type")
//        private String cardType;
//
//        @JsonProperty("royalty_freeze")
//        private String royaltyFreeze;
//
//    }
//
//    @Getter
//    @Setter
//    public static class ExtUserInfo {
//
//        @JsonProperty("cert_no")
//        private String certNo;
//
//        @JsonProperty("min_age")
//        private String minAge;
//
//        @JsonProperty("name")
//        private String name;
//
//        @JsonProperty("mobile")
//        private String mobile;
//
//        @JsonProperty("cert_type")
//        private String certType;
//
//        @JsonProperty("need_check_info")
//        private String needCheckInfo;
//
//        @JsonProperty("identity_hash")
//        private String identityHash;
//
//        @JsonProperty("query_options")
//        private List<String> queryOptions;
//
//    }
//
//}
