package https.github.com.raviteja2110.url.shortner.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for parsing the JSON response from the ip-api.com service.
 */
@Data
public class GeoIpResponse {

    /** The status of the API request (e.g., "success", "fail"). */
    private String status;

    /** The two-letter country code (e.g., "US", "IN"). */
    private String countryCode;

    /** An error message if the request fails. */
    private String message;
}
