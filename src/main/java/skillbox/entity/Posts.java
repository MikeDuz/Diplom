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
@Table(name = "posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private boolean isActive;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition="enum")
    private ModerationStatus moderationStatus;
    @ManyToOne
    private Users moderatorId;
    @ManyToOne
    private Users userId;
    @NotNull
    private Date time;
    @NotNull
    private String title;
    @NotNull
    private String text;
    @NotNull
    private int viewCount;

}
