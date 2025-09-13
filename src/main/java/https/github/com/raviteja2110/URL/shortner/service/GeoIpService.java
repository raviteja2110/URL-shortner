package https.github.com.raviteja2110.url.shortner.service;

import https.github.com.raviteja2110.url.shortner.dto.GeoIpResponse;
import https.github.com.raviteja2110.url.shortner.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Service for looking up the geographical location of an IP address.
 */
@Service
public class GeoIpService {

    private static final Logger logger = LoggerFactory.getLogger(GeoIpService.class);
    private final RestTemplate restTemplate;

    public GeoIpService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieves the country code for a given IP address.
     *
     * @param ipAddress The IP address to look up.
     * @return The two-letter country code, or a default value if the lookup fails.
     */
    public String getCountry(String ipAddress) {
        // For local development, "127.0.0.1" or "0:0:0:0:0:0:0:1" won't resolve to a country.
        if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
            return AppConstants.DEFAULT_COUNTRY_CODE;
        }

        String url = AppConstants.GEO_IP_API_URL + ipAddress;
        try {
            GeoIpResponse response = restTemplate.getForObject(url, GeoIpResponse.class);
            if (response != null && AppConstants.API_SUCCESS_STATUS.equals(response.getStatus())) {
                return response.getCountryCode();
            } else {
                logger.warn(AppConstants.GEO_IP_LOOKUP_WARN_MSG, ipAddress, response != null ? response.getMessage() : AppConstants.UNKNOWN_API_ERROR_MSG);
            }
        } catch (RestClientException e) {
            logger.error("Error calling GeoIP service for IP {}: {}", ipAddress, e.getMessage());
        }
        return AppConstants.DEFAULT_COUNTRY_CODE;
    }
}