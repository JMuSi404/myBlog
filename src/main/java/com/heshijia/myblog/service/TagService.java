package com.heshijia.myblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heshijia.myblog.pojo.Blog;
import com.heshijia.myblog.pojo.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【t_tag】的数据库操作Service
* @createDate 2022-05-23 21:56:44
*/
public interface TagService extends IService<Tag> {
    /**
     * 添加标签
     * @param tag
     * @return
     */
    int saveTag(Tag tag);
    /**
     * 以name为条件查询
     * @param name
     * @return
     */
    List<Tag> queryByName(String name);

    /**
     * 查询标签
     * 用的mybatis的简易分页插件
     * @param page
     * @return
     */
    Page<Tag> queryTagsPage(Page<Tag> page);


    /**
     * 以id为条件删除
     * @param
     * @return
     */
    int  removeTagsById(Long id);

    /**
     * 以id为条件查询
     * @param
     * @return
     */
    Tag queryAllById (@Param ( "id" ) Long id);

    /**
     * 以id为条件修改
     * @param
     * @return
     */
    int editTagNameById (@Param ( "name" ) String name , @Param ( "id" ) Long id);

    /**
     * 查出所有标签
     * @param
     * @return
     */
    List<Tag>  queryTagList();

    /**
     * 查出所有标签和数量
     * @param
     * @return
     */
    List<Tag>  queryTagListAndCount();


    /**
     * 根据blogid查出所有标签
     * @param
     * @return
     */
    List<Tag>  queryTagListByBlogId(Long id);
    /**
     * 查询出标签数量
     * @param
     * @return
     */
   Integer queryTagCount();


}
