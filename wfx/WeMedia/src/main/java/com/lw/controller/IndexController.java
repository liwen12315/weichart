package com.lw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lw.common.Result;
import com.lw.entity.vo.TreeView;



/**
 *   *首页内容
 * @author liwen
 *
 */
@Controller
public class IndexController {
	
	/**
	 * 	*跳转首页
	 */
	@RequestMapping("index")
	protected String goIndex() {
		return "index";
	}
	
	/**
	 *  *首页左边导航树（功能树）
	 */
	@RequestMapping("fun-list")
	@ResponseBody
	protected Result<?> showView(){
		
		List<TreeView> list = new ArrayList<TreeView>();
		
		TreeView goodsTreeView  = new TreeView();
		goodsTreeView.setBackColor("#ffffff");
		goodsTreeView.setColor("#000000");
		goodsTreeView.setHref("goods/list");
		goodsTreeView.setText("商品管理");
		goodsTreeView.setId(1);
		

		TreeView goodsTreeView2  = new TreeView();
		goodsTreeView2.setBackColor("#ffffff");
		goodsTreeView2.setColor("#000000");
		goodsTreeView2.setHref("order/list");
		goodsTreeView2.setText("订单管理");
		goodsTreeView2.setId(2);
		
		list.add(goodsTreeView);
		list.add(goodsTreeView2);
		
		return Result.success(list); 
	}
	
}
