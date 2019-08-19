package com.lw.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.common.Constants;
import com.lw.common.CurrentTime;
import com.lw.common.Result;
import com.lw.common.WxPay.MyWXConfig;
import com.lw.common.WxPay.WXPay;
import com.lw.entity.dto.Goods;
import com.lw.entity.dto.GoodsSku;
import com.lw.entity.dto.MerchantUser;
import com.lw.entity.dto.Order;
import com.lw.service.impl.GoodsService;
import com.lw.service.impl.GoodsSkuService;
import com.lw.service.impl.OrderService;

@Controller
@RequestMapping("order")
public class OrderController {
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private GoodsSkuService goodsSkuService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private CurrentTime currentTime;
	
	
	/**
	 * 	根据物品ID返回物品信息 给订单页
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("new")
	protected String showOrder(HttpServletRequest request,
			@RequestParam(value = "goodsId",required = true)Long goodsId) {
		
		Goods goods = goodsService.getById(goodsId);
		List<GoodsSku> goodsSkuList = goodsSkuService.getSkuListById(goodsId);
		
		request.setAttribute("goods", goods);
		request.setAttribute("goodsSkuList", goodsSkuList);
		
		return "order/new";
	}
	
	/**
	 * 	生成订单接口，在数据库端生成
	 * @param request
	 * @param order
	 * @return
	 */
	@RequestMapping("createOrder")
	@ResponseBody
	protected Result<?> createOrder(HttpServletRequest request,
			Order order){
		
		MerchantUser merchantUser = (MerchantUser) request.getSession().getAttribute(Constants.CURRENT_USER);
		//设置用户名称
		order.setMerchantUserId(merchantUser.getId());
		
		String dateTime = currentTime.getThreadLocal().get().format(new Date());
		
		//设置订单号
		order.setOrderId(dateTime+RandomUtils.nextInt(10000, 100000)+merchantUser.getId());
		
		try {
			orderService.save(order);
		} catch (Exception e) {
			return Result.error(e.getMessage());
		}
		
		return Result.success();
	}
	
	/**
	 * 	订单首页
	 * @param request
	 * @param query
	 * @param pageId
	 * @return
	 */
	@RequestMapping("list")
	protected String showList(HttpServletRequest request,
			@RequestParam(value = "query",required = false)String query,
			@RequestParam(value = "pageId",required = false,defaultValue ="1")Integer pageId){
		
		MerchantUser merchantUser = (MerchantUser) request.getSession().getAttribute(Constants.CURRENT_USER);
		
		QueryWrapper<Order> wrapper = new QueryWrapper<Order>();
		if(!"".equals(query)) {
			
		}
		wrapper.lambda()
				.eq(Order::getMerchantUserId, merchantUser.getId())
				.orderByDesc(Order::getCreateTime);
		
		IPage<Order> page = orderService.page(new Page<>(pageId, Constants.PAGE_SIZE), wrapper);
		
		request.setAttribute("page", page);
		
		return "order/list";
	}
	
	/**
	 *  跳转微信支付页面
	 * @param id
	 * @return
	 */
	@RequestMapping("wxpay")
	protected String goWxpay(HttpServletRequest request,
			@RequestParam(value = "id",required = true)Long id) {
		
		Order order = orderService.getById(id);
		
		request.setAttribute("order", order);
		
		return "order/wxpay";
	}
	
	
	/**
	 * 	
	 * @param merchantUser
	 * @param id
	 * @return
	 */
    @RequestMapping("getQR")
    @ResponseBody
    protected Result<?> getQR(HttpServletRequest request,@RequestParam(required = true) Long id){
        
    	MerchantUser merchantUser = (MerchantUser) request.getSession().getAttribute(Constants.CURRENT_USER);
    	//通过id获取地址
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        try {
            Order order = orderService.getById(id);
            WXPay wxPay = new WXPay(new MyWXConfig());
            Long goodId = order.getGoodId();
            Map<String, String> data = new HashMap<String, String>();
            data.put("body", order.getGoodsName());  //商品描述
            data.put("out_trade_no", sdf.format(new Date()).concat(merchantUser.getId().toString()).concat(String.valueOf(RandomUtils.nextInt(10000, 100000)))); //外部订单号
            data.put("fee_type", "CNY"); //币种
            data.put("total_fee", "1"); //金额，单位:分 ! 不要改!!
            data.put("spbill_create_ip", "127.0.0.1"); //终端ip
            data.put("notify_url", "http://sr9i6m.natappfree.cc/callback/wxpay/notifyCall"); //回调地址
            data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
            data.put("product_id", goodId.toString()); //商品id
            data.put("attach", order.getOrderId()); //内部订单号
            try {
                Map<String, String> resp = wxPay.unifiedOrder(data);
                String resultCode = resp.get("result_code");
                if ("FAIL".equals(resultCode)) {
                    return Result.error(resp.get("err_code_des"));
                } else {
                    return Result.success(resp.get("code_url"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error("支付失败");
    }
    
    /**
     *  获取订单支付状态
     */
    @RequestMapping("getPayStatus")
    @ResponseBody
    protected Result<?> getPayStatus(@RequestParam(value = "id",required = true)Long id){
    	
    	Order order = orderService.getById(id);
    	
    	if(order.getPayState() == 1) {
    		return Result.success("paid");
    	}
    	
    	return Result.success("UnPaid");
    }


}
