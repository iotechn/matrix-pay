package com.dobbinsoft.fw.pay.model.context;

import com.dobbinsoft.fw.pay.enums.PayChannelType;
import lombok.Data;

@Data
public class PayCallbackContext {

    private PayChannelType payChannelType;

}
