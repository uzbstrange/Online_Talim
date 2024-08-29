package online_ta_lim.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String groupName;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private UserEntity teacher;

    @OneToMany(mappedBy = "group")
    private List<Lesson> lessons;

    // Constructor to set teacher automatically
    public Group(String groupName, UserEntity teacher) {
        this.groupName = groupName;
        this.teacher = teacher;
    }
}
