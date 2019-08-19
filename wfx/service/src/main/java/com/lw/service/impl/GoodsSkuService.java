package com.lw.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lw.entity.dto.GoodsSku;
import com.lw.mapper.GoodsSkuMapper;

@Service
public class GoodsSkuService extends ServiceImpl<GoodsSkuMapper, GoodsSku>{
	
	/**
	 * 	通过物品ID删除物品详情表中数据
	 * @param id
	 */
	public void delByGoodsId(Long id) {	
		QueryWrapper<GoodsSku> wrapper = new QueryWrapper<GoodsSku>();
		wrapper.lambda()
				.eq(GoodsSku::getGoodId,id);   //  good_id = #{id}
		
		this.remove(wrapper);
	}
	
	/**
	 *  通过物品ID返回详情列表
	 * @param id
	 * @return
	 */
	public List<GoodsSku> getSkuListById(Long id) {
		
		QueryWrapper<GoodsSku> wrapper = new QueryWrapper<GoodsSku>();
		wrapper.lambda()
				.eq(GoodsSku::getGoodId,id);    // good_id = #{id}
		
		return this.list(wrapper);
	}

}
