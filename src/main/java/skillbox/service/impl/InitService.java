package skillbox.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import skillbox.dto.init.InitDto;

@Service
public class InitService {

    @Value("${blog.title}")
    private String title;
    @Value("${blog.subtitle}")
    private String subtitle;
    @Value("${blog.telegram}")
    private String telegram;
    @Value("${blog.email}")
    private String email;
    @Value("${blog.copyright}")
    private String copyright;
    @Value("${blog.copyrightFrom}")
    private String copyrightFrom;

    public InitDto getInit() {
         return new InitDto(title, subtitle, telegram, email, copyright, copyrightFrom);
    }
}
