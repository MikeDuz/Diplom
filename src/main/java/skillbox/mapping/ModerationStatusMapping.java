package skillbox.mapping;

import skillbox.entity.enums.ModerationStatus;

public class ModerationStatusMapping {

    public static ModerationStatus mapModStatus( String status) {
        switch (status) {
            case("new"):
                return ModerationStatus.NEW;
            case("accepted"):
                return ModerationStatus.ACCEPTED;
            case ("declined"):
                return ModerationStatus.DECLINED;
        }
        return null;
    }
}
