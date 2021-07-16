package skillbox.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skillbox.dto.calendar.CalendarDTO;
import skillbox.repository.PostRepository;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final PostRepository postRepository;

    public CalendarDTO getCalendar(int year) {
        if (year == 0) {
            year = LocalDateTime.now().getYear();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<LocalDateTime> listDate = getDateList();
        List<Integer> yearRequest = new ArrayList<>();
        Map<String, Integer> postByDate = new HashMap<>();
        for (LocalDateTime d : listDate) {
            if (!yearRequest.contains(d.getYear())) {
                yearRequest.add(d.getYear());
            }
            String date = d.format(formatter);
            ;
            if (d.getYear() == year) {
                postByDate.computeIfPresent(date, (a, b) -> b = b + 1);
                postByDate.putIfAbsent(date, 1);
            }
        }
        CalendarDTO calendarDTO = new CalendarDTO();
        calendarDTO.setYears(yearRequest);
        calendarDTO.setPosts(postByDate);
        return calendarDTO;

    }

    private List<LocalDateTime> getDateList() {
        return postRepository.findAllTime(LocalDateTime.now());
    }

}
