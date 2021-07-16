package skillbox.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import skillbox.dto.init.InitDTO;

@Service
public class InitService {

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

    public InitDTO getInit() {
         return new InitDTO(title, subTitle, telegram, email, copyright, copyrightFrom);
    }
}
