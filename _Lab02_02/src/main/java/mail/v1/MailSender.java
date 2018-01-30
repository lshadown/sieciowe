package mail.v1;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
    private String smtpHost;
    private String userName;
    private String userPassword;

    public MailSender(String smtpHost, String userName, String userPassword) {
        this.smtpHost = smtpHost;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public void sendMessage(String from, String to, String subject, String txt)
            throws MessagingException {
        Properties props = System.getProperties();
        props.put("mail.smtp.user", this.userName);
        props.put("mail.smtp.password", this.userPassword);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", this.smtpHost);
        props.put("mail.smtp.ssl.enable", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.debug", "true");


        Session session = Session.getInstance(props, new MailAuthenticator(this.userName, this.userPassword));
        Message msg = new MimeMessage(session);
        msg.setSubject(subject);
        msg.setFrom(new InternetAddress(from));
        msg.addRecipient(Message.RecipientType.TO,
                new InternetAddress(to));
        msg.setContent(txt, "text/plain");

        msg.saveChanges();
        Transport.send(msg);
    }
}
