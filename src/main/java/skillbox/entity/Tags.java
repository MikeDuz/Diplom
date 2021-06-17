package skillbox.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, unique = true)
    private String name;
}
