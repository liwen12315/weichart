package com.lw.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.common.Constants;
import com.lw.common.MyPage;
import com.lw.common.Result;
import com.lw.entity.dto.Goods;
import com.lw.entity.dto.GoodsSku;
import com.lw.entity.dto.GoodsType;
import com.lw.entity.dto.MerchantUser;
import com.lw.service.impl.GoodsService;
import com.lw.service.impl.GoodsSkuService;
import com.lw.service.impl.GoodsTypeService;

@Controller
@RequestMapping("goods")
public class MerchantGoodsController {
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private GoodsTypeService goodsTypeService;
	
	@Autowired
	private GoodsSkuService goodsSkuService;
		
	@Autowired
	private RestHighLevelClient client;
	
	/**
	 * 	物品列表
	 * @param request
	 * @param query
	 * @param typeId
	 * @param pageId
	 * @return
	 */
	@RequestMapping("list2")
	protected String goodsList(HttpServletRequest request,
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
	
	/**
	 * 	ES查询goods list
	 * @param request
	 * @param query
	 * @param goodsTypeId
	 * @param pageId
	 * @return
	 */
	@RequestMapping("list")
	protected String goodsList2(HttpServletRequest request,
			@RequestParam(value = "query",required = false)String query,
			@RequestParam(value = "goodsTypeId",required=false)Long goodsTypeId,
			@RequestParam(value = "pageId",required = false,defaultValue ="1")Integer pageId) {
		
		MerchantUser merchantUser = (MerchantUser) request.getSession().getAttribute(Constants.CURRENT_USER);
		
		SearchRequest res = new SearchRequest("good");
		
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		
		boolQueryBuilder.must(QueryBuilders.matchQuery("merchant_user_id",merchantUser.getId()));
		
		if(!"".equals(query) && query != null ) {
			boolQueryBuilder.must(QueryBuilders.multiMatchQuery(query, "name","promote_desc","sku_title"));	
		}
		
		if(goodsTypeId != null && goodsTypeId != -1) {
			boolQueryBuilder.must(QueryBuilders.matchQuery("goods_type_id",goodsTypeId));
		}
		 
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.sort(SortBuilders.fieldSort("order_num").order(SortOrder.DESC));
        searchSourceBuilder.from((pageId-1)*Constants.PAGE_SIZE).size(Constants.PAGE_SIZE).query(boolQueryBuilder);

        res.searchType(SearchType.DEFAULT).source(searchSourceBuilder);

        SearchResponse response = null;
		try {
			response = client.search(res, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();						//记录集合
        for (SearchHit hit : response.getHits().getHits()) {													
            list.add(hit.getSourceAsMap());
        }
        int recordCount = (int) response.getHits().getTotalHits().value;   //总记录数
        
        MyPage<?> page = new MyPage<Map<String, Object>>(pageId, Constants.PAGE_SIZE, list, recordCount);
        
        request.setAttribute("page", page);
		
		//物品种类
		QueryWrapper<GoodsType> wrapper2 = new QueryWrapper<>();
		wrapper2.lambda()
				.orderByAsc(GoodsType::getOrderNum);
		request.setAttribute("goodsTypeList", goodsTypeService.list(wrapper2));
		
		return "goods/list";
	
	}
	
	
	
	/**	
	 * 	 跳转新增或者修改页面接口
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("add")
	protected String addGoods(HttpServletRequest request,
			@RequestParam(value = "id",required = false)Long id) {
		
		
		List<GoodsType> goodsTypeList = goodsTypeService.list();
		request.setAttribute("goodsTypeList", goodsTypeList);
		//判断id是否为空 如果为空直接返回
		if(id == null) {
			return "goods/add";
		}	
		// ID 查询物品表和详情表
		Goods goods = goodsService.getById(id);
		//详情表
		List<GoodsSku> goodsSkuList = goodsSkuService.getSkuListById(id);
		
		request.setAttribute("goods", goods);
		request.setAttribute("goodsSkuList", goodsSkuList);
		
		return "goods/add";
	}
	
	/**
	 * 	更新或者新增 商品（商品详情）
	 * @param request
	 * @param goods
	 * @return
	 */
	@RequestMapping("save")
	@ResponseBody
	protected Result<?> saveUpdate(HttpServletRequest request,Goods goods){
		
		MerchantUser merchantUser = (MerchantUser) request.getSession().getAttribute(Constants.CURRENT_USER);
		
		goods.setMerchantUserId(merchantUser.getId());
		
		try {
			goodsService.saveUpdateGoods(goods);
			return Result.success();
		} catch (Exception e) {
			return Result.error(e.getMessage());
		}
			
	}
	/**
	 *  删除商品和其对应的详情表
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("del")
	@ResponseBody
	protected Result<?> delById(HttpServletRequest request,
			@RequestParam(value = "id",required = true)Long id){
		
		try {
			goodsService.delGoods(id);
			return Result.success();
		} catch (Exception e) {
			return Result.error(e.getMessage());
		}	
	}
	
	/**
	 *  照片回显接口
	 * @param file
	 * @return
	 */
	@RequestMapping("upload")
	@ResponseBody
	protected Result<?> upLoad(MultipartFile file){
		
		//获取文件名称
		String filename = file.getOriginalFilename();
		//截取最后一个. 后面为文件后缀
		int lastIndex = filename.lastIndexOf(".");
		
		String uuid = UUID.randomUUID().toString();		
		//拼接形成新的文件名
		String newname = uuid+ filename.substring(lastIndex); 
		
		//将文件存到本地磁盘E:\temp\img
		try {
			file.transferTo(new File("E:\\temp\\img\\"+newname));
//			file.transferTo(new File("/usr/local/picture/"+newname));
		} catch (IllegalStateException | IOException e) {
			return Result.error(e.getMessage());
		}
		//ngnix反向代理地址 "http://localhost:8080/"+newname
		return Result.success("http://localhost:8080/"+newname);
	}
	
}
