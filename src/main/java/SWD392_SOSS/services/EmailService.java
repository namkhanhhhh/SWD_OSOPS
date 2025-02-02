package SWD392_SOSS.services;

import jakarta.mail.MessagingException;
import SWD392_SOSS.dtos.UserDto;
import SWD392_SOSS.entities.EmailDetails;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface EmailService {
    void sendSimpleMail(EmailDetails details, UserDto userDto) throws MessagingException, UnsupportedEncodingException;

    void sendEmail(String targetEnail, String link) throws MessagingException, UnsupportedEncodingException;
}
