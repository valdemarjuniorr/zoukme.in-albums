package in.zoukme.zouk_album.controllers.prices;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prices")
public class PriceController {

  @GetMapping
  String pricePage() {
    return "prices/prices";
  }
}
