package com.imooc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.my.mapper.MyMapper;
import com.imooc.pojo.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UsersMapperPlus extends BaseMapper<Users> {
    @Select("select * from users where id=#{id}")
    Users getPO(@Param("id") String id);
}