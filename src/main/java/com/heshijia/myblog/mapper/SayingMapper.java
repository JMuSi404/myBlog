package com.heshijia.myblog.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.heshijia.myblog.pojo.Saying;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Administrator
* @description 针对表【t_saying】的数据库操作Mapper
* @createDate 2022-05-27 09:37:16
* @Entity com.heshijia.myblog.pojo.Saying
*/
public interface SayingMapper extends BaseMapper<Saying> {


    List<Saying> selectAllByName (@Param ( "name" ) String name);



    List<Saying> selectAllBySerialnumber (@Param ( "serialnumber" ) Integer serialnumber);


    Saying selectBySerialnumber (@Param ( "serialnumber" ) Integer serialnumber);
}




