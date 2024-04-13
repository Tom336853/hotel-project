package com.wn.demo.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Jsoup 工具类
 */
public class JsoupUtil {

    //private static final Logger log = LoggerFactory.getLogger(HttpAspect.class);
    /**
     * @param content
     * @return 删除Html标签
     */
    public static String delHTMLTag(String content) {
        content = content.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "").replaceAll("[(/>)<]", "");

        // 去除字符串中的空格 回车 换行符 制表符 等

        content = content.replaceAll("\\s*|\t|\r|\n", "");

        // 去除空格
        content = content.replaceAll("&nbsp;", "");

        return content;
    }

    /**
     * 字符串转化为UTF-8
     *
     * @param str
     * @return
     */
    public static String toUTF8(String str) {
        String result = str;
        try {
            result = changeCharSet(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * @param str
     * @param newCharset
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String changeCharSet(String str, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // 用默认字符编码解码字符串。
            byte[] bs = str.getBytes();
            // 用新的字符编码生成字符串
            return new String(bs, newCharset);
        }
        return str;
    }

    /**
     * @param str
     * @return
     */
    public static String sub(String str) {

        if (!str.isEmpty()) {
            return str.substring(str.indexOf("：") + 1);
        }
        return null;
    }

    public static String subContent(String str) {

        if (!str.isEmpty()) {
            return str.substring(0, str.indexOf("http"));
        }
        return null;
    }

    /**
     * 获取链接的document对象
     *
     * @param url
     * @return document
     */
    public static Document getDoc(String url) {
        Map<String, String> header = new HashMap<String, String>();
        //header.put("Host", "http://info.bet007.com");
        header.put("Host", "http://www.baidu.com");
        header.put("User-Agent", "  Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0");
        header.put("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        header.put("Accept-Language", "zh-cn,zh;q=0.5");
        header.put("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7");
        header.put("Connection", "keep-alive");
        boolean flag = false;
        Document document = null;
        int i=0;
        do {
            try {
                document = Jsoup
                        .connect(url)
                        .header("Host","www.shuquge.com")
                        .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                        .header("Accept-Encoding","gzip, deflate")
                        .header("Accept-Language","zh-CN,zh;q=0.9")
                        .header("Cache-Control","no-cache")
                        .header("Connection","keep-alive")
                        .header("Pragma","no-cache")
                        .header("Upgrade-Insecure-Requests","1")
                        .timeout(5000)
                        .userAgent("Mozilla")//模拟浏览器
                        .get();
                flag = false;
            } catch (IOException e) {
                i++;
                //log.info("获取html失败了"+i+"次");
                flag = true;
            }
        } while (flag);
        return document;
    }

    public static void main(String[] args) {
        Document doc = getDoc("https://news.zua.edu.cn/xwtt1.htm");

        Elements elements = doc.select(".newslist li");
        System.out.println(elements);
        for (Element element1 : elements) {
            String string = element1.select("a").attr("abs:href");
            System.out.println(string);
            String val = element1.select(".time").text();
            System.out.println(val);
            String ab = element1.select("a").attr("title");
            System.out.println(ab);
            Document document = getDoc(string);
            Elements elements2=document.select(".v_news_content p");
            for (Element element2 : elements2) {
                System.out.println(element2.select("p").text());
            }
        }
    }

    public static boolean isConnection(String url) {
        boolean flag = false;
        int counts = 0;
        if (null == url || url.length() <= 0) {
            return flag;
        }
        while (counts < 10) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url)
                        .openConnection();
                int state = connection.getResponseCode();
                if (state == 200) {
                    flag = true;
                }
                break;
            } catch (Exception e) {
                counts++;
                continue;
            }
        }
        return flag;
    }

}
