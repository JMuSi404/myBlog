package com.heshijia.myblog.handler;

import com.heshijia.myblog.pojo.Blog;
import com.heshijia.myblog.service.BlogService;
import com.heshijia.myblog.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ListenHandler {

    private final BlogService blogService;
    private final RedisUtil redisUtil;

    private static final String VIEW_KEY = "views";

    @Autowired
    public ListenHandler(BlogService blogService, RedisUtil redisUtil) {
        this.blogService = blogService;
        this.redisUtil = redisUtil;
    }

    @PostConstruct
    public void init() throws Exception {
        log.info("数据初始化开始...");
        //将数据库中的数据写入redis
        List<Blog> blogList = blogService.list();
        blogList.forEach(blog -> {
            //将浏览量写入redis
            redisUtil.zAdd(VIEW_KEY, blog.getId().toString(), blog.getViews());
        });
        log.info("数据已写入redis...");
    }


    @Scheduled(cron = "*/15 * * * * ?")
    public void updateNum() {
        log.info("周期任务开始执行...");
        Set<ZSetOperations.TypedTuple<String>> viewNum = redisUtil.zReverseRangeWithScores(VIEW_KEY, 0, 10);
        writeNum(viewNum, VIEW_KEY);
        log.info("周期任务执行完毕,redis写入数据库完毕");
    }

    private void writeNum(Set<ZSetOperations.TypedTuple<String>> set, String fieldName) {
        set.forEach(item -> {
            Long id = Long.valueOf(item.getValue());
            Integer num = item.getScore().intValue();
            Blog blog = blogService.getById(id);
            //更新数据库
            if (blog!=null){
                blog.setViews(num);
                blogService.updateNumById(blog);
            }
            log.info("{} 更新完毕", fieldName);
        });
    }


    /**
     * 关闭时操作
      */
     @PreDestroy
     public void afterDestroy()  {
     log.info("开始关闭...");
     //将redis中的数据写入数据库
     Set<ZSetOperations.TypedTuple<String>> viewNum = redisUtil.zReverseRangeWithScores(VIEW_KEY, 0, 10);
     writeNum(viewNum, VIEW_KEY);
     log.info("redis写入数据库完毕");
     }

}