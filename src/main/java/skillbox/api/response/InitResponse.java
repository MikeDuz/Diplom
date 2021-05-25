package skillbox.api.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class InitResponse {
    @Value("${blog.title}")
    private String title;
    @Value("${blog.subtitle}")
    private String subTitle;
    @Value("${blog.telegram}")
    private String telegram;
    @Value("${blog.email}")
    private String email;
    @Value("${blog.copyright}")
    private String copyright;
    @Value("${blog.copyrightFrom}")
    private String copyrightFrom;
}
