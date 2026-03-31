package in.zoukme.zouk_album.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import in.zoukme.zouk_album.exceptions.AbstractUserException;

@ControllerAdvice
public class ExceptionHandlerConfig {

  @ExceptionHandler(AbstractUserException.class)
  public String handleException(AbstractUserException e, Model model) {
    model.addAttribute("message", e.getMessage());

    return "error-toast";
  }

  @ExceptionHandler(Exception.class)
  public String handleException(Exception e, Model model) {
    model.addAttribute("message", e.getMessage());

    return "error-toast";
  }

}
