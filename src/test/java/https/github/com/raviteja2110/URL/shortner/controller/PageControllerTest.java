package https.github.com.raviteja2110.url.shortner.controller;

import https.github.com.raviteja2110.url.shortner.dto.UrlMapping;
import https.github.com.raviteja2110.url.shortner.service.QrCodeService;
import https.github.com.raviteja2110.url.shortner.service.UrlShortenerService;
import https.github.com.raviteja2110.url.shortner.util.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PageController.class)
class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlShortenerService urlShortenerService;

    @MockBean
    private QrCodeService qrCodeService;

    private UrlMapping testMapping;
    private final String testShortCode = "abc123";
    private final String testShortUrl = "http://localhost:8080/" + testShortCode;

    @BeforeEach
    void setUp() {
        testMapping = new UrlMapping();
        testMapping.setShortCode(testShortCode);
        testMapping.setLongUrl("http://example.com");
        testMapping.setCreatedAt(LocalDateTime.now());
    }
    @Test
    void home() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void resultPage() throws Exception {
        // Arrange: Mock the service calls
        when(urlShortenerService.getMappingByShortCode(testShortCode)).thenReturn(testMapping);
        when(urlShortenerService.getFullShortUrl(testShortCode)).thenReturn(testShortUrl);
        when(qrCodeService.generateQrCodeImage(testShortUrl)).thenReturn("qr-code-data");

        // Act & Assert
        mockMvc.perform(get("/result").param("shortCode", testShortCode))
                .andExpect(status().isOk())
                .andExpect(view().name(AppConstants.RESULT_VIEW))
                .andExpect(model().attribute(AppConstants.SHORT_URL_ATTR, testShortUrl))
                .andExpect(model().attribute(AppConstants.QR_CODE_ATTR, "qr-code-data"));
    }

    @Test
    void resultPage_handlesQrCodeGenerationFailure() throws Exception {
        // Arrange: Mock the service calls, with QrCodeService throwing an exception
        when(urlShortenerService.getMappingByShortCode(testShortCode)).thenReturn(testMapping);
        when(urlShortenerService.getFullShortUrl(testShortCode)).thenReturn(testShortUrl);
        when(qrCodeService.generateQrCodeImage(testShortUrl)).thenThrow(new IOException("QR Generation Failed"));

        // Act & Assert
        mockMvc.perform(get("/result").param("shortCode", testShortCode))
                .andExpect(status().isOk())
                .andExpect(view().name(AppConstants.RESULT_VIEW))
                .andExpect(model().attribute(AppConstants.QR_CODE_ATTR, ""));
    }
}