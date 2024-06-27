package com.njz.config;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

@Component
public class SystemProperties {
    /*{
        System.getProperties().keySet().forEach((k) -> System.out.println(k + " :::: " + System.getProperty(k.toString())));
    }*/

    public static void main(String[] args) throws UnsupportedEncodingException {
        // https://bbs.huaweicloud.com/blogs/363960
        String str = "是是是等待";
        String a = new String(str.getBytes(),"GBK");
        String b = new String(str.getBytes("UTF-8"),"GBK");
        String c = new String(str.getBytes("GBK"),"UTF-8");
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(new String(c.getBytes("UTF-8"),"GBK"));
        System.out.println(new String( Charset.defaultCharset().name()));
    }
}
