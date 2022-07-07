package com.heshijia.myblog.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.heshijia.myblog.pojo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Administrator
* @description 针对表【t_tag】的数据库操作Mapper
* @createDate 2022-05-23 21:56:44
* @Entity com.heshijia.myblog.pojo.Tag
*/
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 以name为条件查询
     * @param name
     * @return
     */
    List<Tag> selectAllByName (@Param ( "name" ) String name);

    /**
     * 以id为条件删除
     * @param
     * @return
     */
    int delById (@Param ( "id" ) Long id);

    /**
     * 以id为条件查询
     * @param
     * @return
     */
    Tag selectAllById (@Param ( "id" ) Long id);

    /**
     * 以id为条件修改
     * @param
     * @return
     */
    int updateNameById (@Param ( "name" ) String name , @Param ( "id" ) Long id);


    /**
     * 查询所有标签以及被使用数量
     * @param
     * @return
     */
    List<Tag> selectAllAndcount ();


    List<Tag>   selectTagListByBlogId(Long blogId);

   Integer selectTagCount( );
}




