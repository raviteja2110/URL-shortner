package https.github.com.raviteja2110.URL.shortner.advice;

import https.github.com.raviteja2110.URL.shortner.exception.UrlNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global exception handler for the application.
 * Catches specific exceptions and returns appropriate views or responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the {@link UrlNotFoundException} by displaying a custom error page.
     *
     * @param ex      The caught UrlNotFoundException.
     * @param model   The Spring UI model to pass data to the view.
     * @return The name of the error view.
     */
    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUrlNotFoundException(UrlNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error"; // Renders error.html
    }
}
