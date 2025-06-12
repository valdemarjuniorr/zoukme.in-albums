package in.zoukme.zouk_album.services.aws.ses;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  private static final Logger log = LoggerFactory.getLogger(EmailService.class);
  private final MailSender mailSender;

  public EmailService(MailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void send(Email email) {
    var message = new SimpleMailMessage();
    log.info("Sending email: {}", email);
    message.setTo(email.to());
    message.setFrom(email.from());
    message.setSubject(email.subject());
    message.setText(email.body());
    mailSender.send(message);
    log.info("Email sent");
  }
}
