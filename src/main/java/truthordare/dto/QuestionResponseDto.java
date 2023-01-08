package truthordare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import truthordare.model.QuestionEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDto {

    private Long id;
    private String player;
    private String type;
    private String text;
    private Integer level;

    public static QuestionResponseDto instance(GameDto game, QuestionEntity question) {
        QuestionResponseDto dto = new QuestionResponseDto();
        dto.setId(question.getId());
        dto.setPlayer(game.getPlayerNameByGender(question.getGender()));
        dto.setType(question.getType() == 0 ? "ВОПРОС" : "ДЕЙСТВИЕ");
        dto.setText(question.getText());
        dto.setLevel(question.getLevel());
        return dto;
    }
}
