package com.leyou.page.mq;

import com.leyou.page.service.PageService;
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
    private PageService pageService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "page.item.insert.queue",durable = "true"),
            exchange = @Exchange(name = "ly.item.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    public void ListenInsertOrUpdate(Long spuId){
        if (spuId == null){
            return;
        }
        //处理消息，对静态页进行新增或修改
        pageService.createHtml(spuId);
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "page.item.delete.queue",durable = "true"),
            exchange = @Exchange(name = "ly.item.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    ))
    public void ListenDelete(Long spuId){
        if (spuId == null){
            return;
        }
        //处理消息，对静态页进行删除
        pageService.deleteHtml(spuId);
    }

}
