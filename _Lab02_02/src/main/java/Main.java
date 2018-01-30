import mail.v1.MailListener;
import mail.v1.MailSender;

import javax.mail.MessagingException;

public class Main {

    public static void main(String[] args) throws MessagingException {
        MailSender mailSender = new MailSender("smtp.gmail.com","gruzewski94", "powidz676010polanow");
        mailSender.sendMessage("programowanie-sieciowe@wp.pl","gruzewski.mateusz@outlook.com", "Test", "Testy");
        /*MailListener mailListener =new MailListener("imap.gmail.com","gruzewski94","powidz676010polanow", "imap","993");
*/
    }
}
