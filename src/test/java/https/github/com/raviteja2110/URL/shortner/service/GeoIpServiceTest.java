package https.github.com.raviteja2110.URL.shortner.service;

import https.github.com.raviteja2110.URL.shortner.dto.GeoIpResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeoIpServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private https.github.com.raviteja2110.URL.shortner.service.GeoIpService geoIpService;

    @Test
    void getCountry_success() {
        GeoIpResponse geoIpResponse = new GeoIpResponse();
        geoIpResponse.setStatus("success");
        geoIpResponse.setCountryCode("US");

        when(restTemplate.getForObject("http://ip-api.com/json/127.0.0.1", GeoIpResponse.class))
                .thenReturn(geoIpResponse);

        String country = geoIpService.getCountry("127.0.0.1");

        assertEquals("US", country);
    }

    @Test
    void getCountry_failure() {
        GeoIpResponse geoIpResponse = new GeoIpResponse();
        geoIpResponse.setStatus("fail");

        when(restTemplate.getForObject("http://ip-api.com/json/127.0.0.1", GeoIpResponse.class))
                .thenReturn(geoIpResponse);

        String country = geoIpService.getCountry("127.0.0.1");

        assertEquals("XX", country);
    }

    @Test
    void getCountry_exception() {
        when(restTemplate.getForObject("http://ip-api.com/json/127.0.0.1", GeoIpResponse.class))
                .thenThrow(new RuntimeException("API is down"));

        String country = geoIpService.getCountry("127.0.0.1");

        assertEquals("XX", country);
    }
}
