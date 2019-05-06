package email;

import model.User;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

public class Email {
    private final String username = "TemusOrigami@gmail.com";
    private final String password = "ZP()PS\\/\\/Utp@";
    private User user;
    private String text;

    public Email(User user){
        this.user = user;
    }

    public void createText() {
        this.text = "Hello, You have just created new account... \n\n" +
                "Your data: \n" +
                "Imię: " + user.getImie() + "\nNazwisko: " + user.getNazwisko() +
                "\nLogin: " + user.getLogin() + "\nHasło: " + user.getHaslo() +
                "\nData rejestracji: " + user.getData_rejestracji() +
                "\n\nSince You have not created this account, please ignore this mail...";
    }

    public void sendEmail() {
        createText();

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail()));
            message.setSubject("Registration Complete");
            message.setText(text);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}