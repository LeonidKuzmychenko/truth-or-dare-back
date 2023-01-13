package truthordare.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import truthordare.model.QuestionEntity;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ch.qos.logback.core.util.LocationUtil.CLASSPATH_SCHEME;

@Service
@AllArgsConstructor
public class QuestionsReaderService {

    private final ResourcePatternResolver resourcePatternResolver;

    public List<QuestionEntity> read() throws IOException {
        Resource[] questionsArray = resourcePatternResolver.getResources(CLASSPATH_SCHEME + "questions/*");
        return Stream.of(questionsArray).map(this::read)
                .filter(Objects::nonNull)
                .map(List::of)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public QuestionEntity[] read(Resource resource) {
        Integer level = readNumberFromFileName(resource.getFilename());
        try {
            QuestionEntity[] questions = new ObjectMapper().readValue(resource.getURL(), QuestionEntity[].class);
            for (QuestionEntity question : questions) {
                question.setLevel(level);
            }
            return questions;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer readNumberFromFileName(String filename) {
        Matcher matcher = Pattern.compile("(?<=level_)\\d+(?=\\.json)").matcher(filename);
        String value = matcher.results().findFirst().orElseThrow(() -> new RuntimeException("file name does not contain level")).group();
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

}