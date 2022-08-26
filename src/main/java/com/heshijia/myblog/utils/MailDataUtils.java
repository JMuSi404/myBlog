package com.heshijia.myblog.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailDataUtils implements InitializingBean {
    @Value ("${myblog_mail.domain_name}") //域名
    private String domain_name;

    @Value ("${myblog_mail.mail_sender}") //邮件发送者
    private String mail_sender;


    @Value ("${myblog_mail.mail_cc}") //邮件抄送 所有邮件都抄送一份到抄送邮箱
    private String mail_cc;

    @Value ("${myblog_mail.mail_title}") //邮件标题
    private String mail_title;

   public static String DOMAIN_NAME;
    public static String MAIL_SENDER;
    public static String MAIL_CC;
    public static String MAIL_TITLE;
    @Override
    public void afterPropertiesSet ( ) throws Exception {
        DOMAIN_NAME=domain_name;
        MAIL_SENDER=mail_sender;
        MAIL_CC=mail_cc;
        MAIL_TITLE=mail_title;
    }
}
