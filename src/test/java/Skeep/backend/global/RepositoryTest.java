package Skeep.backend.global;

import Skeep.backend.global.config.JpaAuditingConfiguration;
import Skeep.backend.global.config.QueryDslConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import({QueryDslConfig.class, JpaAuditingConfiguration.class})
@ActiveProfiles("test")
public class RepositoryTest {
}
