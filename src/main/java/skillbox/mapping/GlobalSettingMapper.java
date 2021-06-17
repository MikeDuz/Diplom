package skillbox.mapping;

import skillbox.dto.globalSettig.GlobalSettingDTO;
import skillbox.entity.GlobalSettings;

import java.util.List;

import static skillbox.entity.enums.GlobalSettingCodeValue.YES;

public class GlobalSettingMapper {

    public static GlobalSettingDTO globalSettingMapper(List<GlobalSettings> settings, GlobalSettingDTO settingDTO) {

        settingDTO.setMultiuserMode(settings.get(0).getValue().equals(YES));
        settingDTO.setPostPremoderation(settings.get(1).getValue().equals(YES));
        settingDTO.setStatisticsIsPublic(settings.get(2).getValue().equals(YES));

        return settingDTO;
    }
}
