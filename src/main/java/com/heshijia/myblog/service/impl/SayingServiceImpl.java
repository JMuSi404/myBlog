package com.heshijia.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heshijia.myblog.pojo.Saying;
import com.heshijia.myblog.service.SayingService;
import com.heshijia.myblog.mapper.SayingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【t_saying】的数据库操作Service实现
* @createDate 2022-05-27 09:37:16
*/
/**
 * 废用了,找到了更好的api接口
 */
@Service
public class SayingServiceImpl extends ServiceImpl<SayingMapper, Saying>
    implements SayingService{
@Autowired
private   SayingMapper sayingMapper;


    @Override
    public List<Saying> queryByName (String sayingName) {
        return sayingMapper.selectAllByName(sayingName);
    }

    @Override
    public List<Saying> selectAllBySerialnumber (Integer Serialnumber) {
        return sayingMapper.selectAllBySerialnumber(Serialnumber);
    }

    @Override
    public Saying selectBySerialnumber (Integer Serialnumber) {
        return sayingMapper.selectBySerialnumber(Serialnumber);
    }

    @Override
    public Saying selectAllById (Long id) {
        return sayingMapper.selectById(id);
    }

    @Override
    public int saveSaying (Saying saying) {
        return sayingMapper.insert(saying);
    }

    @Override
    public List<Saying> querySayingListPage ( ) {
        return sayingMapper.selectList(null);
    }

    @Override
    public int removeSayingById (Long id) {
        return sayingMapper.deleteById(id);
    }

    @Override
    public int editSaying (Saying saying) {
        return sayingMapper.updateById(saying);
    }

    @Override
    public int selectSayingCount ( ) {
        return sayingMapper.selectCount(null);
    }
}




