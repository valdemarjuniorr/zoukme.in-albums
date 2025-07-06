package in.zoukme.zouk_album.services.aws.ses;

public record Email(String to, String from, String subject, String body) {

  public static Email newTemplateEmail(String to, String subject) {
    return new Email(to, "contato@zoukme.in", subject, null);
  }
}
