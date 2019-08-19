package com.lw.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.lw.entity.dto.Goods;


/**
 *  
 * @author liwen
 *
 */
@Component
public class ComsumerService {
	
	@Autowired
	private RestHighLevelClient client;
	
	/**
	 * 	监听goods消息 当收到消息后 对ES中的good表进行操作
	 * @param goods
	 * @throws IOException 
	 */
	@JmsListener(destination = "goods")
    public void saveMessage(Goods goods) throws IOException {
        System.out.println("收到save goods消息："+ goods);
        
        
        GetRequest getRequest = new GetRequest("good", goods.getId().toString());
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        //若果存在就执行更新操作
        if (getResponse.isExists()) {
        	 UpdateRequest request = new UpdateRequest("good",goods.getId().toString());
             Map<String, Object> doc = new HashMap<>(); 
             doc.put("id",goods.getId());
             doc.put("name",goods.getName());
             doc.put("merchant_user_id",goods.getMerchantUserId());
             doc.put("pic",goods.getPic());
             doc.put("goods_type_id",goods.getGoodsTypeId());
             doc.put("promote_desc",goods.getPromoteDesc());
             doc.put("sku_title",goods.getSkuTitle());
             doc.put("sku_cost",goods.getSkuCost());
             doc.put("sku_price",goods.getSkuPrice());
             doc.put("sku_pmoney",goods.getSkuPmoney());
             doc.put("order_num",goods.getOrderNum());
             doc.put("state",goods.getState());
             doc.put("create_time",goods.getCreateTime());
             request.doc(doc);
             UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
             System.out.println(response.status().name());
        }else {
        	IndexRequest indexRequest = new IndexRequest("good");
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject()
            		.field("id",goods.getId())
            		.field("name",goods.getName())
            		.field("merchant_user_id",goods.getMerchantUserId())
            		.field("goods_type_id",goods.getGoodsTypeId())
            		.field("promote_desc",goods.getPromoteDesc())
            		.field("sku_title",goods.getSkuTitle())
            		.field("pic",goods.getPic())
            		.field("sku_cost",goods.getSkuCost())
            		.field("sku_price",goods.getSkuPrice())
            		.field("sku_pmoney",goods.getSkuPmoney())
            		.field("state",goods.getState())
            		.field("order_num",goods.getOrderNum())
            		.field("create_time",goods.getCreateTime())
            		.endObject();
            indexRequest.id(goods.getId().toString()).opType("create").source(builder);
            IndexResponse index = client.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println(index.status().name());
        }

       
    }
	
	@JmsListener(destination = "remove")
    public void removeMessage(Long id) throws IOException {
        System.out.println("收到remove goods消息："+ id);
        
        DeleteRequest request = new DeleteRequest("good",id.toString());
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.status().name());
        
    }

	
	
}
