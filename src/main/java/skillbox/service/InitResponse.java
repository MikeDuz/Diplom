package skillbox.service;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import skillbox.dto.init.InitDTO;

@Getter
@Setter
@Service
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

    public InitDTO.Response.Create getInit() {
        val response = new InitDTO.Response.Create(title, subTitle, telegram, email, copyright, copyrightFrom);
        return response;
    }
}
