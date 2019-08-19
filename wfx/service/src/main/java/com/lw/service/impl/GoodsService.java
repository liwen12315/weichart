package com.lw.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lw.entity.dto.Goods;
import com.lw.entity.dto.GoodsSku;
import com.lw.mapper.GoodsMapper;

@Service
public class GoodsService extends ServiceImpl<GoodsMapper, Goods>{
	
	@Autowired
	private GoodsSkuService goodsSkuService;
	
	@Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
	/**
	 * 	上架物品
	 * @param id
	 */
	@Transactional
	public void upGoodsById(Long id) {
		UpdateWrapper<Goods> wrapper = new UpdateWrapper<Goods>();
		wrapper.lambda()
				.eq(Goods::getId, id)
				.set(Goods::getState, 1);
		boolean update = this.update(wrapper);
		
		//如果升级成功就发一条消息 内容为升级后的goods
		if(update) {
			jmsMessagingTemplate.convertAndSend("goods",this.getById(id));
		}
	}
	
	/**
	 * 	下架物品
	 */
	@Transactional
	public void downGoodsById(Long id) {
		UpdateWrapper<Goods> wrapper = new UpdateWrapper<Goods>();
		wrapper.lambda()
				.eq(Goods::getId, id)
				.set(Goods::getState, 2);
		boolean update = this.update(wrapper);
		if(update) {
			jmsMessagingTemplate.convertAndSend("goods",this.getById(id));
		}
		
	}
	
	/**
	 * 	删除物品 同时删除GOODS_SKU表中对应的数据
	 * @param id
	 */
	@Transactional
	public void delGoods(Long id) {
		
		//通过ID删除商品物品
		boolean remove = this.removeById(id);
		
		goodsSkuService.delByGoodsId(id);
		
		//如果删除成功就xiang remove里发一条消息
		if (remove) {
			jmsMessagingTemplate.convertAndSend("remove",id);
		}
		
		
	}
	/**
	 *  更新或者保存物品
	 * @param goods
	 */
	@Transactional
	public void saveUpdateGoods(Goods goods) {
		
		//先判断goodsId是否存在 存在的话就删除对应的物品详情表
		if(goods.getId() != null) {
			goodsSkuService.delByGoodsId(goods.getId());
		}
		
		boolean saveOrUpdate = this.saveOrUpdate(goods);
		
		//如果保存成功就发送一条消息到goods
		if (saveOrUpdate) {
			jmsMessagingTemplate.convertAndSend("goods",goods);
		}
		
		//获取goods表中的详情list 然后插入goodsSku表
		Gson gson = new Gson();
		
		List<String> titleList = gson.fromJson(goods.getSkuTitle(), new TypeToken<List<String>>() {
        }.getType());
		List<String> costList = gson.fromJson(goods.getSkuCost(), new TypeToken<List<String>>() {
        }.getType());
		List<String> priceList = gson.fromJson(goods.getSkuPrice(), new TypeToken<List<String>>() {
        }.getType());
		List<String> pmoneyList = gson.fromJson(goods.getSkuPmoney(), new TypeToken<List<String>>() {
        }.getType());
		
		List<GoodsSku> needInsert = new ArrayList<GoodsSku>();
		
		for(int i=0;i< titleList.size(); i++) {
			GoodsSku goodsSku = new GoodsSku();
			goodsSku.setGoodId(goods.getId());
			goodsSku.setTitle(titleList.get(i));
			goodsSku.setCost(costList.get(i));
			goodsSku.setPrice(priceList.get(i));
			goodsSku.setPmoney(pmoneyList.get(i));
			goodsSku.setOrderNum(goods.getOrderNum());
			needInsert.add(goodsSku);
		}
		
		goodsSkuService.saveBatch(needInsert);   //批量插入goodsSku;
	}
}
