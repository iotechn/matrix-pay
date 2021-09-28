package com.dobbinsoft.fw.pay.model.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * 下载资金账单接口响应结果对象类
 * Created by cwivan on 2018-08-02.
 * </pre>
 *
 * @author cwivan
 */
@Data
@NoArgsConstructor
public class MatrixPayFundFlowResult implements Serializable {
    private static final long serialVersionUID = 8371500036495349207L;

    @Override
    public String toString() {
        return WxGsonBuilder.create().toJson(this);
    }

    /**
     * 资金流水返回对象.
     */
    private List<MatrixPayFundFlowBaseResult> matrixPayFundFlowBaseResultList;

    /**
     * 资金流水总笔数
     */
    private String totalRecord;

    /**
     * 收入笔数
     */
    private String incomeRecord;

    /**
     * 收入金额
     */
    private String incomeAmount;

    /**
     * 支出笔数
     */
    private String expenditureRecord;

    /**
     * 支出金额
     */
    private String expenditureAmount;

}
