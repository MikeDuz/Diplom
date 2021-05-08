package skillbox.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "captcha_codes")
public class CaptchaCodes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private Date time;
    @Column(nullable = false)
    private byte code;
    @Column(nullable = false)
    private byte secretCode;
}
