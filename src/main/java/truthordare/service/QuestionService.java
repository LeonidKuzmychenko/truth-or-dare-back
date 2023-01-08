package truthordare.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import truthordare.dto.GameDto;
import truthordare.dto.QuestionResponseDto;
import truthordare.model.QuestionEntity;
import truthordare.repository.QuestionEntityRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class QuestionService {

    private final QuestionEntityRepository repository;
    private final SessionCacheService cacheService;

    public String start(String male, String female) {
        String session = UUID.randomUUID().toString();
        cacheService.setGame(session, new GameDto(male, female));
        return session;
    }

    public Boolean gameIsExist(String session) {
        return cacheService.getGame(session) != null;
    }

    public Optional<QuestionResponseDto> nextLevelUpQuestion(String session) {
        GameDto game = cacheService.getGame(session);
        if (game == null){
            return Optional.empty();
        }
        else {
            Integer newGender = game.getInverseGender();
            return nextLevelUpQuestion(session, game, newGender);
        }

    }

    private Optional<QuestionResponseDto> nextLevelUpQuestion(String session, GameDto game, Integer gender) {
        Integer level = game.getLevel();
        Set<Long> exclude = game.getExcludes();
        Optional<QuestionEntity> questionEntityOptional = repository.getByLevel(level, gender, exclude);
        if (questionEntityOptional.isPresent()) {
            QuestionEntity questionEntity = questionEntityOptional.get();
            game.addExclude(questionEntity.getId());
            game.setGender(gender);
            cacheService.setGame(session, game);
            return Optional.of(QuestionResponseDto.instance(game, questionEntity));
        } else if (level >= 5) {
            return Optional.empty();
        } else {
            game.setLevel(++level);
            cacheService.setGame(session, game);
            return nextLevelUpQuestion(session, game, gender);
        }
    }

}