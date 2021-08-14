package skillbox.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skillbox.dto.globalSettig.GlobalSettingDto;
import skillbox.entity.GlobalSettings;
import skillbox.entity.User;
import skillbox.entity.enums.GlobalSettingCode;
import skillbox.entity.enums.GlobalSettingCodeValue;
import skillbox.mapping.GlobalSettingMapper;
import skillbox.repository.GlobalSettingRepository;
import skillbox.repository.UserRepository;
import skillbox.service.GlobalSettingService;

import java.security.Principal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class GlobalSettingServiceImpl implements GlobalSettingService {

    private final GlobalSettingRepository globalSettingRep;
    private final UserRepository userRep;

    @Override
    @Transactional
    public GlobalSettingDto getGlobalSettings() {
        GlobalSettingDto settingDTO = new GlobalSettingDto();
        List<GlobalSettings> setting = (List<GlobalSettings>) globalSettingRep.findAll();
        GlobalSettingMapper.globalGetSettingMapper(setting, settingDTO);
        return GlobalSettingMapper.globalGetSettingMapper(setting, settingDTO);
    }

    @Override
    @Transactional
    public void setGlobalSetting(GlobalSettingDto setting, Principal principal) {
        User user = userRep.findByEmail(principal.getName()).get();
        GlobalSettingCodeValue value;
        if(user.isModerator()) {
            if(setting.isMultiuserMode()) {
                value = GlobalSettingCodeValue.YES;
            } else {
                value = GlobalSettingCodeValue.NO;
            }
            globalSettingRep.updateMultiUserMode(value, GlobalSettingCode.MULTIUSER_MODE);
            if(setting.isPostPremoderation()) {
                value = GlobalSettingCodeValue.YES;
            } else {
                value = GlobalSettingCodeValue.NO;
            }
            globalSettingRep.updateMultiUserMode(value, GlobalSettingCode.POST_PREMODERATION);
            if(setting.isStatisticsIsPublic()) {
                value = GlobalSettingCodeValue.YES;
            } else {
                value = GlobalSettingCodeValue.NO;
            }
            globalSettingRep.updateMultiUserMode(value, GlobalSettingCode.STATISTICS_IS_PUBLIC);
        }

    }
}
