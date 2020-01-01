package ru.maxim.barybians.api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comments")
@Data
public class Comment extends BaseEntity implements Comparable<Comment> {

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Column(name = "text")
    private String text;

    @Column(name = "time")
    private Date time;

    @Override
    public int compareTo(Comment other) {
        return Long.compare(time.getTime(), other.getTime().getTime());
    }
}
