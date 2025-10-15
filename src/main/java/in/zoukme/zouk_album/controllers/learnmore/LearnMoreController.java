package in.zoukme.zouk_album.controllers.learnmore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/learnmore")
public class LearnMoreController {

  @GetMapping
  public String learnMore() {
    return "learn-more/learn-more";
  }
}
