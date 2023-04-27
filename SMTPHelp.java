package com.bwzn.xssmg.help;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.util.StringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SMTPHelp {
    public static void Send(String email,String message){
        if (StringUtils.isEmpty(email)){
            return;
        }
        Properties properties = new Properties();

        properties.setProperty("mail.host","nsmtp.oppo.com");

        properties.setProperty("mail.transport.protocol","smtp");

        properties.setProperty("mail.smtp.auth","true");

        try {
            //QQ存在一个特性设置SSL加密
//            MailSSLSocketFactory sf = new MailSSLSocketFactory();
//            sf.setTrustAllHosts(true);
//            properties.put("mail.smtp.ssl.enable", "true");
//            properties.put("mail.smtp.ssl.socketFactory", sf);

            //创建一个session对象
            Session session = Session.getDefaultInstance(properties,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("Cabinet@oppo.com", "Tst#36446337");
                        }
                    });

            //开启debug模式
            session.setDebug(true);

            //获取连接对象
            Transport transport = session.getTransport();

            //连接服务器
            transport.connect("nsmtp.oppo.com", "Cabinet@oppo.com", "Tst#36446337");

            //创建邮件对象
            MimeMessage mimeMessage = new MimeMessage(session);

            //邮件发送人
            mimeMessage.setFrom(new InternetAddress("Cabinet@oppo.com"));

            //邮件接收人
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));

//            InternetAddress[] copyTo = InternetAddress.parse(cc);
//            //邮件抄送人
//            mimeMessage.setRecipients(Message.RecipientType.CC,copyTo);

            //邮件标题
            mimeMessage.setSubject("智能柜信息");

            //邮件内容
            mimeMessage.setContent(message, "text/html;charset=UTF-8");

            //发送邮件
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

            //关闭连接
            transport.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
