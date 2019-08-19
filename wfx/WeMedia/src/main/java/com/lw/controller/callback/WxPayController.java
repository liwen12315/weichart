package com.lw.controller.callback;

import com.lw.common.WxPay.MyWXConfig;
import com.lw.common.WxPay.WXPayConstants;
import com.lw.common.WxPay.WXPayUtil;
import com.lw.service.impl.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("callback/wxpay")
public class WxPayController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private OrderService orderService;

    @RequestMapping("notifyCall")
    @ResponseBody
    public String notifyCall(HttpServletRequest request) throws Exception {
        if (request.getContentLength() < 0) {
            return SUCCESS;
        }
        DataInputStream in;
        String wxNotifyXml = "";
        try {
            in = new DataInputStream(request.getInputStream());
            byte[] dataOrigin = new byte[request.getContentLength()];
            in.readFully(dataOrigin);
            if (null != in) {
                in.close();
            }
            wxNotifyXml = new String(dataOrigin);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> params = WXPayUtil.xmlToMap(wxNotifyXml);
        if (params == null || params.size() < 1) {
            return FAIL;
        }

        if (WXPayUtil.isSignatureValid(params, new MyWXConfig().getKey(), WXPayConstants.SignType.HMACSHA256)) {
            String resultCode = params.get("result_code");
            String orderId = params.get("attach");
            if ("SUCCESS".equals(resultCode) && !StringUtils.isEmpty(orderId)) {
                if (orderService.updatePayState(orderId)) {
                    return SUCCESS;
                }
            }
        }
        return FAIL;
    }
}
