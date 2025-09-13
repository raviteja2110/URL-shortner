package https.github.com.raviteja2110.url.shortner.controller;

import com.google.zxing.WriterException;
import https.github.com.raviteja2110.url.shortner.config.AppProperties;
import https.github.com.raviteja2110.url.shortner.dto.UrlMapping;
import https.github.com.raviteja2110.url.shortner.service.QrCodeService;
import https.github.com.raviteja2110.url.shortner.service.UrlShortenerService;
import https.github.com.raviteja2110.url.shortner.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Controller for handling user-facing web pages.
 */
@Controller
public class PageController {
    private static final Logger logger = LoggerFactory.getLogger(PageController.class);
    private final UrlShortenerService urlShortenerService;
    private final QrCodeService qrCodeService;
    private final AppProperties appProperties;

    public PageController(UrlShortenerService urlShortenerService, QrCodeService qrCodeService, AppProperties appProperties) {
        this.urlShortenerService = urlShortenerService;
        this.qrCodeService = qrCodeService;
        this.appProperties = appProperties;
    }

    /**
     * Displays the home page.
     *
     * @return The name of the index view.
     */
    @GetMapping("/")
    public String home() {
        return AppConstants.INDEX_VIEW;
    }

    /**
     * Displays the result page with details about a shortened URL and a generated QR code.
     *
     * @param shortCode The short code of the URL to display.
     * @param model     The Spring UI model to pass data to the view.
     * @return The name of the result view.
     */
    @GetMapping("/result")
    public String resultPage(@RequestParam String shortCode, Model model) {
        UrlMapping mapping = urlShortenerService.getMappingByShortCode(shortCode);
        String shortUrl = appProperties.getBaseUrl() + "/" + mapping.getShortCode();

        model.addAttribute(AppConstants.SHORT_URL_ATTR, shortUrl);
        model.addAttribute(AppConstants.LONG_URL_ATTR, mapping.getLongUrl());

        // Pass analytics data to the view
        model.addAttribute(AppConstants.CLICK_COUNT_ATTR, mapping.getClickCount());
        model.addAttribute(AppConstants.UNIQUE_VISITORS_ATTR, mapping.getUniqueVisitors().size());
        model.addAttribute(AppConstants.COUNTRIES_ATTR, mapping.getCountries().size());

        // Generate and pass QR code image data
        try {
            String qrCodeImage = qrCodeService.generateQrCodeImage(shortUrl);
            model.addAttribute(AppConstants.QR_CODE_ATTR, qrCodeImage);
        } catch (WriterException | IOException e) {
            logger.error("Failed to generate QR code for URL: {}", shortUrl, e);
            model.addAttribute(AppConstants.QR_CODE_ATTR, ""); // Pass empty string on failure
        }

        return AppConstants.RESULT_VIEW;
    }
}
