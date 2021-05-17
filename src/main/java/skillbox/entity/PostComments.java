package skillbox.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "posts_comments")
public class PostComments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private PostComments parentId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Posts postId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Users userId;
    @Column(nullable = false)
    private Date time;
    @Column(nullable = false)
    private String text;
}
