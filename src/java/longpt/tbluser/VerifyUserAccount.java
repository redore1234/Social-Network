/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.tbluser;

import java.io.Serializable;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author phamt
 */
public class VerifyUserAccount implements Serializable{

    private String email;
    private String password;

    public VerifyUserAccount() {
    }

    public VerifyUserAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void verifyEmail() throws AddressException, MessagingException {
        String emailSystem = "redore0208@gmail.com";
        String passwordSystem = "tl12on34";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailSystem, passwordSystem);
            }
        });

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(emailSystem));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); //email in here is recipient
        msg.setSubject("J3.L.P0010 Verification link");
        msg.setText("Your verification link: " + "http://localhost:8084/J3.L.P0010/DispatchController?btnAction=VerifyEmail&email=" + email + "&password=" + password);
        Transport.send(msg);
    }
}
