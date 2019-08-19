package com.lw.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lw.common.Constants;
import com.lw.common.MyPage;
import com.lw.common.Result;
import com.lw.entity.dto.Goods;
import com.lw.entity.dto.GoodsType;
import com.lw.service.impl.GoodsService;
import com.lw.service.impl.GoodsTypeService;

@Controller
@RequestMapping("goods")
public class GoodsController {
	
	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private GoodsTypeService goodsTypeService;
	
	@Autowired
	private RestHighLevelClient client;
	
	/**
	 * 	*物品首页
	 */
	@RequestMapping("list2")
	protected String showList(HttpServletRequest request, 
			@RequestParam(value = "query",required = false)String query,
			@RequestParam(value = "goodsTypeId",required=false)Long typeId,
			@RequestParam(value = "pageId",required = false,defaultValue ="1")Integer pageId) {
		
		QueryWrapper<Goods> wrapper = new QueryWrapper<>();
		
		if(!"".equals(query)) {
			
		}
		if(typeId != null && typeId != -1) {
			wrapper.lambda().eq(Goods::getGoodsTypeId,typeId);
		}
		wrapper.lambda().orderByAsc(Goods::getOrderNum);
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
	 * 	*物品首页2
	 */
	@RequestMapping("list")
	protected String showList2(HttpServletRequest request, 
			@RequestParam(value = "query",required = false)String query,
			@RequestParam(value = "goodsTypeId",required=false)Long goodsTypeId,
			@RequestParam(value = "pageId",required = false,defaultValue ="1")Integer pageId) {
		
		SearchRequest res = new SearchRequest("good");
		
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		
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
	 * 	*上架物品(通过物品ID把物品状态升级为1)
	 */
	@RequestMapping("up")
	@ResponseBody
	protected Result<?> upGoods(Long id){
		try {
			goodsService.upGoodsById(id);
			return Result.success();
		} catch (Exception e) {	
			return Result.error(e.getMessage());
		}		
	}
	
	/**
	 * 	*下架物品(通过物品ID把物品状态升级为2)
	 */
	@RequestMapping("down")
	@ResponseBody
	protected Result<?> downGoods(Long id){
		try {
			goodsService.downGoodsById(id);
			return Result.success();
		} catch (Exception e) {	
			return Result.error(e.getMessage());
		}		
	}
	
	/**
	 * 	*删除物品(通过物品ID删除物品)
	 */
	@RequestMapping("del")
	@ResponseBody
	protected Result<?> delGoods(Long id){
		
		try {
			goodsService.delGoods(id);
			return Result.success();
		} catch (Exception e) {
			
			return Result.error(e.getMessage());
		}
		
		
	}
	
	
}
