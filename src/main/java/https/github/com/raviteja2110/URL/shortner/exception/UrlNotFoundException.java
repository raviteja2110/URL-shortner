package https.github.com.raviteja2110.URL.shortner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception thrown when a short URL is not found in the database.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UrlNotFoundException extends RuntimeException {

    /**
     * Constructs a new UrlNotFoundException with the specified detail message.
     *
     * @param message The detail message.
     */
    public UrlNotFoundException(String message) {
        super(message);
    }
}
