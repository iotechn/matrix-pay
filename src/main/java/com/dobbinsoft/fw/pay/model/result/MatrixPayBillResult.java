package com.dobbinsoft.fw.pay.model.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 微信对账单结果类.
 *
 * @author DDLeEHi
 */
@Data
@NoArgsConstructor
public class MatrixPayBillResult implements Serializable {
    private static final String TOTAL_DEAL_COUNT = "总交易单数";
    private static final long serialVersionUID = -7687458652694204070L;

    @Override
    public String toString() {
        return WxGsonBuilder.create().toJson(this);
    }

    /**
     * 对账明细列表.
     */
    private List<MatrixPayBillInfo> billInfoList;
    /**
     * 总交易单数.
     */
    private String totalRecord;
    /**
     * 应结订单总金额.
     */
    private String totalFee;
    /**
     * 退款总金额.
     */
    private String totalRefundFee;
    /**
     * 充值券退款总金额.
     */
    private String totalCouponFee;
    /**
     * 手续费总金额.
     */
    private String totalPoundageFee;
    /**
     * 订单总金额.
     */
    private String totalAmount;
    /**
     * 申请退款总金额.
     */
    private String totalAppliedRefundFee;


}
