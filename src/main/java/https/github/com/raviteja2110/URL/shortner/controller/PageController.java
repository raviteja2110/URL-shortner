package https.github.com.raviteja2110.URL.shortner.controller;

import https.github.com.raviteja2110.URL.shortner.dto.UrlMapping;
import https.github.com.raviteja2110.URL.shortner.service.UrlShortenerService;
import https.github.com.raviteja2110.URL.shortner.util.AppConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for handling user-facing web pages.
 */
@Controller
public class PageController {
    private final UrlShortenerService urlShortenerService;

    public PageController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    /**
     * Displays the home page.
     *
     * @return The name of the index view.
     */
    @GetMapping("/")
    public String home(@RequestParam(required = false) String shortUrl, Model model) {
        return AppConstants.INDEX_VIEW;
    }

    /**
     * Displays the result page with details about a shortened URL.
     *
     * @param shortCode The short code of the URL to display.
     * @param model     The Spring UI model to pass data to the view.
     * @return The name of the result view.
     */
    @GetMapping("/result")
    public String resultPage(@RequestParam String shortCode, Model model) {;
        UrlMapping mapping = urlShortenerService.getMappingByShortCode(shortCode);
        model.addAttribute(AppConstants.SHORT_URL_ATTR, AppConstants.LOCAL_HOST_URL + mapping.getShortCode());
        model.addAttribute(AppConstants.LONG_URL_ATTR, mapping.getLongUrl());

        // Pass analytics data to the view
        model.addAttribute(AppConstants.CLICK_COUNT_ATTR, mapping.getClickCount());
        model.addAttribute(AppConstants.UNIQUE_VISITORS_ATTR, mapping.getUniqueVisitors().size());
        model.addAttribute(AppConstants.COUNTRIES_ATTR, mapping.getCountries().size());
        return AppConstants.RESULT_VIEW;
    }
}
