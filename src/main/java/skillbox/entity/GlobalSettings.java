package skillbox.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "global_settings")
public class GlobalSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GlobalSettingCode  code;
    @Column(nullable = false)
    private String  name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GlobalSettingCodeValue value;
}
