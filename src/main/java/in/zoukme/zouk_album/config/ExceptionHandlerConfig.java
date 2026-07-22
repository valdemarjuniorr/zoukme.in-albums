package in.zoukme.zouk_album.config;

import in.zoukme.zouk_album.exceptions.AbstractUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ExceptionHandlerConfig {

  private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerConfig.class);

  @ExceptionHandler(AbstractUserException.class)
  public String handleException(AbstractUserException e, Model model) {
    log.error("User exception occurred: {}", e.getMessage());
    model.addAttribute("message", e.getMessage());

    return "error-toast";
  }

  @ExceptionHandler(Exception.class)
  public String handleException(Exception e, Model model) {
    model.addAttribute("message", e.getMessage());

    return "error-toast";
  }

  @ExceptionHandler(RuntimeException.class)
  public String handleRuntimeException(RuntimeException e, Model model) {
    log.error("RuntimeException occurred: {}", e);

    return "error";
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public String handleResourceNotFound(NoResourceFoundException ex, Model model) {
    // Optional: Pass the missing resource path to your Thymeleaf template
    model.addAttribute("message", ex.getResourcePath());

    return "404";
  }
}
