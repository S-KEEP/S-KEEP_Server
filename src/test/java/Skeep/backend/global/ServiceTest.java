package Skeep.backend.global;

import Skeep.backend.auth.jwt.domain.RefreshTokenRepository;
import Skeep.backend.category.domain.UserCategoryRepository;
import Skeep.backend.location.location.domain.LocationRepository;
import Skeep.backend.location.userLocation.domain.UserLocationRepository;
import Skeep.backend.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ServiceTest {
    @Autowired
    public DatabaseCleaner databaseCleaner;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RefreshTokenRepository refreshTokenRepository;

    @Autowired
    protected UserCategoryRepository userCategoryRepository;

    @Autowired
    protected LocationRepository locationRepository;

    @Autowired
    protected UserLocationRepository userLocationRepository;

    @BeforeEach
    void setUp() {
        databaseCleaner.execute();
        databaseCleaner.flushAndClear();
    }
}