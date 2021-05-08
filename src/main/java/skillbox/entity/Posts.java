package skillbox.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
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
    @Column(nullable = false, columnDefinition="enum('NEW','ACCEPTED','DECLINED')")
    private ModerationStatus moderationStatus;
    private int moderatorId;
    @Column(nullable = false)
    private int userId;
    @Column(nullable = false)
    private Date time;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private int viewCount;

}
