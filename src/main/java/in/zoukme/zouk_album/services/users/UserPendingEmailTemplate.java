package in.zoukme.zouk_album.services.users;

import java.util.Map;

import in.zoukme.zouk_album.services.aws.ses.Email;
import in.zoukme.zouk_album.services.aws.ses.EmailTemplate;

class UserPendingEmailTemplate implements EmailTemplate {

  private final String userEmail;
  private final String token;

  public UserPendingEmailTemplate(String userEmail, String token) {
    this.userEmail = userEmail;
    this.token = token;
  }

  @Override
  public Email getEmail() {
    return new Email(userEmail, "contato@zoukme.in", "Zoukme In - Confirmação de cadastro", null);
  }

  @Override
  public String getTemplateName() {
    return "/email/user-pending-confirmation";
  }

  @Override
  public Map<String, Object> getTemplateVariables() {
    return Map.of("token", token);
  }
}
