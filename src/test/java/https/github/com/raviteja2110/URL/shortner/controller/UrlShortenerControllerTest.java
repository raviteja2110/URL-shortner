package https.github.com.raviteja2110.url.shortner.controller;

import https.github.com.raviteja2110.url.shortner.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(https.github.com.raviteja2110.url.shortner.controller.UrlShortenerController.class)
class UrlShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlShortenerService urlShortenerService;

    @Test
    void shortenUrl() throws Exception {
        when(urlShortenerService.shortenUrl("https://example.com")).thenReturn("shortCode");

        mockMvc.perform(post("/shorten")
                        .param("longUrl", "https://example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/result?shortCode=shortCode"));
    }

    @Test
    void redirect() throws Exception {
        when(urlShortenerService.getOriginalUrl("shortCode", "127.0.0.1")).thenReturn("https://example.com");

        mockMvc.perform(get("/shortCode"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("https://example.com"));
    }
}
