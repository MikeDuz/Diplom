package skillbox.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


import javax.persistence.*;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private boolean idModerator;
    @Column(nullable = false)
    private Date regTime;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String code;
    private String photo;

}
