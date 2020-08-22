package com.web.crawlerjd.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @anthor Tolaris
 * @date 2020/3/6 - 21:54
 */
@Component
public class HttpUtils {

    private PoolingHttpClientConnectionManager clientConnectionManager;

    public HttpUtils() {
        this.clientConnectionManager = new PoolingHttpClientConnectionManager();
        //设置最大连接数
        clientConnectionManager.setMaxTotal(100);
        //设置每个主机的最大连接数
        clientConnectionManager.setDefaultMaxPerRoute(10);
    }

    /**
     * 根据请求地址下载页面数据
     *
     * @param url
     * @return 页面数据
     */
    public String doGetHtml(String url) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.clientConnectionManager).build();
        HttpGet httpGet = new HttpGet(url);
        //设置请求信息
//        {
//                "url": "https://m.jd.com",
//                "raw_url": "https://m.jd.com/",
//                "method": "get",
//                "headers": {
//            "authority": "m.jd.com",
//                    "cache-control": "max-age=0",
//                    "upgrade-insecure-requests": "1",
//                    "user-agent": "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Mobile Safari/537.36",
//                    "sec-fetch-dest": "document",
//                    "accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
//                    "sec-fetch-site": "same-site",
//                    "sec-fetch-mode": "navigate",
//                    "sec-fetch-user": "?1",
//                    "referer": "https://www.jd.com/",
//                    "accept-language": "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7",
//                    "cookie": "__jdu=511697112; areaId=18; ipLoc-djd=18-1574-0-0; shshshfpa=97a2f332-42c7-40c1-9c53-b579b90ae079-1583505636; shshshfpb=zs5zH^%^2FmFHShqzi2M2^%^2FlUOxA^%^3D^%^3D; unpl=V2_ZzNtbREFQ0ciX08AeUsJVWJXG18SB0cdJw4TVX9NXwMyABJYclRCFnQUR1ZnGFsUZAAZWENcRxxFCEdkeBBVAWMDE1VGZxBFLV0CFSNGF1wjU00zQwBBQHcJFF0uSgwDYgcaDhFTQEJ2XBVQL0oMDDdRFAhyZ0AVRQhHZH8ZXAJvBRJdQWdzEkU4dlF7H1sFYzMTbUNnAUEpDkddeBlcSGMDElpKUUMVdjhHZHg^%^3d; __jdv=76161171^|baidu-pinzhuan^|t_288551095_baidupinzhuan^|cpc^|0f3d30c8dba7459bb52f2eb5eba8ac7d_0_bc1cff8e3cda4e83aa59c7d05e27d214^|1583505659301; thor=00D66BD830F1A670BF6A7785873172CCBBFF2539E5AFFC9C618DD72BD2B0E5C16D31C067419D6150B1FD65555547B9C5869578B879AE3B1CEF30BE6CCC580D1E383D81116D94FD09B21F95D4B6EAA6E2941E722CAD84B63490061E9ACE39E59C5292FB93F7FC537ECEE03D8EA8C95B0C3B62895F9C384EE2A6C1028B368819A016058DFA353B23C713CD4BCDBF8E34837F2FCE5652DF2179BCE35ADC4CC1D1D9; pinId=MDv5MEzx-zWddc-_LPkvFbV9-x-f3wj7; pin=jd_553e216594429; unick=jd_553e216594429; ceshi3.com=000; _tp=lY9PFAaiwVw3xE2UtQhltlSil7^%^2FwQKgHSJmUjr7en6M^%^3D; _pst=jd_553e216594429; shshshfp=3dced9a00d9ecc3c51562878816c7265; shshshsID=b9873af92f56f7ee315e2faceca16203_3_1583549296535; __jda=122270672.511697112.1583108275.1583505625.1583548166.2; __jdc=122270672; wlfstk_smdl=ej4bx3joh2bctz3u88swu1900sm3qrx4; 3AB9D23F7A4B3C9B=NV5IPJV6KXBAIOF7RLYLV2RT5QA5NY7TDNYXG7FFEVNOKW3BJZQ2ZWD2QJ3KXFYJXKDJIKLO5GICXCRARGGMHVWD4A; wxa_level=1; retina=1; cid=3; wqmnx1=MDEyNjM5M3AueiBBLiBNcHQoaSk4LmwvNTJhM09PJkg^%^3D; webp=1; __jdb=122270672.17.511697112^|2.1583548166; mba_muid=511697112; mba_sid=15835494478153703103434229051.1; autoOpenApp_downCloseDate_auto=1583549448729_21600000; visitkey=68640442331368455; __jd_ref_cls=MSearch_DarkLines"
//        }
//        }
        httpGet.setHeader("cache-control", "max-age=0");
        httpGet.setHeader("authority", "m.jd.com");
        httpGet.setHeader("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Mobile Safari/537.36");
        httpGet.setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        httpGet.setHeader("referer", "https://www.jd.com/");
        httpGet.setHeader("authority", "m.jd.com");
        httpGet.setHeader("accept-language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");
        httpGet.setHeader("cookie", "__jdu=511697112; areaId=18; ipLoc-djd=18-1574-0-0; shshshfpa=97a2f332-42c7-40c1-9c53-b579b90ae079-1583505636; shshshfpb=zs5zH^%^2FmFHShqzi2M2^%^2FlUOxA^%^3D^%^3D; unpl=V2_ZzNtbREFQ0ciX08AeUsJVWJXG18SB0cdJw4TVX9NXwMyABJYclRCFnQUR1ZnGFsUZAAZWENcRxxFCEdkeBBVAWMDE1VGZxBFLV0CFSNGF1wjU00zQwBBQHcJFF0uSgwDYgcaDhFTQEJ2XBVQL0oMDDdRFAhyZ0AVRQhHZH8ZXAJvBRJdQWdzEkU4dlF7H1sFYzMTbUNnAUEpDkddeBlcSGMDElpKUUMVdjhHZHg^%^3d; __jdv=76161171^|baidu-pinzhuan^|t_288551095_baidupinzhuan^|cpc^|0f3d30c8dba7459bb52f2eb5eba8ac7d_0_bc1cff8e3cda4e83aa59c7d05e27d214^|1583505659301; thor=00D66BD830F1A670BF6A7785873172CCBBFF2539E5AFFC9C618DD72BD2B0E5C16D31C067419D6150B1FD65555547B9C5869578B879AE3B1CEF30BE6CCC580D1E383D81116D94FD09B21F95D4B6EAA6E2941E722CAD84B63490061E9ACE39E59C5292FB93F7FC537ECEE03D8EA8C95B0C3B62895F9C384EE2A6C1028B368819A016058DFA353B23C713CD4BCDBF8E34837F2FCE5652DF2179BCE35ADC4CC1D1D9; pinId=MDv5MEzx-zWddc-_LPkvFbV9-x-f3wj7; pin=jd_553e216594429; unick=jd_553e216594429; ceshi3.com=000; _tp=lY9PFAaiwVw3xE2UtQhltlSil7^%^2FwQKgHSJmUjr7en6M^%^3D; _pst=jd_553e216594429; shshshfp=3dced9a00d9ecc3c51562878816c7265; shshshsID=b9873af92f56f7ee315e2faceca16203_3_1583549296535; __jda=122270672.511697112.1583108275.1583505625.1583548166.2; __jdc=122270672; wlfstk_smdl=ej4bx3joh2bctz3u88swu1900sm3qrx4; 3AB9D23F7A4B3C9B=NV5IPJV6KXBAIOF7RLYLV2RT5QA5NY7TDNYXG7FFEVNOKW3BJZQ2ZWD2QJ3KXFYJXKDJIKLO5GICXCRARGGMHVWD4A; wxa_level=1; retina=1; cid=3; wqmnx1=MDEyNjM5M3AueiBBLiBNcHQoaSk4LmwvNTJhM09PJkg^%^3D; webp=1; __jdb=122270672.17.511697112^|2.1583548166; mba_muid=511697112; mba_sid=15835494478153703103434229051.1; autoOpenApp_downCloseDate_auto=1583549448729_21600000; visitkey=68640442331368455; __jd_ref_cls=MSearch_DarkLines");

        httpGet.setConfig(this.getConfig());
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                if (response.getEntity() != null) {
                    String content = EntityUtils.toString(response.getEntity(), "utf8");
                    return content;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭response
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 下载图片
     *
     * @param url
     * @return 图片名称
     */
    public String doGetImage(String url) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.clientConnectionManager).build();
        HttpGet httpGet = new HttpGet(url);
        //设置请求信息
        httpGet.setConfig(this.getConfig());
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                if (response.getEntity() != null) {
                    //获取图片的后缀
                    String extName = url.substring(url.lastIndexOf("."));
                    //创建图片名，重新命名图片
                    String picName = UUID.randomUUID().toString() + extName;
                    //下载图片
                    OutputStream outputStream = new FileOutputStream(new File("C:\\Users\\MY  GODCUP\\Desktop\\images\\" + picName));
                    response.getEntity().writeTo(outputStream);
                    return picName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭response
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 设置请求信息
     *
     * @return
     */
    public RequestConfig getConfig() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(3000)//创建连接的最长时间
                .setConnectionRequestTimeout(3000)//获取连接的最长时间
                .setSocketTimeout(10 * 1000)//数据传输的最长时间
                .build();
        return config;
    }

}
