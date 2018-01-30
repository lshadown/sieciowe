package mail.v1;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MailListener {
    private String host;
    private String userName;
    private String userPassword;
    private String  protocol;
    private String port;

    public MailListener(String host, String userName, String userPassword, String protocol, String port) {
        this.host = host;
        this.userName = userName;
        this.userPassword = userPassword;
        this.protocol = protocol;
        this.port = port;
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                try {
                    runListener();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, new Date(), 5000);


    }

    private void runListener() throws MessagingException {
        getNewEmails();
    }

    private Properties getServerProperties() {
        Properties properties = new Properties();
        properties.put(String.format("mail.%s.host",
                protocol), host);
        properties.put(String.format("mail.%s.port",
                protocol), port);
        properties.setProperty(
                String.format("mail.%s.socketFactory.class",
                        protocol), "javax.net.ssl.SSLSocketFactory");
        properties.setProperty(
                String.format("mail.%s.socketFactory.fallback",
                        protocol), "false");
        properties.setProperty(
                String.format("mail.%s.socketFactory.port",
                        protocol), String.valueOf(port));
        //properties.setProperty("mail.debug", "true");


        return properties;
    }

    public void getNewEmails() throws MessagingException {
        Properties properties = getServerProperties();
        Session session = Session.getDefaultInstance(properties);

        try {
            Store store = session.getStore(protocol);
            store.connect(userName, userPassword);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            Flags seen = new Flags(Flags.Flag.SEEN);
            FlagTerm unseenFlagTerm = new FlagTerm(seen, false);

            int count = inbox.getMessageCount();
            Message[] messages = inbox.search(unseenFlagTerm);
            for (Message message : messages) {

                    Address[] fromAddresses = message.getFrom();
                    System.out.println("...................");
                    System.out.println("\t From: "
                            + fromAddresses[0].toString());
                    System.out.println("\t To: "
                            + parseAddresses(message
                            .getRecipients(MimeMessage.RecipientType.TO)));
                    System.out.println("\t CC: "
                            + parseAddresses(message
                            .getRecipients(MimeMessage.RecipientType.CC)));
                    System.out.println("\t Subject: "
                            + message.getSubject());
                    System.out.println("\t Sent Date:"
                            + message.getSentDate().toString());
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                     try {
                        message.writeTo(out);
                        System.out.println(out.toString());
                    } catch (Exception ex) {
                        System.out.println("Error reading content!!");
                        ex.printStackTrace();
                    }
                inbox.setFlags(new Message[] {message}, new Flags(Flags.Flag.SEEN), true);
            }

            inbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for protocol: "
                    + protocol);
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        }
    }

    private String parseAddresses(Address[] address) {

        String listOfAddress = "";
        if ((address == null) || (address.length < 1))
            return null;
        if (!(address[0] instanceof InternetAddress))
            return null;

        for (int i = 0; i < address.length; i++) {
            InternetAddress internetAddress =
                    (InternetAddress) address[0];
            listOfAddress += internetAddress.getAddress()+",";
        }
        return listOfAddress;
    }
}
