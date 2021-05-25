package skillbox.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import skillbox.api.response.InitResponse;

@Controller //определяем класс как контроллер
@Log4j2
public class DefaultController {
    private final InitResponse initResponse;

    public DefaultController(InitResponse initResponse) {
        this.initResponse = initResponse;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

}
