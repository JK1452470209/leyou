package com.leyou.search.mq;

import com.leyou.search.service.SearchServiceImpl;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Mr.JK
 * @create 2020-05-25  17:01
 */
@Component
public class ItemListener {

    @Autowired
    private SearchServiceImpl searchServiceImpl;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.insert.queue",durable = "true"),
            exchange = @Exchange(name = "ly.item.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    public void ListenInsertOrUpdate(Long spuId){
        if (spuId == null){
            return;
        }
        //处理消息，对索引库进行新增或修改
        searchServiceImpl.createOrUpdateIndex(spuId);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.delete.queue",durable = "true"),
            exchange = @Exchange(name = "ly.item.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    ))
    public void ListenDelete(Long spuId){
        if (spuId == null){
            return;
        }
        //处理消息，对索引库进行删除
        searchServiceImpl.deleteIndex(spuId);
    }

}
