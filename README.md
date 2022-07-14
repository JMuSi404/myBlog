# Joeblog
个人博客系统 SpringBoot+Mybatisplus
\
博客地址：https://hsjhome.top
## 技术栈
**1.前端**
 - JS框架：JQuery
 - 滚动监听插件:jQueryWaypoints
 - UI框架：[semantic-ui](https://semantic-ui.com/),[Layui](https://layuion.com/docs/)
 - Markdown编辑器:[editor.md](https://pandao.github.io/editor.md/)
 - 动画样式:[animate](https://animate.style/)
 - 文章目录:[tocbot](https://tscanlin.github.io/tocbot/)
 - 代码高亮:[prismjs](https://prismjs.com/)
 - 音乐盒子:[aplayer](https://aplayer.js.org/#/)
 
**2.后端**
 - 核心框架:SpringBoot 2.7.0+MybatisPlus
 - 数据库:mysql、redis
 - 项目构建:jdk1.8、maven 3
 - 模板引擎:thymeleaf
 - 分页插件:PageHelper
 - 登录加密:MD5
 - 开发工具:IDEA  
 - 运行环境:腾讯云 Centos7 轻量应用服务器2核2G
 

## 简介
*因为是个人博客,不需要做用户权限管理,就只有管理员用户,**关于我**和**友链**复用了文章详情页*
\
**博客主页功能**
 - 查看文章信息：文章列表、文章标题、文章内容、发布时间、访问量以及评论等信息
 - 查看标签文章：标签列表、标签文章信息
 - 查看归档：按照文章时间发布顺序查看文章
 - 搜索文章：导航栏右边搜索框根据关键字搜索
 - 音乐播放：上一曲、下一曲、音量控制、播放顺序控制、查看歌词等
 - 评论：评论并回复
 - 查看友链：查看并访问博主在友链页面添加的友链连接

**后台管理**
 - 文章管理:查询文章列表、新增文章、编辑文章、删除文章、搜索文章
 - 标签管理:查询标签列表、新增标签、删除标签
 - 评论管理:查询文章评论列表、删除评论

## 数据库设计
博客详情表：t_blog 
\
标签表：t_tag
\
用户数据表：t_user
\
评论表：t_comment
\
博客标签关联表:t_blog_tag_relation
\
**表sql文件也上传了**
