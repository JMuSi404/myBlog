package com.heshijia.myblog.service;

import com.heshijia.myblog.pojo.Saying;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administrator
* @description 针对表【t_saying】的数据库操作Service
* @createDate 2022-05-27 09:37:16
 * 废用了,找到了更好的api接口
*/
public interface SayingService extends IService<Saying> {
    /**
     * 根据Name查询名言
     * @param sayingName
     * @return
     */
    List<Saying> queryByName(String sayingName);

    /**
     * 根据序号查询名言
     * @param
     * @return
     */
    List<Saying> selectAllBySerialnumber(Integer Serialnumber);


    /**
     * 根据序号查询名言
     * @param
     * @return
     */
    Saying selectBySerialnumber(Integer Serialnumber);



    /**
     * 根据Id查询名言
     * @param
     * @return
     */
    Saying selectAllById(Long id);

    /**
     * 添加名言
     * @param saying
     * @return
     */
     int saveSaying(Saying saying);

    /**
     * 查询名言
     * @return
     */
     List<Saying> querySayingListPage();


    /**
     * 删除名言
     * @return
     */

     int   removeSayingById(Long id);


    /**
     * 修改名言
     * @return
     */

     int     editSaying(Saying saying);


    /**
     * 获取名言数量
     * @return
     */
    int  selectSayingCount();

}
