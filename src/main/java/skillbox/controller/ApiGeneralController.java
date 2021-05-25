package skillbox.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skillbox.api.response.InitResponse;

@RestController //определяем класс как контроллер
@RequestMapping("/api")// задаем URL
@Log4j2
public class ApiGeneralController {

    private final InitResponse initResponse;

    public ApiGeneralController(InitResponse initResponse) {
        this.initResponse = initResponse;
    }

    @GetMapping("/init")
    private InitResponse init() {
        return initResponse;
    }
}
