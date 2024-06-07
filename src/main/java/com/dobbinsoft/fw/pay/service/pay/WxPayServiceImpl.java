package com.dobbinsoft.fw.pay.service.pay;

import com.dobbinsoft.fw.pay.config.PayProperties;
import com.dobbinsoft.fw.pay.exception.MatrixPayException;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;
import com.dobbinsoft.fw.pay.model.request.MatrixPayRefundRequest;
import com.dobbinsoft.fw.pay.model.request.MatrixPayUnifiedOrderRequest;
import com.dobbinsoft.fw.pay.model.result.MatrixPayRefundResult;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * ClassName: WxPayServiceImpl
 * Description: 微信支付实现
 */
public class WxPayServiceImpl implements MatrixPayService {

    private final OkHttpClient client = new OkHttpClient();

    private PayProperties payProperties;

    public WxPayServiceImpl(PayProperties payProperties) {
        this.payProperties = payProperties;
    }

    @Override
    public Object createOrder(MatrixPayUnifiedOrderRequest entity) throws MatrixPayException {
        String xml = "<xml>\n   <appid>wx2421b1c4370ec43b</appid>\n   <attach>支付测试</attach>\n   <body>JSAPI支付测试</body>\n   <mch_id>10000100</mch_id>\n   <detail><![CDATA[{ \"goods_detail\":[ { \"goods_id\":\"iphone6s_16G\", \"wxpay_goods_id\":\"1001\", \"goods_name\":\"iPhone6s 16G\", \"quantity\":1, \"price\":528800, \"goods_category\":\"123456\", \"body\":\"苹果手机\" }, { \"goods_id\":\"iphone6s_32G\", \"wxpay_goods_id\":\"1002\", \"goods_name\":\"iPhone6s 32G\", \"quantity\":1, \"price\":608800, \"goods_category\":\"123789\", \"body\":\"苹果手机\" } ] }]]></detail>\n   <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>\n   <notify_url>https://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>\n   <openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid>\n   <out_trade_no>1415659990</out_trade_no>\n   <spbill_create_ip>14.23.150.211</spbill_create_ip>\n   <total_fee>1</total_fee>\n   <trade_type>JSAPI</trade_type>\n   <sign>0CB01533B8C1EF103065174F50BCA001</sign>\n</xml>";
        MediaType mediaType = MediaType.parse("application/xml");
        RequestBody body = RequestBody.create(xml, mediaType);
        Request request = new Request.Builder()
                .url("/pay/unifiedorder")
                .method("POST", body)
                .build();
//        Response response = client.newCall(request).execute();
        return null;
    }


    @Override
    public MatrixPayRefundResult refund(MatrixPayRefundRequest request) throws MatrixPayException {
        return null;
    }

    @Override
    public MatrixPayOrderNotifyResult checkParsePayResult(Object request) throws MatrixPayException {
        return null;
    }
}
