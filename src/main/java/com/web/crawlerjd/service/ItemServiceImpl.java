package com.web.crawlerjd.service;

import com.web.crawlerjd.dao.ItemDao;
import com.web.crawlerjd.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @anthor Tolaris
 * @date 2020/3/6 - 21:10
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemDao itemDao;

    @Override
    @Transactional
    public void save(Item item) {
        itemDao.insert(item);
    }

    @Override
    public List<Item> find(Long sku) {
        return itemDao.findBySku(sku);
    }


}
