package Skeep.backend.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixedCategoryRepository extends JpaRepository<FixedCategory, Long> {
}
