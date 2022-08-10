package com.mall.mail.send.service.impl;

import com.mall.api.maill.entity.MailInfo;
import com.mall.mail.send.dao.MailDao;
import com.mall.mail.send.service.SendService;
import com.mall.mail.send.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Component
public class SendServiceImpl implements SendService {
//    @Autowired
//    EmailUtil emailUtil;
//    @Resource
//    MailDao mailDao;
//
//    @Value("${spring.mail.subject}") // 从application.yml配置文件中获取
//    private String subject; // 发送发邮箱地址
//    @Override
//    public void insertMailSend(MailInfo info) {
//        info.setSubject(subject);
//        emailUtil.sendMessageCarryFiles(info.getMail(),subject,info.getContent(),info.getFile());
//        mailDao.insert(info);
//    }
}
