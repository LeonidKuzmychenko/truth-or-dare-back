package truthordare.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import truthordare.model.QuestionEntity;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ch.qos.logback.core.util.LocationUtil.CLASSPATH_SCHEME;

@Service
@AllArgsConstructor
public class QuestionsReaderService {

    private final ResourcePatternResolver resourcePatternResolver;

    public List<QuestionEntity> read() throws IOException {
        Resource[] resources = resourcePatternResolver.getResources(CLASSPATH_SCHEME + "questions/*");
        return Arrays.stream(resources).map(this::readQuestions).filter(Objects::nonNull).flatMap(Arrays::stream).collect(Collectors.toList());
    }

    public QuestionEntity[] readQuestions(Resource resource) {
        QuestionEntity[] questionEntities = parseJson(resource, QuestionEntity[].class);
        Integer level = readNumberFromFileName(resource);
        for (QuestionEntity questionEntity : questionEntities) {
            questionEntity.setLevel(level);
        }
        return questionEntities;
    }

    public <T> T[] parseJson(Resource resource, Class<T[]> clazz) {
        try {
            URI uri = resource.getURI();
            Path path = Paths.get(uri);
            String json = Files.readString(path);
            return new ObjectMapper().readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer readNumberFromFileName(Resource resource) {
        String name = resource.getFilename();
        String nameNullable = Optional.ofNullable(name).orElseThrow(() -> new RuntimeException("file name is null"));
        Pattern pattern = Pattern.compile("(?<=level_)\\d+(?=\\.json)");
        Matcher matcher = pattern.matcher(nameNullable);
        String value = matcher.results().findFirst().orElseThrow(() -> new RuntimeException("file name does not contain level")).group();
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

}
