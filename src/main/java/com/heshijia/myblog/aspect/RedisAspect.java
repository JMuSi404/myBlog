package com.heshijia.myblog.aspect;


import com.heshijia.myblog.service.BlogService;
import com.heshijia.myblog.utils.RedisUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * 通过aop用redis更新浏览量
 */
@Aspect
@Component
public class RedisAspect {
    private final BlogService blogService;
    private final RedisUtil redisUtil;

    public RedisAspect(BlogService blogService, RedisUtil redisUtil) {
        this.blogService = blogService;
        this.redisUtil = redisUtil;
    }

    @Pointcut("execution(public * com.heshijia.myblog.service.impl.BlogServiceImpl.queryBlogDetailsById(..))")
    public void myPointCut(){}

    //在这个方法执行后
    @After("myPointCut()")
    public void doAfter(JoinPoint joinPoint) throws Throwable {
        Object[] objs=joinPoint.getArgs();
        Long id=(Long) objs[0];
        //根据id更新浏览量
        redisUtil.zIncrementScore("views",id.toString(),1);
    }
}