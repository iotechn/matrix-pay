package com.dobbinsoft.fw.pay.model.request;

import com.github.binarywang.wxpay.constant.WxPayConstants.BillType;
import com.github.binarywang.wxpay.exception.WxPayException;
import lombok.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * <pre>
 *   微信支付下载对账单请求参数类
 * Created by Binary Wang on 2017-01-11.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class MatrixPayDownloadBillRequest extends MatrixBasePayRequest {
    private static final String[] BILL_TYPES = new String[]{BillType.ALL, BillType.SUCCESS, BillType.REFUND, BillType.RECHARGE_REFUND};
    private static final String TAR_TYPE_GZIP = "GZIP";

    /**
     * <pre>
     * 设备号
     * device_info
     * 否
     * String(32)
     * 13467007045764
     * 终端设备号
     * </pre>
     */
    private String deviceInfo;

    /**
     * <pre>
     * 账单类型
     * bill_type
     * 是
     * ALL
     * String(8)
     * --ALL，返回当日所有订单信息，默认值
     * --SUCCESS，返回当日成功支付的订单
     * --REFUND，返回当日退款订单
     * </pre>
     */
    private String billType;

    /**
     * <pre>
     * 对账单日期
     * bill_date
     * 是
     * String(8)
     * 20140603
     * 下载对账单的日期，格式：20140603
     * </pre>
     */
    private String billDate;

    /**
     * <pre>
     * 压缩账单
     * tar_type
     * 否
     * String(8)
     * GZIP
     * 非必传参数，固定值：GZIP，返回格式为.gzip的压缩包账单。不传则默认为数据流形式。
     * </pre>
     */
    private String tarType;

}
