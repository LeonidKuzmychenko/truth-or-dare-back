package truthordare.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "text", unique = false, nullable = false, length = 1000)
    private String text;

    @Column(name = "type", unique = false, nullable = false)
    private Integer type;

    @Column(name = "level", unique = false, nullable = false)
    private Integer level;

    @Column(name = "gender", unique = false, nullable = false)
    private Integer gender;
}