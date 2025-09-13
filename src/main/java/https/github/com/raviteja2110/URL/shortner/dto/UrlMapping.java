package https.github.com.raviteja2110.url.shortner.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a mapping between a long URL and a short code in the database.
 * Also stores analytics data for the URL.
 */
@Document(collection = "url_mappings")
@Data
public class UrlMapping {

    @Id
    private String id;

    /** The original, long URL. */
    @Indexed(unique = true)
    private String longUrl;

    /** The generated short code that maps to the long URL. */
    @Indexed(unique = true)
    private String shortCode;

    /** The timestamp when the mapping was created. */
    private LocalDateTime createdAt;

    /** The total number of times the short URL has been clicked. */
    private long clickCount = 0;

    /** A set of unique visitor IP addresses to track unique clicks. */
    private Set<String> uniqueVisitors = new HashSet<>();

    /** A set of country codes from which clicks have originated. */
    private Set<String> countries = new HashSet<>();
}
