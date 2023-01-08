package truthordare;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import truthordare.model.QuestionEntity;
import truthordare.repository.QuestionEntityRepository;
import truthordare.service.QuestionsReaderService;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class InitDB implements ApplicationRunner {

    private final QuestionEntityRepository repository;
    private final JdbcTemplate jdbcTemplate;
    private final QuestionsReaderService questionsReaderService;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws IOException {
        List<QuestionEntity> questionEntities = questionsReaderService.read();
        repository.deleteAll();
        jdbcTemplate.batchUpdate(
                "INSERT INTO questions (level, type, text, gender) VALUES (?, ?, ?, ?)",
                questionEntities, questionEntities.size(),
                (ps, argument) -> {
                    ps.setInt(1, argument.getLevel());
                    ps.setInt(2, argument.getType());
                    ps.setString(3, argument.getText());
                    ps.setInt(4, argument.getGender());
                });
    }

}
