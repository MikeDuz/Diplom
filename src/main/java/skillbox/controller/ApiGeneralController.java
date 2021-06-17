package skillbox.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skillbox.dto.globalSettig.GlobalSettingDTO;
import skillbox.dto.init.InitDTO;
import skillbox.dto.tag.TagDTO;
import skillbox.service.AuthCheckService;
import skillbox.service.InitService;
import skillbox.service.SettingsService;
import skillbox.service.TagService;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ApiGeneralController {

    private final InitService initResponse;
    private final SettingsService settingsService;
    private final TagService tagService;

    @GetMapping("init")
    public ResponseEntity<InitDTO> init() {
        return new ResponseEntity<>(initResponse.getInit(), HttpStatus.OK);
    }

    @GetMapping("settings")
    public ResponseEntity<GlobalSettingDTO> settings() {
        return new ResponseEntity<>(settingsService.getGlobalSettings(), HttpStatus.OK);
    }

    @GetMapping("tag")
    public ResponseEntity<TagDTO> tag(@RequestParam(defaultValue = "all") String query) {
        return new ResponseEntity<>(tagService.getTag(query), HttpStatus.OK);
    }

}
