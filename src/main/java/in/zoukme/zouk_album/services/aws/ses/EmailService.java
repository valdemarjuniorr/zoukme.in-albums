package in.zoukme.zouk_album.services.aws.ses;

import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

  private static final Logger log = LoggerFactory.getLogger(EmailService.class);
  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;

  public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
    this.mailSender = mailSender;
    this.templateEngine = templateEngine;
  }

  public void send(Email email) {
    var message = new SimpleMailMessage();
    log.info("Sending email: {}", email);
    message.setTo(email.to());
    message.setFrom("contato@zoukme.in");
    message.setSubject(email.subject());
    message.setText(email.body());
    mailSender.send(message);
    log.info("Email sent");
  }

  public void send(EmailTemplate email) {
    log.info("Sending email template: {}", email.getEmail());
    var message = mailSender.createMimeMessage();
    var helper = new MimeMessageHelper(message);
    var context = new Context();
    context.setVariables(email.getTemplateVariables());
    var htmlContent = templateEngine.process(email.getTemplateName(), context);
    try {
      helper.setTo(email.getEmail().to());
      helper.setFrom(email.getEmail().from());
      helper.setSubject(email.getEmail().subject());
      helper.setText(htmlContent, Boolean.TRUE);

      mailSender.send(message);
      log.info("Email sent");
    } catch (MessagingException | RuntimeException e) {
      log.error("Error creating email message", e);
    }
  }
}
