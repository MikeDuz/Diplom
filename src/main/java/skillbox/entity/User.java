package skillbox.entity;

import lombok.*;
import skillbox.model.Role;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private boolean isModerator;
    @NotNull
    private LocalDateTime regTime;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String code;
    private String photo;

    public Role getRole() {
        return isModerator ? Role.MODERATOR : Role.USER;
    }

}
