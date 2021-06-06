package skillbox.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //определяем класс как контроллер
@Log4j2
public class DefaultController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

}
