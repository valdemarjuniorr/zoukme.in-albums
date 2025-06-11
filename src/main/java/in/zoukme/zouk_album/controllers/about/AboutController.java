package in.zoukme.zouk_album.controllers.about;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/about")
public class AboutController {

    @GetMapping
    String about() {
        return "about/about";
    }
}
