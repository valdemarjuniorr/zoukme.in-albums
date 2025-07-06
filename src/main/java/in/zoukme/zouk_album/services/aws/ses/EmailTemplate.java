package in.zoukme.zouk_album.services.aws.ses;

import java.util.Map;

public interface EmailTemplate {

  /**
   * Returns the email object associated with this template.
   *
   * @return the email object
   */
  Email getEmail();

  /**
   * Returns the name of the email template. The template must be located in template folder
   *
   * @return the template name
   */
  String getTemplateName();

  /**
   * Returns a map of variables to be used in the email template. The variables in the template must
   * be in [[${variableName}]] format.
   *
   * @return a map containing template variables
   */
  Map<String, Object> getTemplateVariables();
}
