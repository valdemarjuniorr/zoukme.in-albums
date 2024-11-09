package in.zoukme.zouk_album.controllers;

import in.zoukme.zouk_album.services.MetricService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/metrics")
public class MetricController {

    private Logger log = LoggerFactory.getLogger(MetricController.class);

    private final MetricService service;

    public MetricController(MetricService service) {
        this.service = service;
    }

    @GetMapping("/visits")
    public String visits(Model model) {
        log.info("Showing visits metrics");
        model.addAttribute("visits", this.service.findVisitAlbumMetric());

        return "metric-visiters";
    }
}
