package https.github.com.raviteja2110.url.shortner.service;

import https.github.com.raviteja2110.url.shortner.config.AppProperties;
import https.github.com.raviteja2110.url.shortner.dto.UrlMapping;
import https.github.com.raviteja2110.url.shortner.repo.UrlMappingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceTest {

    @Mock
    private UrlMappingRepository repository;

    @Mock
    private GeoIpService geoIpService;

    @Mock
    private AppProperties appProperties;

    @InjectMocks
    private UrlShortenerService urlShortenerService;

    @BeforeEach
    void setUp() {
        // Configure the mock AppProperties before each test
        when(appProperties.getBaseUrl()).thenReturn("http://test.host");
    }

    @Test
    void shortenUrl_existing() {
        // Setup
        String longUrl = "http://example.com/a-very-long-url";
        UrlMapping existingMapping = new UrlMapping();
        existingMapping.setShortCode("exist");
        existingMapping.setLongUrl(longUrl);

        when(repository.findByLongUrl(longUrl)).thenReturn(Collections.singletonList(existingMapping));

        // Execute
        String shortUrl = urlShortenerService.shortenUrl(longUrl);

        // Verify
        assertEquals("http://test.host/exist", shortUrl);
    }

    @Test
    void shortenUrl_new() {
        // Setup
        String longUrl = "http://example.com/a-new-long-url";
        when(repository.findByLongUrl(longUrl)).thenReturn(Collections.emptyList());
        when(repository.findByShortCode(anyString())).thenReturn(Optional.empty());

        // Execute
        String shortUrl = urlShortenerService.shortenUrl(longUrl);

        // Verify
        verify(repository).save(any(UrlMapping.class));
        // The generated short code is random, so we check the format.
        assertEquals(23, shortUrl.length()); // "http://test.host/".length + 6
        assertEquals("http://test.host/", shortUrl.substring(0, 17));
    }

    // Add other tests for getOriginalUrl and getMappingByShortCode
}