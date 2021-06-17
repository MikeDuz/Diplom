package skillbox.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "posts_votes")
public class PostVotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private Users userId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @NotNull
    private Posts postId;
    @NotNull
    private LocalDateTime time;
    @NotNull
    private int value;
}
