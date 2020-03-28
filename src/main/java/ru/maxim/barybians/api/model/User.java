package ru.maxim.barybians.api.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User extends BaseEntity {

    @Column(name = "login")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String firstName;

    @Column(name = "surname")
    private String lastName;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "photo")
    private String photoUrl;

    @Column(name = "sex")
    private int sex;

    @Column(name = "status")
    private String status;

    @Column(name = "last_visit")
    private Date lastVisit;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @ManyToMany(mappedBy = "likes", fetch = FetchType.LAZY)
    private List<Post> likes;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    private List<Message> sendedMessages;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @Override
    public String toString(){
        return getId()+": "+getFirstName()+" "+getLastName();
    }

    public String concatToSearchString(){
        return username.toLowerCase() + firstName.toLowerCase() + lastName.toLowerCase();
    }
}