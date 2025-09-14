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
        String fullShortUrl = urlShortenerService.shortenUrl(longUrl);
        // Extract only the short code from the full URL for the redirect parameter.
        String shortCode = fullShortUrl.substring(fullShortUrl.lastIndexOf('/') + 1);
        return AppConstants.REDIRECT_TO_RESULT + shortCode;
    }

    @GetMapping("/{shortCode}")
    public RedirectView redirect(@PathVariable String shortCode, HttpServletRequest request) {
        String visitorIp = getClientIp(request);
        String originalUrl = urlShortenerService.getOriginalUrl(shortCode, visitorIp);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(originalUrl);
        return redirectView;
    }

    /**
     * Extracts the client's real IP address from the request, checking for common proxy headers first.
     * This is crucial for accurate geolocation when deployed behind a reverse proxy (e.g., on Render).
     *
     * @param request The incoming HTTP request.
     * @return The client's IP address.
     */
    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress.split(",")[0].trim();
    }
}