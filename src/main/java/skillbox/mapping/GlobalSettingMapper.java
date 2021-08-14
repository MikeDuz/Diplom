package skillbox.mapping;

import skillbox.dto.globalSettig.GlobalSettingDto;
import skillbox.entity.GlobalSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static skillbox.entity.enums.GlobalSettingCodeValue.YES;

public class GlobalSettingMapper {

    public static GlobalSettingDto globalGetSettingMapper(List<GlobalSettings> settings, GlobalSettingDto settingDTO) {
        settingDTO.setMultiuserMode(settings.get(0).getValue().equals(YES));
        settingDTO.setPostPremoderation(settings.get(1).getValue().equals(YES));
        settingDTO.setStatisticsIsPublic(settings.get(2).getValue().equals(YES));
        return settingDTO;
    }

}
