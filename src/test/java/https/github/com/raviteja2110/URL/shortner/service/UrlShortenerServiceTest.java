package https.github.com.raviteja2110.url.shortner.service;

import https.github.com.raviteja2110.url.shortner.config.AppProperties;
import https.github.com.raviteja2110.url.shortner.dto.UrlMapping;
import https.github.com.raviteja2110.url.shortner.exception.UrlNotFoundException;
import https.github.com.raviteja2110.url.shortner.repo.UrlMappingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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

    private final String longUrl = "https://example.com/a-very-long-url";
    private final String shortCode = "aBcDeF";
    private final String baseUrl = "http://localhost:8080";

    @BeforeEach
    void setUp() {
        // Stubbing moved to individual tests to avoid UnnecessaryStubbingException.
    }

    @Test
    void shortenUrl_whenUrlExists_shouldReturnExistingShortUrl() {
        UrlMapping existingMapping = new UrlMapping();
        existingMapping.setLongUrl(longUrl);
        existingMapping.setShortCode(shortCode);

        // Fix: Return an Optional containing the mapping
        when(appProperties.getBaseUrl()).thenReturn(baseUrl);
        when(repository.findByLongUrl(longUrl)).thenReturn(Optional.of(existingMapping));

        String result = urlShortenerService.shortenUrl(longUrl);

        assertEquals(baseUrl + "/" + shortCode, result);
        verify(repository, never()).save(any(UrlMapping.class));
    }

    @Test
    void shortenUrl_whenUrlIsNew_shouldCreateAndReturnNewShortUrl() {
        // Fix: Return Optional.empty() to simulate a new URL
        when(appProperties.getBaseUrl()).thenReturn(baseUrl);
        when(repository.findByLongUrl(longUrl)).thenReturn(Optional.empty());
        when(repository.findByShortCode(anyString())).thenReturn(Optional.empty());

        String result = urlShortenerService.shortenUrl(longUrl);

        assertTrue(result.startsWith(baseUrl + "/"));
        assertEquals(baseUrl.length() + 1 + 6, result.length());
        verify(repository, times(1)).save(any(UrlMapping.class));
    }

    @Test
    void getOriginalUrl_whenShortCodeExists_shouldReturnLongUrlAndTrackClick() {
        UrlMapping mapping = new UrlMapping();
        mapping.setLongUrl(longUrl);
        mapping.setShortCode(shortCode);

        when(repository.findByShortCode(shortCode)).thenReturn(Optional.of(mapping));
        when(geoIpService.getCountry(anyString())).thenReturn("US");

        String result = urlShortenerService.getOriginalUrl(shortCode, "127.0.0.1");

        assertEquals(longUrl, result);
        assertEquals(1, mapping.getClickCount());
        assertTrue(mapping.getUniqueVisitors().contains("127.0.0.1"));
        assertTrue(mapping.getCountries().contains("US"));
        verify(repository, times(1)).save(mapping);
    }

    @Test
    void getOriginalUrl_whenShortCodeDoesNotExist_shouldThrowUrlNotFoundException() {
        when(repository.findByShortCode(shortCode)).thenReturn(Optional.empty());

        assertThrows(UrlNotFoundException.class, () -> {
            urlShortenerService.getOriginalUrl(shortCode, "127.0.0.1");
        });

        verify(repository, never()).save(any(UrlMapping.class));
    }
}