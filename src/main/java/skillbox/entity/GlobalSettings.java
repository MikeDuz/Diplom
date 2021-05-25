package skillbox.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "global_settings")
public class GlobalSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition="enum")
    private GlobalSettingCode  code;
    @Column(nullable = false)
    private String  name;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition="enum")
    private GlobalSettingCodeValue value;
}
