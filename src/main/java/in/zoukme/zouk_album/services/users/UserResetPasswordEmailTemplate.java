package in.zoukme.zouk_album.services.users;

import java.util.Map;

import in.zoukme.zouk_album.services.aws.ses.Email;
import in.zoukme.zouk_album.services.aws.ses.EmailTemplate;

public class UserResetPasswordEmailTemplate implements EmailTemplate {

  private final String email;
  private final String token;
  private final String fullName;

  public UserResetPasswordEmailTemplate(String fullName, String email, String token) {
    this.email = email;
    this.token = token;
    this.fullName = fullName;
  }

  @Override
  public String getTemplateName() {
    return "/email/user-redefine-password";
  }

  @Override
  public Email getEmail() {
    return new Email(this.email, "contato@zoukme.in", "Zoukme In - Solicitação de redefinição de senha", null);
  }

  @Override
  public Map<String, Object> getTemplateVariables() {
    return Map.of("token", this.token, "fullName", this.fullName);
  }
}
