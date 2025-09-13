package https.github.com.raviteja2110.URL.shortner.repo;


import https.github.com.raviteja2110.URL.shortner.dto.UrlMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UrlMappingRepository extends MongoRepository<UrlMapping, String> {
    Optional<UrlMapping> findByShortCode(String shortCode);
    List<UrlMapping> findByLongUrl(String longUrl);
}