package https.github.com.raviteja2110.url.shortner.controller;

import https.github.com.raviteja2110.url.shortner.config.AppProperties;
import https.github.com.raviteja2110.url.shortner.dto.UrlMapping;
import https.github.com.raviteja2110.url.shortner.service.QrCodeService;
import https.github.com.raviteja2110.url.shortner.service.UrlShortenerService;
import https.github.com.raviteja2110.url.shortner.util.AppConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PageController.class)
@Import(AppProperties.class) // Import the configuration properties bean
class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlShortenerService urlShortenerService;

    @MockBean
    private QrCodeService qrCodeService;

    @Autowired
    private AppProperties appProperties; // Can now be autowired

    @Test
    void home() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void resultPage() throws Exception {
        // Setup
        UrlMapping mapping = new UrlMapping();
        mapping.setShortCode("abc123");
        mapping.setLongUrl("http://example.com");
        mapping.setCreatedAt(LocalDateTime.now());

        when(urlShortenerService.getMappingByShortCode(anyString())).thenReturn(mapping);
        when(qrCodeService.generateQrCodeImage(anyString())).thenReturn("qr-code-data");

        // Execute & Verify
        mockMvc.perform(get("/result").param("shortCode", "abc123"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attributeExists("shortUrl", "longUrl", AppConstants.QR_CODE_ATTR));
    }
}