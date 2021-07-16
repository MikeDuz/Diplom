package skillbox.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skillbox.dto.globalSettig.GlobalSettingDTO;
import skillbox.entity.GlobalSettings;
import skillbox.mapping.GlobalSettingMapper;
import skillbox.repository.GlobalSettingRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SettingsService {

    private final GlobalSettingRepository findAllGlobalSettings;

    public GlobalSettingDTO getGlobalSettings() {
        GlobalSettingDTO settingDTO = new GlobalSettingDTO();
        List<GlobalSettings> setting = (List<GlobalSettings>) findAllGlobalSettings.findAll();
        GlobalSettingMapper.globalSettingMapper(setting, settingDTO);
        return GlobalSettingMapper.globalSettingMapper(setting, settingDTO);
    }
}
