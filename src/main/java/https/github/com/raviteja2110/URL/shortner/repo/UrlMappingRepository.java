package https.github.com.raviteja2110.url.shortner.repo;


import https.github.com.raviteja2110.url.shortner.dto.UrlMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlMappingRepository extends MongoRepository<UrlMapping, String> {
    Optional<UrlMapping> findByShortCode(String shortCode);
    Optional<UrlMapping> findByLongUrl(String longUrl);
}