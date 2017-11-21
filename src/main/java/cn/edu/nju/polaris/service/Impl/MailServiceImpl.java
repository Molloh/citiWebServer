package cn.edu.nju.polaris.service.Impl;

import cn.edu.nju.polaris.service.MailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService{

    private final JavaMailSender mailSender;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void test(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("809739692@qq.com");
        mailMessage.setTo("809739692@qq.com");
        mailMessage.setSubject("主题：简单邮件");
        mailMessage.setText("测试内容");
        mailSender.send(mailMessage);
    }

}
