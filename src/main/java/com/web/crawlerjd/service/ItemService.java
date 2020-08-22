package com.web.crawlerjd.service;

import com.web.crawlerjd.pojo.Item;

import java.util.List;

/**
 * @anthor Tolaris
 * @date 2020/3/6 - 20:56
 */
public interface ItemService {

    /**
     * 保存商品
     *
     * @param item
     */
    void save(Item item);

    /**
     * 查询所有的商品
     *
     * @return
     */
    List<Item> find(Long sku);
}
