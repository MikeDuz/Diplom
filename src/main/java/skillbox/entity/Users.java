package skillbox.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private boolean idModerator;
    @NotNull
    private Date regTime;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private String code;
    private String photo;

}
