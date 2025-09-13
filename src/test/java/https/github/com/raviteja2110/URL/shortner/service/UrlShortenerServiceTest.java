package https.github.com.raviteja2110.URL.shortner.service;


import https.github.com.raviteja2110.URL.shortner.dto.UrlMapping;
import https.github.com.raviteja2110.URL.shortner.exception.UrlNotFoundException;
import https.github.com.raviteja2110.URL.shortner.repo.UrlMappingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceTest {

    @Mock
    private UrlMappingRepository urlMappingRepository;

    @Mock
    private https.github.com.raviteja2110.URL.shortner.service.GeoIpService geoIpService;

    @InjectMocks
    private https.github.com.raviteja2110.URL.shortner.service.UrlShortenerService urlShortenerService;

    @Test
    void shortenUrl_existing() {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setShortCode("shortCode");
        when(urlMappingRepository.findByLongUrl("https://example.com")).thenReturn(Collections.singletonList(urlMapping));

        String shortCode = urlShortenerService.shortenUrl("https://example.com");

        assertEquals("shortCode", shortCode);
        verify(urlMappingRepository, never()).save(any());
    }

    @Test
    void shortenUrl_new() {
        when(urlMappingRepository.findByLongUrl("https://example.com")).thenReturn(Collections.emptyList());
        when(urlMappingRepository.findByShortCode(anyString())).thenReturn(Optional.empty());

        String shortCode = urlShortenerService.shortenUrl("https://example.com");

        assertNotNull(shortCode);
        verify(urlMappingRepository).save(any(UrlMapping.class));
    }

    @Test
    void getOriginalUrl() {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl("https://example.com");
        when(urlMappingRepository.findByShortCode("shortCode")).thenReturn(Optional.of(urlMapping));
        when(geoIpService.getCountry("127.0.0.1")).thenReturn("US");

        String longUrl = urlShortenerService.getOriginalUrl("shortCode", "127.0.0.1");

        assertEquals("https://example.com", longUrl);
        assertEquals(1, urlMapping.getClickCount());
        assertTrue(urlMapping.getUniqueVisitors().contains("127.0.0.1"));
        assertTrue(urlMapping.getCountries().contains("US"));
        verify(urlMappingRepository).save(urlMapping);
    }

    @Test
    void getOriginalUrl_notFound() {
        when(urlMappingRepository.findByShortCode("shortCode")).thenReturn(Optional.empty());

        assertThrows(UrlNotFoundException.class, () -> urlShortenerService.getOriginalUrl("shortCode", "127.0.0.1"));
    }

    @Test
    void getMappingByShortCode() {
        UrlMapping urlMapping = new UrlMapping();
        when(urlMappingRepository.findByShortCode("shortCode")).thenReturn(Optional.of(urlMapping));

        UrlMapping result = urlShortenerService.getMappingByShortCode("shortCode");

        assertEquals(urlMapping, result);
    }

    @Test
    void getMappingByShortCode_notFound() {
        when(urlMappingRepository.findByShortCode("shortCode")).thenReturn(Optional.empty());

        assertThrows(UrlNotFoundException.class, () -> urlShortenerService.getMappingByShortCode("shortCode"));
    }
}
