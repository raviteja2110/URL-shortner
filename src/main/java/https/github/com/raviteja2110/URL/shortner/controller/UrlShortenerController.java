package https.github.com.raviteja2110.URL.shortner.controller;

import https.github.com.raviteja2110.URL.shortner.service.UrlShortenerService;
import https.github.com.raviteja2110.URL.shortner.util.AppConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;

/**
 * Controller for handling URL shortening and redirection.
 */
@Controller
@RequiredArgsConstructor
public class UrlShortenerController {
    private final UrlShortenerService service;

    /**
     * Creates a short URL for the given long URL and redirects to the result page.
     *
     * @param longUrl The original long URL to shorten.
     * @return A redirect view to the result page, displaying the new short code.
     */
    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String longUrl) {
        String shortCode = service.shortenUrl(longUrl);
        return AppConstants.REDIRECT_TO_RESULT + shortCode;
    }

    /**
     * Redirects a short code to its original long URL and tracks the click.
     *
     * @param shortCode The short code to redirect.
     * @param request   The incoming HTTP request, used to get the visitor's IP address.
     * @return A ResponseEntity that performs an HTTP 302 redirect to the original URL.
     */
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode, HttpServletRequest request) {
        String originalUrl = service.getOriginalUrl(shortCode, request.getRemoteAddr());
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
