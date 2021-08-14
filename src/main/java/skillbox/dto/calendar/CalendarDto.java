package skillbox.dto.calendar;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CalendarDto {

    List<Integer> years;
    Map<String, Integer> posts;


}
