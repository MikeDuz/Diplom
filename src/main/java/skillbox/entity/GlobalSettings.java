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
    @Column(nullable = false, columnDefinition="enum('MULTIUSER_MODE','POST_PREMODERATION','STATISTICS_IN_PUBLIC')")
    private GlobalSettingCode  code;
    @Column(nullable = false)
    private String  name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition="enum('YES','NO')")
    private GlobalSettingCodeValue value;
}
