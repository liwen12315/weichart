package com.lw.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.common.Constants;
import com.lw.entity.dto.Goods;
import com.lw.entity.dto.GoodsType;
import com.lw.entity.dto.MerchantUser;
import com.lw.service.impl.GoodsService;
import com.lw.service.impl.GoodsTypeService;

@Controller
@RequestMapping("goods")
public class GoodsController {
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private GoodsTypeService goodsTypeService;
	/**
	 * 	 上架物品首页
	 * @param request
	 * @param query
	 * @param typeId
	 * @param pageId
	 * @return
	 */
	@RequestMapping("list")
	protected String showList(HttpServletRequest request,
			@RequestParam(value = "query",required = false)String query,
			@RequestParam(value = "goodsTypeId",required=false)Long typeId,
			@RequestParam(value = "pageId",required = false,defaultValue ="1")Integer pageId) {
		
			//查询当前用户的ID
			MerchantUser merchantUser = (MerchantUser) request.getSession().getAttribute(Constants.CURRENT_USER);
				
			QueryWrapper<Goods> wrapper = new QueryWrapper<>();
				
			//当搜索框不为空时
			if (!"".equals(query)) {
				
			}
			//根据物品种类查询
			if(typeId != null && typeId != -1) {
				wrapper.lambda().eq(Goods::getGoodsTypeId,typeId);
			}		
			wrapper.lambda()
					.eq(Goods::getMerchantUserId,merchantUser.getId())     //merchant_user_id = #{id};
					.eq(Goods::getState,1)									//条件已上架的商品
					.orderByAsc(Goods::getOrderNum);

			IPage<Goods> page = goodsService.page(new Page<>(pageId, Constants.PAGE_SIZE), wrapper);
			request.setAttribute("page", page);
			
			//物品种类
			QueryWrapper<GoodsType> wrapper2 = new QueryWrapper<>();
			wrapper2.lambda()
					.orderByAsc(GoodsType::getOrderNum);
			request.setAttribute("goodsTypeList", goodsTypeService.list(wrapper2));
				
			return "goods/list";
		
	}
	
	

}
