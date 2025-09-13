package https.github.com.raviteja2110.URL.shortner.service;

import https.github.com.raviteja2110.URL.shortner.dto.GeoIpResponse;
import https.github.com.raviteja2110.URL.shortner.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service for fetching geolocation information for an IP address.
 */
@Service
public class GeoIpService {

    @Autowired
    RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(GeoIpService.class);

    /**
     * Gets the country code (e.g., "US", "IN") for a given IP address.
     *
     * @param ip The IP address to look up.
     * @return The two-letter country code, or a default value ("XX") if the lookup fails.
     */
    public String getCountry(String ip) {
        String url = AppConstants.GEO_IP_API_URL + ip;
        try {
            GeoIpResponse response = restTemplate.getForObject(url, GeoIpResponse.class);
            if (response != null && AppConstants.API_SUCCESS_STATUS.equals(response.getStatus())) {
                return response.getCountryCode();
            } else {
                logger.warn(AppConstants.GEO_IP_LOOKUP_WARN_MSG, ip, response != null ? response.getMessage() : AppConstants.UNKNOWN_API_ERROR_MSG);
                return AppConstants.DEFAULT_COUNTRY_CODE;
            }
        } catch (Exception e) {
            logger.warn(AppConstants.GEO_IP_LOOKUP_WARN_MSG, ip, e.getMessage());
            return AppConstants.DEFAULT_COUNTRY_CODE;
        }
    }
}
