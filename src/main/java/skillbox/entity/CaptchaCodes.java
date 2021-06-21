package skillbox.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "captcha_codes")
public class CaptchaCodes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private LocalDateTime time;
    @Column(nullable = false)
    private byte code;
    @Column(nullable = false)
    private byte secretCode;
}
