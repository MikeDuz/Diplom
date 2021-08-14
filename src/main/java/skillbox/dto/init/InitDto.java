package skillbox.dto.init;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InitDto {

    private String title;
    @JsonProperty("subtitle")
    private String subTitle;
    private String telegram;
    private String email;
    private String copyright;
    private String copyrightFrom;

    public InitDto(String title, String subTitle, String telegram, String email, String copyright, String copyrightFrom) {
        this.title = title;
        this.subTitle = subTitle;
        this.telegram = telegram;
        this.email = email;
        this.copyright = copyright;
        this.copyrightFrom = copyrightFrom;
    }
}
