package https.github.com.raviteja2110.url.shortner.service;

import https.github.com.raviteja2110.url.shortner.dto.GeoIpResponse;
import https.github.com.raviteja2110.url.shortner.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Service for looking up the geographical location of an IP address.
 */
@Service
public class GeoIpService {

    private static final Logger logger = LoggerFactory.getLogger(GeoIpService.class);
    private final RestTemplate restTemplate;

    @Value("${app.geoip.api.url}")
    private String geoIpApiUrl;

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
        try {
            // For local development, localhost/loopback addresses won't resolve to a country.
            InetAddress address = InetAddress.getByName(ipAddress);
            if (address.isLoopbackAddress()) {
                return AppConstants.DEFAULT_COUNTRY_CODE;
            }
        } catch (UnknownHostException e) {
            logger.warn("Invalid IP address format: {}", ipAddress);
            return AppConstants.DEFAULT_COUNTRY_CODE; // Return default for invalid IPs
        }

        String url = geoIpApiUrl + ipAddress;
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