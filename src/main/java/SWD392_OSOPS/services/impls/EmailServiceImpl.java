package SWD392_OSOPS.services.impls;

import SWD392_OSOPS.dtos.UserDto;
import SWD392_OSOPS.entities.EmailDetails;
import SWD392_OSOPS.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendSimpleMail(EmailDetails details, UserDto userDto) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("LKHT@gmail.com", "LKHT Support");
        helper.setTo(details.getRecipient());

        String subject = details.getSubject();

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Your account is:\"" + userDto.getUsername() + "\"</p>"
                + "<p>Your password is:\"" + userDto.getRepeatPassword() + "\"</p>"
                + "<br>"
                + "<p>Please don't share this to anyone else! ";

        helper.setSubject(subject);

        helper.setText(content, true);

        javaMailSender.send(message);
    }
}
