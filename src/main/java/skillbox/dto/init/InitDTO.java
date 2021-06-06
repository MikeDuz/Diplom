package skillbox.dto.init;

import lombok.Value;
import javax.validation.constraints.Positive;


public enum InitDTO {
    ;

    private interface Title {
        @Positive String getTitle();
    }

    private interface Subtitle {
        @Positive String getSubtitle();
    }

    private interface Phone {
        @Positive String getPhone();
    }

    private interface Telegram {
        @Positive String getTelegram();
    }

    private interface Email {
        @Positive String getEmail();
    }

    private interface CopyrightFrom {
        @Positive String getCopyrightFrom();
    }

    public enum Response {;
        @Value
        public static class Create implements Title, Subtitle, Phone, Email, CopyrightFrom {
            String title;
            String subtitle;
            String phone;
            String telegram;
            String email;
            String copyrightFrom;
        }
    }
}
