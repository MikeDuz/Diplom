package skillbox.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //определяем класс как контроллер
@RequestMapping("/")// задаем URL
@Log4j2
public class DefaultController {

    @RequestMapping("/")
    public String index() {
       return "";
    }

}
