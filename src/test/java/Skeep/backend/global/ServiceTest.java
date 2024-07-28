package Skeep.backend.global;

import Skeep.backend.auth.jwt.domain.RefreshTokenRepository;
import Skeep.backend.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ServiceTest {
    @Autowired
    public DatabaseCleaner databaseCleaner;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RefreshTokenRepository refreshTokenRepository;

    @BeforeEach
    void setUp() {
        databaseCleaner.execute();
        databaseCleaner.flushAndClear();
    }
}