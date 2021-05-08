package skillbox.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "posts_votes")
public class PostVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private int userId;
    @Column(nullable = false)
    private int postId;
    @Column(nullable = false)
    private Date time;
    @Column(nullable = false)
    private int value;
}
