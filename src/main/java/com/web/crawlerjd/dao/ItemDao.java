package com.web.crawlerjd.dao;

import com.web.crawlerjd.pojo.Item;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @anthor Tolaris
 * @date 2020/3/6 - 20:51
 */
@Mapper
public interface ItemDao {

    @Select("select * from jd_item")
    List<Item> findAll();

    @Select("select * from jd_item where sku=#{sku}")
    List<Item> findBySku(Long sku);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into jd_item(spu,sku,title,price,pic,url,`create`,`update`) values(#{spu},#{sku},#{title},#{price},#{pic},#{url},#{create},#{update})")
    void insert(Item item);
}
