package com.leyou.page.service;

import java.util.Map;

/**
 * @author Mr.JK
 * @create 2020-05-24  9:37
 */
public interface PageService {
    /**
     * 根据spuid返回加载模型
     * @param spuId
     * @return
     */
    Map<String, Object> loadModel(Long spuId);

    /**
     * 根据spuid创建静态页面
     * @param spuId
     */
    void createHtml(Long spuId);

    /**
     * 根据spuid删除静态页面
     * @param spuId
     */
    void deleteHtml(Long spuId);
}
