package https.github.com.raviteja2110.url.shortner.util;

public class AppConstants {
    public static final String REDIRECT_TO_RESULT = "redirect:/result?shortCode=";
    public static final String INDEX_VIEW = "index";
    public static final String RESULT_VIEW = "result";
    public static final String SHORT_URL_ATTR = "shortUrl";
    public static final String LONG_URL_ATTR = "longUrl";
    public static final String CLICK_COUNT_ATTR = "clickCount";
    public static final String UNIQUE_VISITORS_ATTR = "uniqueVisitors";
    public static final String QR_CODE_ATTR = "qrCodeImage";
    public static final String URL_NOT_FOUND_MSG = "URL not found";
    public static final String URL_NOT_FOUND_FOR_CODE_MSG = "URL not found for short code: ";

    // Constants for GeoIP service
    public static final String DEFAULT_COUNTRY_CODE = "XX";
    public static final String API_SUCCESS_STATUS = "success";
    public static final String GEO_IP_LOOKUP_WARN_MSG = "GeoIP lookup for {} failed with message: {}";
    public static final String UNKNOWN_API_ERROR_MSG = "Unknown API error";
}