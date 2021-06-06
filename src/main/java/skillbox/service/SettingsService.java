package skillbox.service;

import org.springframework.stereotype.Service;
import skillbox.api.response.GlobalSettingsResponse;

@Service
public class SettingsService {

    public GlobalSettingsResponse getGlobalSettings() {
        GlobalSettingsResponse settingsResponse = new GlobalSettingsResponse();
        settingsResponse.setMultiuserMode(true);
        settingsResponse.setPostPremoderation(true);
        settingsResponse.setStatisticsIsPublic(true);
        return settingsResponse;
    }
}
