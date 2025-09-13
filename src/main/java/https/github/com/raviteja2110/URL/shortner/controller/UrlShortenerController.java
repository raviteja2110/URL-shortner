package https.github.com.raviteja2110.url.shortner.controller;

import https.github.com.raviteja2110.url.shortner.service.UrlShortenerService;
import https.github.com.raviteja2110.url.shortner.util.AppConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller for handling URL shortening and redirection logic.
 */
@Controller
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam("longUrl") String longUrl) {
        String shortCode = urlShortenerService.shortenUrl(longUrl);
        return AppConstants.REDIRECT_TO_RESULT + shortCode;
    }

    @GetMapping("/{shortCode}")
    public RedirectView redirect(@PathVariable String shortCode, HttpServletRequest request) {
        String visitorIp = request.getRemoteAddr();
        String originalUrl = urlShortenerService.getOriginalUrl(shortCode, visitorIp);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(originalUrl);
        return redirectView;
    }
}