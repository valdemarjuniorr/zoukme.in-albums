package in.zoukme.zouk_album.controllers.festival;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Deprecated
@Controller
@RequestMapping("/festival/ranking")
public class FestivalController {

    @GetMapping
    String redirect() {
        var url = "http://ec2-15-228-250-57.sa-east-1.compute.amazonaws.com:8080/";
        return "redirect:" + url;
    }
}
