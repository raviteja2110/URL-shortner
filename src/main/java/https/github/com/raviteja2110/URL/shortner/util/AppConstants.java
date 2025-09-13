package https.github.com.raviteja2110.url.shortner.util;

public final class AppConstants {

    private AppConstants() {
        // Private constructor to prevent instantiation
    }

    // API Endpoints
    public static final String GEO_IP_API_URL = "http://ip-api.com/json/";

    // API Responses
    public static final String API_SUCCESS_STATUS = "success";
    public static final String DEFAULT_COUNTRY_CODE = "XX";

    // View Names
    public static final String INDEX_VIEW = "index";
    public static final String RESULT_VIEW = "result";

    // Model Attributes
    public static final String SHORT_URL_ATTR = "shortUrl";
    public static final String LONG_URL_ATTR = "longUrl";
    public static final String CLICK_COUNT_ATTR = "clickCount";
    public static final String UNIQUE_VISITORS_ATTR = "uniqueVisitors";
    public static final String COUNTRIES_ATTR = "countries";
    public static final String QR_CODE_ATTR = "qrCodeImage";

    // URL Prefixes
    public static final String LOCAL_HOST_URL = "http://localhost:8080/";
    public static final String REDIRECT_TO_RESULT = "redirect:/result?shortCode=";

    // Error Messages
    public static final String URL_NOT_FOUND_MSG = "URL not found";
    public static final String URL_NOT_FOUND_FOR_CODE_MSG = "URL not found for code: ";
    public static final String UNKNOWN_API_ERROR_MSG = "Unknown error";
    public static final String GEO_IP_LOOKUP_WARN_MSG = "Could not determine country for IP {}: {}";

}
