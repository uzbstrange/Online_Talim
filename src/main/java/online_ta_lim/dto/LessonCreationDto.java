package online_ta_lim.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online_ta_lim.domain.Group;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LessonCreationDto {
    private String title;
    private String content;
    private Group groupName;
}
