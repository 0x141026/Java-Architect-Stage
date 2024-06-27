package com.imooc.mapper;

import com.imooc.my.mapper.MyMapper;
import com.imooc.pojo.Carousel;

import java.io.InputStream;

public interface FileMapper {
    InputStream selectContent(String id);
}