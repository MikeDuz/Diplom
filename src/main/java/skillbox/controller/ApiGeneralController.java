package skillbox.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skillbox.api.response.AuthCheckResponse;
import skillbox.api.response.GlobalSettingsResponse;
import skillbox.dto.init.InitDTO;
import skillbox.service.AuthCheckService;
import skillbox.service.SettingsService;

@RestController //определяем класс как контроллер
@RequestMapping("/api")// задаем URL
@Log4j2
public class ApiGeneralController {

    private final InitDTO initResponse;
    private final SettingsService settingsService;
    private final AuthCheckService authCheck;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ApiGeneralController(InitDTO initResponse, SettingsService settingsService, AuthCheckService authCheck) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
        this.authCheck = authCheck;
    }

    @GetMapping("/init")
    private InitDTO init() {
        return initResponse;
    }

    @GetMapping("/settings")
    private GlobalSettingsResponse settings() {
        return settingsService.getGlobalSettings();
    }

    @GetMapping("/auth/check")
    private AuthCheckResponse authCheck() {
        return authCheck.getAuthCheck();
    }

}
