package truthordare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import truthordare.model.QuestionEntity;

import java.util.Optional;
import java.util.Set;

@Repository
public interface QuestionEntityRepository extends JpaRepository<QuestionEntity, Long> {

    @Query(value = """
            SELECT * FROM questions q
            where q.level = :level
            and q.id not in (:excludes)
            and q.gender = :gender
            order by random()
            limit 1
            """, nativeQuery = true)
    Optional<QuestionEntity> getByLevel(Integer level, Integer gender, Set<Long> excludes);

}