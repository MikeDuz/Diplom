package skillbox.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import skillbox.entity.enums.ModerationStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private boolean isActive;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition="enum")
    private ModerationStatus moderationStatus;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "moderator_id", referencedColumnName = "id")
    private User moderatorId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userId;
    @NotNull
    private LocalDateTime time;
    @NotNull
    private String title;
    @NotNull
    @Type(type = "text")
    private String text;
    @Column(nullable = false, name = "view_count")
    private int viewCount;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tag2post",
            joinColumns = @JoinColumn(name = "post_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<PostComment> comments = new HashSet<>();

    public void addComment(PostComment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(PostComment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }

    public void addTags(Tag tag) {
        tags.add(tag);
    }

    public static boolean postPublic(Post post) {
        if (post.isActive() &&
                post.getModerationStatus().equals(ModerationStatus.ACCEPTED) &&
                post.getTime().isBefore(LocalDateTime.now())) {
            return true;
        }
        return false;
    }
}
