package com.dobbinsoft.fw.pay.service.pay.ali;

import com.dobbinsoft.fw.pay.config.PayProperties;
import com.dobbinsoft.fw.pay.exception.MatrixPayException;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;
import com.dobbinsoft.fw.pay.model.request.MatrixPayRefundRequest;
import com.dobbinsoft.fw.pay.model.request.MatrixPayUnifiedOrderRequest;
import com.dobbinsoft.fw.pay.model.result.MatrixPayRefundResult;
import com.dobbinsoft.fw.pay.service.pay.MatrixPayService;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClassName: AliPayServiceImpl
 * Description: 支付宝支付实现
 */
@Slf4j
public class AliPayServiceImpl implements MatrixPayService {

    private PayProperties payProperties;

    public AliPayServiceImpl(PayProperties payProperties) {
        this.payProperties = payProperties;
    }

    @Override
    public Object createOrder(MatrixPayUnifiedOrderRequest entity) throws MatrixPayException {
        // TODO
        return null;
    }

    /**
     * 将微信的时间，转化为ali的时间
     *
     * @param time
     * @return
     */
    private String transToAliTime(String time) throws ParseException {
        if (time == null) {
            return null;
        }
        SimpleDateFormat wxSdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date parse = wxSdf.parse(time);
        SimpleDateFormat aliSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return aliSdf.format(parse);
    }

    private String transToWxTime(String time) throws ParseException {
        if (time == null) {
            return null;
        }
        SimpleDateFormat aliSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse = aliSdf.parse(time);
        SimpleDateFormat wxSdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return wxSdf.format(parse);
    }



    @Override
    public MatrixPayRefundResult refund(MatrixPayRefundRequest entity) throws MatrixPayException {
        return null;
    }


    @Override
    public MatrixPayOrderNotifyResult checkParsePayResult(Object request) throws MatrixPayException {
        return null;
    }


    private String fenToYuan(Integer fen) {
        return "" + (fen / 100.0);
    }

}
