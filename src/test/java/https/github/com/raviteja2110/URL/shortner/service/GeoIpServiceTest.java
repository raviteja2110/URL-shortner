package https.github.com.raviteja2110.url.shortner.service;

import https.github.com.raviteja2110.url.shortner.dto.GeoIpResponse;
import https.github.com.raviteja2110.url.shortner.util.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeoIpServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private https.github.com.raviteja2110.url.shortner.service.GeoIpService geoIpService;

    private final String testApiUrl = "http://test-ip-api.com/json/";

    @BeforeEach
    void setUp() {
        // Use ReflectionTestUtils to set the @Value-injected field for the test
        ReflectionTestUtils.setField(geoIpService, "geoIpApiUrl", testApiUrl);
    }

    @Test
    void getCountry_success() {
        String ipAddress = "8.8.8.8";
        GeoIpResponse mockResponse = new GeoIpResponse();
        mockResponse.setStatus(AppConstants.API_SUCCESS_STATUS);
        mockResponse.setCountryCode("US");

        when(restTemplate.getForObject(testApiUrl + ipAddress, GeoIpResponse.class))
                .thenReturn(mockResponse);

        String country = geoIpService.getCountry(ipAddress);

        assertEquals("US", country);
    }

    @Test
    void getCountry_failure() {
        String ipAddress = "127.0.0.1"; // private range IP will cause a 'fail' status

        String country = geoIpService.getCountry(ipAddress);

        assertEquals(AppConstants.DEFAULT_COUNTRY_CODE, country);
    }

    @Test
    void getCountry_exception() {
        String ipAddress = "8.8.8.8";
        // Simulate a network or API error
        when(restTemplate.getForObject(testApiUrl + ipAddress, GeoIpResponse.class))
                .thenThrow(new RestClientException("API is down"));

        String country = geoIpService.getCountry(ipAddress);

        assertEquals(AppConstants.DEFAULT_COUNTRY_CODE, country);
    }
}
