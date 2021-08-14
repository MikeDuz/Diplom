package skillbox.service;

import skillbox.dto.globalSettig.GlobalSettingDto;

import java.security.Principal;

public interface GlobalSettingService {

    GlobalSettingDto getGlobalSettings();

    void setGlobalSetting(GlobalSettingDto settingDto, Principal principal);
}
