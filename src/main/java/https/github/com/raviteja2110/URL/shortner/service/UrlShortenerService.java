package https.github.com.raviteja2110.url.shortner.service;

import https.github.com.raviteja2110.url.shortner.config.AppProperties;
import https.github.com.raviteja2110.url.shortner.dto.UrlMapping;
import https.github.com.raviteja2110.url.shortner.exception.UrlNotFoundException;
import https.github.com.raviteja2110.url.shortner.repo.UrlMappingRepository;
import https.github.com.raviteja2110.url.shortner.util.AppConstants;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class containing the core business logic for URL shortening and analytics.
 */
@Service
@RequiredArgsConstructor
public class UrlShortenerService {
    private final UrlMappingRepository repository;
    private final GeoIpService geoIpService;
    private final AppProperties appProperties;
    private static final int SHORT_CODE_LENGTH = 6;

    /**
     * Shortens a long URL. If the URL has already been shortened, it returns the existing short code.
     * Otherwise, it generates a new short code, saves it, and returns it.
     *
     * @param longUrl The original long URL.
     * @return The full shortened URL.
     */
    public String shortenUrl(String longUrl) {
        // First, check if a mapping for this long URL already exists.
        List<UrlMapping> existingMappings = repository.findByLongUrl(longUrl);
        if (!existingMappings.isEmpty()) {
            return constructFullShortUrl(existingMappings.getFirst().getShortCode());
        }

        String shortCode;
        do {
            shortCode = RandomStringUtils.randomAlphanumeric(SHORT_CODE_LENGTH);
        } while (repository.findByShortCode(shortCode).isPresent());

        UrlMapping mapping = new UrlMapping();
        mapping.setLongUrl(longUrl);
        mapping.setShortCode(shortCode);
        mapping.setClickCount(0);
        mapping.setCreatedAt(LocalDateTime.now());
        
        repository.save(mapping);
        return constructFullShortUrl(shortCode);
    }

    /**
     * Retrieves the original long URL for a given short code and tracks the click event.
     *
     * @param shortCode The short code of the URL.
     * @param visitorId The IP address of the visitor.
     * @return The original long URL.
     * @throws UrlNotFoundException if the short code does not exist.
     */
    public String getOriginalUrl(String shortCode, String visitorId) {
        UrlMapping mapping = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException(AppConstants.URL_NOT_FOUND_MSG));
        
        mapping.setClickCount(mapping.getClickCount() + 1);
        mapping.getUniqueVisitors().add(visitorId);
        String country = geoIpService.getCountry(visitorId);
        if (!AppConstants.DEFAULT_COUNTRY_CODE.equals(country)) {
            mapping.getCountries().add(country);
        }

        repository.save(mapping);
        
        return mapping.getLongUrl();
    }

    /**
     * Retrieves the UrlMapping object for a given short code.
     *
     * @param shortCode The short code to look up.
     * @return The corresponding UrlMapping object.
     * @throws UrlNotFoundException if the short code does not exist.
     */
    public UrlMapping getMappingByShortCode(String shortCode) {
        return repository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException(AppConstants.URL_NOT_FOUND_FOR_CODE_MSG + shortCode));
    }

    private String constructFullShortUrl(String shortCode) {
        return appProperties.getBaseUrl() + "/" + shortCode;
    }
}