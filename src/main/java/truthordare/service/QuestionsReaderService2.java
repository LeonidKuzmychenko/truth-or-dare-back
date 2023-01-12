//package truthordare.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.AllArgsConstructor;
//import org.springframework.core.io.ResourceLoader;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ResourceUtils;
//import truthordare.model.QuestionEntity;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@Service
//@AllArgsConstructor
//public class QuestionsReaderService2 {
//
//    public List<QuestionEntity> read() throws IOException {
//        File directory = ResourceUtils.getFile(ResourceLoader.CLASSPATH_URL_PREFIX + "questions");
//        File[] files = directory.listFiles();
//        return Stream.of(files)
//                .peek(it -> System.out.println(it.getPath()))
//                .map(File::toPath)
//                .map(this::readQuestions)
//                .filter(Objects::nonNull)
//                .flatMap(Arrays::stream)
//                .collect(Collectors.toList());
//    }
//
//    public QuestionEntity[] readQuestions(Path path) {
//        QuestionEntity[] questionEntities = parseJson(path, QuestionEntity[].class);
//        Integer level = readNumberFromFileName(path);
//        for (QuestionEntity questionEntity : questionEntities) {
//            questionEntity.setLevel(level);
//        }
//        return questionEntities;
//    }
//
//    public <T> T[] parseJson(Path path, Class<T[]> clazz) {
//        try {
//            String json = Files.readString(path);
//            return new ObjectMapper().readValue(json, clazz);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public Integer readNumberFromFileName(Path path) {
//        String name = path.toString();
//        String nameNullable = Optional.ofNullable(name).orElseThrow(() -> new RuntimeException("file name is null"));
//        Pattern pattern = Pattern.compile("(?<=level_)\\d+(?=\\.json)");
//        Matcher matcher = pattern.matcher(nameNullable);
//        String value = matcher.results().findFirst().orElseThrow(() -> new RuntimeException("file name does not contain level")).group();
//        try {
//            return Integer.parseInt(value);
//        } catch (NumberFormatException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}
