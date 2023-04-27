package com.bwzn.xssmg.help;

import com.sun.mail.util.MailSSLSocketFactory;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import org.springframework.util.StringUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class ExchangeHelp {
    //private final Logger logger  = LoggerFactory.getLogger(this.getClass());

    private final static String username="Cabinet@oppo.com";

    private final static String password="Tst#36446337";

    //private final static String host="mail.email.myoas.com" ;
    
    private final static String mailServer="nsmtp.oppo.com";


//    public static void Send(String email,String message){
////        if (StringUtils.isEmpty(email)){
////            return;
////        }
////        Properties properties = new Properties();
////
////        properties.setProperty("mail.pop3.host",host);
////
////        properties.setProperty("mail.transport.protocol","pop");
////
////        properties.setProperty("mail.smtp.auth","true");
////
////        try {
////            //QQ存在一个特性设置SSL加密
////            MailSSLSocketFactory sf = new MailSSLSocketFactory();
////            sf.setTrustAllHosts(true);
////            properties.put("mail.pop.ssl.enable", "true");
////            properties.put("mail.pop.ssl.socketFactory", sf);
////
////            //创建一个session对象
////            Session session = Session.getDefaultInstance(properties,
////                    new Authenticator() {
////                        @Override
////                        protected PasswordAuthentication getPasswordAuthentication() {
////                            return new PasswordAuthentication(username, password);
////                        }
////                    });
////
////            //开启debug模式
////            session.setDebug(true);
////
////            //获取连接对象
////            Transport transport = session.getTransport();
////
////            //连接服务器
////            transport.connect(host, username, password);
////
////            //创建邮件对象
////            MimeMessage mimeMessage = new MimeMessage(session);
////
////            //邮件发送人
////            mimeMessage.setFrom(new InternetAddress(username));
////
////            //邮件接收人
////            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
////
////            //邮件标题
////            mimeMessage.setSubject("智能柜信息");
////
////            //邮件内容
////            mimeMessage.setContent(message, "text/html;charset=UTF-8");
////
////            //发送邮件
////            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
////
////            //关闭连接
////            transport.close();
////        }catch (Exception e){
////            System.out.println(e.getMessage());
////            e.printStackTrace();
////        }
//        boolean isOK=false;
//        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2007_SP1);
//        ExchangeCredentials credentials = new WebCredentials(username,password);
//        service.setCredentials(credentials);
//        try {
//            service.setUrl(new URI(host));
//            EmailMessage msg = new EmailMessage(service);
//            msg.setSubject("智能柜信息");
//            MessageBody body = MessageBody.getMessageBodyFromText(message);
//            body.setBodyType(BodyType.Text);
//            msg.setBody(body);
//            //支持多个收件人
//            InternetAddress[] addresses = InternetAddress.parse(email);
//            for (InternetAddress address : addresses) {
//                msg.getToRecipients().add(address.getAddress());
//            }
//
//            msg.send();
//            isOK=true;
//        } catch (Exception e) {
//            System.out.println("发送的错误信息为："+e.getMessage());
//            isOK= false;
//        }
//        //return isOK;
//
//    }

    public static void Send(String email,String message) throws URISyntaxException {
         ExchangeService service=new ExchangeService(ExchangeVersion.Exchange2007_SP1);
         ExchangeCredentials credentials=new WebCredentials(username,password);
         service.setCredentials(credentials);
         try{
             //service.autodiscoverUrl(mailServer);
             service.setUrl(new URI(mailServer));
             EmailMessage msg=new EmailMessage(service);
             msg.setSubject("智能柜信息");
             MessageBody body=MessageBody.getMessageBodyFromText(message);
             body.setBodyType(BodyType.HTML);
             msg.setBody(body);
             msg.getToRecipients().add(email);
             msg.save();
             msg.sendAndSaveCopy();

         }catch (Exception e){

             System.out.println(e.getMessage());
         }


    }

}
