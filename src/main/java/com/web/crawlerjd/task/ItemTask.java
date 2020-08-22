package com.web.crawlerjd.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.crawlerjd.pojo.Item;
import com.web.crawlerjd.service.ItemService;
import com.web.crawlerjd.util.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @anthor Tolaris
 * @date 2020/3/6 - 22:37
 */
@Component
public class ItemTask {

    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    private ItemService itemService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    //当下载任务完成后，间隔多长时间进行下一次的任务
    @Scheduled(fixedDelay = 100 * 1000)
    public void itemTask() throws Exception {
        //声明需要解析的初始地址
        String url = "https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wq=%E6%89%8B%E6%9C%BA&s=55&click=0&page=";
        for (int i = 0; i < 5; i = i + 2) {
            String html = httpUtils.doGetHtml(url + i);
            this.parse(html);
        }
    }

    //解析商品，获取商品数据并存储
    private void parse(String html) throws Exception {
        //解析html获取document
        Document document = Jsoup.parse(html);
        //获取spu
        Elements spuElements = document.select("div#J_goodsList > ul > li");
        for (Element spuElement : spuElements) {
            //获取spu
            long spu = Long.parseLong(spuElement.attr("data-spu"));
            //获取sku
            Elements skuElements = spuElement.select("li.ps-item");
            for (Element skuElement : skuElements) {
                long sku = Long.parseLong(skuElement.select("[data-sku]").attr("data-sku"));
                //根据sku查询数据
                Item item = new Item();
                item.setSku(sku);
                List<Item> listSku = itemService.find(sku);
                if (listSku.size() > 0) {
                    //如果商品存在，就不进行保存
                    continue;
                }
                item.setSpu(spu);
                //获取商品详情的url
                String itemUrl = "https://item.jd.com/" + sku + ".html";
                item.setUrl(itemUrl);
                //获取商品的图片
                String picUrl = "https:" + skuElement.select("img[data-sku]").first().attr("data-lazy-img");
                picUrl = picUrl.replace("/n9/", "/n1/");
                String picName = httpUtils.doGetImage(picUrl);
                item.setPic(picName);
                //获取商品的价格
                String priceJson = httpUtils.doGetHtml("https://p.3.cn/prices/mgets?skuIds=J_" + sku);
                double price = MAPPER.readTree(priceJson).get(0).get("p").asDouble();
                item.setPrice(price);
                //获取商品的标题
                String itemInfo = httpUtils.doGetHtml(item.getUrl());
                String title = Jsoup.parse(itemInfo).getElementsByTag("title").text();
                item.setTitle(title);
                //设置时间
                item.setCreate(System.currentTimeMillis());
                item.setUpdate(item.getCreate());
                //保存商品数据到数据库中
                itemService.save(item);
            }
        }
    }
}
