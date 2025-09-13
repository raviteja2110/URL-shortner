package https.github.com.raviteja2110.url.shortner.controller;

import https.github.com.raviteja2110.url.shortner.service.QrCodeService;
import https.github.com.raviteja2110.url.shortner.service.UrlShortenerService;
import https.github.com.raviteja2110.url.shortner.dto.UrlMapping;
import https.github.com.raviteja2110.url.shortner.util.AppConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(https.github.com.raviteja2110.url.shortner.controller.PageController.class)
class PageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlShortenerService urlShortenerService;

    @MockBean
    private QrCodeService qrCodeService;

    @Test
    void home() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void resultPage() throws Exception {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setShortCode("shortCode");
        urlMapping.setLongUrl("https://example.com");
        urlMapping.setClickCount(10L);
        urlMapping.setUniqueVisitors(Collections.singleton("127.0.0.1"));
        urlMapping.setCountries(Collections.singleton("US"));

        when(urlShortenerService.getMappingByShortCode("shortCode")).thenReturn(urlMapping);
        when(qrCodeService.generateQrCodeImage(anyString())).thenReturn("qrCodeBase64String");

        mockMvc.perform(get("/result").param("shortCode", "shortCode"))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("shortUrl", AppConstants.LOCAL_HOST_URL + "shortCode"))
                .andExpect(model().attribute("longUrl", "https://example.com"))
                .andExpect(model().attribute("clickCount", 10L))
                .andExpect(model().attribute("uniqueVisitors", 1))
                .andExpect(model().attribute("countries", 1))
                .andExpect(model().attribute(AppConstants.QR_CODE_ATTR, "qrCodeBase64String"));
    }
}
