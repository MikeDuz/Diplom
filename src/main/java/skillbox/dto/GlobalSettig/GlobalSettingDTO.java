package skillbox.dto.GlobalSettig;

import lombok.Value;

import javax.validation.constraints.Positive;

public enum GlobalSettingDTO {;
    private interface MultiuserMode{@Positive Boolean getMultiuserMode();}
    private interface PostPremoderation{@Positive Boolean getPostPremoderation();}
    private interface StatisticIsPublic{@Positive Boolean getStatisticIsPublic();}

    public enum Request{;
    @Value
    public static class Create implements MultiuserMode, PostPremoderation, StatisticIsPublic {
        Boolean multiuserMode;
        Boolean postPremoderation;
        Boolean statisticIsPublic;
    }}

}
