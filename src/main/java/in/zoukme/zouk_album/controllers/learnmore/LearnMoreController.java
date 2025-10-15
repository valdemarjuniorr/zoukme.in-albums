package in.zoukme.zouk_album.controllers.learnmore;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.zoukme.zouk_album.controllers.admin.dashboard.DashboardService;

@Controller
@RequestMapping("/learnmore")
public class LearnMoreController {

  private final DashboardService dashboardService;

  public LearnMoreController(DashboardService dashboardService) {
    this.dashboardService = dashboardService;
  }

  @GetMapping
  public String learnMore(Model model) {

    model.addAttribute("albumCount", dashboardService.getTotalAlbums());
    model.addAttribute("photoCount", dashboardService.getTotalPhotos());
    model.addAttribute("cityCount", dashboardService.getDistinctCitiesCount());
    return "learn-more/learn-more";
  }
}
