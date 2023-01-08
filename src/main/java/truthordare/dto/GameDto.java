package truthordare.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class GameDto {

    private final String male;
    private final String female;
    private Integer level;
    private Integer gender;
    private final Set<Long> excludes;

    public GameDto(String male, String female) {
        this.male = male;
        this.female = female;
        this.level = 1;
        this.gender = 0;
        this.excludes = new HashSet<>(Set.of(-1L));
    }

    public void addExclude(Long id) {
        this.excludes.add(id);
    }

    public String getPlayerNameByGender(Integer gender) {
        return gender == 0 ? this.male : this.female;
    }

    public Integer getInverseGender() {
        return this.gender == 0 ? 1 : 0;
    }
}
