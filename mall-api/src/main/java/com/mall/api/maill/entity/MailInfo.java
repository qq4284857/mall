package com.mall.api.maill.entity;

import lombok.Data;

import java.io.File;
import java.util.Date;

@Data
public class MailInfo {
    /**
     * 主键ID
     */
    private String ID;

    /**
     * 用户名
     */
    private String useranme;

    /**
     * 标题
     */
    private String subject;


    /**
     * 邮箱
     */
    private String mail;

    /**
     * 发送内容
     */
    private String content;

    /**
     * 附件路径
     */
    private File[] file;

    /**
     * 发送时间
     */
    private Date sendTime;
}
