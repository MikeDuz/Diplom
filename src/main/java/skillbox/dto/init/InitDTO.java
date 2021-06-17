package skillbox.dto.init;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InitDTO {

    private String title;
    private String subTitle;
    private String telegram;
    private String email;
    private String copyright;
    private String copyrightFrom;

    public InitDTO(String title, String subTitle, String telegram, String email, String copyright, String copyrightFrom) {
        this.title = title;
        this.subTitle = subTitle;
        this.telegram = telegram;
        this.email = email;
        this.copyright = copyright;
        this.copyrightFrom = copyrightFrom;
    }
}
