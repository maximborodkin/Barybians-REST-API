package ru.maxim.barybians.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
public class Post extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "time")
    private Date time;

    @Column(name = "edited")
    private int edited;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "likedposts",
            joinColumns = {@JoinColumn(name = "post_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private List<User> likes;
}
