package truthordare.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import truthordare.model.QuestionEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class QuestionsReaderService {


    private final ResourceLoader resourceLoader;

    public List<QuestionEntity> read() {

//        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("questions");
//        File file = new File()

        return Stream.of("questions/level_1.json",
                        "questions/level_2.json",
                        "questions/level_3.json",
                        "questions/level_4.json",
                        "questions/level_5.json")
                .map(this::read)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }


    public String readOne(String path) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
        if (inputStream != null) {
            String collect = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining(""));
            return collect;
        } else {
            return null;
        }
    }

    public List<QuestionEntity> read(String path) {
        try {
            String json = readOne(path);
            QuestionEntity[] questionEntities = new ObjectMapper().readValue(json, QuestionEntity[].class);
            Integer level = readNumberFromFileName(path);
            for (QuestionEntity questionEntity : questionEntities) {
                questionEntity.setLevel(level);
            }
            return List.of(questionEntities);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public Integer readNumberFromFileName(String path) {
        Pattern pattern = Pattern.compile("(?<=level_)\\d+(?=\\.json)");
        Matcher matcher = pattern.matcher(path);
        String value = matcher.results().findFirst().orElseThrow(() -> new RuntimeException("file name does not contain level")).group();
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

}
